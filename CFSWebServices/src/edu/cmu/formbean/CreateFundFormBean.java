package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CreateFundFormBean {
	private String name;
	private String symbol;
	private String initial_value;
	public CreateFundFormBean(String name, String symbol, String iv) {
		this.name = name;
		this.symbol = symbol;
		this.initial_value = iv;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getInitial_value() {
		return initial_value;
	}
	public void setInitial_value(String initial_value) {
		this.initial_value = initial_value;
	}
	
	public double getInitial_valueDouble() {
		return Double.parseDouble(initial_value);
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (getName() == null || getName().trim().length() == 0) {
            errors.add("The input you provided is not valid");
            return errors;
        }

		if (getSymbol() == null || getSymbol().trim().length() == 0) {
			errors.add("The input you provided is not valid");
			return errors;
		}

		if (errors.size() > 0) {
			return errors;
		}

		try {
			Double.parseDouble(initial_value);
			if (getInitial_valueDouble() <= 0 || getInitial_valueDouble() > 1000000000) {
				errors.add("The input you provided is not valid");
			}
		}
		catch(NumberFormatException e) {
			errors.add("The input you provided is not valid");
		}

		return errors;
	}
	
	public String checkStringFormat (String str) {
    	Pattern format = Pattern.compile("[^<>;\":]*");
        Boolean rightFormat = format.matcher(str).matches();
        if (!rightFormat) {
            return "should not contain angle brackets, colon or quotes";
        } else {
            return "";
        }
    }

}
