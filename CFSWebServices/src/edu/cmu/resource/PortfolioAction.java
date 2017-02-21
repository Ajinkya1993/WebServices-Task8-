package edu.cmu.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.json.JSONException;
import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.FundObject;
import edu.cmu.databean.Portfolio;
import edu.cmu.databean.PositionBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;
import edu.cmu.model.PositionDAO;

public class PortfolioAction {
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private CustomerDAO customerDAO;

	public PortfolioAction() {
		fundDAO = Model.getFundDAO();
		positionDAO = Model.getPositionDAO();
		customerDAO = Model.getCustomerDAO();
	}

	public Portfolio getPortfolio(HttpServletRequest request) throws RollbackException, JSONException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You are not currently logged in");
			return portfolio;
		}
		
		String checkUser = (String) request.getSession(false).getAttribute("userType");
		if (!checkUser.equals("customer")) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You must be a customer to perform this action");
			return portfolio;
		}

		CustomerBean customer = (CustomerBean) session.getAttribute("user");	

		PositionBean[] positions = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
		if (positions.length == 0) {
			Portfolio portfolio = new Portfolio();
			portfolio.setMessage("You don't have any funds in your Portfolio");
			return portfolio;
		}
		Portfolio portfolio = new Portfolio();
		double cash = customerDAO.getCustomerByUserName(customer.getUsername()).getCash();
//		System.out.println(cash);
//		DecimalFormat decimalFormat = new DecimalFormat("##.00");
		portfolio.setCash(String.format("%.2f", cash));
		portfolio.setMessage("The action was successful");
		FundObject[] funds = portfolio.getCustomerDetails(positions, fundDAO);
		// System.out.println(jsonArray.toString());
		portfolio.setFunds(funds);
		return portfolio;
	}
}
