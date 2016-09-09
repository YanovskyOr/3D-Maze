package algorithms.mazeGenerators;

import java.util.List;
import java.util.Random;

/**
 * <h1>Random Cell Chooser</h1>
 * Chooses a random cell from the list while executing the growing tree algorithm
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class RandomCellChooser implements CellChooser {

	private Random rand = new Random();
	
	/**
	 * The overridden method of the CellChooser Interface defining how to choose a random cell from the list.
	 */
	@Override
	public Position choose(List<Position> cells) {
		
		int index = rand.nextInt(cells.size());
		
		return cells.get(index);
	}

}
