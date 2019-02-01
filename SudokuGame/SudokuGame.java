import java.awt.Color;
import java.awt.Font;

/**
 *	Sudoku Game using drag and drop interface. Requires FileUtils.java and
 *	StdDraw.java. The game is played by dragging numbers from the bar on the
 *	right onto the main board. Each row, column, and 3x3 grid must have unique
 *	digits 0-9.
 *
 *	@author	Richard Liu
 *	@since	January 28, 2019
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
 *	Sudoku game board
 */
class SudokuBoard {
	
	private int[][] board;
    // if the grid was original to the loaded puzzle (i.e. cannot be changed)
    private boolean[][] original;
	private final int PUZZLE_SIZE = 9;
	private Pair dragPos;
	private int dragNum;
	private boolean done;
	
	// center of board squares
	private Pair[][] boardPos;
	private Pair[] sidebarPos;
	private final double GRID_SIZE = 0.045;

    // countdown to 200 to display invalid flashing
    private int invalidCtr;
    // row/col of conflicting number
    private int invalidRow;
    private int invalidCol;

    // when button was last clicked
    private long lastClicked;
	
	public SudokuBoard() {
		done = false;
		dragPos = new Pair();
		dragNum = -1;

        invalidCtr = -1;
        invalidRow = -1;
        invalidCol = -1;

        lastClicked = 0;
		
		board = new int[PUZZLE_SIZE][PUZZLE_SIZE];
		for (int i = 0; i < PUZZLE_SIZE; i++)
			for (int j = 0; j < PUZZLE_SIZE; j++)
				board[i][j] = 0;

        original = new boolean[PUZZLE_SIZE][PUZZLE_SIZE];
		
		// create background square positions
		boardPos = new Pair[PUZZLE_SIZE][PUZZLE_SIZE];
		double GRID_SPACING = 0.097;
		// spacing of 3x3 blocks
		double BLOCK_SPACING = 0.01;
		double GRID_OFFSET = 0.102;
		StdDraw.setPenColor(new Color(100, 70, 160));
		for (int row = 0; row < PUZZLE_SIZE; row++) {
			for (int col = 0; col < PUZZLE_SIZE; col++) {
				boardPos[row][col] = new Pair(
					col*GRID_SPACING + GRID_OFFSET + col/3*BLOCK_SPACING,
					row*GRID_SPACING + GRID_OFFSET + row/3*BLOCK_SPACING
				);
			}
		}
		
		// create sidebar square positions
		sidebarPos = new Pair[PUZZLE_SIZE];
		double SIDEBAR_OFFSET_X = 1.1;
		double SIDEBAR_OFFSET_Y = 0.9;
		for (int i = 0; i < PUZZLE_SIZE; i++) {
			sidebarPos[i] = new Pair(
				SIDEBAR_OFFSET_X,
				SIDEBAR_OFFSET_Y - i*GRID_SIZE - i*0.05
			);
		}
		
        makePuzzle();
        
        // UNCOMMENT THIS LINE TO LOAD PUZZLE FROM "puzzle.txt"
		loadPuzzle("puzzle.txt");
	}
	
