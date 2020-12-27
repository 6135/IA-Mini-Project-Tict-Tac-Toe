package app;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Ilayout layout;
    private State parent;

    private Player player;

    private int visitCount;
    private double winScore;
    private double treePolicy = -1;
    private List<State> childArray;

    public State(Ilayout s0, Player player, int visitCount, double winScore,State parent){
        this.layout = s0;
        this.player = player;
        this.visitCount = visitCount;
        this.winScore = winScore;
        this.parent=parent;
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
    public double treePolicy(){return treePolicy;}

    public void setLayout(Ilayout layout) { this.layout = new Board((Board) layout); }
    public void setPlayer(Player player) { this.player = player; }
    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }
    public void setParent(State parent){this.parent=parent;}
    public void setChildArray(List<State> childArray){this.childArray=childArray;}
    public void setTreePolicy(double treePolicy) {this.treePolicy = treePolicy;}

    public void addWinScore(double winScore) { this.winScore += winScore; }
    public void visit(){this.visitCount++;}

    public List<State> children(Player opponent){
        List<State> children = new ArrayList<>();
        for(Ilayout l : layout.children(opponent)){
            State s = new State(l,opponent,0,0);
            children.add(s);
        }
        return children;
    }

    public void play(Heuristics h) {
        /*
        *TODO
        */
    }
}
