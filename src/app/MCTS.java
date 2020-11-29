package app;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class MCTS implements Algorithm {

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
    public final void MCTsSearch(Ilayout s0){
        State v0 = new State(s0,null);
        long startTime = System.nanoTime();
        while(((System.nanoTime() - startTime)/1_000_000_000.0) < 121){
            State vl = MCTsTreePolicy(v0);
            int win_or_loss = MCTsDefaultPolicy(MCTsSim(vl));
            vl = MCTsBackup(vl, win_or_loss);
        }  
        //return MCTsBestChild(v0);
    }

    private State MCTsBackup(State vl, int win_or_loss) {
        return null;
    }

    private int MCTsDefaultPolicy(Object mcTsSim) {
        return 0;
    }

    private State MCTsTreePolicy(State v0) {
        return null;
    }

    private Object MCTsSim(State vl) {
        return null;
    }

} 