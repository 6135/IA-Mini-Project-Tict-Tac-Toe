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

    public Ilayout move(Ilayout b){
        State root = new State(b, null);
        if(!root.getLayout().getAgent().equals(this)){
            return null;
        }
        for(int iteration = 0; iteration < 5000;iteration++){
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
        return State.bestChildScore(root).getLayout();
    }
    
    private State mctsStateSelection(State root){
        State selected = root;
        while(!selected.getChildArray().isEmpty())
            selected = State.bestChildUCB(selected);
        return selected;
    }

    private void mctsStateExpansion(State selected) {
        if(!selected.getLayout().terminal())
            selected.makeChildren();
        //System.out.println(selected.getChildArray());
    }



    private void mctsBackPropagation(State selected, char result){
        State temp = selected;
        while (temp != null) {
            temp.visit();
            if (temp.agentThatMoved().getSymbol() == result )
                temp.addWinScore(1.0);
            else if (result == 'f')
                temp.addWinScore(0.5);
            temp = temp.getParent();
        }
    }

    private char mctsStateSimulate(State selected){
        Ilayout temp = (Ilayout) selected.getLayout().clone();
        char status = temp.status();
        if(status == opponent.getSymbol()) {
            /**
             * If a child is terminal, and results in a loss, this means that if the parent board is chosen, the game will result in a loss if optimal plays are made
             * So if a child is terminal and lost, we need to tell the algorithm that choosing that board, or even exploring it would be costly and uncesseray as it would lead to game loss
             **/ //
            selected.getParent().setWinCount(Integer.MIN_VALUE);//If the parent board were to be thi
            return temp.status();
        }
        while(!temp.terminal())
            temp = temp.randomMove();
            
        return temp.status();
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