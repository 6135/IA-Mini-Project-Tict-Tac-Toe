/**
 * ILayout
 */
package app;

import java.util.List;

public interface Ilayout{
    /**
     * @return the children of the reciever
     */
    List<Ilayout> children();
    
    /**
     * @return the board of the Ilayout
     */
    String toString();
    public Object clone();
    public Ilayout move(int pos) throws IndexOutOfBoundsException,IllegalStateException;
    public Ilayout playout();
    public char status();
    public void resultMessage();
    public void setAgent(Agent agent);
    public Agent getAgent();
    public boolean terminal();
    int getDim();
}

