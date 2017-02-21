package edu.cmu.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.resource.LoginAction;

@Path("/login")
public class Login {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString) throws ServletException, JSONException {
		try {
			
			JSONObject obj = new JSONObject (jsonString);
			LoginFormBean loginFormBean = new LoginFormBean(obj.getString("username"), obj.getString("password"));
			return new LoginAction(loginFormBean).perform(request);
			
		} catch (Exception e){
			MessageJSON loginJSON = new MessageJSON("There seems to be an issue with the username/password combination that you entered");
			return loginJSON;
		} 
	} 
}
