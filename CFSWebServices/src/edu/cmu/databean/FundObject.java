package edu.cmu.databean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FundObject {
	String name;
	String shares;
	String price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}
