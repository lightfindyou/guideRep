package bean;

public class OpenId {
	
	private String openId;
	private String token;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "OpenId.entity1232342   openId=" + openId + "; token=" + token;
	}
	
	
}
