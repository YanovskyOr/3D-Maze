package algorithms.mazeGenerators;

import java.util.List;

/**
 * <h1>Cell Chooser Interface</h1>
 * an interface used by the cell choosers for the growing tree algorithm.
 * <BR>
 * cell choosers override the "choose" method to choose a cell from the list in different ways.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public interface CellChooser{
	/**
	 * This is a method a cell chooser must override, defining its own way of choosing a cell.
	 * @param cells A list of cells, basically positions in the maze, potential for the next move.
	 * @return a position in the maze
	 */
	Position choose(List<Position> cells);
}
