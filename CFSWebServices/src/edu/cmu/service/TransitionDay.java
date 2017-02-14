package edu.cmu.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.model.Model;
import edu.cmu.resource.Controller;
import edu.cmu.resource.TransitionDayAction;

@Path("/transitionDay")
public class TransitionDay {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString) throws ServletException, JSONException {
		try {
			Controller controller = new Controller();
			controller.init();
			Model model = controller.getModel();
			return new TransitionDayAction(model).perform(request);
		} catch (Exception e){
			MessageJSON transitionJSON = new MessageJSON("The input you provided is not valid");
			return transitionJSON;
		} 
	} 
}
