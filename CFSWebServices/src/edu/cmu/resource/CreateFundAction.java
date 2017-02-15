package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.EmployeeBean;
import edu.cmu.formbean.CreateFundFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.EmployeeDAO;
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
		String message = "";
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		MessageJSON createFundMessage = new MessageJSON();
		EmployeeDAO employeeDAO = model.getEmployeeDAO();
		
		// normal validation check
		errors = createFundFormBean.getValidationErrors();
		if (errors.size() > 0) {
			message = "The input you provided is not valid";
			return new MessageJSON(message);
		}
		
		// Check if the user is an employee
        if (!(session.getAttribute("userType") != null) && session.getAttribute("userType").equals("employee")) {
        	createFundMessage = new MessageJSON("You must be an employee to perform this action");
            return createFundMessage;
        }
        try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
        return createFundMessage;
	}

}
