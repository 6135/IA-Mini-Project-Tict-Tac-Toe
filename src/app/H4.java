package app;

import java.util.List;
import java.util.Stack;

public class H4 implements Heuristics {
    /**
     * The function defined 2 rules that govern the movement of the blocks in the "Block world" problem, these are the following rules: 
     * <p> Rule 1: </p>
     * 
     * A block that must move once:
     * <ul>
     * <li>is currently on a block different to the block upon which it rests in the goal OR</li>
     * <li>that has such a block somewhere below it (in the same stack).</li>
     * </ul>
     *
     * Rule 2:<br>
     * A block that must move twice:
     * <ul>
     * <li>is currently on the block upon which it must be placed in the goal state, but that block is a block that must be moved OR</li>
     * <li>if there exists a block that must be moved twice somewhere below it (in the same stack).</li>
     * </ul>                      
     * @param current
     * @param goal
     * @return Returns the Heuristic cost for the current board against the goal board
    */
    @Override
    public double getH(Ilayout current, Ilayout goal){
        List<Stack<Node>> cSts = ((Board) current).stacks;
        List<Stack<Node>> gSts = ((Board) goal).stacks;
        int h=0;
        for (int i = 0; i < cSts.size(); i++){
            boolean mo=false, mt=false,b=true;
            for (int j = 0; j < cSts.get(i).size(); j++) {
                Node n = cSts.get(i).get(j);
                if(j==0) b = position(j, n, gSts);
                else  b = correctBelow(n,gSts,cSts.get(i).get(j-1)); 
                if(mt || (b && mo)){
                    mt=true;
                    h+=2;
                   
                }else if( mo || !b){
                    mo=true;
                    h+=1;
                }     
            } 
        }                          
        return h;
    }

    
    /**
     * This function verifies if the block has the same block below in both current and goal boards
     * @param n Our current node
     * @param g List of stacks of goal
     * @param nBeLow The node below *n in the board being calculated
     * @return True if the *nBeLow is also below *n in goal board, false otherwise
     */
    private boolean correctBelow(Node n,List<Stack<Node>> g, Node nBeLow){
        for (int i = 0; i < g.size(); i++)
            if(positionStack(n, g.get(i))==positionStack(nBeLow, g.get(i))-1) return true;
        return false;
    }

    /**
     * This function verifies if the block has the same position (index) in both current and goal boards
     * @param p The position of *n in current board
     * @param n Our current node
     * @param g List of stacks in goal board
     * @return True if *n is in same position (index) in both current and goal boards, false otherwise.
    */
    private boolean position(int p,Node n, List<Stack<Node>> g){
        for (Stack<Node> stack : g)
            if(p<stack.size() && stack.get(p).equals(n)) return true;  
        return false;
    }

    /**
     * 
     * @param n Node to find
     * @param g Stack to search on
     * @return The position of said node in said stack, if it exists.
    */
    private int positionStack(Node n, Stack<Node> g){
        return g.search(n);  
    }
    
}
