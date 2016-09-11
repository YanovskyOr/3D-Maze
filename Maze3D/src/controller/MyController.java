package controller;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * The MyController class is the controller part of the MVC.
 * <BR>
 * It is used to coordinate between MyView and MyModel.
 * <BR>
 * Uses CommandManager to manage and define commands.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class MyController implements Controller {

	private View view;
	private Model model;
	private CommandsManager commandsManager;
	

	/**
	 * Controller c'tor
	 * @param view
	 * @param model
	 */		
	public void setModelAndView(Model model, View view) {
		this.model = model;
		this.view = view;
		
		commandsManager = new CommandsManager(model, view);
		view.setCommands(commandsManager.getCommandsMap());
	}
	
	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}
	
	@Override
	public void PrintCrossSection(Maze3d maze,int[][] CrossMaze,int index1,int index2){
		view.PrintCrossSection(maze,CrossMaze,index1,index2);
	}
	@Override
	public void notifySolutionIsReady(String name) {
		view.notifySolutioIsReady(name);
		
	}


}
