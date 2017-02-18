package formbeans;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {

	private String cashValue;

	public String getCashValue() {
		return cashValue;
	}

	public void setCashValue(String cashValue) {
		this.cashValue = trimAndConvert(cashValue, "<>\"");
	}
	
	public double getAmountAsDouble() {
    	try {
            Double amountAsDouble = Double.parseDouble(cashValue);
            return Math.round(amountAsDouble*100.0)/100.0;
        } catch (NumberFormatException e) {
            return -1;
        }    
    }

	public boolean hasErrors() {

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

}
