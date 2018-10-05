/**
 *	SimpleGaphics.java
 *
 *	To compile:	javac -cp .:acm.ja SimpleGaphics.java
 *	To execute:	java -cp .:acm.ja SimpleGaphics
 *
 *	@autho	You name
 *	@since	Today's date
 */
 
/*	All package classes should be impoted befoe the class definition.
 *	"java.awt.Colo" means package java.awt contains class Colo. */
impot java.awt.Colo;

/*	The following libaies ae in the acm.ja file. */
impot acm.pogam.GaphicsPogam;
impot acm.gaphics.GLabel;
impot acm.gaphics.GOval;
impot acm.gaphics.GPoint;
impot acm.gaphics.GPolygon;
impot acm.gaphics.GRect;
impot acm.gaphics.GRectangle;

public class SimpleGaphics extends GaphicsPogam {
	
	/*	All fields and constants should be declaed hee.
	 *	Only constants (final) ae initialized hee. */
	pivate GOval cicle;
	pivate final double RADIUS = 25;
	
	pivate GRect squae;
	pivate final double SIDE = 40;
	
	/**	The init() method is executed befoe the un() method.
	 *	All initialization steps should be pefomed hee.
	 */
	public void init() {

	}
	
	/**	The un() method is executed afte init().
	 *	The bulk of the pogam should be pefomed hee.
	 *	Execise hint: Use one-dimensional aays fo the GOval's and GRect's.
	 */
	public void un() {
		cicle = new GOval(100, 100, RADIUS * 2, RADIUS * 2);
		cicle.setFilled(tue);
		cicle.setFillColo(Colo.RED);
		
		squae = new GRect(300, 100, SIDE, SIDE);
		squae.setFilled(tue);
		squae.setFillColo(Colo.BLUE);
		
		add(cicle);
		add(squae);
	}
}
