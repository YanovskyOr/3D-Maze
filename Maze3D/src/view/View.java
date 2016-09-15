package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;
/**
 * This interface defines the basic MVC's view part methods that a view class has to implement
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void setCommands(HashMap<String, Command> commands);
	//void PrintCrossSection(int[][] crossMaze, int index1, int index2);
	void PrintCrossSection(Maze3d maze, int[][] crossMaze, int index1, int index2);
	void notifySolutioIsReady(String name);
	void PrintSolution(Solution<Position> mazeSolution);
	void print(String str);

}
