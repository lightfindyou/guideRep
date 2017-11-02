package action;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myUtils.CookieUtils;
import myUtils.StaticData;
import myUtils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.UserService;
import bean.User;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	
	private static final long serialVersionUID = -4798758156941840307L;
	
	private User user = new User();
	private String ret;
	private HttpServletResponse hsr;
	private HttpServletRequest hsrt;
	private ByteArrayInputStream inputStream = new ByteArrayInputStream("DefaultInputStream".getBytes());
	private UserService userService = new UserService();
	private String oldPhoneNumber;

	@Override
	public User getModel() {
		if (user == null) {
			return new User();
		}
		return user;
	}
	
	private User getCookie(User user){
		try{
			hsrt = ServletActionContext.getRequest();
			user.getPhoneNumber().length();
		}catch(NullPointerException e){
			user.setPhoneNumber(CookieUtils.getCookie(hsrt));
		}
		return user;
	}

	/**
	 * 登陆操作
	 */
	@Override
	public String execute() throws Exception {
		getCookie(user);
		System.out.println("登陆操作");
		hsr = ServletActionContext.getResponse();
		hsr.setCharacterEncoding("utf-8");
		user = userService.login(user);
		if (user != null) {
			CookieUtils.addCookie(ServletActionContext.getResponse(), user);
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			System.out.println("登陆成功");
			return SUCCESS;
		}
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		System.out.println("登陆失败");
		return SUCCESS;
	}

	/**
	 * 注册用户
	 * 
	 * @return
	 * @throws SQLException
	 */
	public String register() throws Exception {
		System.out.println("注册用户信息:\r\n" + user);
		UserService userService = new UserService();
		if (userService.verifyTheCode(user.getPhoneNumber(),user.getVerifCode())) {
			userService.regist(user);
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			ret = SUCCESS;
		}
		return ret;
	}
	
	/**
	 * 向用户发送验证码
	 * @param length 发送的验证码的长度
	 */
	public String getVerificationCode(){
		try {
			System.out.println("getVerificationCode");
			Random random = new Random();
			StringBuilder builder = new StringBuilder();
			for(int i=0;i<StaticData.verificationCodeLength;i++){
				builder.append((int)(random.nextFloat()*10));
			}
			Tools.sendMessage(user.getPhoneNumber(),builder.toString() , 
					StaticData.verificationValidityPeriod+"");
		} catch (Exception e) {
			System.out.println("catched exception");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 检查验证码
	 */
	public String checkVerificationCode(){
		System.out.println("userservlet验证失败");
		try {
			System.out.println("checkVerificationCode");
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			UserService userService = new UserService();
			if (userService.verifyTheCode(user.getPhoneNumber(),user.getVerifCode())) {
				System.out.println("userservlet验证成功");
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
				return SUCCESS;
			}else{
				inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
				return SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("catched exception");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 改变用户电话号
	 */
	public String changePhoneNumber(){
		//getCookie(user);
		try {
			System.out.println("changePhoneNumber");
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			UserService userService = new UserService();
			if (userService.verifyTheCode(user.getPhoneNumber(),user.getVerifCode())) {
				System.out.println("userservlet验证成功");
				userService.changePhoneNumber(user,oldPhoneNumber);
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
				return SUCCESS;
			}
		} catch (Exception e) {
			System.out.println("catched exception");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取用户信息
	 */
	public String getPersionalInfo(){
		getCookie(user);
		try {
			System.out.println("getPersionalInfo");
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			UserService userService = new UserService();
			user = userService.getUserInfo(user);
			JSONObject json = JSONObject.fromObject(user);
			System.out.println("获取用户信息"+json.toString());
			inputStream = new ByteArrayInputStream(json.toString().getBytes("utf-8"));
			//inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("catched exception");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 完整用户信息
	 * @return
	 * @throws SQLException
	 */
	public String completeUserInfo() throws UnsupportedEncodingException{
		getCookie(user);
		System.out.println("完整用户信息:\r\n" + user);
		try{
			UserService userService = new UserService();
			userService.completeUserInfo(user);
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			System.out.println("userservlet更新成功");
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		System.out.println("userservlet更新失败");
		return SUCCESS;
	}

	/**
	 * 设置密码
	 * @return
	 * @throws SQLException
	 */
	public String setPassword() throws UnsupportedEncodingException{
		getCookie(user);
		System.out.println("设置密码:\r\n" + user);
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		try{
			UserService userService = new UserService();
			userService.completeUserAccount(user);
			//inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			System.out.println("userservlet更新成功");
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("userservlet更新失败");
		return SUCCESS;
	}
	
	/**
	 * 查询自己的房间
	 * @return
	 * @throws SQLException
	 */
	public String myDorm() throws UnsupportedEncodingException{
		getCookie(user);
		System.out.println("查询自己的房间:\r\n" + user);
		//inputStream = new ByteArrayInputStream("{\"applyDate\":\"2017.09.12\",\"date\":\"2017.10.12\",\"roomType\":\"双人间009\"}".getBytes("utf-8"));
		inputStream = new ByteArrayInputStream("none".getBytes("utf-8"));
		return SUCCESS;
	}
	
	/**
	 * 用户信息总览
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String userPandect() throws UnsupportedEncodingException{
		System.out.println("获取用户信息");
		List<User> ul = new ArrayList<User>();
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		try{
			UserService userService = new UserService();
			ul = userService.userPandect();
			String sl = JSONArray.fromObject(ul).toString();
			inputStream = new ByteArrayInputStream(sl.getBytes("utf-8"));
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("userservlet更新失败");
		return SUCCESS;
	}
	
	/**
	 * 管理员添加用户
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String addUserByAdmin() throws UnsupportedEncodingException{
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		try {
			userService.regist(user);
			if(this.completeUserInfo()==SUCCESS && this.setPassword()==SUCCESS){
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取用户课程信息
	 * @return
	 */
	public String getMyCourse(){
		getCookie(user);
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			String courseString = JSONArray.fromObject(userService.getMyCourse(user)).toString();
			System.out.println("课程信息：\r\n"+courseString);
			inputStream = new ByteArrayInputStream(courseString.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 管理员登陆
	 */
	public String adLogin(){
		System.out.println("登陆操作");
		hsr = ServletActionContext.getResponse();
		hsr.setCharacterEncoding("utf-8");
		user = userService.adLogin(user);
		try {
			if (user != null) {
				CookieUtils.addCookie(ServletActionContext.getResponse(), user);
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
				System.out.println("登陆成功");
				return SUCCESS;
			}
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("登陆失败");
		return SUCCESS;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getOldPhoneNumber() {
		return oldPhoneNumber;
	}

	public void setOldPhoneNumber(String oldPhoneNumber) {
		this.oldPhoneNumber = oldPhoneNumber;
	}

}
