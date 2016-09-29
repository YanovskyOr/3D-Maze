package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * The model interface difines the common and required commands to be implemented by different models
 * 
 * @author Or Yanovsky and Lilia Misotchenko
 *
 */
public interface Model {
	
	/**
	 * Generates a maze, using user-inputed info
	 * @param name the name of the maze
	 * @param floors amount of floors (the Z axis of the maze)
	 * @param rows amount of rows (the Y axis of the maze)
	 * @param cols amount of columns (the X axis of the maze)
	 */
	void generateMaze(String name, int floors, int rows, int cols);
	
	/**
	 * Returns a maze by its name
	 * @param name name of the required maze
	 * @return Maze3d
	 */
	Maze3d getMaze(String name);
	
	/**
	 * Returns a crossSection of the maze
	 * @param crossBy axis to use (Z/Y/X)
	 * @param index index on the axis
	 * @param name name of the maze
	 * @return String CrossSection
	 */
	String getCrossSection(String crossBy, int index, String name);
	
	/**
	 * Solves the maze for a solution (steps) to get from start position to goal position.
	 * 
	 * @param name the name of the maze to solve
	 * @param the algorithm to use to solve
	 */
	void solveMaze(String name, String algorithm);
	
	/**
	 * Saves the maze to a file.
	 * @param name the name of the maze to save
	 * @param fileName the name to use for saved file
	 */
	void saveMaze(String name, String fileName);
	
	/**
	 * Loads a maze from a file
	 * @param fileName the file name (and possibly path) to load from
	 * @param name the name of the loaded maze
	 */
	void loadMaze(String fileName, String name);
	
	/**
	 * Returns the solution for a maze by the maze's name
	 * @param name the name of the required maze
	 * @return Solution<Position>
	 */
	Solution<Position> getSolution(String name);
	
	/**
	 * Displays a list of files and directories in a given path
	 * @param path
	 */
	void dir(String path);
	
	/**
	 * Exists after termination of all running threads.
	 */
	void exit();
	
	/**
	 * Solves the maze for use in a hint or auto-solution function.
	 * @param position current position of the players character
	 * @param name name of the maze
	 */
	void solveForHint(String position, String name);
	
	/**
	 * Used to load properties from a file and rewrite currently used file.
	 * @param path the path to the desired properties file
	 */
	void loadProperties(String path);
}
