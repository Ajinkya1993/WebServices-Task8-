package edu.cmu.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.JSON.MessageJSON;


public class LogoutAction {
    public LogoutAction() {
    }

    public String getName() {
        return "logout";
    }

    public MessageJSON perform(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	if (session.getAttribute("user") == null) {
    		return new MessageJSON("You are not currently logged in");
    	}
        session.setAttribute("user", null);
        session.setAttribute("userType", null);
        session.invalidate();
        return new MessageJSON("You have been successfully logged out");
    }
}