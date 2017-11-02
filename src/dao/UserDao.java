package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import bean.Course;
import bean.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {

	/**
	 * author jxz 2017年6月
	 */
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * 按微信名和密码查询
	 * 
	 * @param weiChat
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User findByWeiChatAndPassword(String weiChat, String password)
			throws SQLException {
		String sql = "select * from user where weiChat=? && password=?";
		return qr.query(sql, new BeanHandler<User>(User.class), weiChat,
				password);
	}

	/**
	 * 按用户名名和密码查询
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User findByPhoneAndPassword(String phoneNumber, String password) {
		User user = new User();
		try {
			String sql = "select * from user where phoneNumber=? && password=?";
			user = qr.query(sql, new BeanHandler<User>(User.class),
					phoneNumber, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("收到的user" + user);
		return user;
	}

	/**
	 * 按照用户名密码添加用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public int addUser(User user) {
		int i = 0;
		String sql = "insert into userinfo set phoneNumber=?,userName=?,email=?,gender=?,IDNumber=?";
		Object[] params = { user.getPhoneNumber(), user.getUserName(),
				user.getEmail(), user.getGender(), user.getIDNumber() };
		try {
			i = qr.update(sql, params);
			sql = "insert into user set userName=?,phoneNumber=?,password=?";
			i += qr.update(sql, user.getUserName(), user.getPhoneNumber(),
					user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
			// return;
		}
		return i;
	}

	/**
	 * 注册时验证了电话号，所以电话号一定不为空
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void update(User user) {
		String sql = "update userInfo set email=?,gender=?,IDNumber=?,userName=? where phoneNumber=?";
		try {
			qr.update(sql, user.getEmail(), user.getGender(),
					user.getIDNumber(), user.getUserName(),
					user.getPhoneNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据电话号获取数据库中已经存在的用户信息
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User getUserInfo(User user) {
		User userInfo, tmpUser;
		tmpUser = new User();
		userInfo = new User();
		// 通过微信号查找电话号
		String sql = "select * from userInfo where phoneNumber=?";
		try {
			userInfo = qr.query(sql, new BeanHandler<User>(User.class),
					user.getPhoneNumber());
			sql = "select * from user where phoneNumber=?";
			tmpUser = qr.query(sql, new BeanHandler<User>(User.class),
					user.getPhoneNumber());
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			userInfo.setPassword(tmpUser.getPassword());
		} catch (NullPointerException e) {
			System.out.println("用户没有设置密码");
		}
		if(userInfo.getUserName().equalsIgnoreCase("undefined")){
			userInfo.setUserName("");
		}
		return userInfo;
	}

	/**
	 * 设置密码
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public void setPassword(User user) {
		System.out.println("在uerdao中的信息" + user.getPassword()
				+ user.getPhoneNumber());
		String sql = "update user set password=? where phoneNumber=?";
		try {
			qr.update(sql, user.getPassword(), user.getPhoneNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("设置密码完成");
	}

	public void deleteUser(User user) {
		System.out.print("userDao删除用户信息");
		String sql = "delete from user where phoneNumber=?";
		try {
			qr.update(sql, user.getPhoneNumber());
			sql = "delete from userInfo where phoneNumber=?";
			qr.update(sql, user.getPhoneNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("设置密码完成");

	}

	/**
	 * 获取学员信息总览
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public List<User> getPandect() {
		List<User> al = new ArrayList<User>();
		String sql = "select * from userInfo ";
		try {
			al = qr.query(sql, new BeanListHandler<User>(User.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "select * from user where phoneNumber=?";
		return al;
	}

	/**
	 * 获取学员课程
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public List<Course> getMyCourse(User user) {
		Course course;
		System.out.println("userDao中getMycourse的user信息:    " + user.toString());
		System.out.print("学员课程信息:    ");
		List<Course> cl = new ArrayList<Course>();
		String sql = "select * from course";
		try {
			cl = qr.query(sql, new BeanListHandler<Course>(Course.class));
			Iterator<Course> ic = cl.iterator();
			while (ic.hasNext()) {
				course = ic.next();
				String studPhone;
				try {
					studPhone = course.getStudPhone();
					if (!studPhone.contains(user.getPhoneNumber())) {
						ic.remove();
					}
				} catch (NullPointerException e) {
					System.out.println("课程用户列表为空");
					ic.remove();
				}
				
				course.setStudPhone("");
				System.out.print(course.getCourseName() + "   ");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		;
		return cl;
	}

	/**
	 * 管理员登陆
	 * 
	 * @param userName
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User adLogin(String phoneNumber, String password) {
		User user = new User();
		try {
			String sql = "select * from admin where adminName=? && adminPass=?";
			user = qr.query(sql, new BeanHandler<User>(User.class),
					phoneNumber, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("搜到的admin" + user);
		return user;
	}

}
