package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Board implements Ilayout, Cloneable {

	private int dim = 1;
	ArrayList<Stack<Node>> stacks;
	/**
	 * This function creates a new Board(initial configuration or goal) based on a input string
	 * @param str configuration of the board 
	 */
	public Board(String str){
		dim = str.replaceAll("\\s+", "").length();
		stacks();
		int stackIndex = 0;
		for(int i = 0; i < str.length(); i++)
			if(str.charAt(i) == ' ')
				stackIndex++;
			else stacks.get(stackIndex).add(new Node(str.charAt(i)));

	}

	/**
	 * This function creates a new Board(child) with a List of Stacks generated in the function children
	 * @param sts List of Stacks of the board
	 */
	public Board(List<Stack<Node>> sts){
		stacks = (ArrayList<Stack<Node>>) cloneStacks(sts);
		dim = stacks.size();
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
				if(Character.toUpperCase(c) == 'X' || Character.toUpperCase(c) == 'O') {
					s.append('[');
					s.append(c);
					s.append("]");
				}
			}
			s.append('\n')
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