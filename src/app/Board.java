package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board implements Ilayout, Cloneable {

	private int dim = 3;
	private char board [][];
	private Random rand = new Random();
	/**
	 * This function creates a new Board(initial configuration or goal) based on a input string
	 * @param str configuration of the board 
	 */
	public Board(){
		this.board = new char[dim][dim];

	}

	/**
	 * This function creates a new Board(child) with a List of Stacks generated in the function children
	 * @param sts List of Stacks of the board
	 */
	public Board(String str, Player p){
		board= new char[dim][dim];
		int pos=Character.getNumericValue(str.charAt(0));
		int r = (int) (pos/dim);
		int c = (pos%dim);
		board[r][c]=p.getSymbol();
	}

	/**
	 * This function creates a new Board with the same List of Stacks and dim as the source
	 * @param source current Board
	 */
	public Board(Board source){
		this.board = new char[dim][dim];
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
     * Implemented for good measure
     * @return the hash code of the given board
     */
    @Override
    public int hashCode() {
		return Arrays.hashCode(board);
	}
    /**
     * @return comparison function so that contains works properly
     */
	@Override
	public boolean equals(Object l) {
		return false;
		
	}
    /**
     * @return true if the receiver equals the argument 'I'; return false otherwise
     */	
	public boolean isGoal(Ilayout l) {
		return equals(l);
	}

	@Override
	public double getG() {
		return 1.0;					
	}

	/**
	 * @return List of all possible movements, excluding one that equals the initial layout, and any repeated 
	 */
    @Override
    public List<Ilayout> children(Player p) {
        List<Ilayout> children = new ArrayList<>();
		Board b;
		for(int i=0;i<dim;i++){
			for(int j=0;j<dim;j++){
				if(board[i][j]=='\0'){
					b = (Board) this.clone();
					b.board[i][j]=p.getSymbol();
					children.add(b);
				}
			}
		}
        return new ArrayList<>(children);
	}
	
	public boolean terminal(Player p){
		char status = status(p);
		return status != 'i';
	}
	
    public char status(Player p){
		return victory(p) ? 'v' : victory(p.opponent()) ? 'l' : full() ? 'd' : 'i';
    }

    public boolean victory(){
        for (int i = 0; i < 3; i++)
            if(checkRow(i) || checkCol(i))
                return true;
        return checkLRD() || checkRLD();
    }

    public boolean victory(Player p){
        for (int i = 0; i < 3; i++)
            if((checkRow(i) && board[i][0] == p.getSymbol()) || (checkCol(i) && board[0][i] == p.getSymbol()))
                return true;
        return (checkLRD() || checkRLD()) && board[(int)dim/2][(int)dim/2]==p.getSymbol();
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
					s.append("-");
				else
					s.append(c);
			}
			s.append('\n');
		}
		return s.toString();
	}
	public Board move(Player p, int pos) throws IndexOutOfBoundsException,IllegalStateException{
		if(pos > 8 || pos < 0)
			throw new IndexOutOfBoundsException("Position must be a value between 0 and 8");
		Board copy = new Board(this);
		int r = (int) (pos/dim);
		int c = (pos%dim);
		if(copy.board[r][c] != '\0')
			throw new IllegalStateException("This spot is already filled in, try a new move");
		else copy.board[r][c] = p.getSymbol();
		return copy;	
	}
	public Board randMove(Player p){
		Board copy = new Board(this);
		int i = rand.nextInt(dim*dim);
		int r = (int) (i/dim);
		int c = (i%dim);
		if(copy.board[r][c] == '\0'){
			copy.board[r][c] = p.getSymbol();
			return copy;
		}
		else return randMove(p);
	}
}