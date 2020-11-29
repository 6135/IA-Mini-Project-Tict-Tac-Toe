package app;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        Algorithm s = new IDA(); Heuristics h = new HP(); Board start = new Board(sc.nextLine());
        Board goal = new Board(sc.nextLine());
        double b = s.solve(start,goal, h);
        
        if(b==-1) System.out.println("no solution was found");
        else System.out.println((int) h.getH(start, goal));

        sc.close();

    }

}

