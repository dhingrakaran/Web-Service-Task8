package databeans;

import org.genericdao.PrimaryKey;

@PrimaryKey("username,symbol")
public class Position {
    private String username;
    private String symbol;
    private int shares;
    
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
    
    public int getShares() {
        return shares;
    }
    
    public void setShares(int shares) {
        this.shares = shares;
    }
}
