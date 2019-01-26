import java.awt.Color;

/**
 *	StdDraw example
 *
 *	@author	Mr Greenstein
 *	@since	January 24, 2019
 */
public class SudokuGame {

	private SudokuBoard board;
	
	public SudokuGame() {
		board = new SudokuBoard();
		// set the size of the window
		StdDraw.setCanvasSize(600, 500);
		StdDraw.setXscale(0, 1.2);		// (0, 1) default
		StdDraw.setYscale(0, 1);		// (0, 1) default
		StdDraw.enableDoubleBuffering();
	}
	
	public static void main(String[] args) {
		SudokuGame game = new SudokuGame();
		game.run();
	}
	
	public void run() {
		while (true) {
			board.drawBoard();
			board.getMouse();
			StdDraw.show();
			StdDraw.pause(5);
		}
	}
}

/**
 *	A green board with gray background and red circle moving around.
 */
class SudokuBoard {
	
	private int[][] board;
	private final int PUZZLE_SIZE = 9;
	
	public SudokuBoard() {
		board = new int[PUZZLE_SIZE][PUZZLE_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++)
			for (int j = 0; j < PUZZLE_SIZE; j++)
				board[i][j] = 0;
	}
	
	/**	Draws the board in its current state to the GUI.
	 */
	public void drawBoard() {
		// draw gray background
		StdDraw.setPenColor(new Color(150, 150, 150));
		StdDraw.filledRectangle(0.65, 0.5, 0.65, 0.5);
		
		// draw green boarder and black grid
		StdDraw.setPenColor(new Color(20, 150, 50));
		StdDraw.filledRectangle(0.5, 0.5, 0.47, 0.47);
		StdDraw.setPenColor(new Color(0, 0, 0));
		StdDraw.filledRectangle(0.5, 0.5, 0.45, 0.45);
		
		// draw background squares
		double GRID_SPACING = 0.1;
		// spacing of 3x3 blocks
		double BLOCK_SPACING = 0;
		double GRID_OFFSET = 0.1;
		double GRID_SIZE = 0.047;
		StdDraw.setPenColor(new Color(100, 70, 160));
		for (int row = 0; row < PUZZLE_SIZE; row++) {
			for (int col = 0; col < PUZZLE_SIZE; col++) {
				StdDraw.filledRectangle(
					col*GRID_SPACING + GRID_OFFSET + col%3*BLOCK_SPACING,
					row*GRID_SPACING + GRID_OFFSET + row%3*BLOCK_SPACING,
					GRID_SIZE,
					GRID_SIZE
				);
			}
		}
		
	}
	
	/**	Change color if mouse is clicked 
	 *	alternates red/blue,
	 *	if mouse is right on top of circle, then purple
	 */
	public void getMouse() {
		if (StdDraw.isMousePressed()) {
			double xLoc = StdDraw.mouseX();
			double yLoc = StdDraw.mouseY();
		}
	}

}