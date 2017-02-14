package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("username,fund_id")
public class Position {
    private String username;
    private int fund_id;
    private double shares;
    


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
