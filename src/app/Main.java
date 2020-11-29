package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Board start = new Board(sc.nextLine());
        Board goal = new Board(sc.nextLine());
        //double b = s.solve(start,goal, h);
        
        if(false) System.out.println("no solution was found");
        else System.out.println();

        sc.close();

    }

}

