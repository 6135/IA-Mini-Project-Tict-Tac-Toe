package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board implements Ilayout, Cloneable {

	private int dim = 3;
	private char[][] board;
	/**
	 * Player that has the next move, not the one that already moved
	 */
	private Agent player;
	private Random rand = new Random();
	/**
	 * This function creates a new Board(initial configuration or goal) based on a input string
	 * @param str configuration of the board 
	 */
	public Board(Agent p){
		this.board = new char[dim][dim];
		this.player = p;

	}

	public Board(char[][] b,Agent p){
		this.board = new char[dim][dim];
		this.player = p;
		for(int i = 0; i < dim; i++)
			System.arraycopy(b[i], 0,this.board[i],0,dim);
	}


	/**
	 * This function creates a new Board with the same List of Stacks and dim as the source
	 * @param source current Board
	 */
	public Board(Board source){
		this.board = new char[dim][dim];
		this.player = source.player;
		for(int i = 0; i < dim; i++)
			System.arraycopy(source.board[i], 0,this.board[i],0,dim);
		this.dim = source.getDim();
	}

	/**
	 * @return current Board clone
	 */
	@Override
	public Object clone(){
		return new Board(this);
	}

	/**
	 * @return Returns the dimension of the Board
	 */
	public int getDim(){
		return dim;
	}
    /**
     * @return comparison function so that contains works properly
     */
	public boolean equals(Object l) {
		Board b;

		if(l instanceof Board)
			b = (Board) l;
		else return false;
		char[][] rotation90 = RotateArray(board, dim);
		char[][] rotation180 = RotateArray(rotation90, dim);
		char[][] rotation270 = RotateArray(rotation180, dim);

		return Arrays.deepEquals(board, b.board) ||
			Arrays.deepEquals(rotation90, b.board) ||
			Arrays.deepEquals(rotation180, b.board) ||
			Arrays.deepEquals(rotation270, b.board);
	}
	// @Override
	// public boolean equals(Object l){
	// 	Board b;
	// 	if(l instanceof Board)
	// 		b = (Board) l;
	// 	else return false;

	// 	for(int i = 0; i < dim; i++)
	// 		for(int j = 0; j < dim; j++){
	// 			char bPos = board[i][j];
	// 			int xOper = operateRotation(i, dim);
	// 			if( bPos != b.board[i][j] && 
	// 				bPos != b.board[j][xOper] &&
	// 				bPos != b.board[xOper][j] &&
	// 				bPos != b.board[j][i]
	// 				)
	// 				return false;
	// 		}
	// 	return true;	
	// }
	public static char[][] RotateArray(char[][] matrix, int dim) {
		char[][] ret = new char[dim][dim];
	
		for (int i = 0; i < dim; ++i) {
			for (int j = 0; j < dim; ++j) {
				ret[i][j] = matrix[dim - j - 1][i];
			}
		}
	
		return ret;
	}
	/**
	 * @return List of all possible movements, excluding one that equals the initial layout, and any repeated 
	 */
    @Override
    public List<Ilayout> children() {
        List<Ilayout> children = new ArrayList<>();
		Board b;
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				if(board[i][j]=='\0'){
					b = (Board) this.clone();
					b.player = player.opponent(); // next to move
					b.board[i][j]=player.getSymbol(); //that moved
					if(!children.contains(b) )
						children.add(b);
					
				}
			}
		}
		//Collections.shuffle(children);
        return children;
	}


	public boolean uncloseHoles(){
		for (int i = 0; i < dim; i++) {
			if( (closeCol(i,player.getSymbol())==dim-1 && closeCol(i,'\0')==1) || (closeRow(i,player.getSymbol())==dim-1 && closeRow(i,'\0')==1))
				return true;
		}
		return ( (closeLRD(player.getSymbol())== dim-1 && closeLRD('\0')==1) || (closeRLD(player.getSymbol())== dim-1 && closeRLD('\0')==1) );
	}

	private int closeCol(int col,char c){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[i][col]==c) n++;
		}
		return n;
	}

	private int closeRow(int row,char c){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[row][i]==c) n++;
		}
		return n;
	}

	private int closeLRD(char c){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][i]==c) n++;
		return n;
	}

	private int closeRLD(char c){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][(board.length-1)-i] == c) n++;
		return n;
	}

	public boolean terminal(){
		return anyVictory() || full(); 
	}
	
    public char status(){
		if(player.opponent() == null)
			System.out.println("err null");
		return victory() ? (char) player.opponent().getSymbol() : loss() ? (char) player.getSymbol() : full() ? 'f' : 'i';
	}
	
	private boolean anyVictory(){
        for (int i = 0; i < 3; i++)
            if((checkRow(i)) || (checkCol(i)))
                return true;
        return (checkLRD() || checkRLD());			
	}

	public boolean victory(){
		Agent opponent = player.opponent();
		char mySmbol = opponent.getSymbol();
        for (int i = 0; i < 3; i++)
            if((checkRow(i) && board[i][0] ==  mySmbol) || (checkCol(i) && board[0][i] ==  mySmbol))
                return true;
        return (checkLRD() || checkRLD()) && board[dim/2][dim/2]== mySmbol;		
	}

    public boolean loss(){
		char oppSmbol = player.getSymbol();
        for (int i = 0; i < 3; i++)
            if((checkRow(i) && board[i][0] ==  oppSmbol) || (checkCol(i) && board[0][i] ==  oppSmbol))
                return true;
        return (checkLRD() || checkRLD()) && board[dim/2][dim/2]== oppSmbol;
    }

    private boolean checkRow(int row){
        if(board[row][0]=='\0')
            return false;
        for (int i = 1; i < board[row].length; i++) 
            if(board[row][i-1] != board[row][i])
                return false;
        return true;
    }

    private boolean checkCol(int col){
        if(board[0][col]=='\0')
            return false;
        for (int i = 1; i < board.length; i++)
            if(board[i-1][col] != board[i][col])
                return false;
        return true;
    }

    private boolean checkLRD(){
        if(board[0][0]=='\0')
            return false;
        for (int i = 1; i < board.length; i++) 
            if(board[i-1][i-1] != board[i][i])
                return false;
        return true;
    }

    private boolean checkRLD(){
        
        if(board[0][2]=='\0')
            return false;
        for (int i = 1; i < board.length; i++) 
            if(board[i-1][board.length-i] != board[i][board.length-(i+1)])
                return false;
        return true;
    }
	/**
	 * 
	 * @return
	 */
    private boolean full(){
        for (int row = 0; row < 3; row++) 
            for (int col = 0; col < 3; col++)
                if(board[row][col]=='\0')
                    return false;
        return true;       
    }

	/**
	 * @return Returns the configuration that represents each stack of blocks in a separated row. Each stack is within
	 * [] and two consecutive blocks within the same stack are separated by a comma followed by a space.
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				char c = board[i][j];
				if(c =='\0')
					s.append("- ");
				else
					s.append(c+" ");
			}
			s.append('\n');
		}
		return s.toString();
	}
	
	public String flatToString(){
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < dim; i++){
			for(int j = 0; j < dim; j++){
				char c = board[i][j];
				if(c =='\0')
					s.append("-");
				else
					s.append(c);
			}
			s.append(" ");
		}
		return s.toString();
	}

	public Board move(int pos) throws IndexOutOfBoundsException,IllegalStateException{
		//System.out.println("Active agent before move" + player.getName());
		if(pos > 8 || pos < 0)
			throw new IndexOutOfBoundsException("Position must be a value between 0 and 8");
		Board copy = new Board(this);
		int r = (pos/dim);
		int c = (pos%dim);
		if(copy.board[r][c] != '\0')
			throw new IllegalStateException("This spot is already filled in, try a new move");
		else copy.board[r][c] = player.getSymbol();
		copy.player=player.opponent();
		//System.out.println("Active agent after move: " + copy.player.getName());
		return copy;	
	}

	private Ilayout lightPlayout(){
		Board copy = new Board(this);
		int i = rand.nextInt(dim*dim);
		int r = (i/dim);
		int c = (i%dim);
		if(copy.board[r][c] == '\0'){
			copy.board[r][c] = player.getSymbol();
			copy.player = player.opponent();
			return copy;
		}
		else return lightPlayout();
	}

	private Ilayout heavyPlayout(){
		/**
		 * Part I - return imediate win;
		 * Part II - If there is no win and opponent can win next turn fill holes
		 * Part III - If no other condition applies random(future heuristics)
		 */
		Board copy = new Board(this);

		List<Ilayout> children = copy.children();
		for (Ilayout c : children) {
			if(c.status()==copy.getAgent().getSymbol()) return c;
			//else if(!c.uncloseHoles())
		}

		return copy;
	}

	@Override
	public Ilayout playout(){
		return heavyPlayout();
	}

	@Override
	public void resultMessage() {
		char status = status();
		if(status == player.getSymbol())
			System.out.println(player.getName() + " has won the game");
		else if(status == 'f')
			System.out.println("Game draw!");
		else if(status == player.opponent().getSymbol())
			System.out.println(player.opponent().getName() + " has won the game");
		else System.out.println("Unknown message.");

	}
	@Override
	public void setAgent(Agent agent){this.player = agent;}
	@Override
	public Agent getAgent(){return player;}

}