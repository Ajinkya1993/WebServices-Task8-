package edu.cmu.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.RequestCheckFormBean;
import edu.cmu.model.Model;
import edu.cmu.resource.Controller;
import edu.cmu.resource.RequestCheckAction;


@Path("/requestCheck")
public class RequestCheck {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString) throws ServletException, JSONException {
		try {
			
			JSONObject obj = new JSONObject (jsonString);
			RequestCheckFormBean requestCheckFormBean = new RequestCheckFormBean(obj.getString("cashValue"));
			Controller controller = new Controller();
			controller.init();
			Model model = controller.getModel();
			System.out.println("!!!!!!!!!!!!!!");
			return new RequestCheckAction(requestCheckFormBean, model).perform(request);
			
		} catch (Exception e){
			System.out.println("XXXXXXXXXXX");
			MessageJSON message = new MessageJSON("The input you provided is not valid");
			return message;
		} 
	} 
}
