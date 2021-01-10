
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.*;
public class TicTacToeOptimalGameTest {
    private static final int iter =  10000;
    private static final double delta = 0.0;

    @Test 
    public void algorithmTest9(){
        int testID = 9;
           

        Board.State[][] playerPlay1 = { {Board.State.X,Board.State.X,Board.State.O}, {Board.State.Blank,Board.State.X,Board.State.Blank}, {Board.State.X,Board.State.O,Board.State.O} };    
        int cMoves = 0;
        Board playerPlay = new Board(playerPlay1,Board.State.O,false);

        for(int i = 0; i < iter; i++){
            Ilayout botMove = cpu.move(playerBoard);
            if(botMove.equals(expectedBotBoard))
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