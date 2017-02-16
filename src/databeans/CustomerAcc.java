package databeans;

import java.util.ArrayList;
import java.util.List;

public class CustomerAcc {
	private String name = null;
	private String symbol = null;
	private double shares;
	private double price;
	
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
	
	public double getShares() {
	    return shares;
	}
	
	public void setShares(double shares) {
	    this.shares = shares;
	}
	
	public double getPrice() {
	    return price;
	}
	
	public void setPrice(double price) {
	    this.price = price;
	}
	
	
}
