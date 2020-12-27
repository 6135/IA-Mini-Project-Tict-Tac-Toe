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
                nn.setTreePolicy(-s.treePolicy());
				sucs.add(nn);
			}
		}
		return sucs;
    }


    // Comparator<State> cmpState = new Comparator<State>(){
    //     Player initial;
    //     @Override
    //     public int compare(State o1, State o2) {
    //        return (int) Math.signum(treePolicy(o2)-treePolicy(o1));
    //     }
        
    // };

    /**
     * @param start Board with initial configuration
     * @param goal  Board that we want to achieve
     * @param h     heuristic used to solve the problem
     * @return
     * @return Returns g cost to achieve goal from start
     */
    public final State MCTsSearch(State v0) {
        State root = v0;
        root.setTreePolicy(State.MINVAL);
        int iterations =0;
        while(iterations < 10000){
            /**
             * Selection
             */
            State selected = MCTSSelection(root);
        }
        return MCTsBestChild(root);
    }

    private State MCTSSelection(State root){
        Player initial = root.player();
        State selectionCandidate = root;
        while(!selectionCandidate.childArray().isEmpty()){
            selectionCandidate=Collections.max(selectionCandidate.childArray());
        }
        return selectionCandidate;
    }

    public State MCTSExpansion(State selected){
        List<Ilayout> children = selected.layout().children(selected.player().opponent());

        for (Ilayout board : children) {
           new State(board,selected.player().opponent(),selected);
        }
        return selected;
    }

    private State MCTsSim(State vl, Player p) {
        return null;
    }   

    private State MCTsBackPropagation(State vl, Player p) {
        return null;    
    }

    private State MCTsBestChild(State root) {
        return null;
    }
    



} 