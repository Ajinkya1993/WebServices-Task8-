package edu.cmu.service;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.genericdao.RollbackException;
import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.model.Model;
import edu.cmu.resource.Controller;
import edu.cmu.resource.LoginAction;

@Path("/login")
public class Login {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString) throws ServletException, JSONException, RollbackException {
		try {
			
			JSONObject obj = new JSONObject (jsonString);
			LoginFormBean loginFormBean = new LoginFormBean(obj.getString("username"), obj.getString("password"));
			Controller controller = new Controller();
			controller.init();
			Model model = controller.getModel();
			return new LoginAction(loginFormBean, model).perform(request);
			
		} /*catch (Exception e){
			MessageJSON loginJSON = new MessageJSON("There seems to be an issue with the username/password combination that you entered");
			return loginJSON;
		} */
			finally {
				//do  nothing
			}
	} 
}
