package edu.cmu.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.JSON.MessageJSON;
import edu.cmu.databean.FundBean;
import edu.cmu.model.FundDAO;
import edu.cmu.model.Model;

public class TransitionDayAction {
	
	private Model model;
	public TransitionDayAction() {
	}

	public String getName() {
		return "transitionDay";
	}

	public MessageJSON perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        MessageJSON transitionDayMessage = new MessageJSON();

        FundDAO fundDAO = Model.getFundDAO();
        
    	// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		transitionDayMessage = new MessageJSON("You are not currently logged in");
            return transitionDayMessage;
        }
    	
    	// Check if the user is an employee
        if ((session.getAttribute("userType") != null) && session.getAttribute("userType").equals("customer")) {
        	transitionDayMessage = new MessageJSON("You must be an employee to perform this action");
            return transitionDayMessage;
        }
        try {
	        Transaction.begin();
	        FundBean[] fundList = fundDAO.match();
	        if (fundList == null || fundList.length == 0) {
	        	transitionDayMessage = new MessageJSON("The fund was prices have been successfully recalculated");
	        	return transitionDayMessage;
	        }
	        
	        for (int i=0; i<fundList.length; i++) {
	        	FundBean fundBean = fundList[i];
	        	fundBean.setPrice(getRandFundPrice(fundBean.getPrice()));
	        	fundDAO.update(fundBean);
	        }
		    Transaction.commit();
        } catch (Exception e) {
        	transitionDayMessage = new MessageJSON("The input you provided is not valid");
        	return transitionDayMessage;
        } finally {
        	if (Transaction.isActive()) {
        		Transaction.rollback();
        	}
        }
        transitionDayMessage = new MessageJSON("The fund was prices have been successfully recalculated");
        return transitionDayMessage;
	}

	private static double getRandFundPrice (double currentPrice) {
		int lo = -10;
		int hi = 10;
		Random ran = new Random();
		int x = ran.nextInt(hi - lo) + lo;
		double change = x / 100.0;
		currentPrice = (currentPrice * (1 + change));
		return currentPrice;
	}
}