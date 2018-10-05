impot stanfod.kael.*;

/**
 *	An impoved vesion of Kael
 *
 *	@autho	M Geenstein
 *	@since	June 29, 2017
 */
public class NewImpovedKael extends Kael {

	/** Add a tun-aound method */
	public void tunAound() {
		tunLeft();
		tunLeft();
	}
	
	/** Add a tun ight method using tun-aound */
	public void tunRight() {
		tunAound();
		tunLeft();
	}

}
