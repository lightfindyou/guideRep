package action;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import service.CourseService;
import bean.ClassesBean;
import bean.Course;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class CourseAction extends ActionSupport implements ModelDriven<Course> {

	private static final long serialVersionUID = 2589883324269147117L;
	
	private Course course;
	private HttpServletResponse hsr;
	CourseService courseService = new CourseService();
	ByteArrayInputStream inputStream;
	
	@Override
	public Course getModel() {
		if(course == null){
			course = new Course();
		}
		return course;
	}
	
	@Override
	public String execute() throws Exception {
		hsr = ServletActionContext.getResponse();
		hsr.setCharacterEncoding("utf-8");
		course = courseService.queryCourse(course);
		inputStream = new ByteArrayInputStream(course.toString().getBytes("utf-8"));
		return SUCCESS;
	}
	
	/**
	 * 添加班级
	 * @param classes 班级名称
	 * @return
	 */
	public String addClass(){
		/*try {
			System.out.println("class信息：  "+course.getClasses());
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			courseService.addClass(course.getClasses());
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		return SUCCESS;
	}
	
	/**
	 * 获取班级名称列表
	 * @return
	 */
	public String getClassList(){
		List<ClassesBean> lc;
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			lc = courseService.getClassList();
			String classList = JSONArray.fromObject(lc).toString();
			System.out.println("班级信息列表：\r\n"+classList);
			inputStream = new ByteArrayInputStream(classList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 课程信息列表
	 * @return
	 */
	public String courseOverview(){
		List<Course> lc;
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			lc = courseService.getCourseList();
			String courseList = JSONArray.fromObject(lc).toString();
			System.out.println("课程信息列表：\r\n"+courseList);
			inputStream = new ByteArrayInputStream(courseList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 课程信息列表
	 * @return
	 */
	public String addCourse(){
		
		/*course.getClasses()
		course.getCourseName()
		course.get*/
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			if(courseService.addCourse(course)){
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 向课程添加同学
	 * @return
	 */
	public String addStuByCor(){
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			if(course.getCourseId().length()<1 || course.getStudPhone().length()<1)
				return SUCCESS;
			if(courseService.addStuByCor(course)){
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 删除课程学员
	 * @return
	 */
	public String delStuByCor(){
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			if(course.getCourseId().length()<1 || course.getStudPhone().length()<1)
				return SUCCESS;
			if(courseService.delStuByCor(course)){
				inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public HttpServletResponse getHsr() {
		return hsr;
	}

	public void setHsr(HttpServletResponse hsr) {
		this.hsr = hsr;
	}
}
