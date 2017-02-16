package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("username,symbol")
public class Position {
    private String username;
    private String symbol;
    private double shares;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
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
}
