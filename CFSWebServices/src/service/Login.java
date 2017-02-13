package service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import JSON.LoginJSON;

@Path("/login")
public class Login {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginJSON login(@FormParam("username") String username, @FormParam("password") String password) {
		List<String> list = new ArrayList<String>();
		list.add(username);
		list.add(password);
		LoginJSON loginJSON = new LoginJSON(username, list);
		return loginJSON;
	}
}
