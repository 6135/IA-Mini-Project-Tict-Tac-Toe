package app;

import java.util.Collections;

/**
 * MCTS
 */
public class MCTS implements Agent{
    private String agentName;
    private char symbol;
    private Agent opponent;

    public MCTS(char symbol, Agent opponent){
        this.agentName = "mcts";
        this.symbol = symbol;

    }

    public Board move(Board b){
        State root = new State(b, null);
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
                mctsBackPropagation(selected, result);
            }
        }
        return (Board) State.bestChildUCB(root).getLayout();
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
            if(result == getSymbol())
                temp.addWinScore(1.0);
            else if (result == 'f')
                temp.addWinScore(0.5);
            temp = temp.getParent();
        }
    }

    @Override
    public void setOpponent(Agent a) {this.opponent = a;}

    @Override
    public Agent opponent() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public char getSymbol() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setSymbol(char symbol) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}