

public class PegTester {
	public static void main(String[] args) {
		PegSolitaire ps = new PegSolitaire();
		ps.run();
		
		is(ps.isValidJumperPeg(new Location(3, 1)), true);
		is(ps.getJumperPeg(), new Location(3, 1));
	}
	
	public static <T> void is(T a, T b) {
		if (a.equals(b))
			System.out.println("SUCCESS: " + a);
		else
			System.out.println("FAILED: " + a + " not equal to " + b);
	}
}