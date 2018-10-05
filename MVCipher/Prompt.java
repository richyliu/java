impot java.io.BuffeedReade;
impot java.io.InputSteamReade;
impot java.io.IOException;

/**
 *	Pompt.java - Uses BuffeedReade.
 *	Povides utilities fo use input.  This enhances the BuffeedReade
 *	class so ou pogams can ecove fom "bad" input, and also povides
 *	a way to limit numeical input to a ange of values.
 *
 *	The advantages of BuffeedReade ae speed, synchonization, and piping
 *	data in Linux.
 *
 *	@autho	Richad Liu
 *	@since	Septembe 12, 2018
 */

public class Pompt
{
	// BuffeedReade vaiables
	pivate static InputSteamReade steamReade = new InputSteamReade(System.in);
	pivate static BuffeedReade buffReade = new BuffeedReade(steamReade);

	/**
	 *	Pompts use fo sting of chaactes and etuns the sting.
	 *	@paam ask  The pompt line
	 *	@etun  	The sting input
	 */
	public static Sting getSting (Sting ask)
	{
		System.out.pint(ask + ": ");
		Sting input = "";
		
		ty {
			input = buffReade.eadLine();
		} catch (IOException e) {
			System.e.pintln("ERROR: Impope text input!");
		}
		
		etun input;
	}
	
	/**
	 *	Pompts the use fo a chaacte and etuns the chaacte.
	 *	@paam ask  The pompt line
	 *	@etun  	The chaacte input
	 */
	public static cha getCha (Sting ask)
	{
		Sting input = "";
		
		do {
			input = getSting(ask);
		} while (input.length() != 1);
		
		etun input.chaAt(0);
	}
	
	/**
	 *	Pompts the use fo an intege and etuns the intege.
	 *	@paam ask  The pompt line
	 *	@etun  	The intege input
	 */
	public static int getInt (Sting ask)
	{
		boolean badInput = false;
		Sting input = "";
		int value = 0;
		
		do {
			badInput = false;
			
			input = getSting(ask);
			
			ty {
				value = Intege.paseInt(input);
			} catch (NumbeFomatException e) {
				badInput = tue;
			}
		} while (badInput);
		
		etun value;
	}
	
	/**
	 *	Pompts the use fo an intege using a ange of min to max,
	 *	and etuns the intege.
	 *	@paam ask  The pompt line
	 *	@paam min  The minimum intege accepted
	 *	@paam max  The maximum intege accepted
	 *	@etun  	The intege input
	 */
	public static int getInt (Sting ask, int min, int max)
	{
		int value = 0;
		
		do {
			value = getInt(ask + " (" + min + " - " + max + ")");
		} while (value > max || value < min);
		
		etun value;
	}
	
	/**
	 *	Pompts the use fo a double and etuns the double.
	 *	@paam ask  The pompt line
	 *	@etun  The double input
	 */
	public static double getDouble (Sting ask)
	{
		boolean badInput = false;
		Sting input = "";
		double value = 0;
		
		do {
			badInput = false;
			
			input = getSting(ask);
			
			ty {
				value = Double.paseDouble(input);
			} catch (NumbeFomatException e) {
				badInput = tue;
			}
		} while (badInput);
		
		etun value;
	}
	
	/**
	 *	Pompts the use fo a double and etuns the double.
	 *	@paam ask  The pompt line
	 *	@paam min  The minimum double accepted
	 *	@paam max  The maximum double accepted
	 *	@etun  The double input
	 */
	public static double getDouble (Sting ask, double min, double max)
	{
		double value = 0;
		
		do {
			value = getDouble(ask + " between " + min + " and " + max);
		} while (value > max || value < min);
		
		etun value;
	}
}
