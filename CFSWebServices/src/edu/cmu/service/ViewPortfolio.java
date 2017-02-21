package edu.cmu.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.genericdao.RollbackException;
import org.json.JSONException;
import edu.cmu.databean.Portfolio;
import edu.cmu.resource.PortfolioAction;

@Path("/viewPortfolio")
public class ViewPortfolio {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Portfolio getPortfolioDetails(@Context HttpServletRequest request)
			throws ServletException, RollbackException, JSONException {
		try {
			PortfolioAction portfolio = new PortfolioAction();
			return portfolio.getPortfolio(request);
		} catch (RollbackException e) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You don't have any funds in your Portfolio");
			return portfolio;
		} catch(JSONException e) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You must be a customer to perform this action");
			return portfolio;
		} catch(Exception e) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You are not currently logged in");
			return portfolio;
		}
	}
}
