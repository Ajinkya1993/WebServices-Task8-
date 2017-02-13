package edu.cmu.service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.cmu.JSON.LoginJSON;
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.resource.LoginAction;

@Path("/login")
public class Login {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginJSON login(@Context HttpServletRequest request, @BeanParam LoginFormBean loginFormBean) {
		LoginAction loginAction = new LoginAction(loginFormBean);
		return loginAction.perform(request);
	}
}
