

public class PegTester {
	public static void main(String[] args) {
		PegSolitaire ps = new PegSolitaire();
		ps.run();
		
		System.out.println(ps.getPegMoves(new Location(3, 1)));
	}
	
	public void is(Location a, Location b) {
		if (a.equals(b))
			System.out.println("Success: " + a);
		else
			System.out.println("FAILED: " + a + " not equal to " + b);
	}
}