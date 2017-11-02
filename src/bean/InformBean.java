package bean;

import java.util.HashMap;


public class InformBean {
	String to;
	HashMap<String,String> vars;
	
	
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public HashMap<String, String> getVars() {
		return vars;
	}
	public void setVars(HashMap<String, String> vars) {
		this.vars = vars;
	}
}
