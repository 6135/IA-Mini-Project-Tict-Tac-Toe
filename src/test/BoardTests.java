
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.*;
public class BoardTests {
    

    @Test (timeout = 10000)
    public void testChildren(){
        char [][] board ={
            {'\0','X','\0'},
            {'\0','O','\0'},
            {'\0','\0','X'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu2.setOpponent(cpu1);
        Board b = new Board(board,cpu2);
        
        List<Ilayout> children = new ArrayList<>();
        children.addAll(b.children());
        char [][] c1 ={
            {'O','X','\0'},
            {'\0','O','\0'},
            {'\0','\0','X'}
        };
        char [][] c2 ={
            {'\0','X','O'},
            {'\0','O','\0'},
            {'\0','\0','X'}
        };
        char [][] c3 ={
            {'\0','X','\0'},
            {'O','O','\0'},
            {'\0','\0','X'}
        };
        char [][] c4 ={
            {'\0','X','\0'},
            {'\0','O','O'},
            {'\0','\0','X'}
        };
        char [][] c5 ={
            {'\0','X','\0'},
            {'\0','O','\0'},
            {'O','\0','X'}
        };
        char [][] c6 ={
            {'\0','X','\0'},
            {'\0','O','\0'},
            {'\0','O','X'}
        };

        assertTrue(children.contains(new Board(c1,cpu1)));
        assertTrue(children.contains(new Board(c2,cpu1)));
        assertTrue(children.contains(new Board(c3,cpu1)));
        assertTrue(children.contains(new Board(c4,cpu1)));
        assertTrue(children.contains(new Board(c5,cpu1)));
        assertTrue(children.contains(new Board(c6,cpu1)));
        
    }

    @Test (timeout = 15000)
    public void testChildren2(){
        char [][] board ={
            {'\0','X','O'},
            {'\0','O','O'},
            {'X','\0','X'}
        };
        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu2.setOpponent(cpu1);
        Board b = new Board(board,cpu1);
        
        List<Ilayout> children = new ArrayList<>();
        children.addAll(b.children());

        char [][] c1 ={
            {'X','X','O'},
            {'\0','O','O'},
            {'X','\0','X'}
        };
        char [][] c2 ={
            {'\0','X','O'},
            {'X','O','O'},
            {'X','\0','X'}
        };
        char [][] c3 ={
            {'\0','X','O'},
            {'\0','O','O'},
            {'X','X','X'}
        };
        

        
        assertTrue(children.contains(new Board(c1,cpu2)));
        assertTrue(children.contains(new Board(c2,cpu2)));
        assertTrue(children.contains(new Board(c3,cpu2)));
    }

    @Test (timeout = 3000)
    public void testStatus(){
        int testID = 1;
        char [][] board ={
            {'\0','X','O'},
            {'\0','O','O'},
            {'X','X','X'}
        };
        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');

        cpu1.setOpponent(cpu2);
        

        Board b = new Board(board,cpu1);
        char s=b.status();
        
        if( s!='X' )
            System.out.println("Failed testStatus" + testID);
        assertTrue(s=='X');
        System.out.println("Passed testStatus" + testID);
        


    }

    @Test (timeout = 3000)
    public void testStatus2(){
        int testID=2;
        char [][] board ={
            {'X','X','O'},
            {'O','O','X'},
            {'X','O','X'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu1.setOpponent(cpu2);
        Board b = new Board(board,cpu1);

        char s=b.status();
        
        if( s!='f' )
            System.out.println("Failed testStatus" + testID);
        assertTrue(s=='f');
        System.out.println("Passed testStatus" + testID);

    }

    @Test (timeout = 3000)
    public void testStatus3(){
        int testID=3;
        char [][] board ={
            {'\0','X','O'},
            {'O','O','X'},
            {'X','O','X'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu1.setOpponent(cpu2);
        Board b = new Board(board,cpu1);
        


        char s=b.status();
        if( s!='i' )
            System.out.println("Failed testStatus" + testID);
        assertTrue(s=='i');
        System.out.println("Passed testStatus" + testID);
       
    }


    @Test (timeout = 1000)
    public void testVictory(){
        int testID = 1;
        char [][] board ={
            {'X','O','\0'},
            {'O','X','\0'},
            {'\0','\0','X'}
        };
        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');

        cpu1.setOpponent(cpu2);
        

        Board b = new Board(board,cpu2);
        char s = b.status();
        

        if( s!='X' )
            System.out.println("Failed testVictory" + testID);
        assertEquals('X', s);
        System.out.println("Passed testVictory" + testID);
    }

    @Test (timeout = 1000)
    public void testVictory2(){
        int testID = 2;
           
        char[][] board2 ={
            {'X','O','X'},
            {'\0','X','X'},
            {'O','O','O'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');

        cpu1.setOpponent(cpu2);
        Board b = new Board(board2,cpu1);
        char s = b.status();
        assertEquals('O', s);

        if( s!='O' )
            System.out.println("Failed testVictory" + testID);
        assertTrue(s=='O');
        System.out.println("Passed testVictory" + testID);
    }

    

    @Test 
    public void testBoardEquals(){
        char[][] board = { {'\0','X','\0'} , {'\0','\0','\0'} , {'\0','O','\0'} };
        char[][] boardRotated = { {'\0','O','\0'} , {'\0','\0','\0'} , {'\0','X','\0'} };
        char[][] boardRotated2 = { {'\0','\0','\0'} , {'X','\0','O'} , {'\0','\0','\0'} };
        char[][] boardRotated3 = { {'\0','\0','\0'} , {'O','\0','X'} , {'\0','\0','\0'} };

        Board board1 = new Board(board, new MCTS('X'));
        Board board2 = new Board(boardRotated, new MCTS('X'));
        Board board3 = new Board(boardRotated2, new MCTS('X'));
        Board board4 = new Board(boardRotated3, new MCTS('X'));

        assertEquals(board1,board2);
        assertEquals(board1,board3);
        assertEquals(board1,board4);
        assertEquals(board2,board3);
        assertEquals(board2,board4);
        assertEquals(board3,board4);

        
    }

}