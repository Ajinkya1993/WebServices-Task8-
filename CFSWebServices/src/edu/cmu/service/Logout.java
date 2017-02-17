package edu.cmu.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.model.Model;
import edu.cmu.resource.LoginAction;
import edu.cmu.resource.LogoutAction;

@Path("/logout")
public class Logout {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request) {
		try {
			return new LogoutAction().perform(request);
		} catch (Exception e) {
			return new MessageJSON("You are not currently logged in");
		}
	} 
}