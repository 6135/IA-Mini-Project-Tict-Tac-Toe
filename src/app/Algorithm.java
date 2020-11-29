package app;
/**
 * Interface to faciliate switching between algorithms
 */

public interface Algorithm {

    public double solve(Ilayout current, Ilayout goal, Heuristics h);
}
