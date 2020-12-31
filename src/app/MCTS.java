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
        for(int iteration = 0; iteration < 1000;iteration++){
            //System.out.println(iteration);
            /* Phase 1 - Selection */
            State selected = mctsStateSelection(root);
            /* Phase 2 - Expansion */
            if(!selected.getLayout().terminal())
                mctsStateExpansion(selected);
            /* Phase 3 - Simulation */
            for(State s : selected.getChildArray()){
                char result = mctsStateSimulate(s);
                /* Phase 4 - BackPropagation */
                //System.out.println(result);
                mctsBackPropagation(s, result);
            }
        }
        //System.out.println(root.getVisitCount() + " " + root.getWinScore());
        //System.out.println(root.getChildArray());
        return (Board) State.bestChildScore(root).getLayout();
    }
    
    private State mctsStateSelection(State root){
        // TODO: Method is probably wrong taking into cosideration that ROOT stores the player that has the next Move and not the player that has moved
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            //if(selected.getLayout().getAgent().equals(this))
                selected = State.bestChildUCB(selected);
            //else if(selected.getLayout().getAgent().equals(opponent))
                //selected = State.bestEnemyChildUCB(selected);
        return selected;
    }

    private void mctsStateExpansion(State selected) {
        if(!selected.getLayout().terminal())
            selected.makeChildren();
        //System.out.println(selected.getChildArray());
    }

    private char mctsStateSimulate(State selected){
        Board temp = new Board((Board)selected.getLayout());
        if(temp.status() == opponent.getSymbol()){
            //System.out.println("here");
            selected.setWinCount(Integer.MIN_VALUE);
            return temp.status();
        }
        while(!temp.terminal())
            temp = temp.randomMove();
            
        return temp.status();
    }

    private void mctsBackPropagation(State selected, char result){
        State temp = selected;
        while(temp != null){
            temp.visit();
            // if(result == 'f'){
            //     //System.out.println(temp.agentThatMoved().getName());
            //     if(temp.agentThatMoved().equals(this))
            //         temp.addWinScore(0.5);
            //     else temp.addWinScore(-0.5);
            // }
            // if(result == getSymbol()){
            //     //System.out.println(temp.agentThatMoved().getName());
            //     if(temp.agentThatMoved().equals(this))
            //         temp.addWinScore(1);
            //     else temp.addWinScore(-1);
            // }
            if(temp.agentThatMoved().getSymbol() == result)
                temp.addWinScore(1.0);
            if(result == 'f')
                temp.addWinScore(1);
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