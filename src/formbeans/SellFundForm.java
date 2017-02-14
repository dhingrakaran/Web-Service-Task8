package formbeans;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean{
	
	private String symbol;
	private String numShares;
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = trimAndConvert(symbol, "<>\"");
	}

	public String getNumShares() {
		return numShares;
	}

	public void setNumShares(String numShares) {
		this.numShares = trimAndConvert(numShares, "<>\"");
	}
	
	public boolean hasErrors() {
		
		if (symbol == null || symbol.length() == 0) {
			return true;
		}
		
		if (numShares == null || numShares.length() == 0) {
			return true;
		}
		
		try {
            Double.parseDouble(numShares);
        } catch (NumberFormatException e) {
            return true;
        }
		
		return false;
	}
}
