package app;

public class Player {
    private String p;
    private Character c;
    private Player opponent;
    public Player(String player, char c){
        p=player;
        this.c = c;
    }

    public void setSymbol(char c){this.c = c;}
    public char getSymbol(){return c;}
    public String getPlayer(){return p;}
    public String toString(){return p;}
    public void setOpponent(Player op){this.opponent = op;}
    public Player opponent(){return opponent;}
}
