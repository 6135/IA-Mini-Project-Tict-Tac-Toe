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

    

}
