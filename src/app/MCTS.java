package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * MCTS
 */
public class MCTS implements Agent{

    public class State{
        private static final double c = 0.9;
        private State parent;
        private Ilayout layout;
        private List<State> childArray;
        private double winScore;
        private int visitCount;
        
        public State(Ilayout layout, State parent) {
            this.layout = layout;
            this.parent = parent;
            childArray = new ArrayList<>();
        }
    
        public State(){}

        public void setParent(State parent){this.parent = parent;}
        public void setBoard(Ilayout layout){this.layout = layout;}
        public void setChildArray(List<State> childArray){this.childArray = childArray;}
        public void setWinCount(int winScore){this.winScore = winScore;}
        public void setVisitCount(int visitCount){this.visitCount = visitCount;}
    
        public State getParent(){return parent;}
        public Ilayout getLayout(){return layout;}
        public List<State> getChildArray(){return childArray;}
        public double getWinScore(){return winScore;}
        public int getVisitCount(){return visitCount;}
    
    
        /**
         * Taking the root as an example, if one of the root's children is a win, this means that it's a good choice, even the best choice.
         * Thus this fuction, if there is any such children, returns only those children as there is no point in running statistics for any other non-win boards.
         * This function is true for any board, making it usable for any class that implements Ilayout properly. 
         * (less blind searching)
         * @param children The array of children to check from
         * @return The list of children that matters, or the original if none is win.
         */
        private List<Ilayout> returnIf(List<Ilayout> children){
            List<Ilayout> winChild = new ArrayList<>();
            for (Ilayout ilayout : children) {
                if(ilayout.status() == agentHasNextMove().getSymbol()){
                    winChild.add(ilayout);
                }
            }
            return winChild.isEmpty() ? children : winChild;
        }
        /**
         * 
         * @return The State's children 
         */
        public List<State> makeChildren(){
            if(childArray.isEmpty()){
                List<State> children = new ArrayList<>();
                for(Ilayout l : returnIf(layout.children())){
                    children.add(new State(l,this));
                }
                setChildArray(children);
                return children;
            } else return getChildArray();
        }
    
        public void visit(){this.visitCount++;}
        public void addWinScore(double add){this.winScore+=add;}
    
        /**
         * 
         * @return the ucb(Upper Confidence Bound) of the State
         */
        public double ucbCalc(){
            if(visitCount == 0)
                return Double.MAX_VALUE;
            int parentVisits = parent.getVisitCount();
            return (this.winScore / this.visitCount) + (c * ( Math.sqrt( ( Math.log(parentVisits) / this.visitCount) ) ) ) ;
        }
    
    
        private final Comparator<State> cmpUCB = new Comparator<>() {
            public int compare(State o1, State o2) {
                return (int) Math.signum(o1.ucbCalc() - o2.ucbCalc());
            }
        }; 
    
        /**
         * 
         * @param root the starting State
         * @return the child with the best UCB
         */
        public State bestChildUCB(State root) {
            return Collections.max(root.getChildArray(), cmpUCB );
        }
        private final Comparator<State> cmpMaxScore = new Comparator<>() {
            public int compare(State o1, State o2) {
                return (int) Math.signum((o1.winScore/o1.visitCount) - (double) (o2.winScore/o2.visitCount));
            }
        };
    
        /**
         * 
         * @param root the starting State
         * @return the child with the best probability of winning
         */
        public State bestChildScore(State root) {
            return Collections.max(root.getChildArray(), cmpMaxScore);
        }
        
        @Override
        public String toString() {
            return Double.toString(ucbCalc()) + " " + visitCount + " " + winScore + " " + ((Board)layout).flatToString();
        }
    
        public Agent agentHasNextMove(){
            return ((Board) layout).getAgent();
        }
    
        public Agent agentThatMoved(){
            return agentHasNextMove().opponent();
        }
    
        @Override
        public boolean equals(Object s){
            if(s instanceof State){
                return layout.equals(((State)s).layout);
            }
            return false;
        }
    }

