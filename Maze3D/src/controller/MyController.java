package controller;

import model.Model;
import view.View;

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
	public void PrintCrossSection(int[][] CrossMaze,int index1,int index2){
		view.PrintCrossSection(CrossMaze,index1,index2);
	}


}
