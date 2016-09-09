package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * <h1>Growing Tree Generator</h1>
 * The generator of a maze using the growing tree algorithm
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class GrowingTreeGenerator extends Maze3dGeneratorBase{
	
	private Random rand = new Random();
	CellChooser chooser = new FinalCellChooser(); //the way of choosing the cell from the list, default is final cell
	
	/**
	 * The 3D maze generation method using the growing tree algorithm.
	 * @param floors The number of floors (the Z axis) for the 3D maze
	 * @param rows The number of rows for (the Y axis) the 3D maze
	 * @param cols The number of columns for (the X axis) the 3D maze
	 * 
	 * @return An object of type Maze3D representing a 3D maze.
	 */
	public Maze3d generate(int floors, int rows, int cols) {
		
		Maze3d maze = new Maze3d(floors, rows, cols);
		
		//a list of cells, initially empty
		List<Position> cells = new ArrayList<Position>();

		// Initialize all the maze with walls
		initialize(maze);

		// Choose a random starting cell (must be in an even floor, row and column)
		Position startPos = chooseRandomPosition(maze);
		maze.setStartPosition(startPos); //choose a starting position
		maze.setFree(startPos.z, startPos.y, startPos.x); //remove the wall from the starting position

		//Add one cell to C, at random
		cells.add(startPos);
		
		
		while (!cells.isEmpty()) { //as long as the list isn't empty
			
			Position pos = chooser.choose(cells); //use the chooser to choose a cell in different ways (final/random)

			// Find the unvisited neighbors of this cell
			List<Position> neighbors = findUnvisitedNeighbors(maze, pos);	
			
			if (!neighbors.isEmpty()) { //if there are unvisited neighbors 
				// Choose a random neighbor
				int idx = rand.nextInt(neighbors.size()); //choose a random neighbor
				Position neighbor = neighbors.get(idx);
				
				// Carve a passage between current cell and the neighbor
				carvePassageBetweenCells(maze, pos, neighbor);
				cells.add(neighbor); //add the neighbor to the list of cells
			} 
			else {
				cells.remove(pos); //if there are no unvisited neighbors remove the cell from the list
			}	
		}		
		
		Position goalPosition = chooseRandomGoalPosition(maze); // choose a goal position at random
		maze.setGoalPosition(goalPosition);
		
		return maze;
	}
	

	/**
	 * Initialize the empty maze with walls.
	 * @param maze The maze to initialize.
	 */
	private void initialize(Maze3d maze) {
		for (int z = 0; z < maze.getFloors(); z++){
			for (int x = 0; x < maze.getCols(); x++) {
				for (int y = 0; y < maze.getRows(); y++) {
					maze.setWall(z, y, x);
				}
			}
		}
	}
	
	/**
	 * Choose a random position in the maze, must not be on a passage cell.
	 * @param maze3d The maze in which to choose the position.
	 * @return A Position object.
	 */
	private Position chooseRandomPosition(Maze3d maze3d) {	
		int x = rand.nextInt(maze3d.getCols());
		while (x % 2 != 0) {
			x = rand.nextInt(maze3d.getCols());
		}

		int y = rand.nextInt(maze3d.getRows());
		while (y % 2 != 0) {
			y = rand.nextInt(maze3d.getRows());
		}
		
		int z = rand.nextInt(maze3d.getFloors());
		while (z % 2 != 0) {
			z = rand.nextInt(maze3d.getFloors());
		}
		
		return new Position(z, y, x);
	}
	
	/**
	 * Find the unvisited neighbors of a chosen cell.
	 * @param maze The maze in which to search.
	 * @param pos The position which unvisited neighbors are desired.
	 * @return A list of positions
	 */
	private List<Position> findUnvisitedNeighbors(Maze3d maze, Position pos) {
		int[][][] mat = maze.getMaze();
		List<Position> neighbors = new ArrayList<Position>();
		
		if (pos.x - 2 >= 0 && mat[pos.z][pos.y][pos.x - 2] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x - 2));
		}
		
		if (pos.x + 2 < maze.getCols() && mat[pos.z][pos.y][pos.x + 2] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y, pos.x + 2));
		}
		
		if (pos.y - 2 >= 0 && mat[pos.z][pos.y - 2][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y - 2, pos.x));
		}	
		
		if (pos.y + 2 < maze.getRows() && mat[pos.z][pos.y + 2][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z, pos.y + 2, pos.x));
		}
		
		if (pos.z - 2 >=0 && mat[pos.z - 2][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z - 2, pos.y, pos.x));
		}
		
		if (pos.z + 2 < maze.getFloors() && mat[pos.z + 2][pos.y][pos.x] == Maze3d.WALL) {
			neighbors.add(new Position(pos.z + 2, pos.y, pos.x));
		}
		
		return neighbors;
	}
	
	/**
	 * carve the passage between cells
	 * treating the odd-position neighbors as "the walls of the cell" or "the passage between cells".
	 * Even-position neighbors are treated as the cells themselves.
	 * @param maze The maze in which to carve the passage.
	 * @param pos The carving start position
	 * @param neighbor The carving end position
	 */
	private void carvePassageBetweenCells(Maze3d maze, Position pos, Position neighbor) {
		if (neighbor.x == pos.x + 2) {
			maze.setFree(pos.z, pos.y, pos.x + 1);
			maze.setFree(pos.z, pos.y, pos.x + 2);
		}
		else if (neighbor.x == pos.x - 2) {
			maze.setFree(pos.z, pos.y, pos.x - 1);
			maze.setFree(pos.z, pos.y, pos.x - 2);
		}
		else if (neighbor.y == pos.y + 2) {
			maze.setFree(pos.z, pos.y + 1 ,pos.x);
			maze.setFree(pos.z, pos.y + 2 ,pos.x);
		}
		else if (neighbor.y == pos.y - 2) {
			maze.setFree(pos.z, pos.y - 1, pos.x);
			maze.setFree(pos.z, pos.y - 2, pos.x);
		}
		else if(neighbor.z == pos.z + 2) {
			maze.setFree(pos.z + 1, pos.y, pos.x);
			maze.setFree(pos.z + 2, pos.y, pos.x);
		}
		else if(neighbor.z == pos.z - 2) {
			maze.setFree(pos.z - 1, pos.y, pos.x);
			maze.setFree(pos.z - 2, pos.y, pos.x);
		}
	}
	
	/**
	 * Choose a random goal position.
	 * @param maze The maze in which to choose the goal position.
	 * @return An object of type Position
	 */
	private Position chooseRandomGoalPosition(Maze3d maze) {	
		int[][][] mat = maze.getMaze();
		
		int z = rand.nextInt(maze.getFloors());
		int y = rand.nextInt(maze.getRows());
		int x = rand.nextInt(maze.getCols());
		
		while (mat[z][y][x] == Maze3d.WALL) {
			z = rand.nextInt(maze.getFloors());
			y = rand.nextInt(maze.getCols());
			x = rand.nextInt(maze.getRows());
		}		
						
		return new Position(z, y, x);	
	}
	
	/**
	 * Tell the cell chooser which method of choice to use.
	 * @param method The desired method.
	 */
	public void setGrowingTreeAlgorithm(CellChooser method) {
		this.chooser = method;
	}
}

