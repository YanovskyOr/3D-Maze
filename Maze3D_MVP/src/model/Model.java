package model;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface Model {
	void generateMaze(String name, int floors, int rows, int cols);
	Maze3d getMaze(String name);
	void displayCrossSection(String crossBy, int index, String name);
	void solveMaze(String name, String algorithm);
	void saveMaze(String name, String fileName);
	void loadMaze(String fileName, String name);
	Solution<Position> getSolution(String name);
	void dir(String path);
	void exit();
}
