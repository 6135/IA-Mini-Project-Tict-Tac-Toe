package app;

import java.util.Collections;

/**
 * MCTS
 */
public class MCTS {
    Player mcts;
    Player opponent;

    public MCTS(Player mcts){
        this.mcts = mcts;
        opponent = mcts.opponent();
    }

    public State search(State root){
        if(!root.getLayout().getPlayer().equals(opponent)){
            System.out.println("Wrong player were given");
            return null;
        }
        for(int iteration = 0; iteration < 10_000;iteration++){
            /* Phase 1 - Selection*/
            State selected = mctsStateSelection(root);
            /* Phase 2 - Expansion*/
            if(!selected.getLayout().terminal())
                mctsStateExpansion(selected);
            /* Phase 3 - Simulation */
            for(State s : selected.getChildArray()){
                char result = mctsStateSimulate(s);
            }
        }
    }
    
    private State mctsStateSelection(State root){
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            if(selected.getLayout().getPlayer().equals(mcts))
                selected = State.bestEnemyChildUCB(selected);
            else if(selected.getLayout().getPlayer().equals(opponent))
                selected = State.bestChildUCB(selected);
        return selected;
    }

    private void mctsStateExpansion(State selected) {
        selected.makeChildren();
    }

    private char mctsStateSimulate(State selected){
        Board temp = new Board((Board)selected.getLayout());
        while(!temp.terminal())
            temp.randomMove();
        return temp.status();
    }

    private void mctsBackPropagation(State selected, char result){
        State temp = selected;
        while(temp != null){
            temp.visit();
            if(result == 'v')
        }
    }

}