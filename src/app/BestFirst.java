package app;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class BestFirst implements Algorithm {

    protected Queue<State> abertos;
    private List<State> fechados;
    private State actual;
    private Ilayout objective;
    
    public static class State{
        private Ilayout layout;
        private State father;
        private double g;
        
        public State(Ilayout l,State n){
            layout=l;
            father=n;
            if(father!=null)
                g=father.g + l.getG();
            else g=0.0;
        }
        @Override
        public int hashCode(){
            return 1; //doesnt matter for BestFirst Class
        }

        @Override
        public boolean equals(Object state){
            State s = null;
            if(state instanceof State) {
                s = (State) state;
                if(layout.equals(s.layout) && g==s.getG() && father.layout.equals(s.father.layout))
                    return true;
            }
            return false;
        }
        @Override
        public String toString(){ 
            return layout.toString();
        }

        public double getG(){return g;}
        /**
         * Added only for testing porpuses, nothing else
         * @return the layout of the state
         */
        public Ilayout getLayout(){return layout;}
    }

    private final List<State> sucesssores(State n){
        List<State> sucs = new ArrayList<>();
        List <Ilayout> children = n.layout.children();
        for(Ilayout e : children){
            if(n.father == null || !e.equals(n.father.layout) ){
                State nn=new State(e,n);
                sucs.add(nn);
            } 
        }
        return sucs;
    }
    public final double solve(Ilayout s, Ilayout goal, Heuristics h){
        abertos = new PriorityQueue<>(10,(s1,s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        List<State> sucs;
        List<State> sequencia = new ArrayList<>();
        objective=goal;
        
        fechados = new ArrayList<>();
        abertos.add(new State(s,null));

        while(sequencia.isEmpty() && abertos.size()<400000){
            if(abertos.isEmpty())
               System.exit(1);
            actual=abertos.poll();
            if(objective.isGoal(actual.layout)){
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