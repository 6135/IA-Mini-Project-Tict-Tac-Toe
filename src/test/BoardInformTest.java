
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
    public void testChildrenCloseHoles1(){
        int testID = 1;
        char [][] rBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] board ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board rb = new Board(rBoard,player);
        List<Ilayout> children = b.children();

        System.out.println(children);
        
        boolean condition = children.contains(rb);
        if(  !condition )
            System.out.println("Failed algorithmTest" + testID);
        assertTrue(condition);
        System.out.println("Passed algorithmTest" + testID);

    }

    @Test
    public void testChildrenCloseHoles2(){
        int testID = 2;
        char [][] rBoard = { {'X','\0','\0'}, {'\0','X','\0'}, {'O','\0','O'} };
        char [][] board ={ {'X','\0','\0'}, {'\0','X','\0'}, {'O','\0','\0'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board rb = new Board(rBoard,player);
        List<Ilayout> children = b.children();

        System.out.println(children);
        
        boolean condition = children.contains(rb);
        
        if(  !condition )
            System.out.println("Failed algorithmTest" + testID);
        assertTrue(condition);
        System.out.println("Passed algorithmTest" + testID);

    }

    @Test
    public void testChildrenCloseHoles3(){
        int testID = 3;
        char [][] rBoard = { {'\0','O','\0'}, {'\0','X','\0'}, {'\0','X','O'} };
        char [][] board ={ {'\0','\0','\0'}, {'\0','X','\0'}, {'\0','X','O'} };

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board rb = new Board(rBoard,player);
        List<Ilayout> children = b.children();

        System.out.println(children);
        
        boolean condition = children.contains(rb);
        
        if(  !condition )
            System.out.println("Failed algorithmTest" + testID);
        assertTrue(condition);
        System.out.println("Passed algorithmTest" + testID);

    }

    @Test
    public void testChildrenCloseHoles4(){
        int testID = 4;
        char [][] board ={ {'O','\0','X'}, {'\0','X','\0'}, {'\0','X','O'} };

        char [][] rBoard = { {'O','O','X'}, {'\0','X','\0'}, {'\0','X','O'} };
        char [][] r2Board = { {'O','\0','X'}, {'\0','X','\0'}, {'O','X','O'} };
        

        
        Agent bot = new MCTS('O');
        Agent player = new Player("player",'X');

        bot.setOpponent(player);
        Board b = new Board(board,bot);

        Board rb = new Board(rBoard,player);
        Board rb2 = new Board(r2Board,player);
        List<Ilayout> children = b.children();

        System.out.println(children);
        System.out.println(rb2.uncloseHoles());
        System.out.println(rb2.uncloseHoles());
        boolean condition = children.contains(rb);

        boolean condition2 = children.contains(rb2);
        
        if(  !condition || !condition2)
            System.out.println("Failed algorithmTest" + testID);
        assertTrue(condition);
        assertTrue(condition2);
        System.out.println("Passed algorithmTest" + testID);

    }

    
    
}