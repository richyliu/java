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

	/*	Constructor	*/
	public SnakeGame() {
		board = new SnakeBoard(10, 15);
		snake = new Snake(3, 3);
		target = new Coordinate(1, 7);
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
	
	public void run() {
		while (true) {
			oneMove();
		}
	}
	
	public void oneMove() {
		char move = ' ';
		String inStr = "";
		// new head position
		Coordinate moveCoord = new Coordinate(0, 0);
		Coordinate currentHead = snake.get(0);
		
		board.printBoard(snake, target);
		System.out.println("Score: " + score);
		
		while (move == ' ') {
			inStr = Prompt.getString("make a move");
			if (inStr.length() == 1) {
				move = inStr.charAt(0);
				if ("wasd".indexOf(move) < 0)
					move = ' ';
			}
		}
		
		switch (move) {
			case 'w':
				moveCoord = addCoords(currentHead, -1, 0);
				break;
			case 'a':
				moveCoord = addCoords(currentHead, 0, -1);
				break;
			case 's':
				moveCoord = addCoords(currentHead, 1, 0);
				break;
			case 'd':
				moveCoord = addCoords(currentHead, 0, 1);
				break;
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
	}
	
	
	/**
	 * Move the target to somewhere else randomly not on the snake
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
	
	private Coordinate addCoords(Coordinate one, int addRow, int addCol) {
		return new Coordinate(one.getRow() + addRow, one.getCol() + addCol);
	}
}