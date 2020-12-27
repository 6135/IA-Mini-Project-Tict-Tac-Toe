package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
	private final List<State> sucs(State s, Player p) {
		List<State> sucs = new ArrayList<>();
		List<Ilayout> children = s.layout().children(p);
		for (Ilayout e : children) {
			if (s.parent() == null || !e.equals(s.parent().layout())) {
				State nn = new State(e,p.opponent(),s);
				sucs.add(nn);
			}
		}
		return sucs;
    }
    /**
    *  @param n initial state
    * @return a list of  States from all possibles states from  n, ordered by formula
    */
	// private final List<State> sucsOrdered(State s, Player p) {
    //     PriorityQueue<State> sucs = new PriorityQueue<>(cmpState);
	// 	List<Ilayout> children = s.layout().children(p);
	// 	for (Ilayout e : children) {
	// 		if (s.parent() == null || !e.equals(s.parent().layout())) {
	// 			State nn = new State(e,p.opponent(),0,0,s);
	// 			sucs.add(nn);
	// 		}
	// 	}
	// 	return new ArrayList<>(sucs);
    // }

    private double treePolicy(State o){
        try {
            double firstExper = o.winScore()/o.visitCount();
            double secondExper = Math.sqrt((2*(Math.log(o.parent().visitCount()))/o.visitCount()));
            double expr = firstExper + secondExper;
            o.setTreePolicy(expr);
            return expr;
        } catch(Exception e) {
            return Integer.MAX_VALUE;
        }
    }
    Comparator<State> cmpState = new Comparator<State>(){
        
        @Override
        public int compare(State o1, State o2) {
           return (int) Math.signum(treePolicy(o1)-treePolicy(o2));
        }
        
    };

    /**
     * @param start Board with initial configuration
     * @param goal  Board that we want to achieve
     * @param h     heuristic used to solve the problem
     * @return
     * @return Returns g cost to achieve goal from start
     */
    public final State MCTsSearch(Ilayout s0, Player p) {
        State v0 = new State(s0,p,null);
        List<State> expanded;
        PriorityQueue<State> selectableNodes = new PriorityQueue<>(cmpState);
        selectableNodes.add(v0);
        int iteration = 0;
        while(!selectableNodes.isEmpty()){
            //System.out.println(selectableNodes.size());
            State selected = null;
            if(iteration%2==0){
                selected = selectableNodes.poll();
            }else {
                Iterator<State> itr = selectableNodes.iterator();
                while(itr.hasNext())
                    selected = itr.next();
                if(selected != null)
                    selectableNodes.remove(selected);
            }
            if(selected == null)
                return MCTsBestChild(v0);
            expanded = new ArrayList<>(selected.children(p.opponent()));
            
            while(!expanded.isEmpty()){

                selected = expanded.remove(expanded.size()-1);
                selected = MCTsSim(selected, p);

                //backpropagate
                selected = MCTsBackPropagation(selected);
                if(!((Board)selected.layout()).terminal(p))
                    selectableNodes.add(selected);

            }
            
            iteration++;
        }  
        return MCTsBestChild(v0);
    }

    private State MCTsBestChild(State root) {
        State maxed = Collections.max(root.childArray(),cmpState);
        System.out.println(maxed.treePolicy());
        System.out.println(maxed.winScore());
        System.out.println(maxed.visitCount());
        System.out.println(maxed.parent().visitCount());
        return maxed;
    }

    private State MCTsBackPropagation(State vl) {
        State selected = vl;
        State parent = selected.parent();
        while(parent != null){
            parent.addVisits(selected.visitCount());
            parent.addWinScore(selected.winScore());
            parent = parent.parent();
        }
        return selected;
    }

    private State MCTsSim(State vl, Player p) {
        Player initial = p;
        p=p.opponent(); //the player that moves after is the opponent of whoever moved first
        Board b = new Board((Board) vl.layout());
        while ( !b.terminal(initial) ) {
            b=b.randMove(p);
            p=p.opponent();
        }
        char status = b.status(initial);
        if(status == 'v')
            vl.addWinScore(1.0);
        else if(status == 'l')
            vl.addWinScore(-1.0);
        else if(status=='f') vl.addWinScore(0.5);
        
        vl.visit();
        /*System.out.println(b);
        if(status == 'v' && b.victory(initial))
            System.out.println(status);
        else if(status=='v')
            System.out.println('l');
        else System.out.println('f');*/
        
        return vl;
    }



} 