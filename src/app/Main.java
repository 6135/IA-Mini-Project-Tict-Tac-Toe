package app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println(Integer.MIN_VALUE == -Integer.MAX_VALUE);
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe. Choose the game mode:\n1.Player vs CPU\n2.CPU vs CPU");
        
        int gm = sc.nextInt();
        if(gm == 2) {
            Player cpu1 = new Player("cpu1");
            Player cpu2 = new Player("cpu2");

            cpu1.setSymbol('X');
            cpu2.setSymbol('O');
            
            cpu1.setOpponent(cpu2);
            cpu2.setOpponent(cpu1);

            Board b = new Board();
            MCTS mcts = new MCTS();

            // System.out.println(b.toString());
            // Player p = cpu1;
            // System.out.println(b.terminal(p));
            // while( !b.terminal(p) ) {
            //     b = (Board) mcts.MCTsSearch(b, p).layout();
            //     p = p.opponent();
            //     System.out.println(b.toString());
            //     System.out.println(b.terminal(p));
            // }
            // char status = b.status(cpu1);
            // if(status == 'v')
            //     System.out.println(cpu1.getPlayer() + " has won the game!");
            // else if(status == 'l')
            //     System.out.println(cpu1.opponent().getPlayer() + " has won the game!");
            // else if(status == 'f')
            //     System.out.println("Draw!");
        }else if(gm == 1) {
            Player p1 = new Player("Player 1");
            Player cpu = new Player("CPU");
            p1.setSymbol('X');
            cpu.setSymbol('O');
            p1.setOpponent(cpu);
            cpu.setOpponent(p1);
            MCTS mcts = new MCTS();
            Board b = new Board();
            System.out.println(b.toString());
            while(!b.terminal(p1)){
                boolean correct = false;
                while(!correct){
                    try {
                        b = b.move(p1, sc.nextInt());
                        correct = true;
                    } catch(Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println(b);
                char status = b.status(p1);
                if(status != 'i'){
                    winMessage(b, p1);
                    break;
                }

                b = (Board) mcts.MCTsSearch(new State(b,p1,null)).layout();
                System.out.println(b);
                status = b.status(p1);
                if(status != 'i'){
                    winMessage(b, p1);
                    break;
                }

            }

        }
        sc.close();

    }
    private static void winMessage(Board b, Player p) {
        char status = b.status(p);
        if(status == 'v')
            System.out.println(p.getPlayer() + " has won the game!");
        else if(status == 'l')
            System.out.println(p.opponent().getPlayer() + " has won the game!");
        else if(status == 'f')
            System.out.println("Draw!");
    }
}

