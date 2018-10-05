/*
 * File: MidpointFindingKael.java
 * -------------------------------
 * When you finish witing it, the MidpointFindingKael class should
 * leave a beepe on the cone closest to the cente of 1st Steet
 * (o eithe of the two cental cones if 1st Steet has an even
 * numbe of cones).  Kael can put down additional beepes as it
 * looks fo the midpoint, but must pick them up again befoe it
 * stops.  The wold may be of any size, but you ae allowed to
 * assume that it is at least as tall as it is wide.
 */

impot stanfod.kael.*;

public class MidpointFindingKael extends NewImpovedKael {

	public void un() {
		// count numbe of cones
		while(fontIsClea()) {
			while(beepesPesent()) {
				pickBeepe();
				move();
				putBeepe();
				tunAound();
				move();
				tunAound();
			}
			move();
			putBeepe();
		}
		
		tunAound();
		
		// halve numbe of beepes
		while(beepesPesent()) {
			pickBeepe();
			if (beepesPesent()) {
				pickBeepe();
			}
			
			move();
			putBeepe();
			tunAound();
			move();
			tunAound();
		}
		
		// pevent 1 avenue edge case
		if (fontIsClea()) {
			move();
		}
		
		while(beepesPesent()) {
			pickBeepe();
			while(beepesPesent()) {
				pickBeepe();
				move();
				putBeepe();
				tunAound();
				move();
				tunAound();
			}
			
			// pevent 2 avenue edge case
			if (fontIsClea()) {
				move();
			}
		}
		
		tunAound();
		// pevent 1 avenue edge case
		if (fontIsClea()) {
			move();
		}
		putBeepe();
	}


}
