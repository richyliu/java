/**
 *	<Describe the SnakeBoard here>
 *
 *	@author	Richard Liu
 *	@since	November 15, 2018
 */
public class SnakeBoard {
	
	/*	fields	*/
	private char[][] board;			// The 2D array to hold the board
	private int width;
	private int height;
	
	/*	Constructor	*/
	public SnakeBoard(int height, int width) {
		this.width = width;
		this.height = height;
		board = new char[height][width];
		
		resetBoard();
	}
	
	/**
	 * Reset the board to all empty spaces
	 */
	private void resetBoard() {
		for (int r = 0; r < board.length; r++)
			for (int c = 0; c < board[r].length; c++)
				board[r][c] = ' ';
	}
	
	/**
	 *	Print the board to the screen.
	 */
	public void printBoard(Snake snake, Coordinate target) {
		resetBoard();
		
		// add target to board array
		board[target.getRow()][target.getCol()] = '+';
		// add the snake head and body to the board array
		for (int i = 0; i < snake.size(); i++) {
			int row = snake.get(i).getRow();
			int col = snake.get(i).getCol();
			
			// print '@' if its the snake head otherwise print the '*'
			board[row][col] = i == 0 ? '@' : '*';
		}
		
		// print top bar
		System.out.print("+ ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("- ");
		System.out.println("+");
		
		// print main grid
		for (int r = 0; r < board.length; r++) {
			System.out.print("| ");
			
			for (int c = 0; c < board[r].length; c++) {
				System.out.print(board[r][c] + " ");
			}
			
			System.out.println("|");
		}
		
		// print bottom bar
		System.out.print("+ ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("- ");
		System.out.println("+");
	}
	
	/* Helper methods go here	*/
	
	/*	Accessor methods	*/
	public int getWidth() { return width; }
	public int getHeight() { return height; }

	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args) {
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(3, 3);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}