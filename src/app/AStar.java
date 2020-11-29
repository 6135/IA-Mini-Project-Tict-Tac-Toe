package app;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AStar implements Algorithm{

    protected Queue<State> abertos;
    
	public static class State {
		private Ilayout layout;
		private State father;
		private double g;
		
		public State(Ilayout l, State n) {
			layout = l;
			father = n;
			if (father != null) {
				g = father.g + l.getG();
			}	
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
		public String toString() {
			return layout.toString();
		}

		public double getG() {
			return g;
		}

        public double getF(Ilayout goal, Heuristics h) {
            return h.getH(layout,goal) + getG();
        }

	}
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
    
    public final double solve(Ilayout s, Ilayout goal, Heuristics h){
        abertos = new PriorityQueue<>(10,(s1,s2) -> (int) Math.signum(s1.getF(goal,h)-s2.getF(goal,h)));
        List<State> sucs;
        List<State> sequencia = new ArrayList<>();
        State actual;
        List<State> fechados = new ArrayList<>();
        abertos.add(new State(s,null));

        while(sequencia.isEmpty()){
            if(abertos.isEmpty())
               System.exit(1);
            actual = abertos.poll();
            if(actual.layout.isGoal(goal)){
                return actual.getG();
            } else {
                sucs=sucesssores(actual);
                fechados.add(actual);
                Iterator<State> itr=sucs.iterator();
                while(itr.hasNext()){
                    State temp=itr.next();
                    if(!fechados.contains(temp))
                        abertos.add(temp);
                }
            }
        }
        return -1;
    }

} 