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
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("dir", new DirCommand());
		return commands;
	}
	
	//throw exception if user didnt input all of the parameters for the command
	public class GenerateMazeCommand implements Command {

		
		@Override
		public void doCommand(String[] args) {
			if(args.length==5){
			String name = args[1];
			
			int floors = Integer.parseInt(args[2]);
			int rows = Integer.parseInt(args[3]);
			int cols = Integer.parseInt(args[4]);
			model.generateMaze(name, floors, rows, cols);
		}
			else{
			
				view.print("error generating maze: please enter a maze name(with no spaces) , floors ,rows & cols");	
			    
		}
	 }
	}
	//TODO:change to out.write and create print and printerr method in view
	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
			String name = args[1];
			Maze3d maze = model.getMaze(name);
			if(model.getMaze(name)!=null)
		    	view.displayMaze(maze);
			else
				
				view.printErr();//prints err that maze name does not exist
		}
			else 
				view.print("error Displaying maze: please enter an existing maze name");
		}
		
	}
	
	public class DisplayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if(args.length==4){//input is valid
				
				
			String crossBy = args[1];
			if(!crossBy.equalsIgnoreCase("x")&&!crossBy.equalsIgnoreCase("y")&&!crossBy.equalsIgnoreCase("z"))
				view.print("please enter after the command x or y or z");	
			int index = Integer.parseInt(args[2]);
			String name = args[3];
			if(model.getMaze(name)!=null)
			model.displayCrossSection(crossBy,index,name);
			else
				view.printErr();//prints err that maze name does not exist
				
			}
			else 
				view.print("error displaying,  try the command again , sysntax should be (x/y/z)  index name");
		}
	}
	
	public class SolveMazeCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if(args.length==3){
			String name = args[1];
			String algorithm = args[2];
			if(model.getMaze(name)!=null)
			model.solveMaze(name,algorithm);
			else 
				view.printErr();//prints err that maze name does not exist
			}
			
			else 
				view.print("error solving maze,  try the command again , sysntax should be  maze name , algorithm");
			}
	}
	
	public class SaveMazeCommand implements Command{
		@Override
		public void doCommand(String[] args) {
			if(args.length==3){
			String name = args[1];
			String fileName = args[2];
			if(model.getMaze(name)!=null)
			model.saveMaze(name, fileName);
			else 
				view.printErr();//prints err that maze name does not exist
			}
				
		
			else
				view.print("error saving maze,  try the command again , sysntax should be  maze name , file name");
		}	
	}
		
	public class LoadMazeCommand implements Command{
		@Override
		public void doCommand(String[] args) {
			if(args.length==3){
			String fileName = args[1];
			String name = args[2];
			if(model.getMaze(name)==null)
			model.loadMaze(fileName, name);
			else 
				view.printErr();//prints err that maze name does not exist
			}
			else
				view.print("error loading maze,  try the command again , sysntax should be   file name , maze name");
		}
		
		
	}
	
	public class DisplaySolutionCommand implements Command{
		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
			String name = args[1];
			if(model.getMaze(name)!=null)
			model.displaySolution(name);
			else 
				view.printErr();//prints err that maze name does not exist
			}
			else
				view.print("error displaying sollution,  try the command again , enter maze name  after command");
		}

			}
		

	
	public class DirCommand implements Command{
		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
			String path = args[1];
			model.dir(path);
			}
			else
				view.print("error: enter a valid dir after command");
			}
		}
	}
	



	

	
	

