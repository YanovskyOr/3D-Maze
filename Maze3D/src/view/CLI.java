package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import controller.Command;

public class CLI {

	
	private List<Thread> threads = new ArrayList<Thread>();
	HashMap<String, Command> commands = new HashMap<String, Command>();
	
	 BufferedReader in;
	 PrintWriter out;
	 
	// Scanner scanner = new Scanner(in);
	// String cmd = scanner.nextLine();
	 
	 public CLI(BufferedReader in,PrintWriter out, HashMap<String,Command> commands) {
		this.in=in;
		this.out=out;
		this.commands = commands;
	}
	 
void start()throws Exception{
		Thread thread = new Thread(new Runnable() {
			String cmd=null;
			Command cmdName=null;

			@Override
			public void run() {
				
				out.write("Type a command:\n");
				do{
					//out.write("Type a command:\n");
					out.flush();
					try{
						cmd=in.readLine();
					}catch (IOException e){
						e.printStackTrace(out);
					}
				if(!cmd.equals("exit")){
					String[] cmdParts=cmd.split(" ");
					cmdName= commands.get(cmdParts[0]);
					if(!commands.containsKey(cmdParts[0])){
						out.write("Command does not exist \n");
						out.write("Type a valid command:\n");
						out.flush();	
					}
					//TODO:CATCH EXCEPTION id name is with a space
					else{
						//out.write("Your wish is my command");
						cmdName.doCommand(cmdParts);	
						
					}	
				}
				//	out.write("Type a command2:\n");
				}	while(!cmd.equals("exit"));
				
			}
		});
		thread.start();
		threads.add(thread);
	}

@SuppressWarnings("deprecation")
void stopAllThreads(ArrayList<Thread> threads){
	for (Thread thread : threads) {
	//TODO: close all threads safely
	}
}
	
}
