package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Solution</h1>
 * This class defines a solution to a searchable domain.
 * <BR>
 * Contains the list of steps to get from the starting state to the end or goal state.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class Solution<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<State<T>> states = new ArrayList<State<T>>(); // a list of states to get from the starting state to the end or goal state.

	/**
	 * a getter for the states list
	 * @return List
	 *
     */ 
	public List<State<T>> getStates() {
		return states;
	}

	/**
	 * a setter for the states list
	 *
     */ 
	public void setStates(List<State<T>> states) {
		this.states = states;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (State<T> s : states) {
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}
}
