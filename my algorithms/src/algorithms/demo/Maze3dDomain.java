package algorithms.demo;

import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
 * <h1>3D Maze Domain Adapter to Searchable </h1>
 * This is a class used as an adapter between the 3D maze domain 
 * and the searchable interface.
 * <BR>
 * Each method of searchable is overridden to create adaptation between a position in the 3D maze and a state that is used by searchable.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class Maze3dDomain implements Searchable<Position> {
	
	Maze3d maze;
	
	/**
	 * Ctor
	 * @param maze This is the maze that is going to be adapted to be a searchable.
	 */
	public Maze3dDomain(Maze3d maze) { 
		super();
		this.maze = maze;
	}

	/**
	 * This method creates a the starting State out of a maze's start position, to be used by search algorithms
	 */
	@Override
	public State<Position> getStartState() {
		State<Position> startState = new State<Position>();
		startState.setCameFrom(null);
		startState.setCost(1);
		startState.setValue(maze.getStartPosition());
		startState.setKey(maze.getStartPosition().toString());
		return startState;
	}

	/**
	 * This method creates a the goal State out of a maze's goal position, to be used by search algorithms
	 */
	@Override
	public State<Position> getGoalState() {
		State<Position> goalState = new State<Position>();
		goalState.setCameFrom(null);
		goalState.setCost(1);
		goalState.setValue(maze.getGoalPosition());
		goalState.setKey(maze.getGoalPosition().toString());
		return goalState;
	}

	/**
	 * This method requests the maze of a state's possible next moves
	 * @param s This is the position which neighbors are to be determined
	 * @return List This is the list of all the possible next states/moves
	 */
	@Override
	public List<State<Position>> getAllPossibleStates(State<Position> s) {
		List<State<Position>> pos = new ArrayList<State<Position>>();
		List<Position> posList= maze.getPossibleMovesList(s.getValue());
		
		for (Position position : posList)
		{
			State<Position> possibleState = new State<Position>();
			possibleState.setCameFrom(s);
			possibleState.setCost(s.getCost() + 1);
			possibleState.setValue(position);
			possibleState.setKey(position.toString());
			pos.add(possibleState);
		}
		return pos;
	}

	/**
	 * This the overriden method of searchable to get the cost to get to the next state.
	 * <BR>
	 * In the case of the 3DMaze, all moves have the same cost, so the returned value is always 1.
	 * 
	 * @param currState This is the current state/position of the algorithm
	 * @param neighbor This is the neighbor state/position the algorithm needs to move to
	 * @return always returns a cost of 1
	 */
	@Override
	public double getMoveCost(State<Position> currState, State<Position> neighbor) {
		return 1;
	}
	
}
