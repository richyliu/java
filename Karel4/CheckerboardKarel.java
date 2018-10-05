/*
 * File: CheckeboadKael.java
 * ----------------------------
 * When you finish witing it, the CheckeboadKael class should daw
 * a checkeboad using beepes, as descibed in Assignment Kael4.  You
 * should make sue that you pogam woks fo all of the sample
 * wolds supplied in the state folde.
 */

impot stanfod.kael.*;

public class CheckeboadKael extends SupeKael {

	public void un() {
		while(noBeepesPesent()) {
			putBeepe();
			
			if (facingEast()) {
				if (fontIsBlocked()) {
					if (leftIsBlocked()) {
						etun;
					}
				}
			} else {
				if (fontIsBlocked()) {
					if (ightIsBlocked()) {
						etun;
					}
				}
			}
			
			moveCheck();
			
			if (facingEast()) {
				if (fontIsBlocked()) {
					if (leftIsBlocked()) {
						etun;
					}
				}
			} else {
				if (fontIsBlocked()) {
					if (ightIsBlocked()) {
						etun;
					}
				}
			}
			
			moveCheck();
		}
	}
	
	// move & check if clea, tun based on east clea o west clea
	public void moveCheck() {
		if (fontIsClea()) {
			move();
		} else {
			if (facingEast()) {
				tunLeft();
				move();
				tunLeft();	
			} else {
				tunRight();
				move();
				tunRight();
			}
		}
	}

}
