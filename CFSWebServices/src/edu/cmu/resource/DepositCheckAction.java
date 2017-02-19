package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.formbean.DepositCheckFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.EmployeeDAO;
import edu.cmu.model.Model;

public class DepositCheckAction {
	private DepositCheckFormBean depositCheckFormBean;
	
	public DepositCheckAction(DepositCheckFormBean bean) {
		this.depositCheckFormBean = bean;
		
	}

	public String getName() {
		return "depositCheck";
	}
	
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON depositCheckMessage = new MessageJSON();
		CustomerDAO customerDAO = Model.getCustomerDAO();
		EmployeeDAO employeeDAO = Model.getEmployeeDAO();
		
    	// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		depositCheckMessage = new MessageJSON("You are not currently logged in");
            return depositCheckMessage;
        }
		
		// Check if the logged in user is an employee
        if (!session.getAttribute("userType").equals("employee")) {
        	depositCheckMessage = new MessageJSON("You must be an employee to perform this action");
            return depositCheckMessage;
        }
        
        // Check the target user is an employee
        if (employeeDAO.match(MatchArg.equals("username", depositCheckFormBean.getUsername())).length > 0) {
        	depositCheckMessage = new MessageJSON("The input you provided is not valid");
        	return depositCheckMessage;
        }
        
        // normal validation check
      	errors = depositCheckFormBean.getValidationErrors();
      	if (errors.size() > 0) {
      		depositCheckMessage = new MessageJSON("The input you provided is not valid");
      		return depositCheckMessage;
      	}
      	
      	//starting from here is the set new cash value into the user's account.
      	double cash = customerDAO.getCustomerByUserName(depositCheckFormBean.getUsername()).getCash();
      	
//      	//check for the cash if is overflow
//      	if(cash + depositCheckFormBean.getCashDouble() > 1000000000) {
//      		depositCheckMessage = new MessageJSON("The input you provided is not valid");
//      		return depositCheckMessage;
//      	}
      	if(customerDAO.getCustomerByUserName(depositCheckFormBean.getUsername()) == null) {
      		depositCheckMessage = new MessageJSON("The input you provided is not valid");
      		return depositCheckMessage;
      	}
      	CustomerBean customerBean = customerDAO.getCustomerByUserName(depositCheckFormBean.getUsername());
      	customerBean.setCash(cash + depositCheckFormBean.getCashDouble());
      	customerDAO.update(customerBean);
      	
      	depositCheckMessage = new MessageJSON("The check was successfully deposited");
		return depositCheckMessage;
	}
}