	/**	Draws the board in its current state to the GUI.
	 */
	public void drawBoard() {
        Color white = new Color(255, 255, 255);
        Color black = new Color(0, 0, 0);

        StdDraw.setFont(new Font("Arial", Font.PLAIN, 26));

		// draw gray background
		StdDraw.setPenColor(new Color(150, 150, 150));
		StdDraw.filledRectangle(0.65, 0.5, 0.65, 0.5);
		
		// draw green boarder and black grid
		StdDraw.setPenColor(new Color(20, 150, 50));
		StdDraw.filledRectangle(0.5, 0.5, 0.47, 0.47);
		StdDraw.setPenColor(black);
		StdDraw.filledRectangle(0.5, 0.5, 0.45, 0.45);
		
		// draw background squares
		StdDraw.setPenColor(new Color(100, 70, 160));
		for (int row = 0; row < PUZZLE_SIZE; row++) {
			for (int col = 0; col < PUZZLE_SIZE; col++) {
				StdDraw.filledRectangle(
					boardPos[row][col].x(),
					boardPos[row][col].y(),
					GRID_SIZE,
					GRID_SIZE
				);
			}
		}
		
		// draw sidebar background
		StdDraw.setPenColor(new Color(100, 70, 160));
		double SIDEBAR_OFFSET_X = 1.1;
		double SIDEBAR_OFFSET_Y = 0.9;
		for (int i = 0; i < 9; i++) {
			StdDraw.filledRectangle(
				sidebarPos[i].x(),
				sidebarPos[i].y(),
				GRID_SIZE,
				GRID_SIZE
			);
		}
		
		// draw sudoku numbers
		for (int row = 0; row < PUZZLE_SIZE; row++)
			for (int col = 0; col < PUZZLE_SIZE; col++)
				if (board[row][col] > 0) {
		            StdDraw.setPenColor(original[row][col] ? black : white);
					StdDraw.text(
						boardPos[row][col].x(),
						boardPos[row][col].y(),
						board[row][col] + ""
					);
                }
		
		// draw sidebar numbers
		StdDraw.setPenColor(white);
		for (int i = 0; i < 9; i++) {
			StdDraw.text(
				sidebarPos[i].x(),
				sidebarPos[i].y(),
				(i + 1) + ""
			);
		}
		
		// draw number being dragged
		if (dragPos.x() != 1) {
			StdDraw.text(dragPos.x(), dragPos.y(), dragNum + "");
		}
		
		// draw done screen if done
		if (done) {
			StdDraw.setPenColor(new Color(255, 111, 125));
			StdDraw.filledRectangle(0.5, 0.5, 0.2, 0.1);
			StdDraw.setPenColor(white);
			StdDraw.text(0.5, 0.5, "Game finished!");
		}

        // draw invalid position flashing
        if (invalidCtr != -1 && invalidRow != -1 && invalidCol != -1) {
          StdDraw.setPenColor(new Color(255, 0, 0));

          // flash between 0-25 and 75-100
          if (invalidCtr < 25 || invalidCtr > 75)
              StdDraw.rectangle(
                  boardPos[invalidRow][invalidCol].x(),
                  boardPos[invalidRow][invalidCol].y(),
                  GRID_SIZE,
                  GRID_SIZE
              );

          invalidCtr++;
          // reset invalid after 100 ticks (500ms)
          if (invalidCtr > 100)
              invalidCtr = -1;
        }

        // draw new puzzle "button"
        StdDraw.setPenColor(new Color(255, 0, 0));
        StdDraw.filledRectangle(1.09, 0.05, 0.07, 0.03);
        StdDraw.setPenColor(white);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 18));
        StdDraw.text(1.09, 0.05, "New");
	}
	
	public void getMouse() {
		if (StdDraw.isMousePressed()) {
			Pair mousePos = new Pair(StdDraw.mouseX(), StdDraw.mouseY());
			
			// if previously was not dragging, check if mouse within sidebar
			if (dragPos.x() == -1) {
				for (int i = 0; i < PUZZLE_SIZE; i++) {
					if (mousePos.distance(sidebarPos[i]) < GRID_SIZE) {
						dragPos = mousePos;
						dragNum = i + 1;
						return;
					}
				}
			} else {
				// otherwise just copy mousePos to be dragged
				dragPos = mousePos;
			}
            
            // check if clicked button
            if (
                mousePos.x() > 1.02 &&
                mousePos.x() < 1.16 &&
                mousePos.y() > 0.02 &&
                mousePos.y() < 0.08
                ) {
                // prevent too many makePuzzles from mouse hold
                if (System.currentTimeMillis() - lastClicked > 20)
                    makePuzzle();
                lastClicked = System.currentTimeMillis();
                done = false;
            }
		} else {
			// check if mouse ends on a square
			if (dragPos.x() != -1) {
				for (int row = 0; row < PUZZLE_SIZE; row++) {
					for (int col = 0; col < PUZZLE_SIZE; col++) {
						if (dragPos.distance(boardPos[row][col]) < GRID_SIZE) {
							// user dragged number to a square, check not original and validity
							if (!original[row][col] && isBoardValid(row, col, dragNum)) {
								board[row][col] = dragNum;
								// check if board is solved
								if (isPuzzleSolved()) {
									done = true;
								}
							}
							
							dragNum = 0;
							dragPos = new Pair();
							
							return;
						}
					}
				}
				// otherwise it didn't end up on square, reset
				dragNum = 0;
				dragPos = new Pair();
			}
		}
	}
	
	
	public boolean isBoardValid(int row, int column, int num) {
		// check if num is in the row
		for (int i = 0; i < PUZZLE_SIZE; i++)
			if (board[row][i] == num) {
                invalidCtr = 0;
                invalidRow = row;
                invalidCol = i;
				return false;
            }
		// check if num is in column
		for (int i = 0; i < PUZZLE_SIZE; i++)
			if (board[i][column] == num) {
                invalidCtr = 0;
                invalidRow = i;
                invalidCol = column;
				return false;
            }
		// check if num is in 3x3 grid
		for (int i = (int)(row/3)*3; i < (int)(row/3)*3 + 3; i++)
			for (int j = (int)(column/3)*3; j < (int)(column/3)*3 + 3; j++)
				if (board[i][j] == num) {
                    invalidCtr = 0;
                    invalidRow = i;
                    invalidCol = j;
					return false;
                }
		
		return true;
	}
	
	
	/**
	 * Checks if the puzzle is solved.
	 * @param row 		Current row to work on.
	 * @param column	Current column to work on
	 */
	public boolean isPuzzleSolved() {
		for (int i = 0; i < PUZZLE_SIZE; i++)
			for (int j = 0; j < PUZZLE_SIZE; j++)
				// still unsolved square, puzzle not solved
				if (board[i][j] == 0)
					return false;
		
		return true;
	}
	
	
	/**	Load the puzzle from a file
	 *	@param filename		name of puzzle file
	 */
	public void loadPuzzle(String filename) {
		java.util.Scanner infile = FileUtils.openToRead(filename);
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
                board[row][col] = infile.nextInt();
                original[row][col] = board[row][col] != 0;
            }
        }
		infile.close();
	}

    /**
     * Creates a new puzzle with about 40 squares already filled out
     */
    public void makePuzzle() {
        // reset puzzle
        for (int row = 0; row < PUZZLE_SIZE; row++)
            for (int col = 0; col < PUZZLE_SIZE; col++)
                board[row][col] = 0;

        createPuzzle(0, 0);
        for (int row = 0; row < PUZZLE_SIZE; row++) {
            for (int col = 0; col < PUZZLE_SIZE; col++) {
                if (Math.random() < 0.4) {
                  board[row][col] = 0;
                  original[row][col] = false;
                } else {
                  original[row][col] = true;
                }
            }
        }
    }


	/**
	 * Generate a random number within 1 .. n inclusive that is not already in
	 * the array
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
				if (board[row][i] == num) validNum = false;
			// check if num is in column
			for (int i = 0; i < PUZZLE_SIZE; i++)
				if (board[i][column] == num) validNum = false;
			// check if num is in 3x3 grid
			for (int i = (int)(row/3)*3; i < (int)(row/3)*3 + 3; i++)
				for (int j = (int)(column/3)*3; j < (int)(column/3)*3 + 3; j++)
					if (board[i][j] == num) validNum = false;
			
			// num is not in row, column, or 3x3 grid
			if (validNum) {
				board[row][column] = num;
				// check createPuzzle with next column or next row and start from 0 col
				if (column == PUZZLE_SIZE-1 ? createPuzzle(row + 1, 0) : createPuzzle(row, column + 1))
					// puzzle is completed
					return true;
				else
					// grid doesn't work here, reset to no solution
					board[row][column] = 0;
			}
		}
		
		// no number works in this location, so backtrack
		return false;
	}
}


class Pair {
	private double x;
	private double y;
	
	public Pair() {
		this.x = -1;
		this.y = -1;
	}
	
	public Pair(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double x() {
		return x;
	}
	
	public double y() {
		return y;
	}
	
	public double distance(Pair other) {
		return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
	}
}
