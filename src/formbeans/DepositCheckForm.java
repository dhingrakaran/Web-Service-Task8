package formbeans;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean{
	private String username;
    private String cash;
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = trimAndConvert(username, "<>\"");
    }
    
    public String getCash() {
        return cash;
    }
    
    public void setCash(String cash) {
        this.cash = trimAndConvert(cash, "<>\"");
    }
    
    public boolean hasErrors() {
        
    	if (username == null || username.length() == 0) {
            return true;
        }
    	
        if (cash == null || cash.length() == 0) {
            return true;
        }
        
        try {
        	Double.parseDouble(cash);
        } catch (NumberFormatException e) {
        	return true;
        }
        
        if(Double.parseDouble(cash) <= 0) {
        	return true;
        }
        
        if(Double.parseDouble(cash) != Math.round(Double.parseDouble(cash) * 100) / 100) {
			return true;
		}
        
        return false;
    }
}
