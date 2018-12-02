/**
 *	SortMethods - Sorts an array of Integers in ascending order.
 *
 *	@author Richard Liu
 *	@since	November 29, 2018
 */
public class SortMethods {
	
	/**
	 *	Bubble Sort algorithm - in ascending order
	 *	@param arr		array of Integer objects to sort
	 */
	public void bubbleSort(Integer [] arr) {
		for (int a = arr.length - 1; a > 0; a--)
			for (int b = 0; b < a; b++)
				if (arr[b].compareTo(arr[b+1]) > 0)
					swap(arr, b, b+1);
	}
	
	/**
	 *	Swaps two Integer objects in array arr
	 *	@param arr		array of Integer objects
	 *	@param x		index of first object to swap
	 *	@param y		index of second object to swap
	 */
	private void swap(Integer[] arr, int x, int y) {
		Integer temp = arr[x];
		arr[x] = arr[y];
		arr[y] = temp;
	}
	
	/**
	 *	Selection Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void selectionSort(Integer [] arr) {
		for (int i = arr.length; i > 1; i--) {
			// index of max element
			int max = 0;
			
			for (int j = 0; j < i; j++)
				if (arr[j].compareTo(arr[max]) > 0)
					max = j;
			swap(arr, max, i-1);
		}
	}
	
	/**
	 *	Insertion Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void insertionSort(Integer [] arr) {
		for (int i = 1; i < arr.length; i++) {
			Integer next = arr[i];
			
			int j = i;
			while(j > 0 && next.compareTo(arr[j-1]) < 0) {
				arr[j] = arr[j-1];
				j--;
			}
			
			arr[j] = next;
		}
	}
	
	/**
	 *	Merge Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void mergeSort(Integer [] arr) {
		Integer[] sorted = ms(arr, 0, arr.length - 1);
		// copy to arr
		for (int i = 0; i < arr.length; i++)
			arr[i] = sorted[i];
	}
	
	/**
	 * Merge sort recursive part
	 * @param arr	Array of Integer objects to sort
	 * @param start	Where to start sorting (inclusive)
	 * @param end	Where to end sorting (inclusive)
	 * @return		Sorted Integer[] array
	 */
	private Integer[] ms(Integer[] arr, int start, int end) {
		// if 1 number, that is by definition sorted
		if (start == end) {
			return new Integer[]{ arr[start] };
		}
		
		// where to split to sort
		int split = (end - start) / 2;
		// split into 2 parts and sort
		Integer[] a = ms(arr, start, start + split);
		Integer[] b = ms(arr, start + split + 1, end);
		
		Integer[] sorted = new Integer[a.length + b.length];
		// index of a and b array
		int ai = 0;
		int bi = 0;
		// index of sorted array
		int i = 0;
		
		// merge the a and b parts together
		while(ai < a.length && bi < b.length) {
			if (b[bi].compareTo(a[ai]) > 0) {
				sorted[i] = a[ai];
				ai++;
			} else {
				sorted[i] = b[bi];
				bi++;
			}
			i++;
		}
		// add remaining from a and b to sorted array
		while(bi < b.length) {
			sorted[i] = b[bi];
			i++;
			bi++;
		}
		while(ai < a.length) {
			sorted[i] = a[ai];
			i++;
			ai++;
		}
		
		return sorted;
	}
	
	/*****************************************************************/
	/************************* For Testing ***************************/
	/*****************************************************************/
	
	/**
	 *	Print an array of Integers to the screen
	 *	@param arr		the array of Integers
	 */
	public void printArray(Integer[] arr) {
		if (arr.length == 0) System.out.print("(");
		else System.out.printf("( %4d", arr[0]);
		for (int a = 1; a < arr.length; a++) {
			if (a % 10 == 0) System.out.printf(",\n  %4d", arr[a]);
			else System.out.printf(", %4d", arr[a]);
		}
		System.out.println(" )");
	}

	public static void main(String[] args) {
		SortMethods se = new SortMethods();
		se.run();
	}
	
	public void run() {
		Integer[] arr = new Integer[10];
		// Fill arr with random numbers
		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nBubble Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		bubbleSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();

		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nSelection Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		selectionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();


		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
		System.out.println("\nInsertion Sort");
		System.out.println("Array before sort:");
		printArray(arr);
		System.out.println();
		insertionSort(arr);
		System.out.println("Array after sort:");
		printArray(arr);
		System.out.println();


		for (int a = 0; a < 10; a++)
			arr[a] = (int)(Math.random() * 100) + 1;
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