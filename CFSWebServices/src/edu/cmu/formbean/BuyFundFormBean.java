package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuyFundFormBean {
	private String symbol;
	private String cashValue;
	
	public BuyFundFormBean(String symbol, String cashValue){
		this.symbol = symbol;
		this.cashValue = cashValue;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCashValue() {
		return cashValue;
	}

	public void setCashValue(String cashValue) {
		this.cashValue = cashValue;
	}
	
	public double getCashDouble(){
		return Double.parseDouble(cashValue);
	}
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (symbol == null || symbol.length() == 0) {
			errors.add("The input you provided is not valid.");
		}
		if (cashValue == null || cashValue.length() == 0) {
			errors.add("The input you provided is not valid");
			
		}
		if (errors.size() > 0) {
			return errors;
		}
		try {
			Double.parseDouble(cashValue);
			if (getCashDouble() < 1 || getCashDouble() > 1000000000) {
				errors.add("The input you provided is not valid");

			}
		} catch (NumberFormatException e) {
			errors.add("The input you provided is not valid");
		}
		
		return errors;
		
	}
	public String checkStringFormat (String str){
    	Pattern format = Pattern.compile("[^<>;\":]*");
        Boolean rightFormat = format.matcher(str).matches();
        if (!rightFormat) {
            return "should not contain angle brackets, colon or quotes";
        } else {
            return "";
        }
    }
	
	
}
