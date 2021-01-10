package app;


import static org.junit.Assert.assertEquals;


import org.junit.Test;

import app.*;
public class TicTacToeOptimalGameTest {
    private static final int iter =  100000;
    private static final double delta = 0.0;

    @Test 
    public void algorithmTest9(){
        int testID = 9;
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
            //System.out.println(botMove);
        }
        double result = cMoves/(double)(iter);
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }


}