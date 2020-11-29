package app;

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


}
