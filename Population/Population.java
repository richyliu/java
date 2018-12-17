import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;	

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
	private boolean exit;
	
	
	/**
	 * Population constructor, initializes cities list, sort methods, and exit
	 * boolean
	 */
	public Population() {
		cities = new ArrayList<City>();
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
				selectionSortPop(cities);
				break;
			case 2:
				mergeSortPop(cities);
				break;
			case 3:
				insertionSortName(cities);
				break;
			case 4:
				mergeSortName(cities);
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
				mergeSortPop(inState);
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
				mergeSortPop(otherCities);
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
			printCities(cities);
		
		// tell user the elapsed time
		System.out.printf("\nElapsed time %d milliseconds\n", end - start);
	}
	

	/**
	 * Print a list of cities formatted correctly
	 * @param ct	Cites list to print
	 */
	public void printCities(List<City> cityList) {
		// number of cities to print
		// if cityList size is less than this number then size will be used
		int PRINT_NUM = 50;
		
		// print header
		System.out.printf("\n     %-22s %-22s %-12s %12s\n", "State", "City",
				"Type", "Population");
		// print each line with a number
		for (int i = 0; i < Math.min(PRINT_NUM, cityList.size()); i++) {
			System.out.printf("%3d: ", i+1);
			System.out.println(cityList.get(i));
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
	
	
	
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of City objects to sort
	 */
	public void bubbleSort(List<City> arr) {
		for (int a = arr.size() - 1; a > 0; a--)
			for (int b = 0; b < a; b++)
				if (arr.get(b).compareTo(arr.get(b+1)) > 0)
					swap(arr, b, b+1);
	}
	
	/**
	 *	Swaps two City objects in array arr
	 *	@param arr		array of City objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(List<City> arr, int x, int y) {
		City temp = arr.get(x);
		arr.set(x, arr.get(y));
		arr.set(y, temp);
	}
	
	/**
	 *	Selection Sort algorithm - population in ascending order
	 *	@param arr		array of City objects to sort
	 */
	public void selectionSortPop(List<City> arr) {
		for (int i = arr.size(); i > 1; i--) {
			// index of max element
			int max = 0;
			
			for (int j = 0; j < i; j++)
				if (arr.get(j).compareTo(arr.get(max)) > 0)
					max = j;
			swap(arr, max, i-1);
		}
	}
	
	/**
	 *	Insertion Sort algorithm - in ascending order
	 *	@param arr		array of City objects to sort
	 */
	public void insertionSortName(List<City> arr) {
		for (int i = 1; i < arr.size(); i++) {
			City next = arr.get(i);
			
			int j = i;
			while(j > 0 && next.compareToName(arr.get(j-1)) < 0) {
				arr.set(j, arr.get(j-1));
				j--;
			}
			
			arr.set(j, next);
		}
	}
	
	/**
	 *	Merge Sort algorithm - population in descending order
	 *	@param arr		array of City objects to sort
	 */
	public void mergeSortPop(List<City> arr) {
		List<City> sorted = msPop(arr, 0, arr.size() - 1);
		// copy to arr
		for (int i = 0; i < arr.size(); i++)
			arr.set(i, sorted.get(i));
	}
	
	/**
	 * Merge sort recursive part - population in descending order
	 * @param arr	Array of City objects to sort
	 * @param start	Where to start sorting (inclusive)
	 * @param end	Where to end sorting (inclusive)
	 * @return		Sorted List<City> array
	 */
	private List<City> msPop(List<City> arr, int start, int end) {
		// if 1 number, that is by definition sorted
		if (start == end) {
			return new ArrayList<City>(Arrays.asList(arr.get(start)));
		}
		
		// where to split to sort
		int split = (end - start) / 2;
		// split into 2 parts and sort
		List<City> a = msPop(arr, start, start + split);
		List<City> b = msPop(arr, start + split + 1, end);
		
		List<City> sorted = new ArrayList<City>();
		// index of a and b array
		int ai = 0;
		int bi = 0;
		
		// merge the a and b parts together
		while(ai < a.size() && bi < b.size()) {
			if (b.get(bi).compareTo(a.get(ai)) < 0) {
				sorted.add(a.get(ai));
				ai++;
			} else {
				sorted.add(b.get(bi));
				bi++;
			}
		}
		// add remaining from a and b to sorted array
		while(bi < b.size()) {
			sorted.add(b.get(bi));
			bi++;
		}
		while(ai < a.size()) {
			sorted.add(a.get(ai));
			ai++;
		}
		
		return sorted;
	}
	
	/**
	 *	Merge Sort algorithm - name in descending order
	 *	@param arr		array of City objects to sort
	 */
	public void mergeSortName(List<City> arr) {
		List<City> sorted = msName(arr, 0, arr.size() - 1);
		// copy to arr
		for (int i = 0; i < arr.size(); i++)
			arr.set(i, sorted.get(i));
	}
	
	/**
	 * Merge sort recursive part - name in descending order
	 * @param arr	Array of City objects to sort
	 * @param start	Where to start sorting (inclusive)
	 * @param end	Where to end sorting (inclusive)
	 * @return		Sorted List<City> array
	 */
	private List<City> msName(List<City> arr, int start, int end) {
		// if 1 number, that is by definition sorted
		if (start == end) {
			return new ArrayList<City>(Arrays.asList(arr.get(start)));
		}
		
		// where to split to sort
		int split = (end - start) / 2;
		// split into 2 parts and sort
		List<City> a = msName(arr, start, start + split);
		List<City> b = msName(arr, start + split + 1, end);
		
		List<City> sorted = new ArrayList<City>();
		// index of a and b array
		int ai = 0;
		int bi = 0;
		
		// merge the a and b parts together
		while(ai < a.size() && bi < b.size()) {
			if (b.get(bi).compareToName(a.get(ai)) < 0) {
				sorted.add(a.get(ai));
				ai++;
			} else {
				sorted.add(b.get(bi));
				bi++;
			}
		}
		// add remaining from a and b to sorted array
		while(bi < b.size()) {
			sorted.add(b.get(bi));
			bi++;
		}
		while(ai < a.size()) {
			sorted.add(a.get(ai));
			ai++;
		}
		
		return sorted;
	}
}