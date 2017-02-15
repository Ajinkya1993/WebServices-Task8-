package edu.cmu.databean;

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
	private String funds;

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

	public String getFunds() {
		return funds;
	}

	public void setFunds(String funds) {
		this.funds = funds;
	}

	public String getCustomerDetails(PositionBean[] positions, FundDAO fundDAO)
			throws RollbackException, JSONException {

		JSONArray fundsArray = new JSONArray();

		for (PositionBean positionBean : positions) {
			JSONObject jsonObject = new JSONObject();
			FundBean fund = fundDAO.read(positionBean.getFundId());
			jsonObject.put("name", fund.getName());
			jsonObject.put("shares", positionBean.getShares());
			jsonObject.put("price", fund.getPrice());
			fundsArray.put(jsonObject);
		}

		return fundsArray.toString();

	}
}
