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
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import properties.Properties;
import properties.PropertiesLoader;

/**
 * This is the model part of the MVP design.
 * <BR>
 * My model does all the calculations.
 * <BR>
 * Heavy tasks are performed inside separate threads.
 * @author Or Yanovsky and Lilia Misotchenko
 *
 */
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
	
	/**
	 * Generates a maze, using user-inputed info
	 * <BR>
	 * The algorithms used to generate the maze determined from the properties file.
	 * Default is the growing tree algorithm.
	 * @param name the name of the maze
	 * @param floors amount of floors (the Z axis of the maze)
	 * @param rows amount of rows (the Y axis of the maze)
	 * @param cols amount of columns (the X axis of the maze)
	 */
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

	
	/**
	 * Returns a maze by its name
	 * @param name name of the required maze
	 * @return Maze3d
	 */
	@Override
	public Maze3d getMaze(String name) {
		return mazes.get(name);
	}

	
	/**
	 * Returns a crossSection of the maze
	 * @param crossBy axis to use (Z/Y/X)
	 * @param index index on the axis
	 * @param name name of the maze
	 * @return String CrossSection
	 */
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

	/**
	 * Solves the maze for a solution (steps) to get from start position to goal position.
	 * 
	 * @param name the name of the maze to solve
	 * @param the algorithm to use to solve
	 */
	@Override
	public void solveMaze(String name, String algorithm) {
		executor.execute(new Runnable() {
		
			@Override
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
		});
	}
	
	/**
	 * Saves the maze to a file in the running path.
	 * <BR>
	 * A compression algorithm is used to make the saved file smaller.
	 * <BR>
	 * the saved file gets a .maze file extension.
	 * @param name the name of the maze to save
	 * @param fileName the name to use for saved file
	 */
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

	/**
	 * Loads a maze from a file.
	 * <BR>
	 * A decompression algorithm is used to load the compressed .maze file
	 * @param fileName the file name (and possibly path) to load from
	 * @param name the name of the loaded maze
	 */
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
		
	/**
	 * Returns the solution for a maze by the maze's name
	 * @param name the name of the required maze
	 * @return Solution<Position>
	 */
	@Override
	public Solution<Position> getSolution(String name) {
		Solution<Position> mazeSolution=solutions.get(mazes.get(name));
		return mazeSolution;
	}

	/**
	 * Prints the list of files and directories in a given path
	 * @param path
	 */
	@Override
	public void dir(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
	    	  setChanged();
			  notifyObservers("File " + listOfFiles[i].getName());
	      } else if (listOfFiles[i].isDirectory()) {
	    	  setChanged();
			  notifyObservers("Directory " + listOfFiles[i].getName());
	      }
	    }
		
	}
	
	/**
	 * Loads the solution list from a compressed zip (.dat) file for future use.
	 */
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
	
	/**
	 * Saves the solution list to a compressed zip (.dat) file for future use.
	 */
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
	
	/**
	 * Exists after termination of all running threads.
	 * <BR>
	 * Saves the solution list to file using the saveSolutions() method.
	 */
	@Override
	public void exit() {
		executor.shutdownNow();
		saveSolutions();
	}

	/**
	 * Solves the maze for use in a hint or auto-solution function.
	 * @param position current position of the players character
	 * @param name name of the maze
	 */
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

	/**
	 * Used to load properties from a file and rewrite currently used file.
	 * @param path the path to the desired properties file
	 */
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
