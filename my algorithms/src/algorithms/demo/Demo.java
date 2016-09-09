package algorithms.demo;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;

/**
 * <h1>Demo</h1>
 * This class shows a demonstration of the search algorithms
 * BFS and DFS, applied to a 3D Maze.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class Demo {
	
	/**
	 * This is the only method in the Demo class.
	 * It receives no parameters.
	 * <P>
	 * The method does the following:
	 * <P>
	 * a. generates a 10*10*10 3D Maze
	 * <BR>
	 * b. prints the maze
	 * <BR>
	 * c. solves it using each algorithm (BFS then DFS)
	 * <BR>
	 * d. prints to console the amount of nodes/states 
	 * the algorithm passed until getting to the goal.
	 */
	public void run(){
		
		System.out.println("\n**************\n     Demo!\n**************\n");
		
		GrowingTreeGenerator generator = new GrowingTreeGenerator(); // create a growing tree generator
		generator.setGrowingTreeAlgorithm(new RandomCellChooser()); // set the random cell choosing method for the growing tree
		Maze3d randomCellChooserMaze = generator.generate(50, 50, 50); //generate a 10*10*10 maze
		System.out.println(randomCellChooserMaze); //print the maze
		
		Searcher<Position> bfsSearcher = new BFS<Position>(); // create a BFS searcher
		Searcher<Position> dfsSearcher = new DFS<Position>(); // create a DFS searcher
		Searchable<Position> seachableMaze = new Maze3dDomain(randomCellChooserMaze); // use the adapter class "Maze3dDomain" to adapt the 3D Maze to Searchable
		
		System.out.println("\nBFS:\n"); 
		bfsSearcher.search(seachableMaze); // use BFS to solve the maze
		System.out.println("Number of nodes: " + bfsSearcher.getNumberOfNodesEvaluated()); // print the number of nodes used by BFS to the console
		
		
		
		System.out.println("\nDFS:\n");
		dfsSearcher.search(seachableMaze); // use DFS to solve the maze
		System.out.println("Number of nodes: " + dfsSearcher.getNumberOfNodesEvaluated()); // print the number of nodes used by DFS to the console

	}
}
