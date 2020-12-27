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
       char status = b.status();
       Player p = cpu1;
       System.out.println(b.toString());
       while( status != 'v' && status != 'f' ) {
           b=b.randMove(p);
           p=p.opponent();
           System.out.println(b.toString());
           status = b.status();
           System.out.println(status);
       }
       
        sc.close();

    }

}

