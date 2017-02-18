package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundFormBean extends FormBean {
	private String symbol;
	private String numShares;
	
	public String getsymbol() {
		return symbol;
	}
	public void setsymbol(String fund) {
		this.symbol = fund.trim();
	}
	public String getNumShares() {
		return numShares;
	}
	
    public SellFundFormBean(String symbol, String numShares) {
    	this.symbol = symbol;
    	this.numShares = numShares;
    }
	
	public double getSharesDouble() {
		return Double.parseDouble(numShares);
	}
	public void setNumShares(String numShares) {
		this.numShares = numShares.trim();
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if(symbol == null || symbol.length() == 0) {
			errors.add("The input you provided is not valid");
		}
		
		if(numShares == null || numShares.length() == 0) {
			errors.add("The input you provided is not valid");
		}
		if(errors.size() > 0) {
			return errors;
		}
		
		try {
			Double.parseDouble(numShares);
			if (getSharesDouble() < 0) {
				errors.add("The input you provided is not valid");
			}
		}
		catch(NumberFormatException e) {
			errors.add("The input you provided is not valid");
		}
		
		return errors;
	}
		
}