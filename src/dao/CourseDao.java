package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import bean.ClassesBean;
import bean.Course;
import cn.itcast.jdbc.TxQueryRunner;

public class CourseDao {
	private QueryRunner qr = new TxQueryRunner();
	private Course course;
	/**
	 * 按照课程id查询课程信息
	 * @param course
	 * @return
	 */
	public Course queryCourse(Course course){
		String sql = "select * from course where courseId=?";
		try {
			course = qr.query(sql, new BeanHandler<Course>(Course.class),course.getCourseId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return course;
	}
	
	/**
	 * 添加班级 
	 * @param classes 班级名字，如 1班
	 * @return
	 */
	public boolean addClass(String classes){
		String sql = "insert into className set classes=?";
		boolean flag = false;
		try {
			int i = qr.update(sql, classes);
			if(i!=0){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取班级信息总览
	 * @return
	 */
	public List<ClassesBean> getClassList(){
		String sql = "select * from className";
		List<ClassesBean> lc = new ArrayList<ClassesBean>();
		try {
			lc = qr.query(sql, new BeanListHandler<ClassesBean>(ClassesBean.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lc;
	}
	
	/**
	 * 获取课程信息总览
	 * @return
	 */
	public List<Course> getCourseList(){
		String sql = "select * from course";
		List<Course> lc = new ArrayList<Course>();
		try {
			lc = qr.query(sql, new BeanListHandler<Course>(Course.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lc;
	}
	
	/**
	 * 添加课程
	 * @return
	 */
	public boolean addCourse(Course course){
		
		int i=0;
		boolean flag = false;
		try {
			if(course.getCourseId()==null){
				System.out.println("执行插入课程");
				String sql = "insert into course set courseName=?,teacherName=?,location=?,courseSeq=?";
				i = qr.update(sql,course.getCourseName(),course.getTeacherName(),course.getLocation(),
						course.getCourseSeq());
			}else{
				System.out.println("执行修改课程");
				String sql = "update course set courseName=?,teacherName=?,location=?,courseSeq=? where courseId=?";
				i = qr.update(sql,course.getCourseName(),course.getTeacherName(),course.getLocation(),
						course.getCourseSeq(),course.getCourseId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(i!=0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 添加课程学员
	 * @return
	 */
	public boolean addStuByCor(Course course){
		
		int changedItem=0;
		boolean flag = false;
		List<Course> al = new ArrayList<Course>();
		String studPhone;
		try {
				System.out.println("添加课程学员");
				String sql = "select studPhone from course where courseId=?";
				al = qr.query(sql,new BeanListHandler<Course>(Course.class),course.getCourseId());
				studPhone = al.get(0).getStudPhone();
				String[] phoneString = course.getStudPhone().trim().split(";");
				try {
					if(studPhone.length()<1){
						studPhone = "";
					}
				} catch (NullPointerException e) {
					System.out.println("默认字段studPhone为空");
					studPhone = "";
				}
				for(int j=0;j<phoneString.length;j++){
					if(studPhone.contains(phoneString[j])){
						continue;
					}
					studPhone += ";"+phoneString[j];
				}
				if(studPhone.startsWith(";")){
					studPhone = (String) studPhone.subSequence(1, studPhone.length());
				}
				sql = "update course set studPhone=? where courseId=?";
				changedItem = qr.update(sql,studPhone.toString(),course.getCourseId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(changedItem!=0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除课程学员
	 * @return
	 */
	public boolean delStuByCor(Course course){
		
		int changedItem=0;
		int startInd;
		boolean flag = false;
		List<Course> al = new ArrayList<Course>();
		StringBuilder studPhoneBuilder = new StringBuilder();
		String studPhoneString;
		try {
				System.out.println("删除课程学员");
				String sql = "select studPhone from course where courseId=?";
				al = qr.query(sql,new BeanListHandler<Course>(Course.class),course.getCourseId());
				studPhoneString = al.get(0).getStudPhone();
				try {
					if(studPhoneString.length()<1){
						studPhoneString = "";
					}
				} catch (NullPointerException e) {
					System.out.println("默认字段studPhone为空");
					studPhoneString = "";
				}
				studPhoneBuilder.append(studPhoneString.toCharArray());
				String[] phoneString = course.getStudPhone().trim().split(";");
				System.out.println("库中的电话号："+studPhoneString);
				System.out.println("收到的电话号："+course.getStudPhone().trim());
				String[] delPhone = course.getStudPhone().trim().split(";");
				for(int j=0;j<delPhone.length;j++){
					startInd = studPhoneBuilder.indexOf(delPhone[j]);
					if(startInd != -1){
						System.out.println("删除的字符范围："+startInd+"  -  "+(startInd+phoneString[j].length()));
						studPhoneBuilder.delete(startInd-1, (startInd+delPhone[j].length()));
					}
				}
				System.out.println("修改后的电话号："+studPhoneBuilder.toString());
				sql = "update course set studPhone=? where courseId=?";
				changedItem = qr.update(sql,studPhoneBuilder.toString(),course.getCourseId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(changedItem!=0){
			flag = true;
		}
		return flag;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}
