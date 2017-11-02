package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import myUtils.StaticData;
import myUtils.Tools;
import bean.Course;
import bean.User;
import dao.UserDao;

public class UserService extends Tools {
	
	private UserDao userDao = new UserDao();
	private static String verificationCode;

	/**
	 * @param user
	 * @return 返回User类型的参数
	 */
	public User login(User user) {
		try {
			System.out.println(user.toString());
			if (user.getOpenId() != null)
				return userDao.findByWeiChatAndPassword(user.getOpenId(),
						user.getPassword());
			return userDao.findByPhoneAndPassword(user.getPhoneNumber(),
					user.getPassword());
		} catch (SQLException e) {
			throw new RuntimeException();
		}
	}

	/**
	 * 注册功能
	 * 
	 * @param user
	 */
	public void regist(User user) {
		userDao.addUser(user);
	}

	/**
	 * 完善用户信息
	 * @param user
	 */
	public void completeUserInfo(User user){
		User oldUserInfo = userDao.getUserInfo(user);
		if(user.getEmail() == null && oldUserInfo.getEmail()!=null){
			user.setEmail(oldUserInfo.getEmail());
		}
		if(user.getGender() == null && oldUserInfo.getGender()!=null){
			user.setGender(oldUserInfo.getGender());
		}
		if(user.getIDNumber() == null && oldUserInfo.getIDNumber()!=null){
			user.setIDNumber(oldUserInfo.getIDNumber());
		}
		if(user.getUserName() == null && oldUserInfo.getUserName()!=null){
			user.setUserName(user.getUserName());
		}
		userDao.update(user);
	}
	
	/**
	 * 完善用户密码
	 * @param user
	 */
	public void completeUserAccount(User user){
		userDao.setPassword(user);
	}
	/**
	 * 验证用户的验证码是否正确，时间是否超时
	 * @param userName
	 * @param verificationCode
	 * @return
	 */
	public boolean verifyTheCode(String phoneNumber,String code){
		
		verificationCode = StaticData.getVerificationMap().get(phoneNumber);
		System.out.println("电话："+phoneNumber+"  验证码：  "+verificationCode);
		
		if(!UserService.verificationCode.equals(code)){
			System.out.println("userservice验证失败");
			return false;
		}
		
		/**
		 * 时间验证，暂时保留
		 */
		/*if((System.currentTimeMillis()-Integer.valueOf(time))>(StaticData.verificationValidityPeriod*60*1000)){
			StaticData.getVerificationMap().remove(phoneNumber);
			System.out.println("验证失败");
			return false;
		}*/
		System.out.println("userservice验证成功");
		return true;
	}
	
	/**
	 * 改变电话号
	 * @param user
	 * @param oldPhoneNumber
	 * @return
	 */
	public boolean changePhoneNumber(User user,String oldPhoneNumber){
		
		System.out.println("改变电话号");
		User userInfo = new User();
		User oldUser = new User();
		
		if(UserService.verificationCode.equals(user.getVerifCode())){
			System.out.println("userservice验证成功");
			userInfo.setPhoneNumber(oldPhoneNumber);
			userInfo = userDao.getUserInfo(userInfo);
			oldUser.setPhoneNumber(userInfo.getPhoneNumber());
			userInfo.setPhoneNumber(user.getPhoneNumber());
			userDao.addUser(userInfo);
			userDao.deleteUser(oldUser);
			return false;
		}
		
		System.out.println("userservice验证失败");
		return true;
	}
	
	/**
	 * 获取user的信息
	 * 
	 * @param user
	 * @return
	 */
	public User getUserInfo(User user) {
		UserDao userDao = new UserDao();
		user = userDao.getUserInfo(user);
		return user;
	}
	
	/**
	 * 获取用户信息总览
	 * @param user
	 * @return
	 */
	public List<User> userPandect() {
		UserDao userDao = new UserDao();
		List<User> al = new ArrayList<User>();
		al = userDao.getPandect();
		return al;
	}
	
	/**
	 * 获取学员课程
	 * @param user
	 * @return
	 */
	public List<Course> getMyCourse(User user){
		UserDao userDao = new UserDao();
		List<Course> cl = new ArrayList<Course>();
		cl = userDao.getMyCourse(user);
		return cl;
	}
	
	/**
	 * 管理员登陆页面
	 * @param user
	 * @return 返回User类型的参数
	 */
	public User adLogin(User user) {
			return userDao.adLogin(user.getPhoneNumber(),
					user.getPassword());
	}
}
