package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.experimental.max.MaxCore;

public class State implements Comparable<State>{
    private static final Random rand = new Random();
    public static final int MAXVAL = Integer.MAX_VALUE;
    public static final int MINVAL = Integer.MIN_VALUE+1;
    private Ilayout layout;
    private State parent;

    private Player player;

    private int visitCount;
    private int winScore;
    private List<State> childArray;

    public State(Ilayout s0, Player player,State parent){
        this.layout = s0;
        this.player = player;
        this.visitCount = 1;
        this.winScore = 0;
        this.parent=parent;
        childArray = new ArrayList<>();
    }

    public State(State src){
        this.layout = new Board((Board) src.layout);
        this.player = src.player;
        this.visitCount = src.visitCount;
        this.winScore = src.winScore;
        this.parent=src.parent;
        this.childArray=src.childArray;
    }

    public Ilayout layout() { return layout; }
    public Player player() { return player; }
    public int visitCount() { return visitCount; }
    public double winScore() { return winScore; }
    public State parent(){return this.parent;}
    public List<State> childArray() { return childArray; }
    public double treePolicy(){return CalcUCB();}
    public State getRandomChildState(){return childArray().get(rand.nextInt(childArray.size()));}

    public void setLayout(Ilayout layout) { this.layout = new Board((Board) layout); }
    public void setPlayer(Player player) { this.player = player; }
    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }
    public void setParent(State parent){this.parent=parent;}
    public void setChildArray(List<State> childArray){this.childArray=childArray;}
    
    public void addWinScore(double winScore) { this.winScore += winScore; }
    public void addVisits(double visitCount) { this.visitCount += visitCount; }
    public void visit(){this.visitCount++;}

    public List<State> children(Player opponent){
        childArray = new ArrayList<>();
        for(Ilayout l : layout.children(opponent)){
            State s = new State(l,opponent,this);
            childArray.add(s);
        }
        return childArray;
    }

    public double CalcUCB(){
        double firstExper = winScore()/visitCount();
        double secondExper = Math.sqrt((2*(Math.log(parent().visitCount()))/visitCount()));
        double expr = firstExper + secondExper;
        return expr;
    }

    @Override
    public int compareTo(State o) {
        return (int) Math.signum(treePolicy()-o.treePolicy());
    }

    @Override
    public String toString() {
        return layout.toString() + '\n' + this.treePolicy();
    }
}
