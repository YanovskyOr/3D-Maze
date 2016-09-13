package algorithms.search;

import java.util.List;

/**
 * <h1>Common Searcher</h1>
 * This class defines the common properties of any searching algorithm.
 * <BR>
 * Implements the Searcher Interface, thus has a method override in it.
 * <BR>
 * Another method is overridden by search algorithms which extend from this class.
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public abstract class CommonSearcher<T> implements Searcher<T> {
	
	protected int evaluatedNodes; // nodes the searching algorithm evluated (passed through)
	

	/** 
	 * This is the overridden method to get the number of evaluated nodes
	 * 
	 */
	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluatedNodes;
	}
	
	/** 
	 * This method is used to get a solution based on the final goal state the search algorithm reached.
	 * <BR>
	 * The goal state, like each state contains the previous state it was reached from.
	 * <BR>
	 * This method creates a Solution object containing the full route between the statrting position and the goal.
	 * 
	 * @param goalState This is the goalState after the searching algorithm reached it.
	 * @return a solution object
	 */
	protected Solution<T> backTrace(State<T> goalState) {
		Solution<T> sol = new Solution<T>(); // create a solution
		
		State<T> currState = goalState; 
		List<State<T>> states = sol.getStates(); // get the list of states from the new solution
		while (currState != null) {		// until the state is null, check where the state was reached from
			states.add(0, currState); // add the previous state to the beginning of the list
			currState = currState.getCameFrom(); // check where the state was reached from 
		}
		return sol; // return the solution
	}
}
