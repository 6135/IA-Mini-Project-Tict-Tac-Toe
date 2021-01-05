package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class State{
    private static final double c = 0.9;
    private State parent;
    private Ilayout layout;
    private List<State> childArray;
    private double winScore;
    private int visitCount;
    private Random rand = new Random();
    
    public State(Ilayout layout, State parent) {
        this.layout = layout;
        this.parent = parent;
        childArray = new ArrayList<>();
    }


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


    private static Comparator<State> cmpUCB = new Comparator<>() {
        public int compare(State o1, State o2) {
            return (int) Math.signum(o1.ucbCalc() - o2.ucbCalc());
        }
    }; 

    /**
     * 
     * @param root the starting State
     * @return the child with the best UCB
     */
    public static State bestChildUCB(State root){
        return Collections.max(root.getChildArray(), cmpUCB );
    }
    private static Comparator<State> cmpMaxScore = new Comparator<>() {
        public int compare(State o1, State o2) {
            return (int) Math.signum((o1.winScore/o1.visitCount) - (double) (o2.winScore/o2.visitCount));
        }
    };

    /**
     * 
     * @param root the starting State
     * @return the child with the best probability of winning
     */
    public static State bestChildScore(State root) {
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