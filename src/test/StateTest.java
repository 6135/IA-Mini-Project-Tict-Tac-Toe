
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
public class StateTest {


    @Test
    public void testConstructor(){
        int testID = 1;
        char [][] pBoard ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
       

        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Ilayout cB = new Board(cBoard,bot);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testMakeChildren(){
        int testID = 1;
        char [][] pBoard ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
       

        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Ilayout cB = new Board(cBoard,bot);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testMakeChildren2(){
        int testID = 1;
        char [][] pBoard ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
       

        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Ilayout cB = new Board(cBoard,bot);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testUCBCalc(){
        int testID = 1;
        char [][] pBoard ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
       

        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Ilayout cB = new Board(cBoard,bot);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    @Test
    public void testUCBCalc2(){
        int testID = 1;
        char [][] pBoard ={ {'X','\0','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
        char [][] cBoard = { {'X','O','X'}, {'\0','O','\0'}, {'\0','\0','\0'} };
       

        
        Agent bot = new MCTS('O');
        Agent bot2 = new MCTS('X');

        bot.setOpponent(bot2);
        Ilayout cB = new Board(cBoard,bot);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);


        if(  pbHoles!=0 || p2bHoles!=1 )
            System.out.println("Failed testUnclosedHoles" + testID);
        assertEquals(pbHoles, 0);
        assertEquals(p2bHoles, 1);
        System.out.println("Passed testUnclosedHoles" + testID);

    }

    
    
}