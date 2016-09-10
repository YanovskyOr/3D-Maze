package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	//void PrintCrossSection(int[][] crossMaze, int index1, int index2);
	void PrintCrossSection(Maze3d maze, int[][] crossMaze, int index1, int index2);
	void notifySolutioIsReady(String name);
}
