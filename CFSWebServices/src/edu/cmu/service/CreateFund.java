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
import org.json.JSONObject;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.CreateFundFormBean;
import edu.cmu.model.Model;
import edu.cmu.resource.CreateFundAction;

@Path("/createFund")
public class CreateFund {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString, @Context Model model) throws ServletException, JSONException, RollbackException {

		try {
			JSONObject obj = new JSONObject (jsonString);
			CreateFundFormBean createFundFormBean = new CreateFundFormBean(obj.getString("name"), obj.getString("symbol"), obj.getString("initial_value"));
			return new CreateFundAction(createFundFormBean).perform(request);
			
		} catch (Exception e){
			MessageJSON loginJSON = new MessageJSON("The input you provided is not valid");
			return loginJSON;
		} 
		 finally {
			 //do nothing
		 }
	}
}