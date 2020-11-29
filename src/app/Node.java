package app;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private State state;
    private Node parent;
    private List<Node> childArray;

    public Node(State state, Node parent){
        this.state = state;
        this.parent = parent;
        this.childArray = null;
    }

    public Node(Node src){
        this.childArray = new ArrayList<Node>();
        this.state = new State(src.state);
        if(src.parent == null)
            this.parent = null;
        else this.parent = src.parent;
        for(Node child : src.childArray){
            this.childArray.add(new Node(child));
        }
    }
    public State state() { return state; }
    public Node parent() { return parent; }
    public List<Node> childArray() { return childArray; }
    public void setState(State state){this.state=state;}
    public void setParent(Node parent){this.parent=parent;}
    public void setChildArray(List<Node> childArray){this.childArray=childArray;}
    

}
