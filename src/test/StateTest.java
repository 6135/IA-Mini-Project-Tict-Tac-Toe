
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
        Ilayout cB = new Board(cBoard,bot2);
        Ilayout pB = new Board(pBoard,bot);
        State pS = new State(pB,null);
        State cS = new State(cB,pS);
        
        boolean c = cS.getLayout().equals(cB);
        boolean c1 = cS.agentThatMoved().equals(bot);
        if(  !c || !c1 )
            System.out.println("Failed testConstructor" + testID);
        assertTrue(c);
        assertTrue(c1);
        System.out.println("Passed testConstructor" + testID);

    }

    @Test
    public void testMakeChildren(){
        int testID = 1;
        char [][] board ={
            {'\0','X','\0'},
            {'\0','O','\0'},
            {'\0','\0','X'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu2.setOpponent(cpu1);
        Ilayout b = new Board(board,cpu2);
        
        State sP = new State(b,null);
        List<State> children = new ArrayList<>();
        children.addAll(sP.makeChildren());
        char [][] c1 ={ {'O','X','\0'},{'\0','O','\0'},{'\0','\0','X'} };
        char [][] c2 ={ {'\0','X','O'},{'\0','O','\0'},{'\0','\0','X'} };
        char [][] c3 ={ {'\0','X','\0'},{'O','O','\0'},{'\0','\0','X'} };
        char [][] c4 ={ {'\0','X','\0'},{'\0','O','O'},{'\0','\0','X'} };
        char [][] c5 ={ {'\0','X','\0'},{'\0','O','\0'},{'O','\0','X'} };
        char [][] c6 ={ {'\0','X','\0'},{'\0','O','\0'},{'\0','O','X'} };

        Ilayout i1 = new Board(c1,cpu1);
        Ilayout i2 = new Board(c2,cpu1);
        Ilayout i3 = new Board(c3,cpu1);
        Ilayout i4 = new Board(c4,cpu1);
        Ilayout i5 = new Board(c5,cpu1);
        Ilayout i6 = new Board(c6,cpu1);

        
        assertTrue(children.contains(new State(i1,sP)));
        assertTrue(children.contains(new State(i2,sP)));
        assertTrue(children.contains(new State(i3,sP)));
        assertTrue(children.contains(new State(i4,sP)));
        assertTrue(children.contains(new State(i5,sP)));
        assertTrue(children.contains(new State(i6,sP)));

        System.out.println("Passed testMakeChildren" + testID);

    }

    @Test
    public void testUCBCalc(){
        int testID = 1;
        char [][] board ={
            {'\0','X','\0'},
            {'\0','O','\0'},
            {'\0','\0','X'}
        };

        Agent cpu1 = new MCTS('X');
        Agent cpu2 = new MCTS('O');
        cpu2.setOpponent(cpu1);
        Ilayout b = new Board(board,cpu2);
        
        State sP = new State(b,null);
        sP.setVisitCount(3);
        sP.setWinCount(5);

        char [][] c1 ={ {'O','X','\0'},{'\0','O','\0'},{'\0','\0','X'} };
        Ilayout i1 = new Board(c1,cpu1);
        State s1 = new State(i1,sP);
        s1.setVisitCount(1);
        s1.setWinCount(2);

        double u = s1.ucbCalc();
        
        assertEquals(3.482, u,0.001);
        System.out.println("Passed testUCBCalc" + testID);

    }


    
    
}