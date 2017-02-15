package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckFormBean extends FormBean {
	private String cashValue;
	
    public RequestCheckFormBean(String cashValue) {
    	this.cashValue = cashValue;
    }
	
	public String getCashValue() {
		return cashValue;
	}

	public void setCashValue(String cashValue) {
		this.cashValue = cashValue;
	}

	public double getCashDouble() {
		return Double.parseDouble(cashValue);
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
/*
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
		}
		catch(NumberFormatException e) {
			errors.add("The input you provided is not valid");
		}
*/
		return errors;
	}
	
}
