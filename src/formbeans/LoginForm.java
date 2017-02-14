package formbeans;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String username;
    private String password;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String s) {
		username = trimAndConvert(s,"<>\"");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String s) {
		password = s.trim();
	}
        
    public boolean hasErrors() {
    	
    	if (username == null || username.length() == 0) {
    		return true;
    	}
    	
    	if(password == null || password.length() == 0) {
    		return true;
    	}
    	
    	return false;
    }
    
    
    
    
}
