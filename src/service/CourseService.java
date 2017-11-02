package service;

import java.util.List;

import bean.ClassesBean;
import bean.Course;
import dao.CourseDao;

public class CourseService {
	Course myCourse;
	CourseDao courseDao = new CourseDao();
	
	
	/**
	 * 按照id查询某一个课程的详细信息
	 * @param course
	 * @return
	 */
	public Course queryCourse(Course course){
		myCourse = courseDao.queryCourse(course);
		return myCourse;
	}
	
	/**
	 * 给课程添加学员
	 * @param course
	 * @return
	 */
	public boolean addStuByCor(Course course){
		return courseDao.addStuByCor(course);
	}
	
	/**
	 * 给课程删除学员
	 * @param course
	 * @return
	 */
	public boolean delStuByCor(Course course){
		return courseDao.delStuByCor(course);
	}
	
	/**
	 * 获取班级名称列表
	 * @return
	 */
	public List<ClassesBean> getClassList(){
		return courseDao.getClassList();
	}
	
	/**
	 * 获取课程列表
	 * @return
	 */
	public List<Course> getCourseList(){
		return courseDao.getCourseList();
	}
	
	/**
	 * 获取课程列表
	 * @return
	 */
	public boolean addCourse(Course course){
		return courseDao.addCourse(course);
	}


	public Course getMyCourse() {
		return myCourse;
	}

	public void setMyCourse(Course myCourse) {
		this.myCourse = myCourse;
	}
	
	public boolean addClass(String classes){
		return courseDao.addClass(classes);
	}

}
