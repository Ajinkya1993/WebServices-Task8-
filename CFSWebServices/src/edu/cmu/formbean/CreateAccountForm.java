package edu.cmu.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateAccountForm {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String email;
	private String checkCash;
	private double cash;
	private String userName;
	private String password;

	public CreateAccountForm(JSONObject jsonObject) throws JSONException {
		firstName = jsonObject.getString("fname");
		lastName = jsonObject.getString("lname");
		address = jsonObject.getString("address");
		city = jsonObject.getString("city");
		state = jsonObject.getString("state");
		zip = jsonObject.getString("zip");
		email = jsonObject.getString("email");
		String cashInput = jsonObject.getString("cash");
		userName = jsonObject.getString("username");
		password = jsonObject.getString("password");

		// if (firstNameInput != null) {
		// firstName = sanitize(firstNameInput);
		// }
		//
		// if (lastNameInput != null) {
		// lastName = sanitize(lastNameInput);
		// }
		//
		// if (addressInput != null) {
		// address = sanitize(addressInput);
		// }
		//
		// if (cityInput != null) {
		// city = sanitize(cityInput);
		// }
		//
		// if (stateInput != null) {
		// state = sanitize(stateInput);
		// }
		//
		// if (zipCodeInput != null) {
		// zip = sanitize(zipCodeInput);
		// }
		//
		// if (emailInput != null) {
		// email = sanitize(emailInput);
		// }

		if (cashInput != null) {
			checkCash = sanitize(cashInput);
			if (checkCashValue(checkCash)) {
				cash = getCashAsDouble(cashInput);
			}
		} else {
			cash = 0d;
		}

		// if (userNameInput != null) {
		// userName = sanitize(userNameInput);
		// }
		//
		// if (passwordInput != null) {
		// password = sanitize(passwordInput);
		// }
	}

	private boolean checkCashValue(String cashInput) {
		try {
			Double.parseDouble(cashInput);
			// if (cashInput.equals("0") || cashInput.equals("0.0") ||
			// cashInput.equals("0.00")) {
			// return true;
			// }
			if (getCashAsDouble(cashInput) < 0 || getCashAsDouble(cashInput) > 100000000) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	private double getCashAsDouble(String cashInput) {
		return Double.parseDouble(cashInput);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (firstName == null || firstName.length() == 0)
			errors.add("The input you provided is not valid");

		if (lastName == null || lastName.length() == 0)
			errors.add("The input you provided is not valid");

		if (address == null || address.length() == 0)
			errors.add("The input you provided is not valid");

		if (city == null || city.length() == 0)
			errors.add("The input you provided is not valid");

		if (state == null || state.length() == 0)
			errors.add("The input you provided is not valid");

		if (zip == null || zip.length() == 0)
			errors.add("The input you provided is not valid");

		if (email == null || email.length() == 0)
			errors.add("The input you provided is not valid");

		if (checkCash != null && !checkCashValue(checkCash)) {
			errors.add("The input you provided is not valid");
		}

		if (userName == null || userName.length() == 0)
			errors.add("The input you provided is not valid");

		if (password == null || password.length() == 0)
			errors.add("The input you provided is not valid");

		return errors;
	}

	public String sanitize(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}

		Pattern pattern = Pattern.compile("[<>\"&]");
		Matcher matcher = pattern.matcher(input);
		StringBuffer sb = null;
		while (matcher.find()) {
			if (sb == null) {
				sb = new StringBuffer();
			}
			switch (input.charAt(matcher.start())) {
			case '<':
				matcher.appendReplacement(sb, "&lt;");
				break;
			case '>':
				matcher.appendReplacement(sb, "&gt;");
				break;
			case '&':
				matcher.appendReplacement(sb, "&amp;");
				break;
			case '"':
				matcher.appendReplacement(sb, "&quot;");
				break;
			default:
				matcher.appendReplacement(sb, "&#" + ((int) input.charAt(matcher.start())) + ';');
			}
		}

		if (sb == null)
			return input;
		matcher.appendTail(sb);
		return sb.toString();
	}

}
