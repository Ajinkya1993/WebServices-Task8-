package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.formbean.RequestCheckFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.Model;

public class RequestCheckAction {
	private RequestCheckFormBean requestCheckFormBean;
	private Model model;
	
	public RequestCheckAction(RequestCheckFormBean bean, Model model) {
		this.requestCheckFormBean = bean;
		this.model = model;
		}
	
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON message = new MessageJSON();
  	    CustomerBean user = (CustomerBean) session.getAttribute("user");
		CustomerDAO customerDAO = model.getCustomerDAO();

		// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		message = new MessageJSON("You are not currently logged in");
            return message;
        }

    	// Check if the user is an customer
        if (!(session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
        	message = new MessageJSON("You must be a customer to perform this action");
            return message;
        }
		
        // check if there is invalid input
		   errors = requestCheckFormBean.getValidationErrors();
           if (errors.size() > 0) {
        	   message = new MessageJSON("The input you provided is not valid");
               return message;
           }
        
        // update cash
         double cash = customerDAO.getCustomerByUserName(user.getUsername()).getCash();
         if (requestCheckFormBean.getCashDouble() <= cash) {
        	 CustomerBean customerBean = customerDAO.getCustomerByUserName(user.getUsername());
        	 customerBean.setCash(cash - requestCheckFormBean.getCashDouble());
        	 customerDAO.update(customerBean);
         } else {
        	 message = new MessageJSON("You don't have sufficient funds in your account to cover the requested check");
     		return message;
         }
         
		message = new MessageJSON("The check has been successfully requested");
		return message;
		
	}
}
