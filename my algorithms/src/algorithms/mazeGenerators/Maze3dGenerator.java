package algorithms.mazeGenerators;

/**
 * <h1>3D Maze Generator Interface</h1>
 * An interface used by different maze generating algorithms
 * <BR>
 * Defines the basic tasks a generator has to be able to do.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public interface Maze3dGenerator {
	
	/**
	 * Each algorithm must override a generate method to generate the maze.
	 * @param floors Number of floors.
	 * @param rows Number of rows.
	 * @param cols Number of columns.
	 * @return An object of type Maze3d (a 3D maze)
	 */
	Maze3d generate(int floors, int rows, int cols);
	
	/**
	 * Measures the time it takes to execute each maze generating method.
	 * <BR>
	 * The measure algorithm time method is always the same and defined in the class Maze3dGeneratorBase
	 * @param floors Number of floors.
	 * @param rows Number of rows.
	 * @param cols Number of columns.
	 * @return The time it took to execute the algorithm as a string.
	 */
	String measureAlgorithmTime(int floors, int rows, int cols);
	
	void setDone(boolean isDone);
}
