package algorithms.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>DFS Search Algorithm</h1>
 * This is the DFS search algorithm used to solve graphs.
 * <BR>
 * It is an extension of the CommonSearcher abstract class and the Searcher Interface.
 * <BR>
 * First one defines the common methods that might be needed for a search algorithm.
 * <BR>
 * Second one defines the search method - the mandatory function of a searching algorithm.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class DFS<T> extends CommonSearcher<T> {

	private List<State<T>> neighbors = new ArrayList<State<T>>(); // list of a given state's neighbors
	private List<State<T>> visitedList = new ArrayList<State<T>>(); // list of visited neighbors

	
	/** 
	 * This is the DFS (Depth first search) algorithm itself.
	 * <BR>
	 * Implemented as a recursion. 
	 * @param s This is the searchable domain the algorithm will be applied to.
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		return recursiveDFS(s, s.getStartState());
	}
	
	private Solution<T> recursiveDFS(Searchable<T> s, State<T> state){
		
		State<T> goalState = s.getGoalState();
		if(state.equals(goalState)){  //if state is the goal state
			return backTrace(state);//exit
		}
		
		neighbors = s.getAllPossibleStates(state); //N <- unvisited neighbors of state
		
		visitedList.add(state);
		
		for(State<T> currState : neighbors) // for each neighbor s in N {
		{
			if(!visitedList.contains(currState)){ //if not visited
				visitedList.add(currState); //s.visited <- true
				currState.setCameFrom(state); // set where the state was reached from
				evaluatedNodes++; // add the state to the evaluated nodes count
				recursiveDFS(s, currState); //DFS(s)
				}
		}
		return null;
	}
}

