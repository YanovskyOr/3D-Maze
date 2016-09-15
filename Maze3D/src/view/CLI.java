package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.Command;

/**
 * This class defines the command line interface used to input commands by the user into the view part of the MVC model.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
public class CLI {

	
	private List<Thread> threads = new ArrayList<Thread>(); //list of cli threads started, used later to stop all
	HashMap<String, Command> commands = new HashMap<String, Command>(); //list of possible commands
	
	 BufferedReader in;
	 PrintWriter out;

	/**
	 * CTOR for CLI class 
	 * @param in
	 * @param out
	 * @param commands
	 */
	public CLI(BufferedReader in,PrintWriter out, HashMap<String,Command> commands) {
		this.in=in;
		this.out=out;
		this.commands = commands;
	}
	 
	/**
	 * Starts the CLI in a new thread
	 * @throws Exception
	 */
	void start()throws Exception {
		Thread thread = new Thread(new Runnable() {
			String cmd=null;
			Command cmdName=null;

			@Override
			public void run() {
				//waites for commands
				do{
					out.write("Type a command:\n");
					out.flush();
					try{
						cmd=in.readLine(); //reads user command
					} catch (IOException e){
						e.printStackTrace(out);
					  }
					
					if(!cmd.equals("exit") && !cmd.equals(" ")) { //if not exit command
						String[] cmdParts=cmd.split(" "); //splits the command into its name and parameters givven by the user
						cmdName= commands.get(cmdParts[0]);
						
						// if command isnt valid outputs an error message
						if(!commands.containsKey(cmdParts[0])){
							out.write("Command does not exist, try to write the command without spaces in the begining \n");
							out.write("Type a valid command.\n");
							out.flush();	
						}
						else{
						//if valid - executes the command
						cmdName.doCommand(cmdParts);	
						
						}	
					}
					// if the command is exit - exits safely, stopping all the active threads
					else if(cmd.equals("exit")) {
						out.write("exited");
						stopAllThreads(threads);
						
					}
					
					
				}while(!cmd.equals("exit"));
			}
		});
		thread.start();
		threads.add(thread);
	}

	/**
	 * Stops all running threads in the list of threads
	 * @param threads
	 */
	void stopAllThreads(List<Thread> threads){
		for (Thread thread : threads) {
			thread.interrupt();
		}
	}
	
}

