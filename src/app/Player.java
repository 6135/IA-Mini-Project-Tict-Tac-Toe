package app;

import java.util.Scanner;

public class Player implements Agent{
    private String playerName;
    private char c;
    private Agent opponent;
    public Player(String playerName, char symbol){
        this.playerName = playerName;
        this.c = symbol;
    }
        
    @Override
    public int hashCode() {
        return c;
    }

    @Override
    public boolean equals(Object o){ 
        if(o instanceof Player){
            Player p1 = (Player) o;
            return p1.getSymbol() == getSymbol() && playerName.equals(p1.getName());
        }
        return false;
    }
    @Override
    public void setSymbol(char c){this.c = c;}
    @Override
    public void setOpponent(Agent op){this.opponent = op;}
    @Override
    public void setName(String name){this.playerName = name;}
    @Override
    public char getSymbol(){return c;}
    @Override
    public String getName(){return playerName;}
    @Override
    public String toString(){return playerName ;}
    
    public Agent opponent(){return opponent;}

    @Override
    public Board move(Board b) {
        Scanner sc = new Scanner(System.in);
        b = b.move(sc.nextInt());
        sc.close();
        return b;
    }



}
