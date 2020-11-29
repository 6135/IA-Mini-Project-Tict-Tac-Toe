
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
    
    final Heuristics H1 = new H1();
    final Heuristics H4 = new H4();
    final Heuristics HP = new HP();
    Heuristics h = HP;
    final Algorithm AStar = new AStar();
    final Algorithm IDA = new IDA();
    final Algorithm BF = new BestFirst();
    Algorithm alg = IDA;
    @Test(timeout = 1000)
    public void testConstructor() {
        Board b = new Board("AC B");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter ( writer ) ;
        pw.println("[A, C]");
        pw.println("[B]");
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }
    @Test (timeout = 1000)
    public void testConstructor2() {
        Board b = new Board("A C B");
        StringWriter writer = new StringWriter();
        PrintWriter pw = new PrintWriter (writer) ;
        pw.println("[A]");
        pw.println("[C]");
        pw.println("[B]");
        assertEquals(writer.toString(), b.toString());
        pw.close();
    }

    @Test (timeout = 10000)
    public void testChildren(){
        long startTime = System.nanoTime();
        Board b = new Board("AC B");
        List<Ilayout> children = new ArrayList<>();
        children.addAll(b.children());
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0  + " testChildren");
        assertTrue(children.contains(new Board("A C B")));
        assertTrue(children.contains(new Board("A BC")));
        assertTrue(children.contains(new Board("ACB")));
        
    }

    @Test (timeout = 15000)
    public void testChildren2(){
        long startTime = System.nanoTime();
        Board b = new Board("A C B");
        List<Ilayout> children = new ArrayList<>();
        children.addAll(b.children());
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testChildren2");
        assertTrue(children.contains(new Board("BA C")));
        assertTrue(children.contains(new Board("B CA")));
        assertTrue(children.contains(new Board("AB C")));
        assertTrue(children.contains(new Board("A CB")));
        assertTrue(children.contains(new Board("AC B")));
        assertTrue(children.contains(new Board("A BC")));
        assertEquals(children.size(),6);
        
    }
    @Test (timeout = 3000)
    public void testSolve(){
        //aplicar 1 exemplo do prof
        /**
         * AC B
         * A C B
         */
        long startTime = System.nanoTime();
        Board b = new Board("AC B");
        double g = alg.solve(b, new Board("A C B"), h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve");
        assertTrue(1==g);

    }

    @Test (timeout = 3000)
    public void testSolve2(){
        //aplicar 2 exemplo do prof
        /*
        ABC
        A B C
        */
        long startTime = System.nanoTime();
        Board b = new Board("ABC");
        Board goal = new Board("A B C");
        double g = h.getH(b, goal);
        //double g = alg.solve(b, g ,h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve2");

        assertEquals(2,g,0);
    }

    @Test (timeout = 3000)
    public void testSolve3(){
        //aplicar 3 exemplo do prof
        /*
        CAB
        ABC
        */
        long startTime = System.nanoTime();
        Board b = new Board("CAB");
        double g = alg.solve(b, new Board("ABC"),h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve3");
        assertTrue(4==g);
    }
    @Test (timeout = 3000)
    public void testSolve4(){
        /*
        AC B
        A C B
        */
        long startTime = System.nanoTime();
        Board b = new Board("AC B");
        double g = alg.solve(b, new Board("A C B"),h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve4");
        assertTrue(1==g);
    }
    @Test (timeout = 3000)
    public void testSolve5(){
        /*
        BAD FEC
        ABCDEF
        */
        long startTime = System.nanoTime();
        Board b = new Board("BAD FEC");
        double g = alg.solve(b, new Board("ABCDEF"),h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve5");
        assertTrue(7==g);
    }
    @Test (timeout = 3000)
    public void testSolve6(){
        /*
        GF EDC B A
        ABCDEFG
        */
        long startTime = System.nanoTime();
        Board b = new Board("GF EDC B A");
        double g = alg.solve(b, new Board("ABCDEFG"),h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " testSolve6");
        assertTrue(6==g);
    }
    @Test (timeout = 60000)
    public void testTime(){
        long startTime = System.nanoTime();
        Board b = new Board("FACE BDX");
        double g = alg.solve(b, new Board("FBCDEAX"),h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000_000.0 + " G: " + g + " testTime");
    }

    @Test (timeout = 6000000)
    public void testHeuristics(){
        System.out.println("LENG: " + "WNQ GJAZ ORXISEBCYU P MDTLHVFK".length());
        
        System.out.println("H: " + h.getH(new Board("WNQ GJAZ ORXISEBCYU P MDTLHVFK"), new Board("UQR CV WSXH ZN OYATJF MPKDI GE LB")));
        long startTime = System.nanoTime();
        Board b = new Board("WNQ GJAZ ORXISEBCYU P MDTLHVFK");
        Board goal = new Board("UQR CV WSXH ZN OYATJF MPKDI GE LB");
        double gVal = alg.solve(b, goal, h);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println((double)totalTime/1_000_000.0 + " G: " + gVal);
    }
}