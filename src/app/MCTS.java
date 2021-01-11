package app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;



/**
 * MCTS
 */
public class MCTS{

    public class State{
        private static final double c = 0.9;
        private State parent;
        private Board layout;
        private List<State> childArray;
        private double winScore;
        private int visitCount;
        private int newMovePos;

        public State(Board layout, State parent) {
            this.layout = layout;
            this.parent = parent;
            childArray = new ArrayList<>();
        }
    
        public State(Board layout, State parent, int newMovePos) {
            this.layout = layout;
            this.parent = parent;
            this.newMovePos = newMovePos;
            childArray = new ArrayList<>();
        }

        public State(){}

        public void setParent(State parent){this.parent = parent;}
        public void setBoard(Board layout){this.layout = layout;}
        public void setChildArray(List<State> childArray){this.childArray = childArray;}
        public void setWinCount(int winScore){this.winScore = winScore;}
        public void setVisitCount(int visitCount){this.visitCount = visitCount;}
    
        public State getParent(){return parent;}
        public Board getLayout(){return layout;}
        public List<State> getChildArray(){return childArray;}
        public double getWinScore(){return winScore;}
        public int getVisitCount(){return visitCount;}
    
    
        private List<State> returnIfStates(List<State> children){
            List<State> winChild = new ArrayList<>();
            for (State state : children) {
                if(state.layout.isGameOver() && state.layout.getWinner() == nextTurn() ) // TODO: Not completely right ?
                    winChild.add(state);
            }
            return winChild.isEmpty() ? children : winChild;
        }

        public List<Board> childrenAsBoards(){
            List<Board> children = new ArrayList<>();
            for (State s : childArray) {
                children.add(s.layout);
            }
            return children;
        }
    
        public List<State> statifyChildren(){
            List<State> states = new ArrayList<>();
            if(childArray.isEmpty()){
                for (Integer it : layout.getAvailableMoves()) {
                    Board copy = layout.getDeepCopy();
                    copy.move(it);
                    states.add(new State(copy,this,it));
                }
                states = returnIfStates(states);
                setChildArray(states);
                return states;
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
            return Double.toString(ucbCalc()) + " " + visitCount + " " + winScore + " " + ((Board)layout).toString();
        }
    
        @Override
        public boolean equals(Object s){
            if(s instanceof State){
                return layout.equals(((State)s).layout);
            }
            return false;
        }

        public Board.State agentThatMoved(){
            return (layout.getTurn() == Board.State.X) ? Board.State.O : Board.State.X;
        }

        public Board.State nextTurn(){
            return layout.getTurn();
        }        
    }
    private Board.State botSymbol;
    private int iter = 1000;

    /**
     * For each iteration the four steps(Selection,Expansion,Simulation and BackPropagation) will be executed.
     * The Simulation and BackPropagation will occur for each child of selected State
     * @param b last played Board
     * @return the best move against last Played Board
     */
    public int move(Board b){
        botSymbol = b.getTurn();
        State root = new State(b, null);
        for(int iteration = 0; iteration < iter;iteration++){   
            /* Phase 1 - Selection */
            State selected = mctsStateSelection(root);
            /* Phase 2 - Expansion */
            if(!selected.getLayout().isGameOver())
                mctsStateExpansion(selected);
            /* Phase 3 - Simulation */
            for(State s : selected.getChildArray()){
                Board.State result = mctsStateSimulate(s);
                /* Phase 4 - BackPropagation */          
                mctsBackPropagation(s, result);
            }
        }
        // System.out.println(root.visitCount);
        return (new State()).bestChildScore(root).newMovePos;
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
        if(!selected.getLayout().isGameOver())
            selected.statifyChildren();
        
    }

    /**
     * This function back propagates until it reachs the root.
     * For each node of up the tree,it updates the values of visits and win score.
     * If the simulation results in a win for the selected State's Agent the win score of every node up the tree with the same Agent will increment by 1.
     * if the simulation results in a draw the win score of every node up the tree will increment by 0.5.
     * @param selected the State from wich the backpropagation starts
     * @param result the status result of the simulation of the selected Board
     */
    private void mctsBackPropagation(State selected, Board.State result){
        State temp = selected;
        while (temp != null) {
            temp.visit();
            if (temp.agentThatMoved() == result)
                temp.addWinScore(1.0);
            else if(result == Board.State.Blank)
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
    private Board.State mctsStateSimulate(State selected){
        Board temp = selected.getLayout().getDeepCopy();
        if(temp.isGameOver() && temp.getWinner() == opponent(botSymbol)){
            selected.getParent().setWinCount(Integer.MIN_VALUE);
            return temp.getWinner();
        }
        
        while(!temp.isGameOver())
            temp = playout(temp);
            
        return temp.getWinner(); // Board.State.Blank if draw
    }
    private Random rand = new Random();
    private int dim = Board.BOARD_WIDTH;
	/**
	 * @return a randomly moved Board 
	 */
	private Board lightPlayout(Board b){
		Board copy = b.getDeepCopy();
        int i = rand.nextInt(dim*dim);
        // System.out.println(copy.getAvailableMoves());
        // System.out.println(copy.isGameOver());
        while(!copy.getAvailableMoves().contains(i)){
            i = rand.nextInt(dim*dim);}
        copy.move(i);
        return copy;
	}
	private int uncloseHoles(Board.State a, Board b){
        int h=0;
        Board.State[][] board = b.toArray();
		for (int i = 0; i < dim; i++) {
			if(closeCol(i,a,board)==dim-1 && closeCol(i,Board.State.Blank,board)==1) h++;
			if(closeRow(i,a,board)==dim-1 && closeRow(i,Board.State.Blank,board)==1) h++;
		}
		if(closeLRD(a,board)== dim-1 && closeLRD(Board.State.Blank,board)==1) h++;
		if(closeRLD(a,board)== dim-1 && closeRLD(Board.State.Blank,board)==1) h++;
		
		return h;
    }
 
	private int closeCol(int col,Board.State c,Board.State[][] board){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[i][col]==c) n++;
		}
		return n;
    }
    
	private int closeRow(int row,Board.State c,Board.State[][] board){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[row][i]==c) n++;
		}
		return n;
    }
    
	public int closeLRD(Board.State c,Board.State[][] board){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][i]==c) n++;
		return n;
    }
    
	private int closeRLD(Board.State c,Board.State[][] board){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][(board.length-1)-i] == c) n++;
		return n;
	}   

	private Board heavyPlayout(Board b){
		Board copy = b.getDeepCopy();
		int cH=uncloseHoles(opponent(copy.getTurn()),copy);
		
		Board child = null;
        List<Board> children = (new State(copy,null)).childrenAsBoards();
        
		for (Board c : children) {	
			if( c.isGameOver() && c.getWinner()==copy.getTurn() ) return c;
			if( cH > uncloseHoles(c.getTurn(),c)) child=c;
		}
		if(child!=null) return child;
			

		return lightPlayout(b);
	}

	public Board playout(Board b){
		return heavyPlayout(b);
    }
    
    private Board.State opponent(Board.State s){
        return s == Board.State.X ? Board.State.O : Board.State.X;
    }

}