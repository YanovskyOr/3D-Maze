package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

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
	public String getCrossSection(String crossBy, int index, String name) {
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
				
				String cross=maze.printCrossSection(CrossMaze, index1, index2);
				return cross;
				
			} catch (Exception e) {
				setChanged();
				notifyObservers("display_message " + "Index must be in range!");
			}

		 }
		
		else if(crossBy.equalsIgnoreCase(y)){
			try {
				CrossMaze= maze.getCrossSectionByY(index);
				int index1=maze.getFloors();
				int index2=maze.getCols();
				
				String cross=maze.printCrossSection(CrossMaze, index1, index2);
				return cross;
			} catch (Exception e) {
				setChanged();
				notifyObservers("display_message " + "Index must be in range!");
			}
		}
		
		else if(crossBy.equalsIgnoreCase(z)){
			try {
				CrossMaze=maze.getCrossSectionByZ(index);
				int index1=maze.getRows();
				int index2=maze.getCols();
				
				String cross=maze.printCrossSection(CrossMaze, index1, index2);
				return cross;
			} catch (Exception e) {
				setChanged();
				notifyObservers("display_message " + "Index must be in range!");
			}
		}
		
		return null;
	}

	@Override
	public void solveMaze(String name, String algorithm) {
		executor.submit(new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				
				Maze3d maze=getMaze(name);
				Searchable<Position> md=new Maze3dDomain(maze);
				
				switch(algorithm){
				case "bfs":
					Searcher<Position> algBfs=new BFS<Position>();
					Solution<Position> solBfs=algBfs.search(md);
					setChanged();
					notifyObservers("solution_ready " + name);
					solutions.put(mazes.get(name), solBfs);
					break;
				
				case "dfs":
					Searcher<Position> algDfs=new DFS<Position>();
					Solution<Position> solDfs=algDfs.search(md);
					setChanged();
					notifyObservers("solution_ready " + name);
					solutions.put(mazes.get(name), solDfs);
					break;
		
				
				default:
					System.out.println("not solving because no algorithm was selected");	
					break;
				}
				return null;	
				
			}	
		});
	}
	

	@Override
	public void saveMaze(String name, String fileName) {
		OutputStream out;
		
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(fileName+".maze"));
			Maze3d MazeToSave = mazes.get(name);
			byte[] arr = MazeToSave.toByteArray();
			
			int arrLength = arr.length;
			int numberOfBytesRequired = 1;
			
			if (arrLength <= 255) {
				out.write(numberOfBytesRequired);
				out.write(arr.length);
			}	
			else{
				 numberOfBytesRequired = arrLength / 255;
				 out.write(numberOfBytesRequired+1);
				 for ( int i = 0; i < numberOfBytesRequired; i++){
					 	arrLength = arrLength - 255;
					 	out.write(255);
				 }
				 out.write(arrLength);
			}
			out.write(arr);
			out.flush();
			out.close();
			setChanged();
			notifyObservers("maze_saved " + name);
		} catch (Exception e) {
			setChanged();
			notifyObservers("display_message " + "failed to save maze");
		}
	}

	@Override
	public void loadMaze(String fileName, String name) {
		InputStream in;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName));
			int numberOfBytesRequired = in.read();
			int size = 0;
			for (int i = 0; i < numberOfBytesRequired; i++) {
				size = size + in.read();
			}
			byte b[]=new byte[size];
			in.read(b);
			in.close();
			
			Maze3d loaded = new Maze3d(b);
			mazes.put(name, loaded);
			
			setChanged();
			notifyObservers("display " + name);
			setChanged();
			notifyObservers("maze_loaded " + name);

		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers("display_message " + "error loading maze - file not found");
		} catch (IOException e) {
			setChanged();
			notifyObservers("display_message " + "error loading maze");
		}
	}
		


	@Override
	public Solution<Position> getSolution(String name) {
		Solution<Position> mazeSolution=solutions.get(mazes.get(name));
		return mazeSolution;
	}

	@Override
	public void dir(String path) {
		// TODO Auto-generated method stub
		
	}


}
