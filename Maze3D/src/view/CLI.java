package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.Command;

public class CLI {

	
	private List<Thread> threads = new ArrayList<Thread>();
	//HashMap<String, Command> commands=
	
	 BufferedReader in;
	 PrintWriter out;
	 
	 public CLI(BufferedReader in,PrintWriter out) {
		this.in=in;
		this.out=out;
	}
	 
void start(){
	
	
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				
				while(!in.equals("exit")){
				
				}
							
			}	
		});
		thread.start();
		threads.add(thread);	 

		
	}
	
}

