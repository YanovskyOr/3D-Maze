package controller;

import algorithms.mazeGenerators.Maze3d;

/**
 * This is the interface for the controller part of the MVC.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public interface Controller {
	void notifyMazeIsReady(String name);
	//void PrintCrossSection(int[][] CrossMaze, int index1, int index2);
	void PrintCrossSection(Maze3d maze, int[][] CrossMaze, int index1, int index2);
	void notifySolutionIsReady(String name);
}
