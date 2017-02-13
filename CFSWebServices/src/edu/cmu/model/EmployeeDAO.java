package edu.cmu.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.databean.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean>{
	public EmployeeDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(EmployeeBean.class, tableName, cp);
    }
	public void create (EmployeeBean bean)throws RollbackException {
    	try {
    		Transaction.begin();
    		EmployeeBean employees[] = match(MatchArg.equals("username", bean.getUsername()));
    		if (employees != null && employees.length > 0) {
        		throw new RollbackException ("Username already exists");
        	}
    		super.create(bean);
    		Transaction.commit();
    	} finally {
    		if (Transaction.isActive()) {
    			Transaction.rollback();
    		}
    	}
    }
	
	public EmployeeBean read (String username) throws RollbackException{
    	EmployeeBean[] employees = match(MatchArg.equals("username", username));
    	if(employees != null && employees.length > 0) {
    		return employees[0];
    	}
    	return null;
    }
	
	public EmployeeBean changePassword(String username, String password) throws RollbackException {
        try {
            Transaction.begin();
            EmployeeBean[] employees = (EmployeeBean[])match(MatchArg.equals("username", username));

            if (employees == null || employees.length == 0) {
                throw new RollbackException("Employee " + username + " no longer exists");
            }

            employees[0].setPassword(password);

            update(employees[0]);
            Transaction.commit();
            
            return employees[0];
        } finally {
            if (Transaction.isActive()) Transaction.rollback();
        }
    }
}
