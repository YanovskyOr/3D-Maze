package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * The MyModel class is the model part of the MVC.
 * <BR>
 * MyModel is generating, managing and changing the 
 * <BR>
 * Uses CommandManager to manage and define commands.
 * @author Or Yanovsky & Lilia Misotchenko
 *
 */
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
	//TODO: check syntax of user and if name of maze exists
	@Override
	public void displayCrossSection(String crossBy, int index, String name) {
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
	//TODO:change switch case to factory patterns
	//TODO:check first if maze exists (if maze is null) if not throws exception that there is no maze and the user should first create a maze
	//TODO:check that name of the maze exists id not let the user know that they typed the wrong maze name
	
	@Override
	public void solveMaze(String name, String algorithm) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				Maze3d maze=getMaze(name);
				
				Searcher<Position> algBfs=new BFS<Position>();
				Searcher<Position> algDfs=new DFS<Position>();
				Searchable<Position> md=new Maze3dDomain(maze);
				
				switch(algorithm){
				case "bfs":
				
					algBfs.search(md);
					controller.notifySolutionIsReady(name);
					break;
				
				case "dfs":
					algDfs.search(md);
					controller.notifySolutionIsReady(name);
					break;
				
				default:
					System.out.println("not solving because no algorithm was selected");	
					break;
				}				
			}	
		});
		thread.start();
		threads.add(thread);
		
	}

	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	public void saveMaze(String name, String fileName) { 
		OutputStream out;
		
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(fileName+".maze"));
			Maze3d MazeToSave = mazes.get(name);
			byte[] arr = MazeToSave.toByteArray();
			
			out.write(arr.length);
			out.write(arr);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void loadMaze(String fileName, String Name) {
		InputStream in;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName+".maze"));
			int size = in.read();
			byte b[]=new byte[size];
			in.read(b);
			in.close();
			
			Maze3d loaded = new Maze3d(b);
			System.out.println("maze loaded from file:");
			System.out.println(loaded);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//y=row x=cols z=floors
}
