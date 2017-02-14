package edu.cmu.resource;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.formbean.BuyFundFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;
import edu.cmu.model.TransactionDAO;

public class BuyFundAction {
	private BuyFundFormBean buyFundFormBean;
	private Model model;

	public BuyFundAction(BuyFundFormBean formbean, Model model) {
		buyFundFormBean = formbean;
		model = model;
	}

	@Override
	public MessageJSON perform(HttpServletRequest request) {
		// Set errors attributes
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		MessageJSON buyFundMessage = new MessageJSON();

		CustomerDAO customerDAO = model.getCustomerDAO();
		FundDAO fundDAO = model.getFundDAO();
		TransactionDAO transactionDAO = model.getTransactionDAO();

		// Checking if logged in user is a customer.
		// CustomerBean customer = (CustomerBean) session.getAttribute("user");
		CustomerBean customer = null;
		if (session.getAttribute("userType").equals("customer") &&
				
		if (session.getAttribute("user") == null) {
			buyFundMessage = new MessageJSON("You are not currently logged in");
			return buyFundMessage;
		} else if (!session.getAttribute("userType").equals("customer")){
			buyFundMessage = new MessageJSON("You must be an customer to perform this action");
			return buyFundMessage;
		}
		
		if (customer == null) {
			buyFundMessages.add(new MessageJSON("You must log in prior to making this request"));
			return buyFundMessages;
		}

		// Check if the logged in user is an employee.
		if (!(session.getAttribute("user") instanceof CustomerBean)) {
			buyFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
			return buyFundMessages;
		}

		customer = customerDAO.getCustomerByUserName(customer.getUsername());

		// request.setAttribute("customer", customer);

		// ****Input parameter is fundSymbol, need to revise BuyFormBean****
		// Form validation check
		errors.addAll(buyFundForm.getValidationErrors());
		if (errors.size() > 0) {
			for (String error : errors) {
				buyFundMessages.add(new MessageJSON(error));
			}
			return buyFundMessages;
		}

		String fundSymbol = buyFundForm.getSymbol();

		double amountToBuy = Double.parseDouble(buyFundForm.getDollarAmount());

		// Get current customer information

		// Retrieve current available cash balance
		double availableBalance = customer.getCash() / 100.0;

		// Compare the buy fund amount with balance
		// If input is invalid - exceed the current balance,
		// return errors and ask to input a valid number
		if (availableBalance < amountToBuy) {
			buyFundMessages.add(new MessageJSON("I'm sorry, you must first "
					+ "deposit sufficient funds in your account in order to make this purchase"));
			return buyFundMessages;
		}

		// Otherwise, update available cash balance and queue up a pending
		// transaction to DB

		FundBean fundBean = fundDAO.getFundBySymbol(fundSymbol);

		if (fundBean == null) {
			buyFundMessages.add(new MessageJSON("No such Fund Exist"));
			return buyFundMessages;
		}
		FundPriceHistoryDAO fundPriceHistoryDAO = new FundPriceHistoryDAO();

		String latestDate = fundPriceHistoryDAO.getMaxDate();
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		FundPriceHistoryBean fundPriceHistoryBean = fundPriceHistoryDAO
				.getFundPriceHistoryByIdAndDate(fundBean.getFundId(), latestDate);
		long latestFundPrice = fundPriceHistoryBean.getPrice();

		// updating customer balance
		amountToBuy = amountToBuy * 100;
		// multiplying with shares 1000 multiples
		long allocatedShares = (long) (amountToBuy / latestFundPrice);
		if (allocatedShares > 0) {
			availableBalance = availableBalance * 100 - allocatedShares * latestFundPrice;
			customer.setCash((long) (availableBalance));
			customerDAO.updateCustomer(customer);

			// updating transaction table
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomerId(customer.getCustomerId());
			transaction.setAmount((long) (latestFundPrice * allocatedShares));
			transaction.setTransactionType(TransactionBean.BUY_FUND);
			transaction.setFundId(fundBean.getFundId());
			// how to set execute date without explicitly passing parameter?
			// ****Need revise******
			transaction.setExecuteDate(sdfDate.format(new Date()));
			transaction.setShares(allocatedShares * 1000);
			transactionDAO.createTransaction(transaction);

			// Updating position
			PositionDAO positionDAO = new PositionDAO();
			PositionBean position = positionDAO.getPosition(customer.getCustomerId(), fundBean.getFundId());
			if (position == null) {
				position = new PositionBean();
				position.setCustomerId(customer.getCustomerId());
				position.setFundId(fundBean.getFundId());
				position.setShares(allocatedShares * 1000);
				positionDAO.createPosition(position);

			} else {
				// adding shares to in position table
				position.setShares(position.getShares() + allocatedShares * 1000);
				positionDAO.updatePosition(position);
			}
			// Return success message.
			buyFundMessages.add(new MessageJSON("The purchase was successfully completed"));
		} else {
			buyFundMessages.add(new MessageJSON("No shares are allocated. "));
		}
		return buyFundMessages;
	}
}
