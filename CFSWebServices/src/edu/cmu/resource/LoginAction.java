package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.model.EmployeeDAO;
import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.EmployeeBean;
import edu.cmu.formbean.LoginFormBean;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.Model;


public class LoginAction {
	private LoginFormBean loginFormBean;
	private Model model;
	
	public LoginAction(LoginFormBean bean, Model model) {
		this.loginFormBean = bean;
		this.model = model;
		
	}

	public String getName() {
		return "login";
	}
	public MessageJSON perform (HttpServletRequest request) throws RollbackException {
			String message = "";
			HttpSession session = request.getSession();
			List<String> errors = new ArrayList<String>();
			MessageJSON loginJSON = new MessageJSON();
	
			// normal validation check
			errors = loginFormBean.getValidationErrors();
			if (errors.size() > 0) {
				message = "There seems to be an issue with the username/password combination that you entered";
				return new MessageJSON(message);
			}
			
			// Checking if customer has logged in
			CustomerDAO customerDAO = model.getCustomerDAO();
			EmployeeDAO employeeDAO = model.getEmployeeDAO();
			CustomerBean customer = customerDAO.getCustomerByUserName(loginFormBean.getUsername());
			if (customer != null) {
				if (customer.getPassword().equals(loginFormBean.getPassword())) {
					session.setAttribute("user", customer);
					session.setAttribute("userType", "customer");
					message = "Welcome " + customer.getFirstName();
					loginJSON = new MessageJSON(message);
					return loginJSON;
				} else {
					message = "There seems to be an issue with the username/password combination that you entered";
					loginJSON = new MessageJSON(message);
				}
			}
			// Checking if employee has logged in
			EmployeeBean employee = employeeDAO.read(loginFormBean.getUsername());
			if (employee != null) {
				if (employee.getPassword().equals(loginFormBean.getPassword())) {
					session.setAttribute("user", employee);
					session.setAttribute("userType", "employee");
					message = "Welcome " + employee.getFirstName();
					loginJSON = new MessageJSON(message);
					return loginJSON;
				} else {
					message = "There seems to be an issue with the username/password combination that you entered";
					loginJSON = new MessageJSON(message);
				}
			}
			// Checking if user name and password doesn't match for both employee
			// and customer
			if (employee == null && customer == null) {
				message = "There seems to be an issue with the username/password combination that you entered";
				loginJSON = new MessageJSON(message);
				return loginJSON;
			}
			return loginJSON;
	}
}
