package algorithms.search;

import java.util.List;


/**
 * <h1>Searchable Interface</h1>
 * This class defines what a domain that a search algorithm can search should have
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public interface Searchable<T> {
	/**
	 * get the starting state of the searchable domain
	 * @return an object of type state
	 */
	State<T> getStartState(); 
	
	/**
	 * get the goal state of the searchable domain
	 * @return an object of type state
	 */
	State<T> getGoalState();
	
	/**
	 * get all possible next states for a given state
	 * @param s The state to check for next states
	 * @return a list of states
	 */
	List<State<T>> getAllPossibleStates(State<T> s);
	double getMoveCost(State<T> currState, State<T> neighbor); 
}
