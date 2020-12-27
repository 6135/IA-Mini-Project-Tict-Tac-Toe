package app;

public class Player {
    private String p;
    private Character c;
    private Player opponent;
    public Player(String player){p=player;}

    @Override
    public boolean equals(Object o){ 
        if(o instanceof Player){
            Player p1 = (Player) o;
            return p1.getSymbol() == getSymbol() && p1.getPlayer().equals(getPlayer());
        }
        return false;
    }

    public void setSymbol(char c){this.c = c;}
    public char getSymbol(){return c;}
    public String getPlayer(){return p;}
    public String toString(){return p;}
    public void setOpponent(Player op){this.opponent = op;}
    public Player opponent(){return opponent;}
}
