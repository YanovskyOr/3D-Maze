package presenter;

import java.util.Arrays;
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
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("solve", new SolveMazeCommand() );
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("maze_saved", new MazeSavedCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("maze_loaded", new MazeLoadedCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("solution_ready", new SolutionReadyCommand());
		commands.put("display_message", new DisplayMessageCommand());
		commands.put("dir", new DirCommand());
		return commands;
	}
	
	//throw exception if user didnt input all of the parameters for the command
	public class GenerateMazeCommand implements Command {
	
		@Override
		public void doCommand(String[] args) {
			if(args.length==4){
				String name = args[0];
				
				int floors = Integer.parseInt(args[1]);
				int rows = Integer.parseInt(args[2]);
				int cols = Integer.parseInt(args[3]);
				model.generateMaze(name, floors, rows, cols);
			}
			else {
				view.print("error generating maze: please enter a maze name(with no spaces) , floors ,rows & cols");	    
			}
		}
	}

	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if(args.length==1) {
				String name = args[0];
				Maze3d maze = model.getMaze(name);
		
				if(model.getMaze(name)!=null)
					view.displayMaze(maze);
				else
					view.print("maze name does not exist");
				}
		
			else
				view.print("error Displaying maze: please enter an existing maze name");
			}
	}
	
	
	class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String msg = "maze " + name + " is ready";
			view.print(msg);
		}
	}
	
	
	public class DisplayCrossSectionCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			
			if(args.length==3) {//input is valid
				String crossBy = args[0];
				
			if(!crossBy.equalsIgnoreCase("x")&&!crossBy.equalsIgnoreCase("y")&&!crossBy.equalsIgnoreCase("z"))
				view.print("please enter after x or y or z the command!");	
			
			int index = Integer.parseInt(args[1]);
			String name = args[2];
			
			if(model.getMaze(name)!=null)
				view.print(model.getCrossSection(crossBy,index,name));
			else
				view.print("maze name does not exist");
			}
			else
				view.print("error displaying,  try the command again , sysntax should be (x/y/z)  index name");
		}
	}
	
	
	public class SolveMazeCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			
			if(args.length==2){
				String name = args[0];
				String algorithm = args[1];
				if(model.getMaze(name)!=null)
					model.solveMaze(name,algorithm);
				else 
					view.print(" maze name does not exist");
			}
			else 
				view.print("error solving maze,  try the command again , sysntax should be  maze name , algorithm");
		}
	}
	
	
	public class SaveMazeCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
				String name = args[0];
				String fileName = args[1];
				if(model.getMaze(name)!=null)
					model.saveMaze(name, fileName);
				else
					view.print("A maze with that name already exists");
			}
			else
				view.print("error saving maze,  try the command again , sysntax should be  maze name , file name");
		}	
	}
	
	public class MazeSavedCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String toPrint = "maze " + name + " saved successfully";
			view.print(toPrint);
		}	
	}
		
	
//	String name = args[0];
//	String msg = "maze " + name + " is ready";
//	view.print(msg);
//}
	
	public class LoadMazeCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
				String fileName = args[0];
				String name = args[1];
				if(!fileName.contains(".maze"))
					view.print("please enter a .maze file");
				if(model.getMaze(name)==null)
					model.loadMaze(fileName, name);
				else 
					view.print("error: a maze with that name already exists, try another name");
			}
			else
				view.print("error loading maze,  try the command again , sysntax should be   file name , maze name");
		}
	}
	
	public class MazeLoadedCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String toPrint = "maze " + name + " loaded successfully";
			view.print(toPrint);
		}	
	}
	
	public class DisplaySolutionCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==1){
				String name = args[0];
			if(model.getMaze(name)!=null)
				view.printSolution(model.getSolution(name));
			else 
				view.print("maze name does not exist");
			}
			else
				view.print("error displaying sollution,  try the command again , enter maze name  after command");
		}

	}
	
	
	
	public class SolutionReadyCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String toPrint = "solution for " + name + " is ready";
			view.print(toPrint);
			
		}
		
	}
		
	public class DisplayMessageCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			
				String msg = "";
				for (String string : args) {
					msg = msg.concat(string+" ");
				}
				view.print(msg);
		}
	}
	
	public class DirCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==1){
				String path = args[0];
			if(path.contains(":/"))
				try {
					model.dir(path);
				} catch (Exception e) {
					view.print("disc does not exist try again");
				}
			else 
				view.print("please enter a valid dir, for example - c:/.../...");
			}
			else
				view.print("error: enter a valid dir after command");
		}
	}
	
}
	



	

	
	

