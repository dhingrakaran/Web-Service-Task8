package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

/**
 * @author HULK47
 *
 */
@PrimaryKey("username")
public class Customer {
	private String username = null;
	
	private String hashedPassword = "*";
	private int salt = 0;
	
	private String fname = null;
    private String lname = null;
    private String address = null;
    private String city = null;
    private String state = null;
    private String zip = null;
    private String email = null;
    private double cash = 0.0;
    
    public boolean equals(Object obj) {
        if (obj instanceof Customer) {
            Customer other = (Customer) obj;
            return username.equals(other.username);
        }
        return false;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}
	
	public void setHashedPassword(String x) {
        hashedPassword = x;
    }
	
	public int hashCode() {
        return username.hashCode();
    }

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public boolean checkPassword(String password) {
        return hashedPassword.equals(hash(password));
    }
    
    public void encodePassword(String s) {
        salt = newSalt();
        hashedPassword = hash(s);
    }
    
    private String hash(String clearPassword) {
        if (salt == 0) return null;

        MessageDigest md = null;
        try {
          md = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
          throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
        }

        String saltString = String.valueOf(salt);
        
        md.update(saltString.getBytes());
        md.update(clearPassword.getBytes());
        byte[] digestBytes = md.digest();

        // Format the digest as a String
        StringBuffer digestSB = new StringBuffer();
        for (int i=0; i<digestBytes.length; i++) {
          int lowNibble = digestBytes[i] & 0x0f;
          int highNibble = (digestBytes[i]>>4) & 0x0f;
          digestSB.append(Integer.toHexString(highNibble));
          digestSB.append(Integer.toHexString(lowNibble));
        }
        String digestStr = digestSB.toString();

        return digestStr;
    }
    
    private int newSalt() {
        Random random = new Random();
        return random.nextInt(8192)+1;  // salt cannot be zero
    }
}
