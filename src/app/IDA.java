package app;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class IDA implements Algorithm {

    protected Queue<State> abertos;
    private Ilayout objective;
    private State path;
    public static final class State {
		protected Ilayout layout;
		protected State father;
		protected double g;
        /**
         * This function creates a new State with a configuration l and with a father n
         * @param l configuration of the current state
         * @param n father state of the current state
         */
		public State(Ilayout l, State n) {
			layout = l;
			father = n;
			if (father != null)
				g = father.g + l.getG();
			else
				g = 0.0;
		}
        /**
         * Added only for testing porpuses, nothing else
         * @return the father of the state
         */
        public State getFather(){return father;}
        /**
         * Added only for testing porpuses, nothing else
         * @return the layout of the state
         */
        public Ilayout getLayout(){return layout;}
		public String toString() {return layout.toString();}

		public double getG() {return g;}

        public double getF(Ilayout goal, Heuristics h) {
            return h.getH(layout,goal) + getG();
        }
        public boolean isGoal(Ilayout objective){
            return layout.equals(objective);
        }

    }
    /**
     *  @param n initial state
     * @return a list of  States from all possibles states from  n
     */
	private final List<State> sucesssores(State n) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = n.layout.children();
		for (Ilayout e : children) {
			if (n.father == null || !e.equals(n.father.layout)) {
				State nn = new State(e, n);
				sucs.add(nn);
			}
		}
		return sucs;
    }
    /**
     * @param start Board with initial configuration
     * @param goal Board that we want to achieve
     * @param h heuristic used to solve the problem
     * @return Returns g cost to achieve goal from start 
     */
    public final double solve(Ilayout start, Ilayout goal, Heuristics h){
        objective = goal;
        int bound = (int) h.getH(start,objective);
        path = new State(start,null);
        while(true){
            int t = search(0, bound, h);
            if(t == -1){
                return path.getG();}
            bound = t;
            
        }
    }
    /**
     * This function find the minimum of all f greater than t encountered 
     *@param g cost to achieve the goal
     * @param bound depth threshold of ida
     * @param h heuristic used to solve the problem
     * @return the minimum of all f greater than t encountered 
     */
    private final int search(int g, int bound, Heuristics h){
        double hVal = h.getH(path.layout, objective);
        int f = (int) (g + hVal);
        if(f>bound)
            return f;
        if(hVal == 0)
            return -1;
        int min = 999;
        for(State suc : sucesssores(path)){
            State oldPath = path;
            path = suc;
            int t = search(g + 1,bound,h);
            if(path.isGoal(objective))
                return -1;
            else if(t<min) min = t;
            path = oldPath;
        }
        return min;
 
    }

} 