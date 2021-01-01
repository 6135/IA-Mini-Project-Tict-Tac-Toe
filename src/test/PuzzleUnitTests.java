
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
<<<<<<< Updated upstream
        

=======
>>>>>>> Stashed changes

        char s=b.status();
        
        assertTrue(s=='i');
    }

<<<<<<< Updated upstream
    @Test 
    public void testSolve1(){
        int moves = 1000;
        char [][] board ={
            {'\0','\0','\0'},
            {'\0','X','\0'},
            {'\0','\0','\0'}
        };

        char [][] botBoard1 ={{'O','\0','\0'},{'\0','X','\0'},{'\0','\0','\0'}};
        char [][] botBoard12 ={{'\0','\0','O'},{'\0','X','\0'},{'\0','\0','\0'}};
        char [][] botBoard13 ={{'\0','\0','\0'},{'\0','X','\0'},{'O','\0','\0'}};
        char [][] botBoard14 ={{'\0','\0','\0'},{'\0','X','\0'},{'\0','\0','O'}};
        

        int itr = 0;

        Agent bot = new MCTS('O');
        Agent p = new Player("Test", 'X');

        p.setOpponent(bot);
        bot.setOpponent(p);

        Board b = new Board(board,bot);
        
        int rMoves=0;
        

        while (itr<moves) {
            Board botPlay = bot.move(b);
            if(botPlay.equals(new Board(botBoard1,p)) || botPlay.equals(new Board(botBoard12,p)) || botPlay.equals(new Board(botBoard13,p)) || botPlay.equals(new Board(botBoard14,p))) rMoves++;
            

            itr++; 
        }
        double d = (rMoves/moves);
        char [][] b2 = {{'O','\0','X'},{'\0','X','\0'},{'\0','\0','\0'}};

        System.out.println(d);
        assertTrue(d>=0.9);
    }

    @Test (timeout = 3000)
    public void testSolve(){
        //todo
    }
=======
>>>>>>> Stashed changes

    @Test (timeout = 1000)
    public void testVictory(){
        char [][] board ={
            {'X','O','\0'},
            {'O','X','\0'},
            {'\0','\0','X'}
        };
        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');

        cpu1.setOpponent(cpu2);
        cpu2.setOpponent(cpu1);

        Board b = new Board(board,cpu2);
        char status = b.status();
        assertEquals('X', status);
    
        char[][] board2 ={
            {'X','O','X'},
            {'\0','X','X'},
            {'O','O','O'}
        };
        b = new Board(board2,cpu1);
        status = b.status();
        assertEquals('O', status);
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

        System.out.println("Done");
    }
}