package app;

import java.util.ArrayList;
import java.util.List;

public class State {
    private Ilayout layout;
    private Player player;
    private int visitCount;
    private double winScore;
    
    public State(Ilayout layout, Player player, int visitCount, double winScore){
        this.layout = layout;
        this.player = player;
        this.visitCount = visitCount;
        this.winScore = winScore;
    }

    public State(State src){
        this.layout = new Board((Board) src.layout);
        this.player = src.player;
        this.visitCount = src.visitCount;
        this.winScore = src.winScore
    }

    public Ilayout layout() { return layout; }
    public Player player() { return player; }
    public int visitCount() { return visitCount; }
    public double winScore() { return winScore; }

    public void setLayout(Ilayout layout) { this.layout = new Board((Board) layout); }
    public void setPlayer(Player player) { this.player = player; }
    public void setVisitCount(int visitCount) { this.visitCount = visitCount; }
    public void setWinScore(double winScore) { this.winScore = winScore; }
    
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
