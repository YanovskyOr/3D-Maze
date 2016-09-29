package presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import model.MyModel;
import view.MazeWindow;
import view.View;

/**
 * This is the Command Manager used by the Presenter class.
 * The Presenter uses this to define and manage commands.
 * 
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class CommandsManager {

	
	private Model model;
	private View view;
	private Boolean isSolutionReady = false;
		
	/**
	 * CTOR
	 * The commands Manager requires the presenters view and module objects.
	 * @param model
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;		
	}
	
	/**
	 * This is a map of usable commands, some are used only by the CLI, and some only by the GUI
	 * @return commands hash map
	 */
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
		commands.put("load_properties", new LoadPropertiesCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("give_hint", new GiveHintCommand());
		commands.put("auto_solve", new AutoSolveCommand());
		commands.put("solution_ready", new SolutionReadyCommand());
		commands.put("display_message", new DisplayMessageCommand());
		commands.put("dir", new DirCommand());
		commands.put("exit", new ExitCommand());
		return commands;
	}
	
	//throw exception if user didn't input all of the parameters for the command
	/**
	 * This class defines the Generate Maze Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * commands model to generate a maze and view to display the maze generated.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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

	/**
	 * This class defines the Display Maze Command
	 * <BR>
	 * Used by CLI to show a maze by its name.
	 * <BR>
	 * After checking if the maze exists (by name) displays the maze.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	/**
	 * This class defines the Maze Ready Command
	 * <BR>
	 * Notifies the user that the maze was generated successfully
	 * <BR>
	 * commands view to notify about maze readiness.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			view.notifyMazeIsReady(name);
		}
	}
	
	/**
	 * This class defines the Display CrossSection Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Uses the model to get a crossSection from the maze, then displays it to the chosen view.
	 * <BR>
	 * In the GUI this is used to display a floor of the maze graphically.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	/**
	 * This class defines the Solve Maze Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Commands the model to solve a maze, given a name and a search algorithm.
	 * <BR>
	 * When a user uses this command from CLI, the algorithm is part of the command's syntax.
	 * <BR>
	 * When used by the GUI, the view notifies the command manager with an algorithms defined by the properties file.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class SolveMazeCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			
			if(args.length==2){
				String name = args[0];
				String algorithm = args[1];
				if(model.getMaze(name)!=null){
					model.solveMaze(name,algorithm);
				}
				else
					view.print(" maze name does not exist");
			}
			else
				view.print("error solving maze,  try the command again , sysntax should be  maze name , algorithm");
		}
	}
	
	
	/**
	 * This class defines the Save Maze Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Commands the model to save the maze. Provides a name and a filename.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class SaveMazeCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			if(args.length==2){
				String name = args[0];
				String fileName = args[1];
				if(model.getMaze(name)!=null)
					model.saveMaze(name, fileName);
				else
					view.print("maze doesn't exist");
			}
			else
				view.print("error saving maze, try the command again. sysntax should be maze_name, file_name");
		}	
	}
	
	/**
	 * This class defines the Maze Saved Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Notifies the user (via view) that a maze was saved.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class MazeSavedCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String toPrint = "maze " + name + " saved successfully";
			view.print(toPrint);
		}	
	}
		
	
	/**
	 * This class defines the Load Maze Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Uses the model to load a maze. Provides a file name or path and the name for the loaded maze.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	/**
	 * This class defines the Maze Loaded Command
	 * <BR>
	 * Used by both CLI and GUI
	 * <BR>
	 * Notifies the user (via view) that a maze was loaded.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class MazeLoadedCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			String toPrint = "maze " + name + " loaded successfully";
			view.print(toPrint);
		}	
	}
	
	/**
	 * This class defines the Load Properties Command
	 * <BR>
	 * Commands the model to load a properties file providing the path to the file.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class LoadPropertiesCommand implements Command {
		
		@Override
		public void doCommand(String[] args) {
			String path = args[0];
			model.loadProperties(path);
		}	
	}
	
	/**
	 * This class defines the Display Solution Command
	 * <BR>
	 * Uses the view to print a solution.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	/**
	 * This class defines the Give Hint Command
	 * <BR>
	 * Used only by the GUI
	 * <BR>
	 * Commands the model to solve the maze for a hint (returns full solution).
	 * Uses view to display a hint based on the first step from the solution.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class GiveHintCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if(args.length==3){
				String position = args[0];
				String name = args[1];
				
				model.solveForHint(position, name);
				
				
				//waiting for solution ready to change the flag
				//preventing requesting the view for a not-yet-existing solution
				while(isSolutionReady == false) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// 
						e.printStackTrace();
					}
				}

				isSolutionReady = false;
				view.displayHint(model.getSolution(name).getStates().get(1).getValue());
				
				
			}

		}
	}
	
	/**
	 * This class defines the Auto Solve Command
	 * <BR>
	 * Used only by the GUI
	 * <BR>
	 * Commands the model to solve the maze for a hint (returns full solution).
	 * Uses view to move the character towards the goal based on steps of the solution.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class AutoSolveCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			if(args.length==3){
				String position = args[0];
				String name = args[1];
				
				

				model.solveForHint(position, name);
				
				
				//waiting for solution ready to change the flag
				//preventing requesting the view for a not-yet-existing solution
				while(isSolutionReady == false) {
					//System.out.print("");
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				isSolutionReady = false;
				view.autoSolve(model.getSolution(name).getStates());
				
				
			}

		}
	}
	
	/**
	 * This class defines the Solution Ready Command
	 * <BR>
	 * Notifies the user that the solution for the desired maze is ready.
	 * <BR>
	 * Changes the isSolutionReady flag to true, breaking the auto solve and hint command's while loops,
	 * that wait for a solution before displaying its results.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class SolutionReadyCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			isSolutionReady = true;
			
			String name = args[0];
			String toPrint = "solution for " + name + " is ready";
			view.print(toPrint);

		}
		
	}
		
	/**
	 * This class defines the Display Message Command
	 * <BR>
	 * Uses the view to print a message.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	/**
	 * This class defines the Dir Command
	 * <BR>
	 * Uses the model to get a list of files and folders in a given path.
	 * <BR>
	 * Provides the user defined path to the model's dir command.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
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
	
	
	/**
	 * This class defines the Exit  Command
	 * <BR>
	 * Commands the model to exit and notifies user via the view.
	 * @author Or Yanovsky and Lilia Misotchenko
	 *
	 */
	public class ExitCommand implements Command{

		@Override
		public void doCommand(String[] args) {
			String toPrint = "exited";
			model.exit();
			view.print(toPrint);
			
		}
	}
	
}
	



	

	
	

