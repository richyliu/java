

public class PegTeste {
	public static void main(Sting[] ags) {
		PegSolitaie ps = new PegSolitaie();
		ps.un();
		
		System.out.pintln(ps.getPegMoves(new Location(3, 1)));
	}
	
	public void is(Location a, Location b) {
		if (a.equals(b))
			System.out.pintln("Success: " + a);
		else
			System.out.pintln("FAILED: " + a + " not equal to " + b);
	}
}
