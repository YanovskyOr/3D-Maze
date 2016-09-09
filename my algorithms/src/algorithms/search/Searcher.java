package algorithms.search;

/**
 * <h1>Searcher Interface</h1>
 * This class defines what a searching algorithm must have
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public interface Searcher<T> {
	/**
	 * the search method
	 * @return a solution
	 *
     */
    public Solution<T> search(Searchable<T> s);
    
	/**
	 * get how many nodes were evaluated by the algorithm
	 * @return int
	 *
     */ 
    public int getNumberOfNodesEvaluated();
}
