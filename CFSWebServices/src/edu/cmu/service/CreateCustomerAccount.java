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
import edu.cmu.model.Model;
import edu.cmu.resource.CreateCustomerAccountAction;

@Path("/createCustomerAccount")
public class CreateCustomerAccount {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON createNewCustomerAccount(@Context HttpServletRequest request, String jsonString, @Context Model model)
			throws JSONException, ServletException, RollbackException {
		try {
			CreateCustomerAccountAction createAccountObject = new CreateCustomerAccountAction();
			return createAccountObject.createAccount(jsonString, request);
		} catch (Exception e) {
			return new MessageJSON("The input you provided is not valid"); 
		}
	}
}
