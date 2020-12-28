package app;

/**
 * MCTS
 */
public class MCTS implements Agent{
    private String agentName;
    private char symbol;
    private Agent opponent;

    public MCTS(char symbol){
        this.agentName = "CPU";
        this.symbol = symbol;

    }

    public Board move(Board b){
        State root = new State(b, null);
        if(!root.getLayout().getAgent().equals(this)){
            return null;
        }
        for(int iteration = 0; iteration < 10000;iteration++){
            /* Phase 1 - Selection */
            State selected = mctsStateSelection(root);
            /* Phase 2 - Expansion */
            if(!selected.getLayout().terminal())
                mctsStateExpansion(selected);
            /* Phase 3 - Simulation */
            for(State s : selected.getChildArray()){
                char result = mctsStateSimulate(s);
                /* Phase 4 - BackPropagation */
                mctsBackPropagation(s, result);
            }
        }
        System.out.println(root.getVisitCount() + " " + root.getWinScore());
        System.out.println(root.getChildArray());
        return (Board) State.bestChildUCB(root).getLayout();
    }
    
    private State mctsStateSelection(State root){
        // TODO: Method is probably wron taking into cosideration that ROOT stores the player that has the next Move and not the player that has moved
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            selected = State.bestChildUCB(selected);
            /*if(selected.getLayout().getAgent().equals(this))
                selected = State.bestEnemyChildUCB(selected);
            else if(selected.getLayout().getAgent().equals(opponent))
                selected = State.bestChildUCB(selected);*/
        return selected;
    }

    private void mctsStateExpansion(State selected) {
        selected.makeChildren();
        //System.out.println(selected.getChildArray());
    }

    private char mctsStateSimulate(State selected){
        Board temp = new Board((Board)selected.getLayout());
        while(!temp.terminal())
            temp = temp.randomMove();
            
        return temp.status();
    }

    private void mctsBackPropagation(State selected, char result){
        State temp = selected;
        while(temp != null){
            temp.visit();
            if(result == getSymbol())
                temp.addWinScore(1);
             if(result == 'f')
                 temp.addWinScore(0.5);
            temp = temp.getParent();
        }
    }

    @Override
    public void setOpponent(Agent a) {this.opponent = a;}

    @Override
    public Agent opponent() {return opponent;}

    @Override
    public char getSymbol() {return symbol;}

    @Override
    public void setSymbol(char symbol) {this.symbol = symbol;}

    @Override
    public void setName(String name) {this.agentName = name;}

    @Override
    public String getName() {return agentName;}
    @Override
    public int hashCode() {
        return getSymbol();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof MCTS){
            MCTS mcts = (MCTS) o;
            return getName() == mcts.getName() && getSymbol() == mcts.getSymbol();
        }
        return false;
    }
}