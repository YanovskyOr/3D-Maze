package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * <h1>Maze Position</h1>
 * Defines the properties and functionality of a position in a maze, represented by a cell.
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class Position implements Serializable {

	private static final long serialVersionUID = 1L;

	public int z; //floors
	public int y; //rows
	public int x; //columns
	
	/**
	 * CTOR for Setting a position
	 * @param z
	 * @param y
	 * @param x
	 */
	public Position(int z, int y, int x) {
		this.z = z;
		this.y = y;
		this.x = x;
	}
	
	//printing a position
	@Override
	public String toString() {
		return "(" + z + "," + y + "," + x + ")";
	}

	//to compare positions
	@Override
	public boolean equals(Object obj) {
		Position pos = (Position)obj;
		return (this.z == pos.z && this.y == pos.y && this.x == pos.x);
	}
	
}
