package app;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Board b = new Board();
        MCTS mcts = new MCTS();
        
        while(!b.isGameOver()){
            if(b.getTurn() == Board.State.X)
                b.move(sc.nextInt());
            else {b.move(mcts.move(b));
            System.out.println();}
            System.out.println(b.toString());
        }
        

        // b.move(sc.nextInt());
        // System.out.println(b.toString());
        // b = mcts.move(b);
        // System.out.println(b.toString());
        // b.move(sc.nextInt());
        // System.out.println(b.toString());
        // b = mcts.move(b);
        // System.out.println(b.toString());
        // b.move(sc.nextInt());
        // System.out.println(b.toString());
        // b = mcts.move(b);
        // System.out.println(b.toString());
        sc.close();
    }

}

