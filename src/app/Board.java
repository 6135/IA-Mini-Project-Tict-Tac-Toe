package app;

import java.lang.invoke.StringConcatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Board implements Ilayout, Cloneable {

	private int dim = 3;
	private char board [][];
	/**
	 * This function creates a new Board(initial configuration or goal) based on a input string
	 * @param str configuration of the board 
	 */
	public Board(){
		board = new char[dim][dim];

	}

	/**
	 * This function creates a new Board(child) with a List of Stacks generated in the function children
	 * @param sts List of Stacks of the board
	 */
	public Board(String str){
		board= new char[dim][dim];
		int si=0;
		int pos=Character.getNumericValue(str.charAt(0));
		int r=(dim *(int)(pos/dim))-1;
		int c=(pos%dim)-1;
		board[r][c]='X';
	}

	/**
	 * This function creates a new Board with the same List of Stacks and dim as the source
	 * @param source current Board
	 */
	public Board(Board source){
		stacks = (ArrayList<Stack<Node>>) cloneStacks(source.stacks);
		dim = source.getDim();
	}

	/**
	 * @return current Board clone
	 */
	@Override
	public Object clone(){
		return new Board(this);
		
	}

	/**
	 * Initializes dim Stakcs and adds them to the List
	 */
	private void stacks() {
		stacks = new ArrayList<>();
		for(int i = 0; i < dim; i++)
			stacks.add(new Stack<>());		
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
		int hashCode = 0;
		for(Stack<Node> s : stacks)
			hashCode +=s.hashCode();
		return hashCode;
	}
    /**
     * @return comparison function so that contains works properly
     */
	@Override
	public boolean equals(Object l) {
		if(l instanceof Board){
			ArrayList<Stack<Node>> s = ((Board) l).stacks;
			return stacks.containsAll(s);
		} else return false;
		
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
    public List<Ilayout> children() {
        HashMap<Ilayout, Integer> children = new HashMap<>();
		Node c; 
		Board b;
		int index = 0;
        for (int i = 0; i < stacks.size(); i++)        
            if(!stacks.get(i).empty()) {    
                for (int j = 1; j < dim; j++) {

					b = (Board) this.clone();
                    c=b.stacks.get(i).pop();                      
					b.stacks.get((j+i)%dim).push(c); 
                    children.put(b,index++);                          
                }                 
			}
        return new ArrayList<>(children.keySet());
	}
	
	public boolean terminal(){
		char status = status();
		return status == 'v' || status == 'f';
	}
	
    public char status(){
		return victory() ? 'v' : full() ? 'f' : 'i';
        
    }

    public boolean victory(){
        for (int i = 0; i < 3; i++)
            if(checkRow(i) || checkCol(i))
                return true;
        return checkLRD() || checkRLD();
    }

    private boolean checkRow(int row){
        /*
            if(board[row][0]==' ')
                return false;
            else return board[row][0]==board[row][1] && board[row][1]==board[row][2];
        */
        for (int i = 1; i < board[row].length; i++) 
            if(board[row][i-1] != board[row][i])
                return false;
        return true;
    }

    private boolean checkCol(int col){
        /*
            if(board[0][col]==' ')
                return false;
            else return board[0][col]==board[1][col] && board[1][col]==board[2][col];
        */
        for (int i = 1; i < board.length; i++)
            if(board[i-1][col] != board[i][col])
                return false;
        return true;
    }

    private boolean checkLRD(){
        /*
            if(board[0][0]==' ')
                return false;
            else return board[0][0] == board[1][1] && board[1][1] == board[2][2];
        */
        for (int i = 1; i < board.length; i++) 
            if(board[i-1][i-1] != board[i][i])
                return false;
        return true;
    }

    private boolean checkRLD(){
        /*
            if(board[0][2]==' ')
                return false;
            else return board[0][2] == board[1][1] && board[1][1] == board[2][0];
        */
        for (int i = 1; i < board.length; i++) 
            if(board[i-1][board.length-i] != board[i][board.length-(i+1)])
                return false;
        return true;
    }

    private boolean full(){
        for (int row = 0; row < 3; row++) 
            for (int col = 0; col < 3; col++)
                if(board[row][col]==' ')
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
				s.append('[');
				if(c ==' ')
					s.append("-");
				else
					s.append(c);
				s.append("]");
			}
			s.append('\n');
		}
		return s.toString();
	}

	/**
	 * 
	 * @param ss A List of Node Stacks to be copied
	 * @return A Copy of the aforementioned list
	 */
	public List<Stack<Node>> cloneStacks(List<Stack<Node>> ss){
		List<Stack<Node>> cloned = new ArrayList<>();
		for(Stack<Node> s : ss)
			cloned.add(cloneStack(s));
		return cloned;
	}
	/**
	 * 
	 * @param s Node stack to be copied
	 * @return A copy of the aforementioned stack
	 */
	private Stack<Node> cloneStack(Stack<Node> s){
		Stack<Node> newS = new Stack<>();
		for(Node n : s)
			newS.add(n);
		return newS;
	}

}