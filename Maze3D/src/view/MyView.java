package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import controller.Command;
import controller.Controller;

/**
 * The MyView class is the view part of the MVC.
 * <BR>
 * MyView is used to interact with the user via the CLI interface.
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class MyView implements View {


	private BufferedReader in;
	private PrintWriter out;
	Maze3d maze;
	HashMap<String, Command> commands;
	
	/**
	 * CTOR
	 * @param controller 
	 */
	public MyView(Controller controller ) {

		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		commands = new HashMap<String,Command>();
	}
	
	/**
	 * Used to start the CLI in a new thread.
	 * @throws Exception
	 */
	public void start() throws Exception {
		try {
			CLI cli = new CLI(in, out, commands);
		cli.start();
		}catch (IOException e) {
			out.write("can't start cli");
		 }
	}
	
	
	/**
	 * Notifies maze is ready
	 * @param name maze name
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		out.write("Maze "+ name +" is Ready\n");
		out.flush();
	}

	/**
	 * prints the maze to the cli
	 * @param maze the maze to print
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		out.write(maze.toString());
		out.flush();
	}
	
	/**
	 * Prints a cross section
	 * @param maze the maze which cross section is to be printed
	 * @param crossMaze
	 * @param index1 
	 * @param index2
	 */
	@Override
	public void PrintCrossSection(Maze3d maze ,int[][] crossMaze,int index1 ,int index2)
	{
		out.write(maze.printCrossSection(crossMaze, index1, index2));	
		out.flush();
	}

	
	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
	}

	
	@Override
	public void notifySolutioIsReady(String name) {
		out.write("Solution for "+ name +" is Ready\n");	
		out.flush();
	}

	/**
	 * Prints the solution
	 * @param mazeSolution the solution to print 
	 */
	@Override
	public void PrintSolution(Solution<Position> mazeSolution) {
		 List<State<Position>> states = new ArrayList<State<Position>>();
		 states=mazeSolution.getStates();
	for (State<Position> state : states) {
		 out.write(state+",");
		 out.write("\n");
		 out.flush();
		}
	}


	/**
	 * A simple method to print a string to the view/cli.
	 * @param str the string to print
	 */
	@Override
	public void print(String str) {
		out.write(str);
	    out.write("\n");
		out.flush();
	}

	
}


