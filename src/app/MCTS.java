package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


public class MCTS {

    protected Queue<State> abertos;
    private Ilayout objective;
    private State path;

    /**
     *  @param n initial state
     * @return a list of  States from all possibles states from  n
     */
	private final List<State> sucesssores(State s, Player p) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = s.layout().children(p);
		for (Ilayout e : children) {
			if (s.parent() == null || !e.equals(s.parent().layout())) {
				State nn = new State(e,p.opponent(),0,0,s);
				sucs.add(nn);
			}
		}
		return sucs;
    }
    
    Comparator<State> cmpState = new Comparator<State>(){
        private double stateExp(State o){
            try {
                double firstExper = o.winScore()/o.visitCount();
                double secondExper = Math.sqrt((2*(Math.log(o.parent().visitCount()))/o.visitCount()));
                double expr = firstExper - secondExper;
                o.setTreePolicy(expr);
                return expr;
            } catch(Exception e) {
                return Integer.MAX_VALUE;
            }
        }
        
        @Override
        public int compare(State o1, State o2) {
           return (int) Math.signum(stateExp(o1)-stateExp(o2));
        }
        
    };

    /**
     * @param start Board with initial configuration
     * @param goal Board that we want to achieve
     * @param h heuristic used to solve the problem
     * @return Returns g cost to achieve goal from start 
     */
    public final void MCTsSearch(Ilayout s0, Player p){
        State v0 = new State(s0,p,0,0,null);
        long startTime = System.nanoTime();
        PriorityQueue<State> pq = new PriorityQueue<>(cmpState);
        pq.add(v0);
        while(((System.nanoTime() - startTime)/1_000_000_000.0) < 121){
            State vl = MCTsTreePolicy(v0); 
            int win_or_loss = MCTsDefaultPolicy(MCTsSim(vl));
            vl = backPropagation(vl, win_or_loss);
        }  
        //return MCTsBestChild(v0);
    }

    private State backPropagation(State vl, int win_or_loss) {
        if(vl.parent()!=null){
            vl.addWinScore(win_or_loss);
            return backPropagation(vl.parent(),win_or_loss);
        }else return vl;
    }

    private int MCTsDefaultPolicy(Object mcTsSim) {
        return 0;
    }

    private State MCTsTreePolicy(State v0) {
        return null;
    }

    private Object MCTsSim(State vl) {
        while (/*is not terminal*/ ) {
            if()
        }
        return null;
    }




} 