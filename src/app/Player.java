package app;

public class Player {
    private int playerID;
    private String playerName;
    private char c;
    private Player opponent;
    public Player(int player,String playerName, char symbol){
        this.playerID=player;
        this.playerName = playerName;
        this.c = symbol;
    }
        
    @Override
    public int hashCode() {
        return playerID;
    }

    @Override
    public boolean equals(Object o){ 
        if(o instanceof Player){
            Player p1 = (Player) o;
            return p1.getSymbol() == getSymbol() && p1.getPlayer() == getPlayer() && playerName.equals(p1.getPlayerName());
        }
        return false;
    }

    public void setSymbol(char c){this.c = c;}
    public void setOpponent(Player op){this.opponent = op;}
    public void setPlayerName(String name){this.playerName = name;
    }
    public char getSymbol(){return c;}
    public int getPlayer(){return playerID;}
    public String getPlayerName(){return playerName;}

    public String toString(){return Integer.toString(playerID);}

    public Player opponent(){return opponent;}
}
