package view;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class MyView extends Observable implements View, Observer {
	
	private BufferedReader in;
	private PrintWriter out;
	private CLI cli;
	private MazeWindow window;
	
	

	public MyView(BufferedReader in, PrintWriter out) {
		this.in = in;
		this.out = out;
		
		cli = new CLI(in, out);
		cli.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o == cli) {
			setChanged();
			notifyObservers(arg);
		}
	}

	@Override
	public void notifyMazeIsReady(String name) {
		out.println("maze " + name + " is ready!");
		out.flush();
	}

	@Override
	public void displayMaze(Maze3d maze) {
		out.println(maze);
		out.flush();
	}

	@Override
	public void print(String str) {
		out.println(str);
		out.flush();
	}

	@Override
	public void start() {
		cli.start();
	}

	@Override
	public void printCrossSection(Maze3d maze, int[][] crossSec, int axis1, int axis2) {
		out.println(maze.printCrossSection(crossSec, axis1, axis2));
		out.flush();
	}

	@Override
	public void printSolution(Solution<Position> mazeSolution) {
		 List<State<Position>> states = new ArrayList<State<Position>>();
		 try {
			states=mazeSolution.getStates();
		} catch (Exception e) {
			out.println("solution doesnt exist");
		}
		 for (State<Position> state : states) {
			 out.println(state+",");
			 out.flush();
		 }
	}

}
