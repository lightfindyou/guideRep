package bean;

public class User {

	/**
	 * author jxz 2017年6月11日14点17分
	 */
	private String openId;
	private String userName;
	private String password;
	private String email;
	private String gender;
	private String IDNumber;
	private String phoneNumber;
	private String verifCode;
	// 1代表是管理员，0和其他代表不是管理员
	private String admin;
	private String adminName;
	private String adminPass;

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	//private String classes;

	@Override
	public String toString() {
		return "User.entity   openId=" + openId + "; userName=" + userName
				+ ";\r\n password=" + password + "; email=" + email + "; \r\ngender="
				+ gender + "; IDNumber=" + IDNumber + "; \r\nadmin=" + admin+ 
				"; phoneNumber=" + phoneNumber+";\r\n verifCode=" + verifCode;
	}

	public String getOpenId() {
		return openId;
	}


	public void setOpenId(String openId) {
		this.openId = openId;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIDNumber() {
		return IDNumber;
	}

	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	
	public String getVerifCode() {
		return verifCode;
	}

	public void setVerifCode(String verifCode) {
		this.verifCode = verifCode;
	}

	/*public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}*/

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
}
