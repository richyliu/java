/**
 *	SudokuMaker - Creates a Sudoku puzzle using recursion and backtracking
 *
 *	@author	Richard Liu
 *	@since  Jan. 22, 2018
 *
 */
public class SudokuMaker {

	private int[][] puzzle;
	private static final int PUZZLE_SIZE = 9;
	
	public SudokuMaker() {
		// create blank puzzle
		puzzle = new int[PUZZLE_SIZE][PUZZLE_SIZE];
		
		// init to all 0's
		for (int i = 0; i < PUZZLE_SIZE; i++)
			for (int j = 0; j < PUZZLE_SIZE; j++)
				puzzle[i][j] = 0;
	}
	
	/**
	 * Generate an array of random number within 1 .. n inclusive that is not
	 * already in the array
	 * @param arr	Array to generate from. 0's are treated as empty
	 * @param n		Maximum value.
	 */
	public int generateRandom(int[] arr, int n) {
		int rand = (int)(Math.random() * n) + 1;
		
		// if rand already in arr, generate again
		for (int i = 0; i < arr.length; i++)
			if(arr[i] == rand)
				return generateRandom(arr, n);
		
		// otherwise return rand (as it doesn't exist in arr)
		return rand;
	}
	
	/**
	 * Creates a sudoku puzzle recusively. Call initially with (0, 0)
	 * @param row 		Current row to work on.
	 * @param column	Current column to work on
	 */
	public boolean createPuzzle(int row, int column) {
		// if row is past the last one, puzzle is done
		if (row == PUZZLE_SIZE) return true;
		
		// create list of random numbers
		int[] nums = new int[PUZZLE_SIZE];
		// then randomly generate numbers
		for (int i = 0; i < PUZZLE_SIZE; i++)
			nums[i] = generateRandom(nums, PUZZLE_SIZE);
		
		// for each number in list to try
		for (int num : nums) {
			// whether the num is not in the row, column, or 3x3 grid or not
			boolean validNum = true;
			
			// check if num is in the row
			for (int i = 0; i < PUZZLE_SIZE; i++)
				if (puzzle[row][i] == num) validNum = false;
			// check if num is in column
			for (int i = 0; i < PUZZLE_SIZE; i++)
				if (puzzle[i][column] == num) validNum = false;
			// check if num is in 3x3 grid
			for (int i = (int)(row/3)*3; i < (int)(row/3)*3 + 3; i++)
				for (int j = (int)(column/3)*3; j < (int)(column/3)*3 + 3; j++)
					if (puzzle[i][j] == num) validNum = false;
			
			// num is not in row, column, or 3x3 grid
			if (validNum) {
				puzzle[row][column] = num;
				// check createPuzzle with next column or next row and start from 0 col
				if (column == PUZZLE_SIZE-1 ? createPuzzle(row + 1, 0) : createPuzzle(row, column + 1))
					// puzzle is completed
					return true;
				else
					// grid doesn't work here, reset to no solution
					puzzle[row][column] = 0;
			}
		}
		
		// no number works in this location, so backtrack
		return false;
	}
		
	/**
	 *	printPuzzle - prints the Sudoku puzzle with borders
	 *	If the value is 0, then print an empty space; otherwise, print the number.
	 */
	public void printPuzzle() {
		System.out.print("  +-----------+-----------+-----------+\n");
		String value = "";
		for (int row = 0; row < puzzle.length; row++) {
			for (int col = 0; col < puzzle[0].length; col++) {
				// if number is 0, print a blank
				if (puzzle[row][col] == 0) value = " ";
				else value = "" + puzzle[row][col];
				if (col % 3 == 0)
					System.out.print("  |  " + value);
				else
					System.out.print("  " + value);
			}
			if ((row + 1) % 3 == 0)
				System.out.print("  |\n  +-----------+-----------+-----------+\n");
			else
				System.out.print("  |\n");
		}
	}
	
	public static void main(String[] args) {
		SudokuMaker sm = new SudokuMaker();
		sm.createPuzzle(0, 0);
		sm.printPuzzle();
	}
}