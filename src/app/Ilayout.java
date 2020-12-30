/**
 * ILayout
 */
package app;

import java.util.List;

public interface Ilayout {
    /**
     * @return the children of the reciever
     */
    List<Ilayout> children();
    /**
     * @return true if the receiver equals the argument 'I'; return false otherwise
     */
    boolean isGoal(Ilayout i);
    /**
    *@return the cost for moving from the input config to the receiver.
    */
    double getG();
    /**
     * @return the board of the Ilayout
     */
    String toString();
    public Board move(int pos) throws IndexOutOfBoundsException,IllegalStateException;
    public Board randomMove();
    public char status();
    public void resultMessage();
    public void setAgent(Agent agent);
    public Agent getAgent();
    public boolean terminal();
    int getDim();
}

