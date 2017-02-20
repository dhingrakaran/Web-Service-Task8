package databeans;

import java.sql.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey ("symbol")
public class Fund {
	private String symbol = null;
	private String name = null;
	private double initial_value;
	private Date create_fund_date;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public Date getCreate_fund_date() {
		return create_fund_date;
	}

	public void setCreate_fund_date(Date create_fund_date) {
		this.create_fund_date = create_fund_date;
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
