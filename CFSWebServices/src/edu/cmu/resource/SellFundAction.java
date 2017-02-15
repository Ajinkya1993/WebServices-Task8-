package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.databean.CustomerBean;
import edu.cmu.model.PositionDAO;
import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.PositionBean;
import edu.cmu.formbean.SellFundFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;

public class SellFundAction {
	private SellFundFormBean sellfundFormBean;
	private Model model;
	
	public SellFundAction(SellFundFormBean bean, Model model) {
		this.sellfundFormBean = bean;
		this.model = model;
		}
	
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON message = new MessageJSON();
  	    PositionDAO positionDAO = model.getPositionDAO();
		CustomerDAO customerDAO = model.getCustomerDAO();
		FundDAO fundDAO = model.getFundDAO();
		
	   	// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		message = new MessageJSON("You are not currently logged in");
            return message;
        }
    	
    	// Check if the user is an customer
        if ((session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
        	message = new MessageJSON("You must be a customer to perform this action");
            return message;
        }
		
        // check if there is invalid input
		   errors = sellfundFormBean.getValidationErrors();
           if (errors.size() > 0) {
        	   message = new MessageJSON("The input you provided is not valid");
               return message;
           }
         
        // update shares and cash
           CustomerBean user = (CustomerBean) session.getAttribute("user");
           int fundid = fundDAO.read(sellfundFormBean.getsymbol()).getFundId();
           double shares = positionDAO.getPosition(user.getCustomerId(), fundid).getShares();
    	   double price = fundDAO.read(sellfundFormBean.getsymbol()).getPrice();
           if (sellfundFormBean.getSharesDouble() <= shares) {
        	   PositionBean positionBean = positionDAO.getPosition(user.getCustomerId(), fundid);
        	   CustomerBean customerBean = customerDAO.getCustomerByUserName(user.getUsername());
        	   positionBean.setShares(shares - sellfundFormBean.getSharesDouble());
        	   customerBean.setCash(customerBean.getCash() + sellfundFormBean.getSharesDouble() * price);
        	   positionDAO.update(positionBean);
        	   customerDAO.update(customerBean);
        	 } else {      	 
        		 message = new MessageJSON("You don’t have that many shares in your portfolio");
        		 return message;
        	 }
		
           message = new MessageJSON("The shares have been successfully sold");
		   return message;
	
}
}
