import java.util.Scanner;
import java.io.PrintWriter;

/**
 *	Snake Game - <Description goes here>
 *	
 *	@author	Richard Liu
 *	@since	November 15, 2018
 */
public class SnakeGame {
	
	private Snake snake;		// the snake in the game
	private SnakeBoard board;	// the game board
	private Coordinate target;	// the target for the snake
	private int score;			// the score of the game
	
	private final String FILE_NAME = "snakeGameSave.txt";

	/*	Constructor	*/
	public SnakeGame() {
		//board = new SnakeBoard(20, 30);
		//snake = new Snake(3, 3);
		board = new SnakeBoard(8, 2);
		snake = new Snake(0, 0);
		target = new Coordinate(1, 1);
		// move target to a random location
		replaceTarget();
	}
	
	/*	Main method	*/
	public static void main(String[] args) {
		SnakeGame game = new SnakeGame();
		game.run();
	}
	
	/**	Print the game introduction	*/
	public void printIntroduction() {
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**	Print help menu	*/
	public void helpMenu() {
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("Press enter to continue");
	}
	
	/**
	 * Main run method, executes the main loop which runs the game
	 */
	public void run() {
		// whether the user wants to exit or not (or they died)
		boolean exit = false;
		
		// print the introduction ASCII art
		printIntroduction();
		
		while (!exit) {
			// one move returns a boolean for exiting
			exit = oneMove();
		}
		
		System.out.println("\nGame is over");
		System.out.println("Score = " + score);
		System.out.println("\nThanks for playing SnakeGame!!");
	}
	
	/**
	 * Asks the user to move, load, or save and make that move or action.
	 * @return Whether the user wants to quit or not
	 */
	private boolean oneMove() {
		// the user's selected action. set to space for default
		char move = ' ';
		// input string from prompt
		String inStr = "";
		// new head position
		Coordinate moveCoord = new Coordinate(0, 0);
		// the current location of the head of the snake
		Coordinate currentHead = snake.get(0);
		
		// print the board with the current snake and target
		board.printBoard(snake, target);
		
		// while user had not made a valid action
		while (move == ' ') {
			// prompt the user for action
			inStr = Prompt.getString("Score: " + score + " (w - North, s - South, d - East, a - West, h - Help)");
			// only valid action if inStr is 1 long
			if (inStr.length() == 1) {
				move = inStr.charAt(0);
				
				switch (move) {
					// move head if direction key
					case 'w':
						moveCoord = board.addCoords(currentHead, -1, 0);
						break;
					case 'a':
						moveCoord = board.addCoords(currentHead, 0, -1);
						break;
					case 's':
						moveCoord = board.addCoords(currentHead, 1, 0);
						break;
					case 'd':
						moveCoord = board.addCoords(currentHead, 0, 1);
						break;
					// show help menu
					case 'h':
						helpMenu();
						// dont quit, but don't do the rest of the method
						return false;
					case 'q':
						// prompt the user for quitting and return based on choice
						return promptQuit();
					case 'r':
						// load file and don't do the rest of the method
						loadFile();
						return false;
					case 'f':
						// load file and don't do the rest of the method
						saveFile();
						return false;
					default:
						// invalid move, set back to space
						move = ' ';
				}
			}
		}
		
		
		
		// check if move ran out of bounds
		if (
			moveCoord.getRow() < 0 || moveCoord.getRow() >= board.getHeight() ||
			moveCoord.getCol() < 0 || moveCoord.getCol() >= board.getWidth()
		) {
			return true;
		}
		
		// check if snake ran into itself
		for (Coordinate snakePart : snake) {
			if (snakePart.equals(moveCoord))
				return true;
		}
		
		// check if head on target
		if (moveCoord.equals(target)) {
			score++;
			// eat target
			snake.add(0, moveCoord);
			// move the target
			replaceTarget();
		} else {
			// move snake only if not eaten target
			for (int i = snake.size()-1; i > 0; i--) {
				snake.set(i, snake.get(i-1));
			}
			snake.set(0, moveCoord);
		}
		
		// check if no where else for the snake to move
		if (board.surrounded(snake.get(0), snake))
			return true;
		
		// or if only 5 squares left
		if (board.getWidth() * board.getHeight() - snake.size() <= 5)
			return true;
		
		return false;
	}
	
	
	/**
	 * Move the target somewhere else randomly not on the snake
	 */
	private void replaceTarget() {
		target = new Coordinate(
			(int)(Math.random() * board.getHeight()),
			(int)(Math.random() * board.getWidth())
		);
		
		// check that target is not on snake piece
		for (Coordinate snakePart : snake) {
			if (snakePart.equals(target)) {
				replaceTarget();
				return;
			}
		}
	}
	
	
	/**
	 * Load save file and store into field variables
	 */
	private void loadFile() {
		// use fileutils to open file
		Scanner in = FileUtils.openToRead(FILE_NAME);
		// tokens of the next line, init to empty string
		String[] nextLineTokens = {""};
		
		// get the scores line split by space
		nextLineTokens = in.nextLine().split(" ");
		// score is the integer of the 2nd token on that line
		score = Integer.parseInt(nextLineTokens[1]);
		
		// get the target line split by space
		nextLineTokens = in.nextLine().split(" ");
		// row and col of target is the 2nd and 3rd token
		target = new Coordinate(
			Integer.parseInt(nextLineTokens[1]) - 1,
			Integer.parseInt(nextLineTokens[2]) - 1
		);
		
		// get the snake size in the next line
		nextLineTokens = in.nextLine().split(" ");
		// snaek size is the 2nd token
		int snakeSize = Integer.parseInt(nextLineTokens[1]);
		
		// create a new empty snake
		Snake newSnake = new Snake();
		newSnake.clear();
		
		// add to new snake based on size
		for (int i = 0; i < snakeSize; i++) {
			// read the next line, with the row and col of snake body position
			nextLineTokens = in.nextLine().split(" ");
			newSnake.add( new Coordinate(
				Integer.parseInt(nextLineTokens[0]) - 1,
				Integer.parseInt(nextLineTokens[1]) - 1
			) );
		}
		
		// set snake to the new snake
		snake = newSnake;
	}
	
	
	/**
	 * Save the current fields to a save file
	 */
	private void saveFile() {
		// if the user has made the choice to save
		boolean madeChoiceToSave = false;
		
		// while choice has not been made
		while(!madeChoiceToSave) {
			// prompt the user if save
			String input = Prompt.getString("\nSave game? (y or n)");
			
			// if "y", save
			if (input.equals("y"))
				madeChoiceToSave = true;
			// if no, return and exit this method
			else if (input.equals("n"))
				return;
		}
		
		// open writer via fileutils
		PrintWriter out = FileUtils.openToWrite(FILE_NAME);
		// save score, target, and snake size
		out.println("Score " + score);
		out.println("Target " + (target.getRow()+1) + " " + (target.getCol()+1));
		out.println("Snake " + snake.size());
		// save snake body
		for (Coordinate snakePart : snake)
			out.println((snakePart.getRow()+1) + " " + (snakePart.getCol()+1));
		
		// close file to save it
		out.close();
	}
	
	/**
	 * Prompt the user if they want to quit
	 * @return	True if the user wants to quit. False otherwise
	 */
	private boolean promptQuit() {
		// user input string
		String input = "";
		
		// keep on asking the user. return statements break the loop
		while(true) {
			// get input string
			input = Prompt.getString("\nDo you really want to quit? (y or n)");
			
			// if user entered "y", quit the game
			if (input.equals("y")) {
				return true;
			}
			// if user entered "n", exit the method but don't quit
			if (input.equals("n")) {
				return false;
			}
		}
	}
}