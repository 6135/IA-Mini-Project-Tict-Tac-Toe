package app;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to Tic Tac Toe. Choose the game mode:\n1.Player vs CPU\n2.CPU1 vs CPU2");

        int gm = sc.nextInt();
      
        Agent p1;
        switch (gm) {
            case 1:
                System.out.println("Player's name: ");  
                String p = sc.next();
               
                p1 = new Player(p,'X');
                System.out.println("Enter a number from 0-8 to make a move.");
                break;
                
            case 2:
                p1 = new MCTS('X');
                break; 
        
            default:
                p1= new MCTS('X');
                break;
        }
       
        
        
        Agent cpu1 = new MCTS('O');     

        p1.singleSetOpponent(cpu1);
        cpu1.singleSetOpponent(p1);

        Ilayout b;
        Agent agent = p1;
        int itr = 1;
        int player1 = 0,player2 = 0,draw = 0;
        while(itr == 1){
            
            /*if(((int)itr%5) == 0)
                System.out.println(itr);*/
            b = new Board(agent);
            while(!b.terminal()){
                //Thread.sleep(1000);
                if(!(p1 instanceof MCTS))
                    System.out.println(agent.getName() + "'s Turn to move!");
                boolean doneMoving = false;
                while(!doneMoving){
                    try {
                        b = agent.move(b);
                        doneMoving = true;
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
                
                //if(!(p1 instanceof MCTS))
                System.out.println(b);
                agent = agent.opponent();
                
            }
            agent = p1;
            if(!(p1 instanceof MCTS))
                b.resultMessage();
            //if(b.status() == p1.getSymbol())
            //    player1++;
            //else if(b.status() == cpu1.getSymbol())
            //    player2++;
            //else if(b.status() == 'f')
            //    draw++;
            //itr++;
            //System.out.println("\'"+b.status()+"\'");
            System.out.println("1.Play again.\n2.Exit.");
            itr=sc.nextInt();
        }

        
        //System.out.println("---------");

        //System.out.println("Player1: "+player1);
        //System.out.println("Player2: "+player2);
        //System.out.println("Draw: "+draw);
        sc.close();
    }

}

