package view;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void print(String str);
	void printCrossSection(Maze3d maze, int[][] crossSec, int axis1, int axis2);
	void printSolution(Solution<Position> mazeSolution);
	void start();
}
