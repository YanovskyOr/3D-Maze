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

public class MyView implements View {

	private Controller controller;
	//private CLI cli;
	private BufferedReader in;
	private PrintWriter out;
	Maze3d maze;
	HashMap<String, Command> commands;
	
	
	public MyView(Controller controller ) {
		this.controller = controller;
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		commands = new HashMap<String,Command>();
	}
	
	public void start() throws Exception {
		try {
			CLI cli = new CLI(in, out, commands);
		cli.start();
		}catch (IOException e) {
			//TODO:catch
		 }
	}
	
	
	
	@Override
	public void notifyMazeIsReady(String name) {
		System.out.println("Maze "+ name +" is Ready");
	}

	@Override
	public void displayMaze(Maze3d maze) {
	System.out.println(maze);
	}
	
	@Override
	public void PrintCrossSection(Maze3d maze ,int[][] crossMaze,int index1 ,int index2)
	{
		System.out.println(maze.printCrossSection(crossMaze, index1, index2));	
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
	}

	@Override
	public void notifySolutioIsReady(String name) {
		System.out.println("Solution for "+ name +" is Ready");	
	}

	@Override
	public void PrintSolution(Solution<Position> mazeSolution) {
		 List<State<Position>> states = new ArrayList<State<Position>>();
		 states=mazeSolution.getStates();
	for (State<Position> state : states) {
		 System.out.print(state+",");
		
	}
	 System.out.println();	
		}
		
		
	}


