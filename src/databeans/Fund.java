package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey ("symbol")
public class Fund {
	private String symbol = null;
	private String name = null;
	private double initial_value; 
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public double getInitial_value() {
		return initial_value;
	}

	public void setInitial_value(double initial_value) {
		this.initial_value = initial_value;
	}
}
