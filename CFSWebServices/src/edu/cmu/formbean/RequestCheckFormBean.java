package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckFormBean extends FormBean {
	private String amtWithdrawn;
	private String amtConfirm;
	private String requestCheck;
	public String getAmtWithdrawn() {
		return amtWithdrawn;
	}
	public void setAmtWithdrawn(String amtWithdrawn) {
		this.amtWithdrawn = amtWithdrawn.trim();
	}
	public double getAmountDouble() {
		return Double.parseDouble(amtWithdrawn);
	}
	public String getAmtConfirm() {
		return amtConfirm;
	}
	public void setAmtConfirm(String amtConfirm) {
		this.amtConfirm = amtConfirm.trim();
	}
	public String getRequestCheck() {
		return requestCheck;
	}
	public void setRequestCheck(String requestCheck) {
		this.requestCheck = requestCheck;
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (amtWithdrawn == null || amtWithdrawn.length() == 0) {
			errors.add("The input you provided is not valid");
		}
		
		if (amtConfirm == null || amtConfirm.length() == 0) {
			errors.add("The input you provided is not valid");
		}
		if(requestCheck == null) {
			errors.add("The input you provided is not valid");
		}
		if (errors.size() > 0) {
			return errors;
		}
		
		if (!amtWithdrawn.equals(amtConfirm)) {
			errors.add("The input you provided is not valid");
		}
		try {
			Double.parseDouble(amtWithdrawn);
			if (getAmountDouble() < 1 || getAmountDouble() > 10000000) {
				errors.add("The input you provided is not valid");
			}
		}
		catch(NumberFormatException e) {
			errors.add("The input you provided is not valid");
		}

		return errors;
	}
	
}
