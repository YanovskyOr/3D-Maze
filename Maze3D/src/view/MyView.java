package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;

public class MyView implements View {

	private Controller controller;
	private CLI cli;
	Maze3d maze;
	HashMap<String, Command> commands;
	
	
	public MyView(Controller controller ){
		this.controller = controller;
		

	}
	
	public void start() throws Exception{
		cli.start();
	}
	
	
	
	@Override
	public void notifyMazeIsReady(String name) {
		System.out.println("Maze is Ready");

	}

	@Override
	public void displayMaze(Maze3d maze) {
	System.out.println(maze);

	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		this.commands=commands;
		
	}
}
