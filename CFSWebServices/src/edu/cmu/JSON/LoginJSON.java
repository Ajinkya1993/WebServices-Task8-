package edu.cmu.JSON;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class LoginJSON {
	private String message;
	public LoginJSON() {
		
	}
	public LoginJSON(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
