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
		String firstNameInput = jsonObject.getString("fname");
		String lastNameInput = jsonObject.getString("lname");
		String addressInput = jsonObject.getString("address");
		String cityInput = jsonObject.getString("city");
		String stateInput = jsonObject.getString("state");
		String zipCodeInput = jsonObject.getString("zip");
		String emailInput = jsonObject.getString("email");
		String cashInput = jsonObject.getString("cash");
		String userNameInput = jsonObject.getString("username");
		String passwordInput = jsonObject.getString("password");

		if (firstNameInput != null) {
			firstName = sanitize(firstNameInput).trim();
		}

		if (lastNameInput != null) {
			lastName = sanitize(lastNameInput).trim();
		}

		if (addressInput != null) {
			address = sanitize(addressInput).trim();
		}

		if (cityInput != null) {
			city = sanitize(cityInput).trim();
		}

		if (stateInput != null) {
			state = sanitize(stateInput).trim();
		}

		if (zipCodeInput != null) {
			zip = sanitize(zipCodeInput).trim();
		}

		if (emailInput != null) {
			email = sanitize(emailInput).trim();
		}

		if (cashInput != null) {
			checkCash = sanitize(cashInput).trim();
			if (checkCashValue(checkCash)) {
				cash = getCashAsDouble(cashInput);
			}
		} else {
			cash = 0d;
		}

		if (userNameInput != null) {
			userName = sanitize(userNameInput).trim();
		}

		if (passwordInput != null) {
			password = sanitize(passwordInput).trim();
		}
	}

	private boolean checkCashValue(String cashInput) {
		try {
			Double.parseDouble(cashInput);
			if (getCashAsDouble(cashInput) < 1 || getCashAsDouble(cashInput) > 10000000) {
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
			errors.add("First name is required.");

		if (firstName != null && firstName.length() > 50) {
			errors.add("First name must be within 50 characters.");
		}

		if (lastName == null || lastName.length() == 0)
			errors.add("Last name is required.");

		if (lastName != null && lastName.length() > 50) {
			errors.add("Last name must be within 50 characters.");
		}

		if (address == null || address.length() == 0)
			errors.add("Address is required.");

		if (address != null && address.length() > 50) {
			errors.add("Address must be within 50 characters.");
		}

		if (city == null || city.length() == 0)
			errors.add("City is required.");

		if (city != null && city.length() > 50) {
			errors.add("City must be within 50 characters.");
		}

		if (state == null || state.length() == 0)
			errors.add("State is required.");

		if (state != null && state.length() > 50) {
			errors.add("State must be within 50 characters.");
		}

		if (zip == null || zip.length() == 0)
			errors.add("ZIP code is required.");

		if (zip != null && zip.length() > 50) {
			errors.add("Zip code must be within 50 characters.");
		}

		if (email == null || email.length() == 0)
			errors.add("E-mail ID is required.");

		if (email != null && email.length() > 50) {
			errors.add("E-mail ID must be within 50 characters.");
		}

		if (checkCash != null && !checkCashValue(checkCash)) {
			errors.add("Cash value is not in the right format.");
		}

		if (checkCash != null && checkCash.length() > 50) {
			errors.add("Cash must be within 50 characters.");
		}

		if (userName == null || userName.length() == 0)
			errors.add("User name is required.");

		if (userName != null && userName.length() > 50) {
			errors.add("User name must be within 50 characters.");
		}

		if (password == null || password.length() == 0)
			errors.add("Password is required.");

		if (password != null && password.length() > 50) {
			errors.add("Password must be within 50 characters.");
		}

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
