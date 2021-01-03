
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import app.*;
public class BoardInformTest {


    @Test
    public void testUnclosedHoles1(){
        int testID = 1;
        char [][] board ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] c2Board = { {'X','\0','X'}, {'O','O','\0'}, {'\0','\0','\0'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board pb = new Board(cBoard,player);
        Board p2b = new Board(c2Board,player);
        
        
        int pbHoles = pb.uncloseHoles(player);
        int p2bHoles = p2b.uncloseHoles(player);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testUnclosedHoles2(){
        int testID = 2;
        char [][] board ={ {'X','\0','\0'}, {'\0','X','\0'}, {'O','\0','\0'} };
        char [][] cBoard = { {'X','\0','\0'}, {'\0','X','\0'}, {'O','\0','O'} };
        char [][] c2Board = { {'X','\0','\0'}, {'O','X','\0'}, {'O','\0','\0'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board pb = new Board(cBoard,player);
        Board pb2 = new Board(c2Board,player);
        
        int pbHoles = pb.uncloseHoles(player);
        int pb2Holes = pb2.uncloseHoles(player);
        
        if(  pbHoles!=0 || pb2Holes!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(pb2Holes, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testUnclosedHoles3(){
        int testID = 3;
        char [][] board ={ {'\0','\0','\0'}, {'\0','O','X'}, {'\0','\0','X'} };
        char [][] cBoard = { {'\0','\0','O'}, {'\0','O','X'}, {'\0','\0','X'} };
        char [][] c2Board ={ {'\0','O','\0'}, {'\0','O','X'}, {'\0','\0','X'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board pb = new Board(cBoard,player);
        Board pb2 = new Board(c2Board,player);
        
        
        int pbHoles = pb.uncloseHoles(player);
        int pb2Holes = pb2.uncloseHoles(player);
        
        if( pbHoles!=0 || pb2Holes!=1)
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(pb2Holes, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testUnclosedHoles4(){
        int testID = 4;
        char [][] board ={ {'O','\0','X'}, {'\0','X','\0'}, {'\0','X','O'} };

        char [][] cBoard = { {'O','O','X'}, {'\0','X','\0'}, {'\0','X','O'} };
        char [][] c2Board = { {'O','\0','X'}, {'\0','X','\0'}, {'O','X','O'} };
        char [][] c3Board ={ {'O','\0','X'}, {'\0','X','O'}, {'\0','X','O'} };
        

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board pb = new Board(cBoard,player);
        Board pb2 = new Board(c2Board,player);
        Board pb3 = new Board(c3Board,player);
        
    
        int pbHoles = pb.uncloseHoles(player);
        int pb2Holes = pb2.uncloseHoles(player);
        int pb3Holes = pb3.uncloseHoles(player);

        if(  pbHoles!=1 ||  pb2Holes!=1 ||  pb3Holes!=2)
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 1);
        assertEquals(pb2Holes, 1);
        assertEquals(pb3Holes, 2);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testHeavyPlayout(){
        int testID = 1;
        char [][] board = { {'X','\0','O'}, {'\0','\0','\0'}, {'\0','\0','X'} };

        char [][] cBoard = { {'X','\0','O'}, {'\0','O','\0'}, {'\0','\0','X'} };


        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);
        Board cB = new Board(cBoard,player);

        Board heavyBoard=(Board)b.playout();
    
        int bHoles = b.uncloseHoles(bot.opponent());
        int pbHoles = cB.uncloseHoles(bot.opponent());
        boolean e= heavyBoard.equals(cB);
        
        
        if(  pbHoles!=0 ||  bHoles!=1 || !e)
            System.out.println("Failed testHeavyPlayout" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(bHoles, 1);
        assertTrue(e);
        System.out.println("Passed testHeavyPlayout" + testID);

    }

    @Test
    public void testHeavyPlayout2(){
        int testID = 2;
        char [][] board = { {'X','\0','O'}, {'\0','O','\0'}, {'\0','\0','X'} };

        char [][] cBoard = { {'X','\0','O'}, {'\0','O','\0'}, {'X','\0','X'} };
        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Board b = new Board(board,bot2);
        Board cB = new Board(cBoard,bot);

        Board heavyBoard=(Board)b.playout();
    
        int bHoles = b.uncloseHoles(bot2.opponent());
        int pbHoles = cB.uncloseHoles(bot2.opponent());
        boolean e= heavyBoard.equals(cB);
        
       
        if(  pbHoles!=0 ||  bHoles!=1 || !e)
            System.out.println("Failed testHeavyPlayout" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(bHoles, 1);
        assertTrue(e);
        System.out.println("Passed testHeavyPlayout" + testID);

    }

    @Test
    public void testHeavyPlayout3(){
        int testID = 3;
        char [][] board = { {'X','\0','O'}, {'\0','O','\0'}, {'X','\0','X'} };

        char [][] cBoard = { {'X','\0','O'}, {'\0','O','\0'}, {'X','O','X'} };
        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Board b = new Board(board,bot);
        Board cB = new Board(cBoard,bot2);

        Board heavyBoard=(Board)b.playout();
    
        int bHoles = b.uncloseHoles(bot.opponent());
        int pbHoles = cB.uncloseHoles(bot.opponent());
        boolean e= heavyBoard.equals(cB);
        
        
        if(  pbHoles!=1 ||  bHoles!=2 || !e)
            System.out.println("Failed testHeavyPlayout" + testID);
        assertEquals(pbHoles, 1);
        assertEquals(bHoles, 2);
        assertTrue(e);
        System.out.println("Passed testHeavyPlayout" + testID);

    }

    @Test
    public void testHeavyPlayout4(){
        int testID = 4;
        char [][] board = { {'X','\0','O'}, {'O','O','\0'}, {'X','\0','X'} };

        char [][] cBoard = { {'X','\0','O'}, {'O','O','\0'}, {'X','X','X'} };
       
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Board b = new Board(board,bot2);
        Board cB = new Board(cBoard,bot);

        Board heavyBoard=(Board)b.playout();
    
        int bHoles = b.uncloseHoles(bot2.opponent());
        int b2Holes = cB.uncloseHoles(bot2.opponent());
        boolean e= heavyBoard.equals(cB);
        
        
        if(  b2Holes!=1 ||  bHoles!=1 || !e)
            System.out.println("Failed testHeavyPlayout" + testID);
        assertEquals(b2Holes, 1);
        assertEquals(bHoles, 1);
        assertTrue(e);
        System.out.println("Passed testHeavyPlayout" + testID);

    }
    
}