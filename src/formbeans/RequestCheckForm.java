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

	public boolean hasErrors() {

		if (cashValue == null || cashValue.length() == 0) {
			return true;
		}

		try {
			Double.parseDouble(cashValue);
		} catch (NumberFormatException e) {
			return true;
		}

		return false;
	}

}
