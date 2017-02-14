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

	}
}
