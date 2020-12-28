package app;

import java.util.Scanner;

public class Main {

    // public static void main(String[] args) {
    //     Scanner sc = new Scanner(System.in);
    //     Player p1 = new Player("Me",'X');
    //     Player p2 = new Player("Me 2",'O');

    //     p1.setOpponent(p2);
    //     p2.setOpponent(p1);

    //     Board b = new Board(p1);
    //     //Player playing = p1;
    //     int itr = 0;
    //     int player1 =0,player2 =0,draw =0;
    //     while(itr < 1){
    //         b=new Board(p1);
    //         while(!b.terminal()){
    //             // b = b.randomMove();
    //             boolean done = false;
    //             while(!done){
    //                 try {
    //                     b = b.move(sc.nextInt());
    //                     done = true;
    //                 } catch (Exception e) {
    //                    System.out.println(e.toString());
    //                 }
    //             }
    
    //             System.out.println(b.toString());
    //         }
    //         if(b.status() == p1.getSymbol())
    //             player1++;
    //         else if(b.status() == p2.getSymbol())
    //             player2++;
    //         else if(b.status() == 'f')
    //             draw++;
    //         itr++;
    //         System.out.println("\'"+b.status()+"\'");
    //     }
    //     System.out.println("---------");

    //     System.out.println("Player1: "+player1);
    //     System.out.println("Player2: "+player2);
    //     System.out.println("Draw: "+draw);
    //     sc.close();
    // }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agent p1 = new Player("Player",'X');
        Agent cpu1 = new Player("CPU",'O');     
        
        p1.setOpponent(cpu1);
        cpu1.setOpponent(p1);

        Board b = new Board(p1);
        MCTS mcts = new MCTS(cpu1);
        Agent agent = p1;
        while(!b.terminal()){
            b = agent.move(b);
            System.out.println(b);
            agent =
        }

        sc.close();
    }

}

