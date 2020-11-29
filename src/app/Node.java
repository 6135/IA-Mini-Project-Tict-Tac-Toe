package app;

public class Node implements Cloneable{
    private int heuristicMoves;
    private char symbol;
    
    /**
     * Build a node object
     * @param c The character that must be saved in Node
     */
    public Node(char c){
        symbol = c;
        heuristicMoves = -1;
 
    }
    /**
     * A copy constructor
     * @param source 
     */
    public Node(Node source){
        heuristicMoves = source.moves();
        symbol = source.charc();
    }
    /**
     * 
     * @param hm Number of times the node must move in order to reach goal state, acording to whatever heuristic is being used
     */
    public void setMoves(int hm){
        heuristicMoves = hm;
    }
    /**
     * 
     * @return Returns the number of movements needed for heuristic HP
     */
    public int moves() {
        return heuristicMoves;
    }
    /**
     * 
     * @return Return the symbol assigned to the Node 
     */
    public char charc(){
        return symbol;
    }
    /**
     * @return Returns Node hashcode same as ASCII code of the symbol
     */
    @Override
    public int hashCode(){
        return symbol;
    }

    /**
     * @return Returns the comparison between 2 Nodes
     */
    @Override
    public boolean equals(Object o){
        return o instanceof Node && symbol == ((Node) o).charc();
    }

    /**
     * @return Returns a new Node with the same heuristicMoves and symbol of the current Node
     */
    @Override
    public Object clone(){
        Node n;
        try {
            n = (Node) super.clone();
            n.heuristicMoves = heuristicMoves;
            n.symbol = symbol;
            return n;
        } catch (Exception e){
            return new Node(this);
        }
    }

    /**
     * @return Returns the symbol value as String
     */
    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
    
}
