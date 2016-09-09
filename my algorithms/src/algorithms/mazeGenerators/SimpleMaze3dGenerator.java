package algorithms.mazeGenerators;

import java.util.Random;

/**
 * <h1>Simple Maze Generator</h1>
 * A generator of a simple maze route curved from a starting position to a goal position.
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class SimpleMaze3dGenerator extends Maze3dGeneratorBase {

	private Random rand = new Random();
	
	//ratio of the walls in the maze
	private static final float WALLS_RATIO = 0.5F;
	
	
	/**
	 * The 3D maze generation method using a simple algorithm.
	 * @param floors The number of floors (the Z axis) for the 3D maze
	 * @param rows The number of rows for (the Y axis) the 3D maze
	 * @param cols The number of columns for (the X axis) the 3D maze
	 * 
	 * @return An object of type Maze3D representing a 3D maze.
	 */
	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		Maze3d maze3d = new Maze3d(floors, rows, cols);
		
		//number of walls in the maze, in relation to the dimensions of the maze and the desired walls ratio
		int wallsNum = (int)(WALLS_RATIO * floors * rows * cols);
		
		// populate the maze with random walls
		for (int i = 0; i < wallsNum; i++){
			int z = rand.nextInt(floors);
			int y = rand.nextInt(rows);
			int x = rand.nextInt(cols);
			
			maze3d.setWall(z, y, x);
		}
		
		// Choose a random entrance 
		Position startPos = chooseRandomPosition(maze3d);
		maze3d.setStartPosition(startPos);	
				
		// Choose a random exit 
		Position goalPos = chooseRandomPosition(maze3d);
		maze3d.setGoalPosition(goalPos);
		
		//create passage between floors
		if(startPos.z < goalPos.z)
			for (int i = startPos.z + 1; i < goalPos.z; i++)
				maze3d.setFree(i, startPos.y, startPos.x);
		
		else if (startPos.z > goalPos.z)
			for (int i = startPos.z - 1; i > goalPos.z; i--)
				maze3d.setFree(i, startPos.y, startPos.x);
		
		//create rows passage
		if(startPos.y < goalPos.y)
			for (int i = startPos.y; i < goalPos.y; i++)
				maze3d.setFree(goalPos.z, i, startPos.x);
		
		else if (startPos.y > goalPos.y)
			for (int i = startPos.y; i > goalPos.y; i--)
				maze3d.setFree(goalPos.z, i, startPos.x);
		
		//create columns passage
		if(startPos.x < goalPos.x)
			for (int i = startPos.x; i < goalPos.x; i++)
				maze3d.setFree(goalPos.z, goalPos.y, i);
		
		else if (startPos.x > goalPos.x)
			for (int i = startPos.x; i > goalPos.x; i--)
				maze3d.setFree(goalPos.z, goalPos.y, i);
		
		return maze3d;	
	}
	
	/**
	 * Choose a random position
	 * @param maze3d The maze in which to choose.
	 * @return An object of type Position (a maze cell).
	 */
	private Position chooseRandomPosition(Maze3d maze3d) {
		int[][][] mat = maze3d.getMaze();
		
		int z = rand.nextInt(maze3d.getFloors());
		int y = rand.nextInt(maze3d.getRows());
		int x = rand.nextInt(maze3d.getCols());
		
		while (mat[z][y][x] == Maze3d.WALL) {
			z = rand.nextInt(maze3d.getFloors());	
			y = rand.nextInt(maze3d.getRows());	
			x = rand.nextInt(maze3d.getCols());	
		}
		
		return new Position(z, y, x);
	}	
	
}