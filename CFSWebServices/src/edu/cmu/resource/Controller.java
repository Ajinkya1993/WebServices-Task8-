package edu.cmu.resource;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

//import model.Model;
import edu.cmu.model.Model;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Model model;
	public Controller() {
		super();
	}

	public void init() throws ServletException {
		try {
			model = new Model(getServletConfig());
		} catch (RollbackException e) {
			e.printStackTrace();
		}
	}
	public Model getModel () {
		return model;
	}
}
