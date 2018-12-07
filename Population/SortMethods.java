import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *	SortMethods - Sorts an array of Citys in ascending order.
 *
 *	@author Richard Liu
 *	@since	November 29, 2018
 */
public class SortMethods {
	
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
	 *	Selection Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of City objects to sort
	 */
	public void selectionSort(List<City> arr) {
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
	 *	Insertion Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of City objects to sort
	 */
	public void insertionSort(List<City> arr) {
		for (int i = 1; i < arr.size(); i++) {
			City next = arr.get(i);
			
			int j = i;
			while(j > 0 && next.compareTo(arr.get(j-1)) < 0) {
				arr.set(j, arr.get(j-1));
				j--;
			}
			
			arr.set(j, next);
		}
	}
	
	/**
	 *	Merge Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of City objects to sort
	 */
	public void mergeSort(List<City> arr) {
		List<City> sorted = ms(arr, 0, arr.size() - 1);
		// copy to arr
		for (int i = 0; i < arr.size(); i++)
			arr.set(i, sorted.get(i));
	}
	
	/**
	 * Merge sort recursive part
	 * @param arr	Array of City objects to sort
	 * @param start	Where to start sorting (inclusive)
	 * @param end	Where to end sorting (inclusive)
	 * @return		Sorted List<City> array
	 */
	private List<City> ms(List<City> arr, int start, int end) {
		// if 1 number, that is by definition sorted
		if (start == end) {
			return new ArrayList<City>(Arrays.asList(arr.get(start)));
		}
		
		// where to split to sort
		int split = (end - start) / 2;
		// split into 2 parts and sort
		List<City> a = ms(arr, start, start + split);
		List<City> b = ms(arr, start + split + 1, end);
		
		List<City> sorted = new ArrayList<City>();
		// index of a and b array
		int ai = 0;
		int bi = 0;
		
		// merge the a and b parts together
		while(ai < a.size() && bi < b.size()) {
			if (b.get(bi).compareTo(a.get(ai)) > 0) {
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
	
	/*****************************************************************/
	/************************* For Testing ***************************/
	/*****************************************************************/
	
	/**
	 *	Print an array of Citys to the screen
	 *	@param arr		the array of Citys
	 */
	public void printArray(List<City> arr) {
		if (arr.size() == 0) System.out.print("(");
		else System.out.printf("( %4d", arr.get(0).getPop());
		for (int a = 1; a < arr.size(); a++) {
			if (a % 10 == 0) System.out.printf(",\n  %4d", arr.get(a).getPop());
			else System.out.printf(", %4d", arr.get(a).getPop());
		}
		System.out.println(" )");
	}

	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		List<City> arr = new ArrayList<City>();
		// Fill arr with random numbers
		for (int a = 0; a < 10; a++)
			arr.add(new City("foo", "bar", "baz", (int)(Math.random() * 100) + 1));
		System.out.println("\nBubble Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		bubbleSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		arr.clear();
		for (int a = 0; a < 10; a++)
			arr.add(new City("foo", "bar", "baz", (int)(Math.random() * 100) + 1));
		System.out.println("\nSelection Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		selectionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		arr.clear();
		for (int a = 0; a < 10; a++)
			arr.add(new City("foo", "bar", "baz", (int)(Math.random() * 100) + 1));
		System.out.println("\nInsertion Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		insertionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		arr.clear();
		for (int a = 0; a < 10; a++)
			arr.add(new City("foo", "bar", "baz", (int)(Math.random() * 100) + 1));
		System.out.println("\nMerge Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		mergeSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

	}
}