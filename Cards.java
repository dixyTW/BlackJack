
public class Cards {
	String symbol;
	String name;
	int value;
	
	public Cards (int value, String symbol, String name) {
		this.value = value;
		this.symbol = symbol;
		this.name = name;
	}
	
	public String toString() { 
		StringBuilder sb = new StringBuilder(10);
		sb.append(name);
		sb.append(symbol);
		return sb.toString();
	}

	public boolean is_equal(Cards other) {
		//compares the values, not the symbol
		if (this == other) return true;
		
		boolean isEqual = true;
		if (!other.name.equals(this.name)) {
			isEqual = false;
		}
		if (!(other.value == this.value)) {
			isEqual = false;
		}
		return isEqual;
	}
	
}
