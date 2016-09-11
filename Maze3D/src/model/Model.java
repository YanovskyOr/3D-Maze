package model;

import algorithms.mazeGenerators.Maze3d;

/**
 * This is the interface for the model part of the MVC.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public interface Model {
	void generateMaze(String name, int floors, int rows, int cols);
	Maze3d getMaze(String name);
	void displayCrossSection(String crossBy, int index, String name);
	void solveMaze(String name, String algorithm);
	void saveMaze(String name, String fileName);
	void loadMaze(String fileName, String name);
}
