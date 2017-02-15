package edu.cmu.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.genericdao.RollbackException;
import org.json.JSONException;
import edu.cmu.JSON.MessageJSON;
import edu.cmu.resource.Controller;
import edu.cmu.resource.CreateCustomerAccountAction;

@Path("/createCustomerAccount")
public class CreateCustomerAccount {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON createNewCustomerAccount(@Context HttpServletRequest request, String jsonString) throws JSONException, ServletException, RollbackException {
		Controller controller = new Controller();
		controller.init();
		CreateCustomerAccountAction createAccountObject = new CreateCustomerAccountAction(controller.getModel());
		return createAccountObject.createAccount(jsonString, request);
	}
}
