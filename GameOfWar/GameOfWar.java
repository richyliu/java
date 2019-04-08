/**
 *	The Game of War
 *	A simple card game where 2 players (one player and the computer) each put
 * 	down one card at a time and the higher of the cards determine the winner.
 * 	If the cards are equals, the players go to "war" and each draw 3 more cards.
 * 	The fourth cards determine the winner of all 10 cards. If the cards are the
 * 	same again, war is repeated until there is a winner or someone runs out of
 * 	cards, at which point they lose.
 *
 *	Requires ListNode and Prompt classes
 *	
 *	@author	Richard Liu
 *	@since	April 4th, 2019
 */
public class GameOfWar
{
	/*	Fields  */
	private DeckOfCards player;
	private DeckOfCards computer;

	/**	Constructor */
	public GameOfWar() {
		player = new DeckOfCards();
		computer = new DeckOfCards();
	}

	public static void main(String[] args) {
		GameOfWar game = new GameOfWar();
		game.run();
	}

	/**	Prints the introduction to the game */
	public void printIntroduction() {
		System.out.println("   ______                        ____  ____   _       __");
		System.out.println("  / ____/___ _____ ___  ___     / __ \\/ __/  | |     / /___ ______");
		System.out.println(" / / __/ __ `/ __ `__ \\/ _ \\   / / / / /_    | | /| / / __ `/ ___/");
		System.out.println("/ /_/ / /_/ / / / / / /  __/  / /_/ / __/    | |/ |/ / /_/ / /");
		System.out.println("\\____/\\__,_/_/ /_/ /_/\\___/   \\____/_/       |__/|__/\\__,_/_/");
		System.out.println("\nWelcome to the Game of War");
	}

	/**
	 * Main run method of the game
	 */
	public void run() {
		printIntroduction();
		System.out.println();

		// let the user choose between interactive and simulation
		String input = "";
		while(!input.equals("i") && !input.equals("s"))
			input = Prompt.getString("Interactive or Simulation (i or s)");

		// run the respective method
		if (input.equals("i"))
			interactive();
		else
			simulation();
	}

	/**
	 * Deal a deck of shuffled 52 cards to player/computer
	 */
	public void dealCards() {
		DeckOfCards source = new DeckOfCards();
		source.fill();
		source.shuffle();
		player.clear();
		computer.clear();
		for (int i = 0; i < 26; i++) {
			player.add(source.draw());
			computer.add(source.draw());
		}
	}

	/**
	 * Run interactive game, method ends when user quits
	 */
	public void interactive() {
		dealCards();
		System.out.printf("\nDeck sizes: player = %d, computer = %d\n\n", player.size(), computer.size());

		boolean quit = false;

		// continue running while not quitting
		while(!quit) {
			// prompt the user to quit, shuffle, or battle
			String input = Prompt.getString("Press 'ENTER' to fight another battle or 's' to shuffle your deck!");
			if (input.equals("q"))
				quit = true;
			else if (input.equals("s"))
				player.shuffle();
			else
				battle(true);

			// quit if someone lost
			if (player.size() == 0 || computer.size() == 0)
				quit = true;

			// print stats at the end
			if (player.size() == 0)
				System.out.println("Deck sizes: player = 0, computer = 52\n");
			else if (computer.size() == 0)
				System.out.println("Deck sizes: player = 52, computer = 0\n");
			else
				System.out.printf("Deck sizes: player = %d, computer = %d\n\n", player.size(), computer.size());
		}

		// print computer/player won
		System.out.println();
		if (player.size() == 0)
			System.out.println("Sorry - COMPUTER won!!");
		else if (computer.size() == 0)
			System.out.println("Congratulations - PLAYER won!!");
		else
			System.out.println("Thank you for playing");
		System.out.println();
	}

	/**
	 * Run a simulation of the game
	 */
	public void simulation() {
		// number of games to simluate
		int games = Prompt.getInt("Number of games", 20, 50);
		System.out.println();

		// total combined turns across all games
		int totalTurns = 0;
		// number of times the player won
		int playerWon = 0;

		// run the games
		for (int i = 0; i < games; i++) {
			// deal new cards
			dealCards();

			// keep on playing until someone loses
			int turns;
			for (turns = 0; player.size() > 0 && computer.size() > 0; turns++) {
				// shuffle every 10 plays
				if (turns % 10 == 0) {
					player.shuffle();
					computer.shuffle();
				}
				battle(false);
			}

			// print stats
			System.out.printf("Game %d: %d turns  ", i + 1, turns);

			if (player.size() > 0) {
				System.out.println("Player won");
				playerWon++;
			} else
				System.out.println("Computer won");

			// add to total turns
			totalTurns += turns;
		}

		// print average turns and player won stats
		System.out.printf("\nAverage number of turns = %.2f\n", (double)totalTurns / games);
		System.out.printf("\nPlayer won %d times.\n\n", playerWon);
	}

	/**
	 * One round or "battle"
	 * @param print Whether or not to print output
	 */
	public void battle(boolean print) {
		// draw two cards
		Card playerDraw = player.draw();
		Card computerDraw = computer.draw();
		// pot contains the cards that player/computer could win
		DeckOfCards pot = new DeckOfCards();
		pot.add(playerDraw);
		pot.add(computerDraw);

		if (print) {
			System.out.println("You drew " + playerDraw);
			System.out.println("The computer drew " + computerDraw);
		}

		// check if they're equals
		int compare = playerDraw.compareTo(computerDraw);

		// keep on doing war while they're equal
		while (compare == 0) {
			if (print)
				System.out.println("Its a tie! Battle again!");
			// draw 3 cards
			for (int i = 0; i < 3; i++) {
				if (player.isEmpty() || computer.isEmpty()) return;
				pot.add(player.draw());
				pot.add(computer.draw());
			}
			// at any stage if either deck is empty, exit the battle
			if (player.isEmpty() || computer.isEmpty()) return;
			// 4th cards draw determine winner
			playerDraw = player.draw();
			computerDraw = computer.draw();
			// add to pot
			pot.add(playerDraw);
			pot.add(computerDraw);
			if (print) {
				System.out.println("You drew " + playerDraw);
				System.out.println("The computer drew " + computerDraw);
			}

			// new compare between 4th cards
			compare = playerDraw.compareTo(computerDraw);
		}

		// check who won
		int potSize = pot.size();
		if (compare > 0) {
			if (print)
				System.out.print("You");
			// add the pot to the player's cards
			while(!pot.isEmpty())
				player.add(pot.draw());
		} else {
			if (print)
				System.out.print("The computer");
			while(!pot.isEmpty())
				computer.add(pot.draw());
		}

		if (print)
			System.out.println(" won " + potSize + " cards!");
	}
}
