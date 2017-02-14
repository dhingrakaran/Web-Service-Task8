package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey ("fund_id")
public class Fund {
	private int fund_id;
	private String name = null;
	private String symbol = null;
	
	public int getFund_id() {
		return fund_id;
	}
	
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	
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
}
