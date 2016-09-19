package model;

import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.search.Solution;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<Maze3d,Solution<Position>> solutions = new ConcurrentHashMap<Maze3d,Solution<Position>>();

	public MyModel() {
		executor = Executors.newFixedThreadPool(50);
	}
	
	

	@Override
	public void generateMaze(String name, int floors, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {
			
			@Override
			public Maze3d call() throws Exception {
				GrowingTreeGenerator generator = new GrowingTreeGenerator();
				generator.setGrowingTreeAlgorithm(new RandomCellChooser());
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				setChanged();
				notifyObservers("maze_ready " +name);
				return maze;
			}
		});
	}

	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	@Override
	public void exit() {
		executor.shutdownNow();
	}

	@Override
	public void displayCrossSection(String crossBy, int index, String name) {
		Maze3d maze=getMaze(name);
		String x = "x";
		String y= "y";
		String z= "z";
		int [][] CrossMaze;
		
		if(crossBy.equalsIgnoreCase(x)){
			try {
				CrossMaze= maze.getCrossSectionByX(index);
				int index1=maze.getFloors();
				int index2=maze.getRows();
				
				
				notifyObservers("print_cross_section " + maze + CrossMaze + index1 + index2);
			} catch (Exception e) {
				notifyObservers("display_message " + "Index must be in range!");
			}

		 }
		
		else if(crossBy.equalsIgnoreCase(y)){
			try {
				CrossMaze= maze.getCrossSectionByY(index);
				int index1=maze.getFloors();
				int index2=maze.getCols();
				
				notifyObservers("print_cross_section " + maze + CrossMaze + index1 + index2);
			} catch (Exception e) {
				notifyObservers("display_message " + "Index must be in range!");
			}
		}
		
		else if(crossBy.equalsIgnoreCase(z)){
			try {
				CrossMaze=maze.getCrossSectionByZ(index);
				int index1=maze.getRows();
				int index2=maze.getCols();
				
				notifyObservers("print_cross_section " + maze + CrossMaze + index1 + index2);
			} catch (Exception e) {
				notifyObservers("display_message " + "Index must be in range!");
			}
		}	
	}

	@Override
	public void solveMaze(String name, String algorithm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveMaze(String name, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadMaze(String fileName, String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Solution<Position> getSolution(String name) {
		Solution<Position> mazeSolution=solutions.get(name);
		return mazeSolution;
	}

	@Override
	public void dir(String path) {
		// TODO Auto-generated method stub
		
	}

}
