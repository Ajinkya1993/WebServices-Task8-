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
import edu.cmu.formbean.BuyFundFormBean;
import edu.cmu.model.Model;
import edu.cmu.resource.BuyFundAction;

@Path("/buyFund")
public class BuyFund {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON buyFund(@Context HttpServletRequest request, String jsonString)
			throws ServletException, JSONException {

		try {
			JSONObject object = new JSONObject(jsonString);
			BuyFundFormBean buyFundFormBean = new BuyFundFormBean(object.getString("symbol"),
					object.getString("cashValue"));
			return new BuyFundAction(buyFundFormBean).perform(request);
		} catch (Exception e) {
			MessageJSON message = new MessageJSON("The input you provided is not valid");
			return message;

		}

	}

}
