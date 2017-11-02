package bean;

public class Course {
	
	private String courseId;
	private String courseName;
	private String teacherName;
	private String location;
	private String courseSeq;
	private String studPhone;

	@Override
	public String toString() {
		return "{  courseId : " + courseId + ",courseName : " + courseName
				+ ",teacherName : " + teacherName + ",location : " + location
				+ ",courseSeq : " + courseSeq + ",studPhone : " + studPhone + " }";
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getCourseSeq() {
		return courseSeq;
	}

	public void setCourseSeq(String courseSeq) {
		this.courseSeq = courseSeq;
	}

	public String getStudPhone() {
		return studPhone;
	}

	public void setStudPhone(String studPhone) {
		this.studPhone = studPhone;
	}

}
