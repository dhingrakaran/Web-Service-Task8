package formbeans;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean{
	
	private String symbol;
	private String cashValue;

	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = trimAndConvert(symbol, "<>\"");
	}

	public String getCashValue() {
		return cashValue;
	}
	public void setCashValue(String cashValue) {
		this.cashValue = trimAndConvert(cashValue, "<>\"");
	}
	
	public boolean hasErrors() {

		if (symbol == null || symbol.length() == 0) {
			return true;
		}
		
		if (cashValue == null || cashValue.length() == 0) {
			return true;
		}
		
        try {
            Double.parseDouble(cashValue);
        } catch (NumberFormatException e) {
            return true;
        }
        
        if(Double.parseDouble(cashValue) != Math.round(Double.parseDouble(cashValue) * 100) / 100) {
			return true;
		}

		return false;		
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (symbol == null || symbol.length() == 0) {
			errors.add("symbol");
		}
		
		if (cashValue == null || cashValue.length() == 0) {
			errors.add("cashValue");
		}
		
        try {
            Double.parseDouble(cashValue);
        } catch (NumberFormatException e) {
        	errors.add("double cashValue");
        }
        
        if(Double.parseDouble(cashValue) != Math.round(Double.parseDouble(cashValue) * 100) / 100) {
        	errors.add("cashValue Wrong Format");
		}
        return errors;

	}
	
}
