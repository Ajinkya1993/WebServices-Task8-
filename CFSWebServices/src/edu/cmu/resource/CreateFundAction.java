package edu.cmu.resource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.FundBean;
import edu.cmu.formbean.CreateFundFormBean;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;

public class CreateFundAction {

	private CreateFundFormBean createFundFormBean;
	private Model model;
	
	public CreateFundAction(CreateFundFormBean bean, Model model) {
		this.createFundFormBean = bean;
		this.model = model;
		
	}

	public String getName() {
		return "createFund";
	}
	
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON createFundMessage = new MessageJSON();
		FundBean fundBean = new FundBean();
		FundDAO fundDAO = model.getFundDAO();
		
		// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		createFundMessage = new MessageJSON("You are not currently logged in");
            return createFundMessage;
        }
		
		// Check if the user is an employee
        if (!(session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
        	createFundMessage = new MessageJSON("You must be an employee to perform this action");
            return createFundMessage;
        }
        
        // normal validation check
 		errors = createFundFormBean.getValidationErrors();
 		if (errors.size() > 0) {
 			createFundMessage = new MessageJSON("The input you provided is not valid");
 			return createFundMessage;
 		}
     		
    	//write in FundDAO
		fundBean.setName(createFundFormBean.getName());
		fundBean.setSymbol(createFundFormBean.getSymbol());
		fundBean.setPrice(createFundFormBean.getInitial_valueDouble());
		//set the current date
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate localDate = LocalDate.now();
		fundBean.setDate(dtf.format(localDate).toString());
		fundDAO.create(fundBean);
		
		createFundMessage = new MessageJSON("The fund was successfully created");
        return createFundMessage;
	}

}
