package edu.cmu.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.json.JSONException;
import org.json.JSONObject;

import edu.cmu.databean.*;
import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.CreateAccountForm;
import edu.cmu.model.CustomerDAO;
import edu.cmu.model.Model;

public class CreateCustomerAccountAction {
	private CustomerDAO customerDAO;

	public CreateCustomerAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public MessageJSON createAccount(String jsonString, HttpServletRequest request)
			throws JSONException, RollbackException {
		JSONObject jsonObject = new JSONObject(jsonString);
		CreateAccountForm form = new CreateAccountForm(jsonObject);
		List<String> errors = new ArrayList<String>();

		EmployeeBean user = (EmployeeBean) request.getSession().getAttribute("user");
		if (user == null) {
			return new MessageJSON("You are not currently logged in");
		}
		
		String checkUser = (String) request.getSession(false).getAttribute("userType");
		if (!checkUser.equals("Employee")) {
			return new MessageJSON("You must be an employee to perform this action");
		}
		
		errors.addAll(form.getValidationErrors());

		if (errors.size() > 0) {
			return new MessageJSON("The input you provided is not valid");
		}

		CustomerBean checkCustomer = customerDAO.getCustomerByUserName(form.getUserName());
		if (checkCustomer != null) {
			return new MessageJSON("The input you provided is not valid");
		}

		CustomerBean customer = new CustomerBean();
		customer.setAddress(form.getAddress());
		customer.setCash(form.getCash());
		customer.setCity(form.getCity());
		customer.setEmail(form.getEmail());
		customer.setFirstName(form.getFirstName());
		customer.setLastName(form.getLastName());
		customer.setPassword(form.getPassword());
		customer.setState(form.getState());
		customer.setUsername(form.getUserName());
		customer.setZip(form.getZip());

		customerDAO.create(customer);

		return new MessageJSON(form.getFirstName() + " was registered successfully");

	}

}
