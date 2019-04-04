/**
 *	Card class
 *
 *	@author	Mr Greenstein
 */
public class Card implements Comparable<Card>
{
	private Rank myRank;
	private Suit mySuit;
	
	public Card(Rank r, Suit s)
	{
		myRank = r;
		mySuit = s;
	}
	
	/**	Compares this card rank to other card rank
	 *	@param other	the other card to compare
	 *	@return			0 if this Rank is same as other card, negative if this Rank is less,
	 *					positive if this Rank is greater
	 */
	public int compareTo(Card other)
	{
		return myRank.compareTo(other.myRank);
	}
	
	/**	Determines if the cards are of equal Rank and Suit
	 *	@param other	the other card to compare
	 *	@return			true if the card rank and suit are equal; false otherwise
	 */
	@Override
	public boolean equals(Object other)
	{
		return myRank.compareTo(((Card)other).myRank) == 0
					&& mySuit.compareTo(((Card)other).mySuit) == 0;
	}
	
	/**	Accessor methods */
	public Rank getRank() { return myRank; }
	public Suit getSuit() { return mySuit; }
	
	/**	toString method returns "TWO of DIAMONDS" */
	public String toString()
	{
		return myRank + " of " + mySuit;
	}
}