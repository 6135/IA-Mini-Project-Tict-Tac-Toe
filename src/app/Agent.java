package app;

public interface Agent {

    Ilayout move(Ilayout b);
    void setOpponent(Agent a);
    Agent opponent();
    char getSymbol();
    void setSymbol(char symbol);
    void setName(String name);
    String getName();
}
