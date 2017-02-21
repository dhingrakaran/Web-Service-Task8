package formbeans;

import org.mybeans.form.FormBean;

public class CreateCustomerAccountForm extends FormBean{
    private String fname;
    private String lname;
    private String username;
    private String password;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String cash;
    
    public String getFname() {
		return fname;
	}
    
	public void setFname(String fname) {
		this.fname = trimAndConvert(fname, "<>\"");
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = trimAndConvert(lname, "<>\"");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = trimAndConvert(username, "<>\"");
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = trimAndConvert(address, "<>\"");
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = trimAndConvert(city, "<>\"");
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = trimAndConvert(state, "<>\"");
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = trimAndConvert(zip, "<>\"");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = trimAndConvert(email, "<>\"");
	}

	public String getCash() {
		return cash;
	}
	
	public double getCashAsDouble() {
	    try {
	        Double cashAsDouble = Double.parseDouble(cash);
	        return Math.round(cashAsDouble * 100.0) / 100.0;
	    } catch (NumberFormatException e) {
	        return -1;
	    }

	}

	public void setCash(String cash) {
		this.cash = trimAndConvert(cash, "<>\"");
	}

	public boolean hasErrors() {

        if (fname == null || fname.length() == 0) {
            return true;
        }

        if (lname == null || lname.length() == 0) {
            return true;
        }

        if (username == null || username.length() == 0) {
        	return true;
        }

        if (password == null || password.length() == 0) {
        	return true;
        }

        if (address == null || address.length() == 0) {
        	return true;
        }
        
        if (city == null || city.length() == 0) {
        	return true;
        }
        
        if (state == null || state.length() == 0) {
        	return true;
        }
        
        if (zip == null || zip.length() == 0) {
        	return true;
        }
        
        if (email == null || email.length() == 0) {
        	return true;
        }
        
        if (cash == null || cash.length() == 0) {
        	cash = "0.00";
        }
        
        try {
        	Double.parseDouble(cash);
        } catch (NumberFormatException e) {
        	return true;
        }
        
        if(Double.parseDouble(cash) < 0) {
			return true;
		}
        
        if(Double.parseDouble(cash) != Math.round(Double.parseDouble(cash) * 100) / 100) {
			return true;
		}
        
        return false;
    }
}

