package algorithms.mazeGenerators;

import java.util.List;

/**
 * <h1>Final Cell Chooser</h1>
 * chooses the last cell of the list while executing the growing tree algorithm
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class FinalCellChooser implements CellChooser {

	/**
	 * The overridden method of the CellChooser Interface defining how to choose the final cell of the list.
	 */
	@Override
	public Position choose(List<Position> cells) {
		Position pos = cells.get(cells.size() - 1);
		return pos;
	}

}
