/**
 *	The Game of War
 *	(Description)
 *
 *	Requires ListNode and Prompt classes
 *	
 *	@author	
 *	@since	
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

	public void run() {
		DeckOfCards source = new DeckOfCards();
		source.fill();
		source.shuffle();
		player.clear();
		computer.clear();
		for (int i = 0; i < 26; i++) {
			player.add(source.draw());
			computer.add(source.draw());
		}

		printIntroduction();
		System.out.printf("\nDeck sizes: player = %d, computer = %d\n\n", player.size(), computer.size());

		boolean quit = false;

		while(!quit) {
			String input = Prompt.getString("Press 'ENTER' to fight another battle or 's' to shuffle your deck!");
			if (input.equals("q"))
				quit = true;
			else if (input.equals("s"))
				player.shuffle();
			else
				battle();

			if (player.size() == 0 || computer.size() == 0)
				quit = true;

			if (player.size() == 0)
				System.out.println("Deck sizes: player = 0, computer = 52\n");
			else if (computer.size() == 0)
				System.out.println("Deck sizes: player = 52, computer = 0\n");
			else
				System.out.printf("Deck sizes: player = %d, computer = %d\n\n", player.size(), computer.size());
		}

		System.out.println();
		if (player.size() == 0)
			System.out.println("Sorry - COMPUTER won!!");
		else if (computer.size() == 0)
			System.out.println("Congratulations - PLAYER won!!");
		else
			System.out.println("Thank you for playing");
		System.out.println();
	}

	public void battle() {
		Card playerDraw = player.draw();
		Card computerDraw = computer.draw();
		DeckOfCards pot = new DeckOfCards();
		pot.add(playerDraw);
		pot.add(computerDraw);

		System.out.println("You drew " + playerDraw);
		System.out.println("The computer drew " + computerDraw);

		int compare = playerDraw.compareTo(computerDraw);

		int count = 0;
		while (compare == 0) {
			count++;
			if (count == 2) {
				System.out.println("\033[0;31m===========================================================================");
				System.out.println("===========================================================================");
				System.out.println("===========================================================================");
				System.out.println("===========================================================================");
				System.out.println("===========================================================================");
				System.out.println("===========================================================================");
			}
			System.out.println("Its a tie! Battle again!");
			for (int i = 0; i < 3; i++) {
				if (player.isEmpty() || computer.isEmpty()) return;
				pot.add(player.draw());
				pot.add(computer.draw());
			}
			if (player.isEmpty() || computer.isEmpty()) return;
			playerDraw = player.draw();
			computerDraw = computer.draw();
			pot.add(playerDraw);
			pot.add(computerDraw);
			System.out.println("You drew " + playerDraw);
			System.out.println("The computer drew " + computerDraw);

			compare = playerDraw.compareTo(computerDraw);
		}

		int potSize = pot.size();
		if (compare > 0) {
			System.out.print("You");
			while(!pot.isEmpty())
				player.add(pot.draw());
		} else {
			System.out.print("The computer");
			while(!pot.isEmpty())
				computer.add(pot.draw());
		}

		System.out.println(" won " + potSize + " cards!");
	}
}
