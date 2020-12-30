
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.*;
public class PuzzleUnitTests {
    
    @Test(timeout = 1000)
    public void testConstructor() {
        //todo
    }
    @Test (timeout = 1000)
    public void testConstructor2() {
        //todo
    }

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
        Board b = new Board(board);
        
        

        cpu2.setSymbol('O');
        
        List<Ilayout> children = new ArrayList<>();
        children.addAll(b.children(cpu2));
        char [][] c1 ={
            {'O','X',' '},
            {' ','O',' '},
            {' ',' ','X'}
        };
        char [][] c2 ={
            {' ','X','O'},
            {' ','O',' '},
            {' ',' ','X'}
        };
        char [][] c3 ={
            {' ','X',' '},
            {'O','O',' '},
            {' ',' ','X'}
        };
        char [][] c4 ={
            {' ','X',' '},
            {' ','O','O'},
            {' ',' ','X'}
        };
        char [][] c5 ={
            {' ','X',' '},
            {' ','O',' '},
            {'O',' ','X'}
        };
        char [][] c6 ={
            {' ','X',' '},
            {' ','O',' '},
            {' ','O','X'}
        };
        assertTrue(children.contains(new Board(c1)));
        assertTrue(children.contains(new Board(c2)));
        assertTrue(children.contains(new Board(c3)));
        assertTrue(children.contains(new Board(c4)));
        assertTrue(children.contains(new Board(c5)));
        assertTrue(children.contains(new Board(c6)));
        
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
    public void testStatus1(){
        char [][] board ={
            {'\0','X','O'},
            {'\0','O','O'},
            {'X','X','X'}
        };
        Agent cpu1 = new MCTS('X');
        Board b = new Board(board,cpu1);
        

        cpu1.setSymbol('X');

        char s=b.status();
        
        assertTrue(s=='X');


    }

    @Test (timeout = 3000)
    public void testStatus2(){
        char [][] board ={
            {'X','X','O'},
            {'O','O','X'},
            {'X','O','X'}
        };

        Agent cpu1 = new MCTS('X');
        Board b = new Board(board,cpu1);

        char s=b.status();
        assertTrue(s=='f');


    }

    @Test (timeout = 3000)
    public void testStatus3(){
        char [][] board ={
            {'\0','X','O'},
            {'O','O','X'},
            {'X','O','X'}
        };

        Agent cpu1 = new MCTS('X');
        Board b = new Board(board,cpu1);
        
;

        char s=b.status();
        
        assertTrue(s=='i');
    }

    @Test (timeout = 3000)
    public void testSolve(){
        //todo
    }

    @Test (timeout = 3000)
    public void testSolve2(){
        //todo
    }

    @Test (timeout = 3000)
    public void testSolve3(){
        //todo
    }
    @Test (timeout = 3000)
    public void testSolve4(){
        //todo
    }
    @Test (timeout = 3000)
    public void testSolve5(){
        //todo

    }
    @Test (timeout = 3000)
    public void testSolve6(){
        //todo
    }
    @Test (timeout = 60000)
    public void testTime(){
        //todo
    }

    
}