package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>3D Maze</h1>
 * This class defines the properties of a 3D maze and the functionality it provides.
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class Maze3d {
	private int[][][] maze;
	private int floors;
	private int rows;
	private int cols;
	
	private Position startPosition;
	private Position goalPosition;
	
	public static final int FREE = 0;
	public static final int WALL = 1;
	
	/**
	 * CTOR
	 * @param floors Number of floors to create
	 * @param rows Number of rows to create
	 * @param cols Number of columns to create
	 */
	public Maze3d(int floors, int rows, int cols) {
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		maze = new int[floors][rows][cols];
	}
	
	//getters and setters
	public Position getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}

	public Position getGoalPosition() {
		return goalPosition;
	}

	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}

	public int[][][] getMaze() {
		return maze;
	}
	
	public int getFloors() {
		return floors;
	}
	
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
	

	/**
	 * Sets a cell as a wall
	 * @param z
	 * @param y
	 * @param x
	 */
	public void setWall(int z, int y, int x) {
		maze[z][y][x] = WALL;
	}
	
	/**
	 * Sets a cell as a free space
	 * @param z
	 * @param y
	 * @param x
	 */
	public void setFree(int z, int y, int x) {
		maze[z][y][x] = FREE;
	}
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int z = 0; z < floors; z++) {
			for (int y = 0; y < rows; y++) {
				for (int x = 0; x < cols; x++){
				if (z == startPosition.z && y == startPosition.y && x == startPosition.x) 
					sb.append("E");
				else if (z == goalPosition.z && y == goalPosition.y && x == goalPosition.x)
					sb.append("X");
				else
					sb.append(maze[z][y][x]);
				}
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Returns the next possible moves from a specific cell (Position) as a list of user friendly strings.
	 * @param p The position to find possible moves from.
	 * @return A array of possible moves.
	 */
	public String[] getPossibleMoves(Position p) {
		List<Position> movesList = new ArrayList<Position>();
		
		
		if (p.x - 1 >= 0 && maze[p.z][p.y][p.x - 1] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y, p.x - 1));
		}
		
		if (p.x + 1 < getCols() && maze[p.z][p.y][p.x + 1] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y, p.x + 1));
		}
		
		if (p.y - 1 >= 0 && maze[p.z][p.y - 1][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y - 1, p.x));
		}	
		
		if (p.y + 1 < getRows() && maze[p.z][p.y + 1][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y + 1, p.x));
		}
		
		if (p.z - 1 >=0 && maze[p.z - 1][p.y][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z - 1, p.y, p.x));
		}
		
		if (p.z + 1 < getFloors() && maze[p.z + 1][p.y][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z + 1, p.y, p.x));
		}
		
		String[] moves = new String[movesList.size()];
		for(int i = 0; i < movesList.size(); i++)
			moves[i] = movesList.get(i).toString();
		
		return moves;
	}
	
	/**
	 * Returns the next possible moves from a specific cell (Position) as a list Position objects.
	 * <BR>
	 * Used to adapt to general domain solving problems when requested for next possible states.
	 * @param p The position to find possible moves from.
	 * @return A list of possible next positions.
	 */
	public List<Position> getPossibleMovesList(Position p) {
		
		List<Position> movesList = new ArrayList<Position>();
		
		
		if (p.x - 1 >= 0 && maze[p.z][p.y][p.x - 1] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y, p.x - 1));
		}
		
		if (p.x + 1 < getCols() && maze[p.z][p.y][p.x + 1] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y, p.x + 1));
		}
		
		if (p.y - 1 >= 0 && maze[p.z][p.y - 1][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y - 1, p.x));
		}	
		
		if (p.y + 1 < getRows() && maze[p.z][p.y + 1][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z, p.y + 1, p.x));
		}
		
		if (p.z - 1 >=0 && maze[p.z - 1][p.y][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z - 1, p.y, p.x));
		}
		
		if (p.z + 1 < getFloors() && maze[p.z + 1][p.y][p.x] == Maze3d.FREE) {
			movesList.add(new Position(p.z + 1, p.y, p.x));
		}
		
		return movesList;
	}

	/**
	 * Make a 2d array representing the cross-section of the 3d maze by an X-axis mark of choice.
	 * @param i The desired X-axis
	 * @return  A cross section as a 2d array
	 */
	public int[][] getCrossSectionByX(int i) {
		if(i < 0 || i > getCols())
			throw new IndexOutOfBoundsException("Index Must be in range");;
		int[][] crossSec = new int[getFloors()][getRows()];
		for(int z = 0; z < getFloors(); z++)
			for(int y = 0; y < getRows(); y++)
				crossSec[z][y] = maze[z][y][i];
		return crossSec;
	}
	
	/**
	 * Make a 2d array representing the cross-section of the 3d maze by a Y-axis mark of choice.
	 * @param i The desired Y-axis
	 * @return  A cross section as a 2d array
	 */
	public int[][] getCrossSectionByY(int i) {
		if(i < 0 || i > getRows())
			throw new IndexOutOfBoundsException("Index Must be in range");;
		int[][] crossSec = new int[getFloors()][getCols()];
		for(int z = 0; z < getFloors(); z++)
			for(int x = 0; x < getCols(); x++)
				crossSec[z][x] = maze[z][i][x];
		return crossSec;
	}

	/**
	 * Make a 2d array representing the cross-section of the 3d maze by a Z-axis mark of choice
	 * @param i The desired Z-axis
	 * @return  A cross section as a 2d array
	 */
	public int[][] getCrossSectionByZ(int i) {
		if(i < 0 || i > getFloors())
			throw new IndexOutOfBoundsException("Index Must be in range");;
		int[][] crossSec = new int[getRows()][getCols()];
		for(int y = 0; y < getRows(); y++)
			for(int x = 0; x < getCols(); x++)
				crossSec[y][x] = maze[i][y][x];
		return crossSec;
	}
}