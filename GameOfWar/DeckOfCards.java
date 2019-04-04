import java.util.ArrayList;
import java.util.List;

/**
 *	Deck of cards
 *
 *	@author	
 *	@since	
 */
public class DeckOfCards
{
	private SinglyLinkedList<Card> deck;	// the deck of cards

	/** constructor */
	public DeckOfCards() {
		deck = new SinglyLinkedList<Card>();
	}

	/**	Add the Card to the bottom of the deck
	 *	@param c		the card to add
	 */
	public void add(Card c) {
		deck.add(0, c);
	}

	/**	Removes the card from the top of the deck
	 *	@return		the card removed from the top of the deck; null if deck is empty
	 */
	public Card draw() {
		return deck.remove(deck.size() - 1);
	}

	/**	Clear the deck of cards */
	public void clear()	{
		deck.clear();
	}

	/**	Clear deck and fills with standard 52 card deck */
	public void fill() {
		for (Suit suit : Suit.values())
			for (Rank rank : Rank.values())
				deck.add(new Card(rank, suit));
	}

	/**	@return		true if deck is empty; false otherwise */
	public boolean isEmpty() {
		return deck.isEmpty();
	}

	/**	Randomizes the order of the cards in the deck
	 *	This method must be SinglyLinkedList-based (do not use arrays or ArrayList)
	 */
	public void shuffle() {
		int deckSize = deck.size();
		for (int i = 0; i < deckSize; i++) {
			Card removed = deck.remove((int)(Math.random() * deckSize));
			deck.add(0, removed);
		}
	}

	/**	@return		the size of the deck */
	public int size() {
		return deck.size();
	}

	/**	print the deck */
	public void print()
	{
		for (int a = 0; a < deck.size(); a++)
			System.out.print(deck.get(a).getValue() + "; ");
		System.out.println();
	}

	/***************************************************************************/
	/************************** Testing methods ********************************/
	/***************************************************************************/
	public static void main(String[] args)
	{
		DeckOfCards doc = new DeckOfCards();
		doc.fill();
		System.out.println("Deck size = " + doc.size());
		doc.print();
		System.out.println();
		doc.shuffle();
		System.out.println("Deck size = " + doc.size());
		doc.print();
		System.out.println();
	}
}
