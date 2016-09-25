package algorithms.search;

import org.junit.Test;

import algorithms.demo.Maze3dDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomCellChooser;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;


public class BFSTest {

	@Test
	public void testBFSForMazeDomainGrowingTree() {
		BFS<Position> test = new BFS<Position>();
		
		GrowingTreeGenerator generator = new GrowingTreeGenerator(); // create a growing tree generator
		generator.setGrowingTreeAlgorithm(new RandomCellChooser()); // set the random cell choosing method for the growing tree
		Maze3d randomCellChooserMaze = generator.generate(50, 50, 50);
		
		Searchable<Position> seachableMaze = new Maze3dDomain(randomCellChooserMaze);
		
		test.search(seachableMaze);
	}
	
	@Test
	public void testBFSForMazeDomainSimple() {
		BFS<Position> test = new BFS<Position>();
		
		SimpleMaze3dGenerator generator = new SimpleMaze3dGenerator(); // create a growing tree generator
		
		Maze3d simple = generator.generate(50, 50, 50);
		
		Searchable<Position> seachableSimpleMaze = new Maze3dDomain(simple);
		
		test.search(seachableSimpleMaze);
	}
	
	@Test
	public void testBFSForNull() {
		BFS<Position> testNull = new BFS<Position>();
		testNull.search(null);
	}
	
	@Test
	public void testBFSForSmallMazeDomainGrowingTree() {
		BFS<Position> test = new BFS<Position>();
		
		GrowingTreeGenerator generator = new GrowingTreeGenerator(); // create a growing tree generator
		generator.setGrowingTreeAlgorithm(new RandomCellChooser()); // set the random cell choosing method for the growing tree
		Maze3d randomCellChooserMaze = generator.generate(4, 4, 4);
		
		Searchable<Position> seachableMaze = new Maze3dDomain(randomCellChooserMaze);
		
		test.search(seachableMaze);
	}
	
	@Test
	public void testBFSForHugeMazeDomainGrowingTree() {
		BFS<Position> test = new BFS<Position>();
		
		GrowingTreeGenerator generator = new GrowingTreeGenerator(); // create a growing tree generator
		generator.setGrowingTreeAlgorithm(new RandomCellChooser()); // set the random cell choosing method for the growing tree
		Maze3d randomCellChooserMaze = generator.generate(300, 300, 300);
		
		Searchable<Position> seachableMaze = new Maze3dDomain(randomCellChooserMaze);
		
		test.search(seachableMaze);
	}

	

}
