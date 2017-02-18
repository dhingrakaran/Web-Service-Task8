package formbeans;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean{
    private String name;
    private String symbol;
    private String initial_value;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = trimAndConvert(name, "<>\"");
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = trimAndConvert(symbol, "<>\"");
    }
    
    public String getInitial_value() {
		return initial_value;
	}

	public void setInitial_value(String initial_value) {
		this.initial_value = trimAndConvert(initial_value, "<>\"");
	}

	public boolean hasErrors() {
        
        if (name == null || name.length() == 0) {
            return true;
        }
        
        if (symbol == null || symbol.length() == 0) {
            return true;
        }
        
        if (initial_value == null || initial_value.length() == 0) {
            return true;
        }
        
        try {
        	Double.parseDouble(initial_value);
        } catch (NumberFormatException e) {
        	return true;
        }
        
        if(Double.parseDouble(initial_value) <= 0) {
        	return true;
        }
        
        return false;
    }
}
