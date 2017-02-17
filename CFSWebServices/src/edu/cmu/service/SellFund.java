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
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.formbean.SellFundFormBean;
import edu.cmu.model.Model;
import edu.cmu.resource.LoginAction;
import edu.cmu.resource.SellFundAction;

@Path("/sellFund")
public class SellFund {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request, String jsonString, @Context Model model) throws ServletException, JSONException {
		try {
			
			JSONObject obj = new JSONObject (jsonString);
			SellFundFormBean sellFundFormBean = new SellFundFormBean(obj.getString("symbol"), obj.getString("numShares"));
			return new SellFundAction(sellFundFormBean).perform(request);
			
		} catch (Exception e){
			MessageJSON message = new MessageJSON("The input you provided is not valid");
			return message;
		} 
	} 
}
