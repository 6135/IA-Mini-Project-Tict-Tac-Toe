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
	 * This function creates a new Board
	 * @param p Agent that has the next move
	 */
	public Board(Agent p){
		this.board = new char[dim][dim];
		this.player = p;

	}

	/**
	 * This function create a new Board
	 * @param b bidimensional array that represents the state of the board
	 * @param p Agent that has the next move
	 */
	public Board(char[][] b,Agent p){
		this.board = new char[dim][dim];
		this.player = p;
		for(int i = 0; i < dim; i++)
			System.arraycopy(b[i], 0,this.board[i],0,dim);
	}


	/**
	 * This function creates a new Board with the same bidimensional array and dim as the source
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
        return children;
	}

	/**
	 * This function allows checking the number of positions that can take the Agent to a victory
	 * @param a Agent's Symbol that needs to be checked
	 * @return the number of unclosed holes,meaning the number of positions that can take him to victory
	 */
	public int uncloseHoles(Agent a){
		int h=0;
		for (int i = 0; i < dim; i++) {
			if(closeCol(i,a.getSymbol())==dim-1 && closeCol(i,'\0')==1) h++;
			if(closeRow(i,a.getSymbol())==dim-1 && closeRow(i,'\0')==1) h++;
		}
		if(closeLRD(a.getSymbol())== dim-1 && closeLRD('\0')==1) h++;
		if(closeRLD(a.getSymbol())== dim-1 && closeRLD('\0')==1) h++;
		
		return h;
	}

	/**
	 * 
	 * @param col the column to be checked
	 * @param c the Agent's Symbol 
	 * @return the number of positions in column <i>col</i> that have that symbol
	 */
	private int closeCol(int col,char c){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[i][col]==c) n++;
		}
		return n;
	}

	/**
	 * 
	 * @param row the row to be checked
	 * @param c the Agent's Symbol
	 * @return the number of positions in row that have that symbol
	 */
	private int closeRow(int row,char c){
		int n=0;
		for (int i = 0; i < dim; i++) {
			if(board[row][i]==c) n++;
		}
		return n;
	}
	
	/**
	 * 
	 * @param c the Agent's Symbol
	 * @return the number of positions in LRD(Left to Right Diagonal) that have that symbol
	 */
	public int closeLRD(char c){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][i]==c) n++;
		return n;
	}

	/**
	 * 
	 * @param c the Agent's Symbol
	 * @return he number of positions in RLD(Right to Left Diagonal) that have that symbol
	 */
	private int closeRLD(char c){
		int n=0;
		for (int i = 0; i < board.length; i++) 
			if(board[i][(board.length-1)-i] == c) n++;
		return n;
	}

	/**
	 * @return true if the board is in a terminal state, meaning there is a victory or it's full, or false if the is in a inconclusive state
	 */
	public boolean terminal(){
		return status()!='i'; 
	}
	
    public char status(){
		if(player.opponent() == null)
			System.out.println("error opponent is null");
		return victory() ? (char) player.opponent().getSymbol() : loss() ? (char) player.getSymbol() : full() ? 'f' : 'i';
	}
	
	/**
	 * 
	 * @return true if there is a winner and it's the last Agent that played, false if otherwise
	 */
	public boolean victory(){
		Agent opponent = player.opponent();
		char mySmbol = opponent.getSymbol();
        for (int i = 0; i < 3; i++)
            if(closeRow(i, mySmbol)==dim || closeCol(i, mySmbol)==dim)
                return true;
        return (closeLRD(mySmbol)==dim || closeRLD(mySmbol)==dim);		
	}

	/**
	 * 
	 * @return true if there is a loser and it's the Agent that has the next move, false otherwise
	 */
    public boolean loss(){
		char oppSmbol = player.getSymbol();
        for (int i = 0; i < 3; i++)
            if(closeRow(i, oppSmbol)==dim || closeCol(i, oppSmbol)==dim)
                return true;
        return (closeLRD(oppSmbol)==dim || closeRLD(oppSmbol)==dim);
    }

	/**
	 * 
	 * @return true if there is no more plays to be made, false otherwise
	 */
    private boolean full(){
        for (int row = 0; row < 3; row++) 
            for (int col = 0; col < 3; col++)
                if(board[row][col]=='\0')
                    return false;
        return true;       
    }

	/**
	 * @return Returns the configuration that represents the board{@} with the empty positons represented by <i>-<i/>
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

	/**
	 * 
	 * @param pos position chosen by the Player
	 * @return a Board resulting from the Player's move
	 */
	public Board moveToPos(int pos) throws IndexOutOfBoundsException,IllegalStateException{
		if(pos > 8 || pos < 0)
			throw new IndexOutOfBoundsException("Position must be a value between 0 and 8");
		Board copy = new Board(this);
		int r = (pos/dim);
		int c = (pos%dim);
		if(copy.board[r][c] != '\0')
			throw new IllegalStateException("This spot is already filled in, try a new move");
		else copy.board[r][c] = player.getSymbol();
		copy.player=player.opponent();

		return copy;	
	}

	/**
	 * @return a randomly moved Board 
	 */
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

	/**
	 * 
	 * Part I - return imediate win;
	 * Part II - If there is no win and opponent can win next turn fill holes
     * Part III - If no other condition applies random
	 * 
	 * @return The best possible Board played by the Simulation, this can be by winning or blocking the other Agent from winning, if none of those apply a randomly moved Board will be returned 
	 */
	private Ilayout heavyPlayout(){
		Board copy = new Board(this);
		int cH=uncloseHoles(copy.player.opponent());
		
		Ilayout child = null;
		List<Ilayout> children = copy.children();
		for (Ilayout c : children) {	
			if(c.status()==copy.getAgent().getSymbol() ) return c;
			if( cH> ((Board)c).uncloseHoles(c.getAgent())) child=c;
		}
		if(child!=null) return child;
			

		return lightPlayout();
	}

	@Override
	public Ilayout playout(){
		return heavyPlayout();
	}

	/**
	 * This function prints out the result of the game
	 */
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