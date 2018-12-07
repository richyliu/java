/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Richard Liu
 *	@since	December 3, 2018
 */
public class City implements Comparable<City> {
	
	// fields
	private String name;			// city name
	private String state;			// state name
	private String designation;		// place designation (i.e. "city", "village", etc.)
	private int population;			// population of city
	
	// constructor
	public City(String name, String state, String designation, int population) {
		this.name = name;
		this.state = state;
		this.designation = designation;
		this.population = population;
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */
	@Override
	public int compareTo(City other) {
		if (this.population != other.population)
			return this.population - other.population;
		
		if (this.state.compareTo(other.state) != 0)
			return this.state.compareTo(other.state);
		
		return this.name.compareTo(other.name);
	}
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */
	public boolean equals(Object other) {
		return other != null && other instanceof City && compareTo((City)other) == 0;
	}
	
	/**	Accessor methods */
	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}