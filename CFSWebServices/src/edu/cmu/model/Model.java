// Name: Ajinkya Nimbalkar Date: 5th December 2016 Course Number :08672
package edu.cmu.model;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.databean.CustomerBean;
import edu.cmu.databean.EmployeeBean;
import edu.cmu.databean.FundBean;
import edu.cmu.databean.FundPriceHistoryBean;
import edu.cmu.databean.PositionBean;
import edu.cmu.databean.TransactionBean;

public class Model {
	
	private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;
    private FundDAO fundDAO;
    private PositionDAO positionDAO;

    public Model(ServletConfig config) throws ServletException, RollbackException {
        try {
            //String jdbcDriver = config.getInitParameter("jdbcDriver");
            //String jdbcURL = config.getInitParameter("jdbcURL");
        	
        	String jdbcDriver = "com.mysql.jdbc.Driver";
            String jdbcURL = "jdbc:mysql:///test";

            ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);

            customerDAO = new CustomerDAO(pool, "customer");
            employeeDAO = new EmployeeDAO(pool, "employee");
            fundDAO = new FundDAO(pool, "fund");
            positionDAO = new PositionDAO(pool, "position");
            
            //pre-populatnig with employee
            
            if (employeeDAO.getCount()==0) {
	            EmployeeBean employeebean = new EmployeeBean();
	            employeebean.setAddrLine1("address1");
	            employeebean.setAddrLine2("address2");
	            employeebean.setCity("city");
	            employeebean.setFirstName("firstname");
	            employeebean.setLastName("lastname");
	            employeebean.setPassword("password");
	            employeebean.setState("state");
	            employeebean.setUsername("username");
	            employeebean.setZip("zip");
	            employeeDAO.create(employeebean);
            }
            
            if (customerDAO.getCount()==0) {
	            CustomerBean customerbean = new CustomerBean();
	            customerbean.setAddress("address1");
	            customerbean.setCity("city");
	            customerbean.setFirstName("cus-0");
	            customerbean.setLastName("lastname");
	            customerbean.setPassword("password");
	            customerbean.setState("state");
	            customerbean.setUsername("customer");
	            customerbean.setZip("zip");
	            customerbean.setCash(1000);
	            customerDAO.create(customerbean);
            }
            
        } catch (DAOException e) {
            throw new ServletException(e);
        } catch (RollbackException f) {
        	throw new ServletException(f);
        } finally {
        	if (Transaction.isActive()) {
        		Transaction.rollback();
        	}
        }
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }
    
    public FundDAO getFundDAO() {
        return fundDAO;
    }
    public PositionDAO getPositionDAO() {
        return positionDAO;
    }
}
