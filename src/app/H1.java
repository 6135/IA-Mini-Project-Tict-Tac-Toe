package app;
import java.util.List;
import java.util.Stack;


public final class H1 implements Heuristics {
    
    @Override
    /**
     * The function verify which blocks are not in the correct position, that is, the blocks with
     * diferent index on goal board and current board.
     * @param currenthave 
     * @param goal
     * @return Returns the Heuristic cost for the current board against the goal board 
     */
    public double getH(Ilayout current, Ilayout goal){
        List<Stack<Node>> cSts = ((Board) current).stacks;
        List<Stack<Node>> gSts = ((Board) goal).stacks;
        int wPlace=0;
        for (int i = 0; i < cSts.size(); i++)
            for (int j = 0; j < cSts.get(i).size(); j++)             
                if(!position(j,cSts.get(i).get(j),gSts)) wPlace++;
        return wPlace;
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
}