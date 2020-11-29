package app;

import java.util.List;
import java.util.Stack;

public class HP implements Heuristics {
    /**
     * The function defined a set of 6 rules that govern the movement of the blocks in the "Block world" problem, these are the following rules: 
     * <p> Rule 1: </p>
     * 
     * We do not move the node if:
     * <ul>
     * <li>It's position in current is same as in goal AND</li>
     * <li>The node below it is the correct one AND</li>
     * <li>The one below it does NOT need to be moved</li>
     * </ul>
     *
     * Rule 2:<br>
     * We do move the node twice if:
     * <ul>
     * <li>It's position in current is same as in goal AND</li>
     * <li>The node below it is the correct one AND</li>
     * <li>The node below it needs to be moved</li>
     * </ul>                      
     * Rule 3:
     * We do move the node twice if:
     * <ul>
     * <li>It's position in current is same as in goal AND</li>
     * <li>The node below it is the wrong one AND</li>
     * <li>The node below it needs to be moved twice</li>
     * </ul>
     * Rule 4:
     * We do move the node twice if:
     * <ul>
     * <li>It has anywhere below it a node that moves AND</li>
     * <li>The nodes below it is the same nodes that should be below it in goal </li>
     * </ul>
     * Rule 5:
     * We do move the node twice if:
     * <ul>
     * <li>The node below it is the wrong one AND</li>
     * <li>It's not at the start of a stack (index = 0) in either goal or current boards AND</li>
     * <li>It's above nodes that do not needs to be moved </li>
     * </ul>
     * Rule 6:
     * <li>The simplest of the 6 rules, if it is above the wrong block, moves once.</li>
     * </ul>
     * We believe these rules achieve near perfect results for most if not all board configurations.
     * @param current
     * @param goal
     * @return Returns the Heuristic cost for the current board against the goal board
     */
    @Override
    public double getH(Ilayout current, Ilayout goal) {
        List<Stack<Node>> cStacks = ((Board) current).stacks;
        List<Stack<Node>> gStacks = ((Board) goal).stacks;
        int h = 0;
        for(Stack<Node> cS : cStacks){
            if(!gStacks.contains(cS)) {
                for(int cPos = 0; cPos < cS.size(); cPos++){
                    boolean hasRuled = false;
                    Node n = cS.get(cPos);
                    boolean p = position(cPos, n, gStacks);
                    boolean cB;

                    if(cPos>0)  cB = correctBelow(n, gStacks,cS.get(cPos-1));
                    else cB=p;
                    if(p){
                        if(cB){
                            if((cPos==0 || cS.get(cPos-1).moves()==0)){
                                n.setMoves(0);
                                hasRuled = true;
                                //System.out.println("R1");
                            }                        
                            else if( !hasRuled &&  cS.get(cPos-1).moves()>0){
                                n.setMoves(2);
                                h+=2;
                                hasRuled = true;
                                //System.out.println("R2");
                            }
                        } else     
                            if(!hasRuled &&  nMoves(cPos,2,cS)) {
                                n.setMoves(2);
                                h+=2;
                                hasRuled = true;
                                //System.out.println("R3");
                            }     
                    } else {
                        if(!cB && ( (cPos!=0 && !position(0,n , gStacks)) && 
                          ( cPos > 0 && cS.get(cPos-1).moves()==0) ) ) {
                            n.setMoves(2);
                            h+=2;
                            hasRuled = true;
                            //System.out.println("R5");
                            
                        }
                    } 
                    if(!hasRuled) {              
                        if((cPos>0) && nMoves(cPos, cS) && belowInGoal(n,cPos,cS, gStacks)) {
                            n.setMoves(2);
                            h+=2;
                            hasRuled = true;
                            //System.out.println("R4");
                        } else
                        if(!cB){
                            n.setMoves(1);
                            h++;
                            hasRuled = true;
                            //System.out.println("R6");
                        }
                    }
                    //System.out.println(n + " " + n.moves());
                }
                
            }
        }

        return h;
    }
    /**
     * 
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
     * 
     * @param n Node to find
     * @param g Stack to search on
     * @return The position of said node in said stack, if it exists.
     */
    private int positionStack(Node n, Stack<Node> g){
        return g.search(n);  
    }
    /**
     * 
     * @param size Up untill which point to check (so we only check below our node)
     * @param n The ammount of moves a node needs to have to meet rule conditions
     * @param c The stack where to search
     * @return True if there's a node whose moves matches *n in *c false otherwise
     */
    private boolean nMoves(int size, int n, Stack<Node> c){
        for (int i = size-1; i >-1; i--) {
            if(c.get(i).moves()==n) return true;
        }
        return false;
    }
    /**
     * 
     * @param size Up untill which point to check (so we only check below our node)
     * @param c The stack where to search
     * @return True if there's a node that moves at least once in *c false otherwise
     */
    private boolean nMoves(int size,Stack<Node> c){
        for (int i = size-1; i >-1; i--) {
            if(c.get(i).moves()>0) return true;
        }
        return false;
    }
    /**
     * 
     * @param n Our current node
     * @param nPos The posisiont it's in
     * @param selfStack The stack it's in
     * @param goal The list of stacks of goal
     * @return Returns true if a node below *n in *selStack is also below *n in *goal false otherwise
     */
    private boolean belowInGoal(Node n,int nPos ,Stack<Node> selfStack,List<Stack<Node>> goal) {
        for(int i = nPos-1; i >=0; i--){
            Node belowNode = selfStack.get(i);
            for (Stack<Node> stack : goal) {
                int indexOf = stack.indexOf(belowNode);
                if(indexOf!=-1 && stack.indexOf(n)>indexOf)
                    return true;
            } 
        } return false;

    }

}
