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
		this.model = model;
	}

	public MessageJSON perform(HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON buyFundMessage = new MessageJSON();
		CustomerBean user = (CustomerBean) session.getAttribute("user");
		FundDAO fundDAO = model.getFundDAO();
		TransactionDAO transactionDAO = model.getTransactionDAO();

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
		if (!(session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
			buyFundMessage = new MessageJSON("You must be a customer to perform this action");
		}
		try {
			Transaction.begin();
			double balance = user.getCash();
			
			
		} catch (RollbackException e) {
			// TODO: handle exception
		}
		
		

		buyFundMessage = new MessageJSON("The fund has been successfully purchased");
		return buyFundMessage;

	}
}
