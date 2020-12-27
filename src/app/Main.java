package app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /*System.out.println("Welcome to Tic-Tac-Toe. Choose the game mode:\n1.Player vs CPU\n2.CPU vs CPU");
        
        int gm = sc.nextInt();*/
        Player cpu1 = new Player("cpu1");
        Player cpu2 = new Player("cpu2");

        cpu1.setSymbol('X');
        cpu2.setSymbol('O');

        cpu1.setOpponent(cpu2);
        cpu2.setOpponent(cpu1);

        Board b = new Board();
        MCTS mcts = new MCTS();
        System.out.println(b.toString());
        Player p = cpu1;
        System.out.println(b.terminal(p));
        while( !b.terminal(p) ) {
            b = (Board) mcts.MCTsSearch(b, p).layout();
            p = p.opponent();
            System.out.println(b.toString());
            System.out.println(b.terminal(p));
        }
        char status = b.status(cpu1);
        if(status == 'v')
            System.out.println(cpu1.getPlayer() + " has won the game!");
        else if(status == 'l')
            System.out.println(cpu1.opponent().getPlayer() + " has won the game!");
        else if(status == 'f')
            System.out.println("Draw!");
        sc.close();

    }

}

