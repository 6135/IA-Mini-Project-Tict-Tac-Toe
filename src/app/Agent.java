package app;

public interface Agent {
    public Ilayout move(Ilayout b);
    public void singleSetOpponent(Agent a);
    public void setOpponent(Agent a);
    public Agent opponent();
    public char getSymbol();
    public void setSymbol(char symbol);
    public void setName(String name);
    public String getName();
}
