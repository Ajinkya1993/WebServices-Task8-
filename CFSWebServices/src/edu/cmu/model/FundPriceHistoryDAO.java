package edu.cmu.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import edu.cmu.databean.FundBean;
import edu.cmu.databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean>{
	public FundPriceHistoryDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(FundPriceHistoryBean.class, tableName, cp);
    }
	
	public FundPriceHistoryBean[] research (FundBean bean) throws RollbackException {
		FundPriceHistoryBean[] funds = match(MatchArg.equals("fundId", bean.getFundId()));
    	if (funds == null || funds.length == 0) {
    		throw new RollbackException ("No fund history for this symbol");
    	} 
    	for (int i = 0; i < funds.length; i++) {
    		String temp = funds[i].getPriceDate().trim();
    		String month = temp.substring(0,2);
    		String day = temp.substring(3,5);
    		String year = temp.substring(6,10);
			funds[i].setPriceDate(year+"-"+month+"-"+day);
		}
    	for (int i = 0; i < funds.length/2; i++)
    	  {
    	     FundPriceHistoryBean temp = funds[i];
    	     funds[i] = funds[funds.length-1 - i];
    	     funds[funds.length-1 - i] = temp;
    	  }
    	return funds;
	}
	//An implementation for find the latest fund price.
	public double fundLatestPrice (FundBean bean) throws RollbackException {
		FundPriceHistoryBean[] funds = match(MatchArg.equals("fundId", bean.getFundId()));
		if (funds == null || funds.length == 0) {
			throw new RollbackException ("No fund history for this symbol");
		}
		int last = funds.length - 1;
		return funds[last].getPrice();
	}
}

