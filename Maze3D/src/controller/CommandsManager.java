package controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * This is the Command Manager used for the MyController class.
 * The controller uses this to define and manage commands.
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */

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
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("solve", new SolveMazeCommand() );
		
		return commands;
	}
	
	//throw exception if user didnt input all of the parameters for the command
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			int floors = Integer.parseInt(args[2]);
			int rows = Integer.parseInt(args[3]);
			int cols = Integer.parseInt(args[4]);
			model.generateMaze(name, floors, rows, cols);
		}		
	}
	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[1];
			Maze3d maze = model.getMaze(name);
			view.displayMaze(maze);
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String crossBy = args[1];
			int index=Integer.parseInt(args[2]);
			String name = args[3];
			model.DisplayCrossSection(crossBy,index,name);
			
			
		}
	}
	
	public class SolveMazeCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String name=args[1];
			String algorithm =args[2];
			model.solveMaze(name,algorithm);
			
		}
		
	}
	}
	
	

