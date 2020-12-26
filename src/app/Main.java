package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Welcome to Tic-Tac-Toe. Choose the game mode:\n1.Player vs CPU\n2.CPU vs CPU");
        
        int gm = sc.nextInt();
        Player cpu1 = new Player("cpu1");
        Player cpu2 = new Player("cpu2");

        
        
        Board b= new Board(sc.nextLine());
       
       

        sc.close();

    }

}

