package app;

import java.util.ArrayList;
import java.util.List;
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
        p = p.opponent();
		List<Ilayout> children = s.layout().children(p);
		for (Ilayout e : children) {
            //System.out.println(e);
			if (s.parent() == null || !e.equals(s.parent().layout())) {
                State nn = new State(e,p,s);
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
    public State MCTsSearch(State rootNode) {

        Player opponent = rootNode.player().opponent();

        int iter = 0;
        while (iter < 10000) {
            // Phase 1 - Selection
            State promisingNode = MCTSSelection(rootNode); //done
            // Phase 2 - Expansion
            if ( ((Board)promisingNode.layout()).status(rootNode.player()) == 'i')
                expandState(promisingNode);

            // Phase 3 - Simulation
            State nodeToExplore = promisingNode;
            if (promisingNode.childArray().size() > 0) {
                nodeToExplore = promisingNode.getRandomChildState();
            }
            char playoutResult = simulateRandomPlayout(nodeToExplore,rootNode.player());
            // Phase 4 - Update
            backPropogation(nodeToExplore, playoutResult);
            iter++;
        }
        
        State winnerNode = rootNode.getChildWithMaxScore();
        return winnerNode;
    }
    private State MCTSSelection(State root){
        State selectionCandidate = root;
        while(!selectionCandidate.childArray().isEmpty()){
            selectionCandidate =State.findBestStateWithUCTMax(selectionCandidate);
        }
        return selectionCandidate;
    }

    // public int MCTSExpansion(State selected, Player p){
    //     Board b = (Board)selected.layout();
    //     //System.out.println(p.getPlayer());
    //     //System.out.println(selected);
    //     if(!b.terminal(p)) {
    //         List<State> children = sucs(selected,selected.player());
    //         selected.setChildArray(children);
    //     }
    //     //System.out.println(selected.childArray());
    //     return selected.childArray().size();
    // }

    private void expandState(State state) {
        state.setChildArray(state.children(state.player().opponent()));
    }

    // private char MCTsSim(State selected, Player p) {
    //     Player initial = p;
    //     p = selected.player();
    //     Board b = ((Board) selected.layout());
    //     char status = b.status(initial);
    //     //System.out.println("simStart: ");
    //     //System.out.println(selected.layout().toString());
    //     while(status == 'i'){
    //         p=p.opponent();
    //         b = b.randMove(p);
    //         //System.out.println(b);
    //         status = b.status(initial);
    //     }
    //     return status;
    // }   

    private char simulateRandomPlayout(State state, Player initial) {
        Board tempBoard = new Board((Board) state.layout());
        State tempState = new State(tempBoard,state.player(),state.parent());
        char boardStatus = ((Board)tempState.layout()).status(initial);

        if (boardStatus == 'l') {
            state.parent().setWinScore(State.MINVAL);
            return boardStatus;
        }

        while (boardStatus == 'i') {
            //System.out.println(boardStatus);
            tempState.switchPlayer();
            tempState.setLayout(((Board)tempState.layout()).randMove(tempState.player()));
            boardStatus = ((Board)tempState.layout()).status(initial);
            //System.out.println(tempState.layout());
        }
        if(boardStatus == 'v')
            return tempState.player().getSymbol();
        else if(boardStatus == 'l')
            return tempState.player().opponent().getSymbol();
        else return boardStatus;
    }

    // private void MCTsBackPropagation(State vl, char result) {
    //     State previousState = vl;

    //     //System.out.println("back propagate");
        
    //     while(previousState != null){
    //         previousState.visit();
    //         //System.out.println(result);
    //         if(result == 'v'){
    //             previousState.addWinScore(10.0);
    //             //System.out.println(previousState.winScore());
    //         }
    //         previousState = previousState.parent();
    //     } 
    //     //System.out.println(vl.winScore());     
    // }

    private void backPropogation(State stateToExplore, char result) {
        State tempState = stateToExplore;
        while (tempState != null) {
            tempState.visit();
            if (tempState.player().getSymbol() == result)
                tempState.addWinScore(10);
            tempState = tempState.parent();
        }
    }

    // private State MCTsBestChildSelectionMaxRatio(State root) {
    //     State bestChild=root.childArray().get(0);
    //     //System.out.println(root.childArray());
    //     for (State child : root.childArray())
    //         if(child.winScore()/child.visitCount()>bestChild.winScore()/bestChild.visitCount())
    //             bestChild = child;
    //     return bestChild;
    // }
    // private State MCTsBestChildSelectionMax(State root) {
    //     State bestChild=root.childArray().get(0);
    //     //System.out.println(root.childArray());
    //     for (State child : root.childArray())
    //         if(child.treePolicy()>bestChild.treePolicy())
    //             bestChild = child;
    //     return bestChild;
    // }

    // private State MCTsBestChildSelectionMin(State root) {
    //     State bestChild=root.childArray().get(0);
    //     for (State child : root.childArray())
    //         if(child.treePolicy()<bestChild.treePolicy())
    //             bestChild = child;
    //     return bestChild;
    // }
    



} 