package app;

import java.util.Scanner;

public class Main {
    static char board[][] = new char[3][3];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        board[0][0] = 'i';
        /*System.out.println("Welcome to Tic-Tac-Toe. Choose the game mode:\n1.Player vs CPU\n2.CPU vs CPU");
        
        int gm = sc.nextInt();*/
        /*System.out.println("\'"+board[0][0]+"\'");
        if(board[1][0] != '\0')
            System.out.println("\'"+board[1][0]+"\'");*/

        
        Player cpu1 = new Player("cpu1");
        Player cpu2 = new Player("cpu2");

        cpu1.setSymbol('X');
        cpu2.setSymbol('O');

       Board b = new Board();
       
       b.children(cpu1);

        sc.close();

    }

}

