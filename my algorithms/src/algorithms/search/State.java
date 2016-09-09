package algorithms.search;

/**
 * <h1>State</h1>
 * This is a generic class used to define a state in a searchable domain
 * <BR>
 * The searchable adapter class should use this class to adapt a specific domain state to the generic state.
 * 
 * @author Or Yanovsky
 * @version 1.0
 * @since 2016-08-16
 */
public class State<T> implements Comparable<State<T>> {
	
	private State<T> cameFrom; // each states knows its previous state
	private double cost; // the cost to get to that state
	private T value; // the actual domain state
	private String key; // used to compare two states 
	
	
	//setters and getters
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public State(String key) {
		this.key = key;
	}
	
	//CTOR
	public State() {
		
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	

	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		State<T> s = (State<T>)obj;
		return s.value.equals(this.value);
	}
	
	@Override
	public int compareTo(State<T> s) {
		return (int)(this.getCost() - s.getCost());	
		// return > 0 if this > s
		//        < 0 if this < s
		//        = 0 if this == s
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
	
	
}
