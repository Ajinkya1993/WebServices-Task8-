package JSON;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class LoginJSON {
	private String message;
	private List<String> menu;
	public LoginJSON() {
		
	}
	public LoginJSON(String message) {
		this.message = message;
		this.menu = new ArrayList<>();
	}
	public LoginJSON(String message, List<String> menu) {
		this.message = message;
		this.menu = menu;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getMenu() {
		return menu;
	}
	public void setMenu(List<String> menu) {
		this.menu = menu;
	}

}
