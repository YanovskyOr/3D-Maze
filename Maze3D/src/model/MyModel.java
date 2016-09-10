package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.RandomCellChooser;
import controller.Controller;

public class MyModel implements Model {

	private Controller controller;	
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	
	private List<Thread> threads = new ArrayList<Thread>();

	public MyModel(Controller controller) {
		this.controller = controller;
	}
	
	
	@Override
	public void generateMaze(String name, int floors, int rows, int cols) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				generator.setGrowingTreeAlgorithm(new RandomCellChooser());
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				controller.notifyMazeIsReady(name);				
			}	
		});
		thread.start();
		threads.add(thread);
	}
	//TODO:close threads safely

	
	//TODO:do we want displayCrossSecction to be in  another thread?
	@Override
	public void DisplayCrossSection(String crossBy, int index, String name) {
	 Maze3d maze=getMaze(name);
	String x = "x";
	String y= "y";
	String z= "z";
	int [][] CrossMaze;
	
	 if(crossBy.equalsIgnoreCase(x)){
	CrossMaze= maze.getCrossSectionByX(index);
	 int index1=maze.getFloors();
	 int index2=maze.getRows();
	 controller.PrintCrossSection(maze,CrossMaze,index1,index2);
	 }
	else if(crossBy.equalsIgnoreCase(y)){
		CrossMaze= maze.getCrossSectionByY(index);
	 int index1=maze.getFloors();
	 int index2=maze.getCols();
	 controller.PrintCrossSection(maze,CrossMaze,index1,index2);
	}
	else{
		CrossMaze=maze.getCrossSectionByZ(index);
	 int index1=maze.getRows();
	 int index2=maze.getCols();
	 controller.PrintCrossSection(maze,CrossMaze,index1,index2);
	}
	
}
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}


	//y=row x=cols z=floors

}
