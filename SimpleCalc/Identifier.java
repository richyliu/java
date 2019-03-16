public class Identifier {
	private String name;
	private double value;

	public Identifier(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public double getValue() { return value; }

	public void setValue(double newValue) { value = newValue; }

	public String getName() { return name; }
}