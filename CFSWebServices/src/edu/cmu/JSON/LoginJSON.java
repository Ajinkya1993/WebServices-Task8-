package edu.cmu.JSON;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class LoginJSON {
	private String message;
	private List<String> links;
	public LoginJSON() {
		
	}
	public LoginJSON(String message) {
		this.message = message;
		this.links = new ArrayList<>();
	}
	public LoginJSON(String message, List<String> links) {
		this.message = message;
		this.links = links;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}

}
