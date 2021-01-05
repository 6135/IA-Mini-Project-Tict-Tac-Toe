
package test;

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
    public void algorithmTest1(){
        int testID = 1;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
        

        char[][] playerPlay1 = { {'X','\0','\0'}, {'\0','\0','\0'}, {'\0','\0','\0'} };
        char[][] botExpectedPlay1 = { {'X','\0','\0'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);
        for(int i = 0; i < iter; i++){
            if(cpu.move(playerBoard).equals(expectedBotBoard))
                cMoves++;
            //System.out.println(cpu.move(playerBoard));
        }
        double result = cMoves/(double)(iter);
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest2(){
        int testID = 2;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
           

        char[][] playerPlay1 = { {'X','X','\0'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char[][] botExpectedPlay1 = { {'X','X','O'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);
        for(int i = 0; i < iter; i++){
            if(cpu.move(playerBoard).equals(expectedBotBoard))
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest3(){
        int testID = 3;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
           

        char[][] playerPlay1 = { {'X','X','O'}, {'\0','O','\0'}, {'X','\0','\0'} };
        char[][] botExpectedPlay1 = { {'X','X','O'}, {'O','O','\0'}, {'X','\0','\0'} };
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);
        for(int i = 0; i < iter; i++){
            if(cpu.move(playerBoard).equals(expectedBotBoard))
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID);
    }

    @Test 
    public void algorithmTest4(){
        int testID = 4;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
           

        char[][] playerPlay1 = { {'X','X','O'}, {'O','O','X'}, {'X','\0','\0'} };
        char[][] botExpectedPlay1 = { {'X','X','O'}, {'O','O','X'}, {'X','O','\0'} };
        char[][] botExpectedPlay2 = { {'X','X','O'}, {'O','O','X'}, {'X','\0','O'} };
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);
        Ilayout expectedBotBoard2 = new Board(botExpectedPlay2,p1);
        for(int i = 0; i < iter; i++){
            Ilayout botMove = cpu.move(playerBoard);
            if(botMove.equals(expectedBotBoard) || botMove.equals(expectedBotBoard2))
                cMoves++;
        }
        double result = cMoves/(double)(iter);
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed algorithmTest" + testID);
        assertEquals(1, result , delta);
        System.out.println("Passed algorithmTest" + testID); //game ends, no more moves would work
    }

    @Test 
    public void algorithmTest5(){
        int testID = 5;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
          

        char[][] playerPlay1 = { {'X','\0','\0'}, {'\0','O','\0'}, {'\0','\0','X'} };
        char[][] botExpectedPlay1 = { {'X','O','\0'}, {'\0','O','\0'}, {'\0','\0','X'} };
        char[][] botExpectedPlay2 = { {'X','\0','\0'}, {'\0','O','O'}, {'\0','\0','X'} };
    
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);
        Ilayout expectedBotBoard2 = new Board(botExpectedPlay2,p1);
        for(int i = 0; i < iter; i++){
            Ilayout botMove = cpu.move(playerBoard);
            if(botMove.equals(expectedBotBoard) || botMove.equals(expectedBotBoard2))
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

    @Test 
    public void algorithmTest6(){
        int testID = 6;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
           

        char[][] playerPlay1 = { {'X','O','\0'}, {'\0','O','\0'}, {'\0','X','X'} };
        char[][] botExpectedPlay1 = { {'X','O','\0'}, {'\0','O','\0'}, {'O','X','X'} };
    
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay1,p1);

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

    @Test 
    public void algorithmTest7(){
        int testID = 7;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
            

        char[][] playerPlay1 = { {'X','O','X'}, {'\0','O','\0'}, {'O','X','X'} };
        char[][] botExpectedPlay = { {'X','O','X'}, {'\0','O','O'}, {'O','X','X'} };
    
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay,p1);

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

    @Test 
    public void algorithmTest8(){
        int testID = 8;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
          

        char[][] playerPlay1 = { {'O','X','\0'}, {'\0','X','\0'}, {'\0','\0','\0'} };
        char[][] botExpectedPlay = { {'O','X','\0'}, {'\0','X','\0'}, {'\0','O','\0'} };
    
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay,p1);

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

    @Test 
    public void algorithmTest9(){
        int testID = 9;
        Agent p1 = new Player("Player",'X');
        Agent cpu = new MCTS('O');

        p1.setOpponent(cpu);
           

        char[][] playerPlay1 = { {'\0','X','O'},{'X','O','X'},{'\0','\0','\0'} };
        char[][] botExpectedPlay = { {'\0','X','O'}, {'X','O','X'}, {'O','\0','\0'} };
    
        int cMoves = 0;
        Ilayout playerBoard = new Board(playerPlay1,cpu);
        Ilayout expectedBotBoard = new Board(botExpectedPlay,p1);

        for(int i = 0; i < iter; i++){
            Ilayout botMove = cpu.move(playerBoard);
            assertEquals(expectedBotBoard,botMove);
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

    @Test 
    public void testBotVSBot(){
        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');

        cpu1.setOpponent(cpu2);
          

        Ilayout b;
        Agent currentPlayer = cpu1;
        int draws = 0;
        /**
         * First player alternates
         */
        for(int i = 0; i < iter; i++){
            b = new Board(currentPlayer);
            while(!b.terminal()) {
                b = currentPlayer.move(b);
                currentPlayer = currentPlayer.opponent();
            }
            if(b.status() == 'f')
                draws++;
        }
        double result = draws / (double) iter;
        System.out.println(result);
        if( result < (1.0-delta) )
            System.out.println("Failed testBotVSBot");
        assertEquals(1, result, delta);
        System.out.println("Passed testBotVSBot");        
    }

}