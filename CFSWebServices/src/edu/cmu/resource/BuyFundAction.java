package edu.cmu.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.FundBean;
import edu.cmu.databean.PositionBean;
import edu.cmu.formbean.BuyFundFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;
import edu.cmu.model.PositionDAO;
import edu.cmu.model.TransactionDAO;

public class BuyFundAction {
	private BuyFundFormBean buyFundFormBean;
	private Model model;

	public BuyFundAction(BuyFundFormBean formbean, Model model) {
		buyFundFormBean = formbean;
		this.model = model;
	}

	public MessageJSON perform(HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON buyFundMessage = new MessageJSON();
		CustomerDAO customerDAO = model.getCustomerDAO();
		PositionDAO positionDAO = model.getPositionDAO();
		FundDAO fundDAO = model.getFundDAO();

		// Validation errors check
		errors = buyFundFormBean.getValidationErrors();
		if (errors.size() > 0) {
			buyFundMessage = new MessageJSON("The input you provided is not valid");
			return buyFundMessage;
		}

		// Not logged in
		if (session.getAttribute("user") == null) {
			buyFundMessage = new MessageJSON("You are not currently logged in");
		}

		// Not customer
		if ((session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
			buyFundMessage = new MessageJSON("You must be a customer to perform this action");
		}
		try {
			FundBean fundBean = fundDAO.read(buyFundFormBean.getSymbol());
			if (fundBean == null) {
				buyFundMessage = new MessageJSON("The fund is not founded");
			} else {

				CustomerBean user = (CustomerBean) session.getAttribute("user");

				double price = fundBean.getPrice();
				double currentBalance = customerDAO.getCustomerByUserName(user.getUsername()).getCash();

				double buyAmount = buyFundFormBean.getCashDouble();
				int shares = (int) (Math.min(currentBalance, buyAmount) / price);

				if (shares < 1) {
					buyFundMessage = new MessageJSON("You didn't provide enough cash to make this purchase");
					return buyFundMessage;
				}

				if (currentBalance - buyAmount <= 0) {
					buyFundMessage = new MessageJSON(
							"You don't have enough cash in your account to make this purchase");
					return buyFundMessage;

				}
				// updated
				double currentShares =0.0;
				int fundid = fundDAO.read(buyFundFormBean.getSymbol()).getFundId();
				double priceAmount = fundDAO.read(buyFundFormBean.getSymbol()).getPrice();
				if (positionDAO.getPosition(user.getCustomerId(), fundid) != null) {
				currentShares = positionDAO.getPosition(user.getCustomerId(), fundid).getShares();
				}

				double cashBalance = customerDAO.getCustomerByUserName(user.getUsername()).getCash();
				double newBalance = cashBalance - priceAmount * shares;
				user.setCash(newBalance);
				customerDAO.update(user);
				PositionBean positionBean = positionDAO.getPosition(user.getCustomerId(), fundid);
				if (positionBean != null) {
					positionBean.setShares(positionBean.getShares() + shares);
					positionBean.setTotalValue(positionBean.getTotalValue() + shares * priceAmount);
					positionDAO.update(positionBean);
				} else {
				
				PositionBean newPositBean = new PositionBean();
				newPositBean.setCustomerId(user.getCustomerId());
				newPositBean.setShares(shares);
				newPositBean.setFundId(fundid);
				newPositBean.setTotalValue(shares * priceAmount);
				positionDAO.create(newPositBean);
				}

			}

		} catch (Exception e) {
			buyFundMessage = new MessageJSON("The input you provided is not valid");
			return buyFundMessage;
		}
		buyFundMessage = new MessageJSON("The fund has been successfully purchased");
		return buyFundMessage;

		
		
	}
}
