package mvc.controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import mvc.model.Model;
import mvc.view.View;

public class CommandsManager {

	
	private Model model;
	private View view;
		
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	public HashMap<String, Command> getCommandsMap() {
		HashMap<String, Command> commands = new HashMap<String, Command>();
		commands.put("generate_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		
		return commands;
	}
	
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int floors = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			model.generateMaze(name, floors, rows, cols);
		}		
	}
	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	
}
