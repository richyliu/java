public class PracticeTest1 extends ProTester {
    public static void main(String[] args) {
        new PracticeTest1();
    }
    
    
    public void run() {
		next("Problem 1");
		{
			int fall = 5;
			int leaves = 6;
			int cooler = 11;

			do {
				if (leaves % fall > 2) fall++;
				else if (leaves % fall < 2) {
					fall--;
					leaves++;
				}
				if (cooler / fall == 2) cooler--;
				else {
					cooler++;
					leaves += 2;
				}
				ptl(fall + " " + leaves + " " + cooler);
			} while(cooler/leaves >= 1);
		}
		
		next("Problem 2");
		{
			ptl( (int)(74.6+64.8/14.4)%14 );
			ptl( (double)55/4-(int)5.6*3 );
			ptl( (byte)(85 + 3%14-92/7%11) );
			ptl( 100+7/2+7*3%4-15 == 90 );
			ptl( 35.7 >= 7 + 49/7%12 * 4 );
			ptl( (double)58/3 != (double)(58/3) );
		}
		
		next("Problem 3");
		{
			int number = 5;

			do {
				for (int col = 1; col <= number; col++) {
					if (col == number) pt("*");
					else if (col == 1) pt("1");
					else if (number == 3) pt("*");
					else pt("?");
				}
				ptl();
				number--;
			} while(number > 0);
		}
		
		next("Problem 4 (reverseString)");
		{
			be(reverseString("Java"), "avaJ");
			be(reverseString("foo"), "oof");
			be(reverseString(""), "");
			be(reverseString("a"), "a");
		}
		
		next("Problem 6 (removeChars)");
		{
			be(removeChars("APCS", 2), "AC");
			be(removeChars("a", 4), "a");
			be(removeChars("ab", 2), "a");
		}
		
		next("Problem 7 (shifty)");
		{
			double[] d1 = new double[]{1.0, 2.0, 3.0};
			shifty(d1);

			pta(d1);
		}
		
		next("Problem 8 (isKeyword)");
		{
			be(isKeyword("*#ars"), false);
			be(isKeyword("*ars"), false);
			be(isKeyword("foobar"), true);
			be(isKeyword("f"), true);
			be(isKeyword(""), false);
			be(isKeyword("arb ar"), false);
		}
	}


    public String reverseString(String phrase) {
        String ret = "";
        for (int i = phrase.length()-1; i >= 0; i--)
            ret += phrase.charAt(i);

        return ret;
    }
    
    
    public String removeChars(String phrase, int index) {
        String ret = "";
        for (int i = 0; i < phrase.length(); i++) {
            if ((i + 1) % index != 0)
                ret += phrase.charAt(i);
        }

        return ret;
    }
    

    public void shifty(double[] number) {
        double end = number[number.length-1];

        for (int i = number.length-1; i > 0; i--)
            number[i] = number[i-1];

        number[0] = end;
    }

    public boolean isKeyword(String keyword) {
        for (int i = 0; i < keyword.length(); i++)
            if (!Character.isLetter(keyword.charAt(i))) return false;

        return keyword.length() > 0;
    }
}