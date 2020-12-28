package app;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

/**
 * State
 */
public class State implements Comparable<State> {
    private State parent;
    private Ilayout layout;
    private List<State> childArray;
    private double winScore;
    private int visitCount;

    public State(Ilayout layout, State parent) {
        this.layout = layout;
        this.parent = parent;
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


    public List<State> makeChildren(){
        if(childArray.isEmpty()){
            List<State> children = new ArrayList<>();
            for(Ilayout l : layout.children()){
                children.add(new State(l,this));
            }
            setChildArray(children);
            return children;
        } else return getChildArray();

    }
    public void visit(){this.visitCount++;}
    public void addWinScore(double add){this.winScore+=add;}

    public double ucbCalc(){
        int parentVisits = parent.getVisitCount();
        double expr1 = this.winScore / this.visitCount; 
        double expr2 = Math.sqrt(2) * (Math.sqrt((Math.log(parentVisits) / this.visitCount)));
        return expr1+expr2;
    }

    @Override
    public int compareTo(State o) {
        return (int) Math.signum(ucbCalc() - o.ucbCalc());
    }

    public static State bestChildUCB(State root){
        if(root.getChildArray().isEmpty())
            return null;
        else return Collections.max(root.getChildArray());
    }
    public static State bestEnemyChildUCB(State root){
        if(root.getChildArray().isEmpty())
            return null;
        else return Collections.min(root.getChildArray());
    }

}