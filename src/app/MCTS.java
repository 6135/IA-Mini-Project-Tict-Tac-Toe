package app;

/**
 * MCTS
 */
public class MCTS implements Agent{
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
     * For each iteration 
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
        
        return State.bestChildScore(root).getLayout();
    }
    /**
     * This function selectes the best leaf node according to the UCB
     * @param root the starting State
     * @return the the best leaf
     */
    private State mctsStateSelection(State root){
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            selected = State.bestChildUCB(selected);
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