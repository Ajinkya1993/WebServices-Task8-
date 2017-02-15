package edu.cmu.resource;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.json.JSONArray;
import org.json.JSONException;

import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.Portfolio;
import edu.cmu.databean.PositionBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;
import edu.cmu.model.PositionDAO;

public class PortfolioAction {
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	
	public PortfolioAction(Model model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
	}
	
	public Portfolio getPortfolio(HttpServletRequest request) throws RollbackException, JSONException {
//		CustomerBean customer = (CustomerBean) request.getSession().getAttribute("user");
//		if(customer == null) {
//			Portfolio portfolio = new Portfolio();
//			portfolio.setMessage("You are not currently logged in");
//			return portfolio;
//		}
		
//		String checkUser = (String) request.getSession(false).getAttribute("userType");
//		if (!checkUser.equals("Customer")) {
//			Portfolio portfolio = new Portfolio();
//			portfolio.setMessage("You must be a customer to perform this action”");
//			return portfolio;
//		}
		
		CustomerBean customer = customerDAO.getCustomerByUserName("gg"); // for testing
		PositionBean[] positions = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
		Portfolio portfolio = new Portfolio();
		portfolio.setCash(String.valueOf(customer.getCash()));
		portfolio.setMessage("The action was successful");
		JSONArray jsonArray = portfolio.getCustomerDetails(positions, fundDAO);
		portfolio.setFunds(jsonArray);
		return portfolio;
	}
}
