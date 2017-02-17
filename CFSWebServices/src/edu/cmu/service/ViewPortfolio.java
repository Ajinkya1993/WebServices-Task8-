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
import edu.cmu.model.Model;
import edu.cmu.resource.PortfolioAction;

@Path("/viewPortfolio")
public class ViewPortfolio {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Portfolio getPortfolioDetails(@Context HttpServletRequest request, @Context Model model) throws ServletException, RollbackException, JSONException {
		PortfolioAction portfolio = new PortfolioAction();
		return portfolio.getPortfolio(request);
	}
}
