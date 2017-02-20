package edu.cmu.databean;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import org.genericdao.RollbackException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.cmu.model.FundDAO;

@XmlRootElement
public class Portfolio {

	private String message;
	private String cash;
	private FundObject[] funds;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCash() {
		return cash;
	}

	public void setCash(String cash) {
		this.cash = cash;
	}

	public FundObject[] getFunds() {
		return funds;
	}

	public void setFunds(FundObject[] funds) {
		this.funds = funds;
	}

	public FundObject[] getCustomerDetails(PositionBean[] positions, FundDAO fundDAO)
			throws RollbackException {

		List<FundObject> fundsArray = new ArrayList<>();;

		for (PositionBean positionBean : positions) {
			FundObject fund = new FundObject();
			FundBean fundBean = fundDAO.read(positionBean.getFundId());
			fund.setName(fundBean.getName());
			double shares = positionBean.getShares();
			fund.setShares(String.format("%.0f", shares));
			fund.setPrice(String.format("%.2f", fundBean.getPrice()));
			fundsArray.add(fund);
		}
		
		FundObject[] funds = new FundObject[fundsArray.size()];
		for (int i = 0; i < funds.length; i++) {
			funds[i] = fundsArray.get(i);
		}
		return funds;

	}
}
