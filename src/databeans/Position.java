package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id,fund_id")
public class Position {
    private int customer_id;
    private int fund_id;
    private double shares;
    
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
    
    public double getShares() {
        return shares;
    }
    
    public void setShares(double shares) {
        this.shares = shares;
    }
}
