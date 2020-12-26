package app;

public class Player {
    private String p;
    private Player opponent;
    public Player(String player){p=player;}

    public String getPlayer(){return p;}
    public String toString(){return p;}
    public void setOpponent(Player op){this.opponent = op;}
    public Player opponent(){return opponent;}
}
