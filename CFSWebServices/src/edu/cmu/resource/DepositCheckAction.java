package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.FundBean;
import edu.cmu.databean.TransactionBean;
import edu.cmu.formbean.DepositCheckFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;
import edu.cmu.model.TransactionDAO;

public class DepositCheckAction {
	private DepositCheckFormBean depositCheckFormBean;
	private Model model;
	
	public DepositCheckAction(DepositCheckFormBean bean, Model model) {
		this.depositCheckFormBean = bean;
		this.model = model;
		
	}

	public String getName() {
		return "depositCheck";
	}
	
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON depositCheckMessage = new MessageJSON();
		CustomerBean user = (CustomerBean) session.getAttribute("user");
		CustomerDAO customerDAO = model.getCustomerDAO();
		
    	// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		depositCheckMessage = new MessageJSON("You are not currently logged in");
            return depositCheckMessage;
        }
		
		// Check if the user is an employee
        if (!(session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
        	depositCheckMessage = new MessageJSON("You must be an employee to perform this action");
            return depositCheckMessage;
        }
        
        // normal validation check
      	errors = depositCheckFormBean.getValidationErrors();
      	if (errors.size() > 0) {
      		depositCheckMessage = new MessageJSON("The input you provided is not valid");
      		return depositCheckMessage;
      	}
      	
      	//starting from here is the set new cash value into the user's account.
      	//No transition day call is necessary?
      	double cash = customerDAO.getCustomerByUserName(user.getUsername()).getCash();
      	
      	//check for the cash if is overflow
      	if(cash + depositCheckFormBean.getCashDouble() > 1000000000) {
      		depositCheckMessage = new MessageJSON("The input you provided is not valid");
      		return depositCheckMessage;
      	}
      	
      	CustomerBean customerBean = customerDAO.getCustomerByUserName(user.getUsername());
      	customerBean.setCash(cash + depositCheckFormBean.getCashDouble());
      	customerDAO.update(customerBean);
      	
      	depositCheckMessage = new MessageJSON("The check was successfully deposited");
		return depositCheckMessage;
	}
}
