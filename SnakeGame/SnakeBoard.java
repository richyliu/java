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
			// print left side bar
			System.out.print("| ");
			
			// print the row of the board
			for (int c = 0; c < board[r].length; c++) {
				System.out.print(board[r][c] + " ");
			}
			
			// print right side bar
			System.out.println("|");
		}
		
		// print bottom bar
		System.out.print("+ ");
		for (int i = 0; i < board[0].length; i++)
			System.out.print("- ");
		System.out.println("+");
	}
	
	/* Helper methods go here	*/
	
	/**
	 * Checks if a certain location is surrounded on all sides by a snake body
	 * @param head	Location of the snake head
	 * @param snake	Snake body
	 * @return True if the head is surrounded by the snake body or side of board
	 */
	public boolean surrounded(Coordinate head, Snake snake) {
		// all 4 sides
		int[][] sides = {
			{0, -1},
			{1, 0},
			{0, 1},
			{-1, 0}
		};
		// num sides surrounded
		int numSurrounded = 0;
		
		// loop through 4 sides
		for (int i = 0; i < sides.length; i++) {
			Coordinate loc = addCoords(head, sides[i][0], sides[i][1]);
			
			if (
				loc.getRow() < 0 || loc.getRow() >= getHeight() ||
				loc.getCol() < 0 || loc.getCol() >= getWidth() ||
				snake.contains(loc)
			)
				numSurrounded++;
		}
		
		return numSurrounded >= 4;
	}
	
	
	/**
	 * Add row and column to a coordinate.
	 * @param coord		Coordinate to add
	 * @param addRow	Row to be added to coord
	 * @param addCol	Column to be added to coord
	 * @return 			New added coord
	 */
	public Coordinate addCoords(Coordinate coord, int addRow, int addCol) {
		return new Coordinate(coord.getRow() + addRow, coord.getCol() + addCol);
	}
	
	
	/**
	 * Add two coordinates together
	 * @param coord		First coordinate to add
	 * @param coord2	Second coordinate to add
	 * @return 			New added coord
	 */
	public Coordinate addCoords(Coordinate coord, Coordinate coord2) {
		return new Coordinate(
			coord.getRow() + coord2.getRow(),
			coord.getCol() + coord2.getCol()
		);
	}
	
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