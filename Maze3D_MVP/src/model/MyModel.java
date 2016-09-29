package model;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

import properties.Properties;
import properties.PropertiesLoader;

public class MyModel extends Observable implements Model {
	
	private ExecutorService executor;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<Maze3d,Solution<Position>> solutions = new ConcurrentHashMap<Maze3d,Solution<Position>>();
	private Properties properties;
	
	public MyModel() {
		properties = PropertiesLoader.getInstance().getProperties();
		executor = Executors.newFixedThreadPool(properties.getNumOfThreads());
		loadSolutions();
	}
	
//	public void clearSolution(String name)
//	{
//		this.solutions.remove(this.getMaze(name));
//		
//	}
	

	@Override
	public void generateMaze(String name, int floors, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {
			
			@Override
			public Maze3d call() throws Exception {
				//TODO check if strategy pattern is applicable
				switch(properties.getGenerateMazeAlgorithm()) {
					case "GrowingTree":
					{
						GrowingTreeGenerator generator = new GrowingTreeGenerator();
						generator.setGrowingTreeAlgorithm(new RandomCellChooser());
						Maze3d maze = generator.generate(floors, rows, cols);
						mazes.put(name, maze);
						
						setChanged();
						notifyObservers("maze_ready " + name);
						return maze;
					}
					case "Simple":
					{
						SimpleMaze3dGenerator generator = new SimpleMaze3dGenerator();
						Maze3d maze = generator.generate(floors, rows, cols);
						mazes.put(name, maze);
						
						setChanged();
						notifyObservers("maze_ready " + name);
						return maze;
					}
					default:
						GrowingTreeGenerator generator = new GrowingTreeGenerator();
						generator.setGrowingTreeAlgorithm(new RandomCellChooser());
						Maze3d maze = generator.generate(floors, rows, cols);
						mazes.put(name, maze);
						
						setChanged();
						notifyObservers("maze_ready " + name);
						return maze;
				}
			}
		});
	}

	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
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
		executor.execute(new Runnable() {
		//executor.submit(new Callable<Solution<Position>>() {

			
			@Override
//			public Solution<Position> call() throws Exception {
		    public void run() {
				

				Maze3d maze=getMaze(name);
				Searchable<Position> md=new Maze3dDomain(maze);
				
				switch(algorithm){
				case "bfs":
					Searcher<Position> algBfs=new BFS<Position>();
					Solution<Position> solBfs=algBfs.search(md);

					solutions.put(mazes.get(name), solBfs);
					
					setChanged();
					notifyObservers("solution_ready " + name);
					break;
				
				case "dfs":
					Searcher<Position> algDfs=new DFS<Position>();
					Solution<Position> solDfs=algDfs.search(md);

					solutions.put(mazes.get(name), solDfs);
					
					setChanged();
					notifyObservers("solution_ready " + name);
					break;
		
				
				default:
					notifyObservers("display_message " + "not solving because no algorithm was selected");	
					break;
				}
				
		    }
				//return null;	
			
			
			
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
	
	
	@SuppressWarnings("unchecked")
	private void loadSolutions() {
		File file = new File("solutions.dat");
		if (!file.exists())
			return;
		
		ObjectInputStream ois = null;
		
		try {
			ois = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
			mazes = (Map<String, Maze3d>)ois.readObject();
			solutions = (Map<Maze3d, Solution<Position>>)ois.readObject();		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				ois.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	private void saveSolutions() {
		ObjectOutputStream oos = null;
		try {
		    oos = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
			oos.writeObject(mazes);
			oos.writeObject(solutions);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public void exit() {
		executor.shutdownNow();
		saveSolutions();
	}

	@Override
	public void solveForHint(String position, String name) {

		String posString = position.substring(1, position.length()-1);
		String[] posStringSplit = posString.split(",");
		
		Position pos = new Position(Integer.parseInt(posStringSplit[0]),Integer.parseInt(posStringSplit[1]),Integer.parseInt(posStringSplit[2]));

		
				
		mazes.get(name).setStartPosition(pos);
		String algorithm = properties.getSolveMazeAlgorithm();
		setChanged();
		notifyObservers("solve " + name + " " + algorithm);

		
	}

	@Override
	public void loadProperties(String path) {
		
		PropertiesLoader.setInstance(path);
		
		properties = new Properties();
		
		properties = PropertiesLoader.getInstance().getProperties();
		
		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder(new FileOutputStream("properties.xml"));
			xmlEncoder.writeObject(PropertiesLoader.getInstance().getProperties());
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			xmlEncoder.close();
		}
	}


	



}
