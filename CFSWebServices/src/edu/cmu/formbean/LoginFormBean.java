package edu.cmu.formbean;

import javax.ws.rs.FormParam;

public class LoginFormBean {
	private @FormParam("username") String username;
	private @FormParam("password") String password;
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
    public LoginFormBean() {
    	
    }
    
}
