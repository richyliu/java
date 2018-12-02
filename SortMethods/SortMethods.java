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
		int[] a = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			a[i] = arr[i];
		}
		
		ms(a, 0, arr.length - 1);
	}
	
	private int[] ms(int[] arr, int start, int end) {
		if (start == end) {
			return new int[]{ arr[start] };
		}
		
		int split = (end - start) / 2;
		int[] a = ms(arr, start, start + split);
		int[] b = ms(arr, start + split+1, end);
		for (int x = 0; x < a.length; x++)
			System.out.print(a[x] + ", ");
		System.out.println();
		
		int ap = 0;
		int bp = 0;
		
		int[] sorted = new int[end - start + 1];
		int i = 0;
		
		while(ap < a.length && bp < b.length) {
			if (a[ap] < b[bp]) {
				sorted[i] = a[ap];
				i++;
				ap++;
			} else {
				sorted[i] = b[bp];
				i++;
				ap++;
			}
		}
		while(bp < b.length) {
			sorted[i] = b[bp];
			i++;
			bp++;
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