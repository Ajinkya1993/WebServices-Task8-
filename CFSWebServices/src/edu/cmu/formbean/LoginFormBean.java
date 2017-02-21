package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LoginFormBean {
	private String username;
	private String password;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    public LoginFormBean(String username, String password) {
    	this.username = username;
    	this.password = password;
    }
    
    public LoginFormBean() {
    	
    }
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        	
        if (getUsername() == null || getUsername().trim().length() == 0) {
            errors.add("Username is required");
            return errors;
        }
        if (getPassword() == null || getPassword().trim().length() == 0) {
            errors.add("Password is required");
            return errors;
        }
        String errorUN = checkStringFormat(getUsername());
        if (errorUN != "") {
        	errors.add("User Name " + errorUN);
        	return errors;
        }
        String errorPwd = checkStringFormat(getPassword());
        if (errorPwd != "") {
        	errors.add(errorPwd);
        	return errors;
        }

        return errors;
    }
    
    public String checkStringFormat (String str) {
    	Pattern format = Pattern.compile("[^<>;\":]*");
        Boolean rightFormat = format.matcher(username).matches();
        if (!rightFormat) {
            return "should not contain angle brackets, colon or quotes";
        } else {
            return "";
        }
    }
}
