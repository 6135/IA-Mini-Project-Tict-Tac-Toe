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
        s.setChildArray(sucs);
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
        int iterations =0;
        Player p = root.player();
        while(iterations < 1000){
            State selected = MCTSSelection(root);
            MCTSExpansion(selected,p);
            for(State selectedToSim : selected.childArray()) {
                char result = MCTsSim(selectedToSim, p);
                MCTsBackPropagation(selectedToSim, p, result);
            }
           iterations++; 
        }
        return MCTsBestChildSelectionMax(root);
    }

    private State MCTSSelection(State root){
        State selectionCandidate = root;
        Player initial = root.player();
        while(!selectionCandidate.childArray().isEmpty()){
            if(selectionCandidate.player().equals(initial))
                selectionCandidate = MCTsBestChildSelectionMax(selectionCandidate);
            else selectionCandidate = MCTsBestChildSelectionMin(selectionCandidate);
        }
        return selectionCandidate;
    }

    public int MCTSExpansion(State selected, Player p){
        Board b = (Board)selected.layout();
        if(!b.terminal(p)) {
            List<State> children = sucs(selected,selected.player().opponent());
            selected.setChildArray(children);
        }
        return selected.childArray().size();
    }

    private char MCTsSim(State selected, Player p) {
        Player initial = p;
        Board b = ((Board) selected.layout());
        char status = b.status(initial);
        while(status == 'i'){
            p=p.opponent();
            b = b.randMove(p);
            status = b.status(initial);
        }
        return status;
    }   

    private void MCTsBackPropagation(State vl, Player p, char result) {
        State previousState = vl;
        while(previousState != null){
            previousState.visit();
            //System.out.println(result);
            if(result == 'v'){
                previousState.addWinScore(1.0);
            }
                
            previousState = previousState.parent();
        }       
    }
    private State MCTsBestChildSelectionMax(State root) {
        State bestChild=root.childArray().get(0);
        for (State child : root.childArray())
            if(child.treePolicy()>bestChild.treePolicy())
                bestChild = child;
        return bestChild;
    }
    private State MCTsBestChildSelectionMin(State root) {
        State bestChild=root.childArray().get(0);
        for (State child : root.childArray())
            if(child.treePolicy()<bestChild.treePolicy())
                bestChild = child;
        return bestChild;
    }
    



} 