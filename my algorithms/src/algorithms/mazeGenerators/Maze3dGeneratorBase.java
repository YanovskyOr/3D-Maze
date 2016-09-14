package algorithms.mazeGenerators;

/**
 * <h1>3D Maze Generator Base</h1>
 * This class defines the common properties maze generators.
 * <BR>
 * Basically defines a common way of measuring the execution time of a maze generator.
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public abstract class Maze3dGeneratorBase implements Maze3dGenerator{
	
	protected boolean isDone = false;
		
	public boolean isDone() {
		return isDone;
	}
	
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	
	/**
	 * A method to calculate the time it takes to generate the maze, algorithm doesn't matter.
	 * <BR>
	 * Calculates the time that passed between start and end of the algorithms running time.
	 */
	@Override
	public String measureAlgorithmTime(int rows, int cols, int floors) {
		long startTime = System.currentTimeMillis();
		this.generate(rows, cols, floors);
		long endTime = System.currentTimeMillis();
		return String.valueOf(endTime - startTime);
	}
	
}
