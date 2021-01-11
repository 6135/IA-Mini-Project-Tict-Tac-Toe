package app;


import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TicTacToeOptimalGameTest {
    private static final int iter =  1000;
    private static final double delta = 0.1;

    @Test 
    public void algorithmTest1(){
        int testID = 1;
        MCTS mcts = new MCTS();
        

        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.Blank,Board.State.Blank},
            {Board.State.Blank,Board.State.Blank,Board.State.Blank},
            {Board.State.Blank,Board.State.Blank,Board.State.Blank}
        };
        // char[][] botExpectedPlay1 = { {'X','\0','\0'}, {'\0','O','\0'}, {'\0','\0','\0'} };

        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            // System.out.println(botMove);
            if(botMove == 4 )
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest2(){
        int testID = 2;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.X,Board.State.Blank},
            {Board.State.Blank,Board.State.O,Board.State.Blank},
            {Board.State.Blank,Board.State.Blank,Board.State.Blank}
        };
        // char[][] botExpectedPlay1 = { {'X','X','O'}, {'\0','O','\0'}, {'\0','\0','\0'} };

        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            // System.out.println(botMove);
            if(botMove == 2 )
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest3(){
        int testID = 3;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.X,Board.State.O},
            {Board.State.Blank,Board.State.O,Board.State.Blank},
            {Board.State.X,Board.State.Blank,Board.State.Blank}
        };

        // char[][] botExpectedPlay1 = { {'X','X','O'}, {'O','O','\0'}, {'X','\0','\0'} };

        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            // System.out.println(botMove);
            if(botMove == 3 )
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest4(){
        int testID = 4;
        MCTS mcts = new MCTS();
        
        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.X,Board.State.O},
            {Board.State.O,Board.State.O,Board.State.X},
            {Board.State.X,Board.State.Blank,Board.State.Blank}
        };

        // char[][] botExpectedPlay1 = { {'X','X','O'}, {'O','O','X'}, {'X','O','\0'} };
        // char[][] botExpectedPlay2 = { {'X','X','O'}, {'O','O','X'}, {'X','\0','O'} };

        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            // System.out.println(botMove);
            if(botMove == 7 || botMove == 8 )
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest5(){
        int testID = 5;
        MCTS mcts = new MCTS();
        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.Blank,Board.State.Blank},
            {Board.State.Blank,Board.State.O,Board.State.Blank},
            {Board.State.Blank,Board.State.Blank,Board.State.X} };
        // char[][] botExpectedPlay1 = { {'X','O','\0'}, {'\0','O','\0'}, {'\0','\0','X'} };
        // char[][] botExpectedPlay2 = { {'X','\0','\0'}, {'\0','O','O'}, {'\0','\0','X'} };
    
        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            // System.out.println(botMove);
            if(botMove == 1 || botMove == 5 || botMove == 7 || botMove == 3)
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest6(){
        int testID = 6;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.O,Board.State.Blank},
            {Board.State.Blank,Board.State.O,Board.State.Blank},
            {Board.State.Blank,Board.State.X,Board.State.X}
        };
        // char[][] botExpectedPlay1 = { {'X','O','\0'}, {'\0','O','\0'}, {'O','X','X'} };
    
        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            if(botMove == 6)
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest7(){
        int testID = 7;
        MCTS mcts = new MCTS();
            

        Board.State[][] playerPlay1 = { 
            {Board.State.X,Board.State.O,Board.State.X},
            {Board.State.Blank,Board.State.O,Board.State.Blank},
            {Board.State.O,Board.State.X,Board.State.X} 
        };
        // char[][] botExpectedPlay = { {'X','O','X'}, {'\0','O','O'}, {'O','X','X'} };
    
        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            if(botMove == 5)
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test (timeout = (iter*500))
    public void algorithmTest8(){
        int testID = 8;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.O,Board.State.X,Board.State.Blank},
            {Board.State.Blank,Board.State.X,Board.State.Blank},
            {Board.State.Blank,Board.State.Blank,Board.State.Blank} };

        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            if(botMove == 7)
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest9(){
        int testID = 9;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.Blank,Board.State.X,Board.State.O},
            {Board.State.X,Board.State.O,Board.State.X},
            {Board.State.Blank,Board.State.Blank,Board.State.Blank} 
        };
    
        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            if(botMove == 6)
                cMoves++;
            // System.out.println(botMove);
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test (timeout = (iter*600))
    public void testBotVSBot(){
        MCTS mcts = new MCTS();
        int draws = 0;

        Board b;
        for(int i = 0; i < iter; i++){
            b = new Board();
            while(!b.isGameOver())
                b.move(mcts.move(b));
            
            if(b.isGameOver() && b.getWinner() == Board.State.Blank)
                draws++;

        }

        double result = draws / (double) iter;
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed testBotVSBot");
        assertEquals(1, result, delta);
        System.out.println("Passed testBotVSBot");        
    }

    @Test (timeout = (iter*600))
    public void algorithmTest10(){
        int testID = 10;
        MCTS mcts = new MCTS();

        Board.State[][] playerPlay1 = { 
            {Board.State.O,Board.State.X,Board.State.X}, 
            {Board.State.Blank,Board.State.X,Board.State.Blank}, 
            {Board.State.O,Board.State.O,Board.State.X} 
        };    
        int cMoves = 0;
        Board playerPlayBoard = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            int botMove = mcts.move(playerPlayBoard);
            if(botMove == 3)
                cMoves++;
            // System.out.println(botMove);
        }
        double result = cMoves/(double)(iter);
        // System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

}