    private int iter = 250;
    private String agentName;
    private char symbol;
    private Agent opponent;

    public MCTS(char symbol){
        this.agentName = "CPU";
        this.symbol = symbol;

    }

    public MCTS(char symbol, int iter){
        this.agentName = "CPU";
        this.symbol = symbol;
        this.iter = iter;
    }

    /**
     * For each iteration the four steps(Selection,Expansion,Simulation and BackPropagation) will be executed.
     * The Simulation and BackPropagation will occur for each child of selected State
     * @param b last played Ilayout
     * @return the best move against last Played Ilayout
     */
    public Ilayout move(Ilayout b){
        State root = new State(b, null);
        if(!root.getLayout().getAgent().equals(this)){
            return null;
        }
        for(int iteration = 0; iteration < iter;iteration++){   
            /* Phase 1 - Selection */
            State selected = mctsStateSelection(root);
            /* Phase 2 - Expansion */
            if(!selected.getLayout().terminal())
                mctsStateExpansion(selected);
            /* Phase 3 - Simulation */
            for(State s : selected.getChildArray()){
                char result = mctsStateSimulate(s);
                /* Phase 4 - BackPropagation */          
                mctsBackPropagation(s, result);
            }
        }
        
        return (new State()).bestChildScore(root).getLayout();
    }
    /**
     * This function selects the best leaf node according to the UCB
     * @param root the starting State
     * @return the the best leaf
     */
    private State mctsStateSelection(State root){
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            selected = (new State()).bestChildUCB(selected);
        return selected;
    }

    /**
     * This function creates the children of the State selected
     * @param selected the State to be expanded
     */
    private void mctsStateExpansion(State selected) {
        if(!selected.getLayout().terminal())
            selected.makeChildren();
        
    }

    /**
     * This function back propagates until it reachs the root.
     * For each node of up the tree,it updates the values of visits and win score.
     * If the simulation results in a win for the selected State's Agent the win score of every node up the tree with the same Agent will increment by 1.
     * if the simulation results in a draw the win score of every node up the tree will increment by 0.5.
     * @param selected the State from wich the backpropagation starts
     * @param result the status result of the simulation of the selected Ilayout
     */
    private void mctsBackPropagation(State selected, char result){
        State temp = selected;
        while (temp != null) {
            temp.visit();
            if (temp.agentThatMoved().getSymbol() == result )
                temp.addWinScore(1.0);
            else if(result == 'f')
                temp.addWinScore(0.5);

            temp = temp.getParent();
        }
    }

    /**
     * This function simulates the game from selected State untill it reaches a terminal state.
     * If a child is terminal, and results in a loss, this means that if the parent board is chosen, the game will result in a loss if optimal plays are made
     * So if a child is terminal and lost, we need to tell the algorithm that choosing that board, or even exploring it would be costly and uncesseray as it would lead to game loss
     * @param selected the State to be simulated
     * @return the result of the simulation
     */
    private char mctsStateSimulate(State selected){
        Ilayout temp = (Ilayout) selected.getLayout().clone();
        char status = temp.status();
        if(status == opponent.getSymbol()) {
            selected.getParent().setWinCount(Integer.MIN_VALUE);
            return temp.status();
        }
        
        while(!temp.terminal())
            temp = temp.playout();
            
        return temp.status();
    }

    @Override
    public void setOpponent(Agent a) {
        this.opponent = a;
        a.singleSetOpponent(this);
    }

    @Override
    public Agent opponent() {return opponent;}

    @Override
    public char getSymbol() {return symbol;}

    @Override
    public void setSymbol(char symbol) {this.symbol = symbol;}

    @Override
    public void setName(String name) {this.agentName = name;}

    @Override
    public String getName() {return agentName;}
    @Override
    public int hashCode() {
        return getSymbol();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof MCTS){
            MCTS mcts = (MCTS) o;
            return getName() == mcts.getName() && getSymbol() == mcts.getSymbol();
        }
        return false;
    }

    @Override
    public void singleSetOpponent(Agent a) {
        this.opponent = a;

    }
}