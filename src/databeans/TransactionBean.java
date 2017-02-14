package databeans;

import java.sql.Date;

import org.genericdao.PrimaryKey;

/*
 * @author priyanka
 */
@PrimaryKey ("transaction_id")
public class TransactionBean {
	
	private int transaction_id;
	private int customer_id;
	private int fund_id;
	private Date execute_date = null;
	private double shares = 0.0;
	private String transaction_type = null;
	private double amount = 0.0;

	public int getTransaction_id() {
		return transaction_id;
	}
	
	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}
	
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	
	public int getFund_id() {
		return fund_id;
	}
	
	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}
	
	public Date getExecute_date() {
		return execute_date;
	}
	
	public void setExecute_date(Date execute_date) {
		this.execute_date = execute_date;
	}
	
	public double getShares() {
		return shares;
	}
	
	public void setShares(double shares) {
		this.shares = shares;
	}
	
	public String getTransaction_type() {
		return transaction_type;
	}
	
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
}