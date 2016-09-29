package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.Model;
import view.View;

/**
 * This is the presenter part of the MVP.
 * <BR>
 * The presenter is used as an observer for the model and view.
 * <BR>
 * It receives notifications containing commands and initiates the commands using it's commands manager.
 * @author Or Yanovsky and Lilia Misotchenko
 *
 */
public class Presenter implements Observer {

	private Model model;
	private View view;
	private CommandsManager commandsManager;
	private HashMap<String, Command> commands;
	
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
			
		commandsManager = new CommandsManager(model, view);
		commands = commandsManager.getCommandsMap();
	}


	@Override
	public void update(Observable o, Object arg) {
		String commandLine = (String)arg;
		
		String splitCommand[] = commandLine.split(" ");
		String command = splitCommand[0];
		
		if(!commands.containsKey(command)) {
			view.print("Command doesn't exist");
		}
		else {
			String[] args = null;
			if (splitCommand.length > 1) {
				String commandArgs = commandLine.substring(commandLine.indexOf(" ") + 1);
				args = commandArgs.split(" ");
			}
			Command cmd = commands.get(command);
			cmd.doCommand(args);
		}
	}

}
