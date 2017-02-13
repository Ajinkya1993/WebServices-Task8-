package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.JSON.LoginJSON;
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.model.CustomerDAO;


public class LoginAction {
	private LoginFormBean loginFormBean;

	public LoginAction(LoginFormBean bean) {
		loginFormBean = bean;
	}

	public String getName() {
		return "login";
	}
	public LoginJSON perform (HttpServletRequest request) {
		String message = "";
		String username = loginFormBean.getUsername();
		String password = loginFormBean.getPassword();
		List<String> links = new ArrayList<String>();
		if (loginFormBean.getUsername().equals("team10")) {
			message = "Welcome " + username;
			links.add("Other use cases");
			links.add("Logout: {BASEURI}/logout");
			links.add("View Portfolio: : {BASEURI}/viewPortfolio");
			links.add("Buy Fund: : {BASEURI}/buyFund");
			links.add("Sell Fund: {BASEURI}/sellFund");
			links.add("Request Check: {BASEURI}/requestCheck");
			return new LoginJSON(message, links);
		} else {
			message = "There seems to be an issue with the username/password combination that you entered";
			return new LoginJSON(message);
		}
	}
}
