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
        Agent p1 = new MCTS('X');
        Agent cpu1 = new MCTS('O');     

        p1.setOpponent(cpu1);
        cpu1.setOpponent(p1);

        Board b;
        Agent agent = p1;
        int itr = 0;
        int player1 =0,player2 =0,draw =0;
        while(itr < 50){
            System.out.println(itr);
            b = new Board(agent);
            while(!b.terminal()){
                //System.out.println("agent move:");
                boolean doneMoving = false;
                while(!doneMoving){
                    try {
                        b = agent.move(b);
                        doneMoving = true;
                    } catch (Exception e){
                        System.out.println(e.toString());
                    }
                }
                System.out.println(b);
                agent = agent.opponent();
                //System.out.println("agent switch");
            }
            if(b.status() == p1.getSymbol())
                player1++;
            else if(b.status() == cpu1.getSymbol())
                player2++;
            else if(b.status() == 'f')
                draw++;
            itr++;
            System.out.println("\'"+b.status()+"\'");
        }
        System.out.println("---------");

        System.out.println("Player1: "+player1);
        System.out.println("Player2: "+player2);
        System.out.println("Draw: "+draw);
        sc.close();

        // b.resultMessage();
        sc.close();
    }

}

