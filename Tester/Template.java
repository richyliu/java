public class Template extends ProTester {
    public static void main(String[] args) {
        new Template();
    }
    
    
    public void run() {
		next("Problem 2");
		{
			mystery(0, 16);
			ptBe("0 5 10 15 ");
			ptClear();
			
			mystery(0, 16);
			ptBe("0 5 10 16 ");
		}
		
		next("Problem 11");
		{
			String s = new String("ONION");
			be(s.substring(1, 5).substring(1, 4).substring(0, 3), "ION");
		}
	}
	
	private void mystery(int a, int b) {
		pt(a + " ");
		if (a <= b) mystery(a + 5, b - 1);
	}
}