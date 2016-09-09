package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <h1>BFS Search Algorithm</h1>
 * This is the BFS search algorithm used to solve graphs.
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
public class BFS<T> extends CommonSearcher<T> {

	private PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>(); // unvisited nodes
	private Set<State<T>> closedList = new HashSet<State<T>>(); // visited nodes
	
	/** 
	 * This is the BFS (Best first search) algorithm itself.
	 * <BR>
	 * It chooses the next node by cost of nodes in the priority queue.
	 * @param s This is the searchable domain the algorithm will be applied to.
	 */
	@Override
	public Solution<T> search(Searchable<T> s) {
		State<T> startState = s.getStartState(); // get the start state of the searchable domain
		openList.add(startState); // add the start state to the open list
		
		while (!openList.isEmpty()) { // while the open list is not empty
			State<T> currState = openList.poll(); // take a state out
			evaluatedNodes++; // add the state to the evaluated nodes count
			closedList.add(currState); // put the state into the closed (visited) states list
			
			State<T> goalState = s.getGoalState(); // check if the current state is the goal state
			if (currState.equals(goalState)) { 
				return backTrace(goalState); // if it is, trace back the route to the goal
			}
			
			List<State<T>> neighbors = s.getAllPossibleStates(currState); // get all the possible next nodes from the current state
			
			for (State<T> neighbor : neighbors) {  // for each neighbor 
				if (!openList.contains(neighbor) && !closedList.contains(neighbor)) { // if it was never known before
					neighbor.setCameFrom(currState); // designate to it where it came from
					neighbor.setCost(currState.getCost() + s.getMoveCost(currState, neighbor)); // set the cost to get to it
					openList.add(neighbor); // add the neighbor to the open (unvisited) list
				}
				else { // if the neighbor is known
					double newPathCost = currState.getCost() + s.getMoveCost(currState, neighbor); // set the new cost
					if (neighbor.getCost() > newPathCost) { // if the new cost differs from the original cost
						neighbor.setCost(newPathCost); // change the cost of the neighbor
						neighbor.setCameFrom(currState); // set where it came from
						
						if (!openList.contains(neighbor)) { // if the neighbor wasn't in the open list
							openList.add(neighbor); // add it to the open list
						} 
						else { // must notify the priority queue about the change of cost
							openList.remove(neighbor);
							openList.add(neighbor);
						}
					}
				}			
			}
		}
		return null;
	}

}
