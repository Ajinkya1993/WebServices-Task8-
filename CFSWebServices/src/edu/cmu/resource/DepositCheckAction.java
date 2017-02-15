package edu.cmu.resource;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.formbean.DepositCheckFormBean;
import edu.cmu.model.Model;

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
		return null;
	}
}
