package app;
/**
 * Interface to faciliate switching between Heuristics
 */

public interface Heuristics {
    double getH(Ilayout current, Ilayout goal);
}
