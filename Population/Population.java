import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *	Population - Sorts and prints the cities of USA according to population or
 * 	name using various sort methods. Can also sort cities of a certain name or
 * 	in a certain state by population.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Richard
 *	@since	December 3, 2018
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";
	// sort methods instance
	private SortMethods sort;
	private boolean exit;
	
	
	/**
	 * Population constructor, initializes cities list, sort methods, and exit
	 * boolean
	 */
	public Population() {
		cities = new ArrayList<City>();
		sort = new SortMethods();
		exit = false;
	}
	
	
	/**
	 * Main method that calls the run method
	 */
	public static void main(String[] args) {
		Population pop = new Population();
		pop.run();
	}
	
	
	/**
	 * Main run loop of the program
	 */
	public void run() {
		readData();
		printIntroduction();
		System.out.printf("%d cities in database\n", cities.size());
		
		// prompt user and print sort while not exiting
		while(!exit) prompt();
		
		System.out.println("\nThank you for using Population!\n");
	}
	
	
	/**
	 * Prompt user for selection and sorts and prints results
	 */
	public void prompt() {
		printMenu();
		int selection = Prompt.getInt("Enter selection", 1, 9);
		
		// time how long it takes to sort
		long start = System.currentTimeMillis();
		switch (selection) {
			// sort cities using sort method for 1-4
			case 1:
				sort.selectionSortPop(cities);
				break;
			case 2:
				sort.mergeSortPop(cities);
				break;
			case 3:
				sort.insertionSortName(cities);
				break;
			case 4:
				sort.mergeSortName(cities);
				break;
			// 5-6 require additional prompting
			case 5:
				String state = "";
				List<City> inState = new ArrayList<City>();
				
				// prompt until there are cites with state
				while(inState.size() == 0) {
					// ask user for state
					state = Prompt.getString("\nEnter state name (ie. Alabama)");
					
					// filter all cities with same state name
					inState.clear();
					for (City city : cities)
						if (city.getState().equals(state))
							inState.add(city);
				}
				
				System.out.println("\nFifty most populous cities in " + state);
				
				// sort and print
				sort.mergeSortPop(inState);
				printCities(inState);
				// don't do the stuff after the break
				return;
			case 6:
				String cityName = "";
				List<City> otherCities = new ArrayList<City>();
				
				// prompt until there are cities with name
				while(otherCities.size() == 0) {
					// ask user for city name
					cityName = Prompt.getString("\nEnter city name");
					
					// filter all cities with same state name
					for (City city : cities)
						if (city.getName().equals(cityName))
							otherCities.add(city);
				}
				
				System.out.printf("\nCity %s by population\n", cityName);
				
				// sort and print
				sort.mergeSortPop(otherCities);
				printCities(otherCities);
				// don't do the stuff after the break
				return;				
			// exit if user requests so
			case 9:
				exit = true;
			// not an action, return from method
			default:
				return;
		}
		// finish sorting
		long end = System.currentTimeMillis();
		
		// print cites for selections 1-4
		if (selection >= 1 && selection <= 4)
			printCities();
		
		// tell user the elapsed time
		System.out.printf("\nElapsed time %d milliseconds\n", end - start);
	}
	
	
	/**
	 * Print the field cities array list
	 */
	public void printCities() {
		printCities(cities);
	}
	
	
	/**
	 * Print a list of cities formatted correctly
	 * @param ct	Cites list to print
	 */
	public void printCities(List<City> ct) {
		// number of cities to print
		// if ct list size is less than this number then size will be used
		int PRINT_NUM = 50;
		
		// print header
		System.out.printf("\n     %-22s %-22s %-12s %12s\n", "State", "City",
				"Type", "Population");
		// print each line with a number
		for (int i = 0; i < Math.min(PRINT_NUM, ct.size()); i++) {
			System.out.printf("%3d: ", i+1);
			System.out.println(ct.get(i));
		}
	}
	
	
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("\n1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}
	
	
	/**
	 * Read input data and save to list of cities
	 */
	public void readData() {
		Scanner in = FileUtils.openToRead(DATA_FILE);
		// get tokens by tab or newline
		in.useDelimiter("[\t\n]");
		
		// add from file to cities list
		while(in.hasNext())
			cities.add(new City(in.next(), in.next(), in.next(), in.nextInt()));
	}	
}