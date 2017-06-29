package srs.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;
import com.opensymphony.xwork2.ActionSupport;
import srs.model.Course;
import srs.model.EnrollmentStatus;
import srs.model.Section;
import srs.service.CourseService;

public class CourseAction extends ActionSupport {
	private CourseService courseService = new CourseService();
	private String page;
	private String rows;
	private Course course;
	private String courseNo;
	private String courseName;
	private String rowNo;
	private Section section;
	private String sectionNo;
	private InputStream inputStream;
	private String courseNo2;
	private String room;
	private String seatingCapacity;
	private String professorSsn;
	private String dayOfWeek;
	private String timeOfDay;
	private String stuNo;
	private String fullNo;

	public InputStream getResult()
	{
		return inputStream;
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}
	
	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}

	public String getCourseNo2() {
		return courseNo2;
	}

	public void setCourseNo2(String courseNo2) {
		this.courseNo2 = courseNo2;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(String seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getProfessorSsn() {
		return professorSsn;
	}

	public void setProfessorSsn(String professorSsn) {
		this.professorSsn = professorSsn;
	}

	public String getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}



	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}



	public String getFullNo() {
		return fullNo;
	}

	public void setFullNo(String fullNo) {
		this.fullNo = fullNo;
	}



	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}



	static{
		HttpServletResponse response=ServletActionContext.getResponse();  
        response.setContentType("text/html");
        response.setContentType("text/plain; charset=utf-8");
	}
	
	public String allCourses(){
		int pageNum=Integer.parseInt((page ==null || page=="0")? "1":page);
    	int number=Integer.parseInt((rows == null|| rows=="0")? "10":rows);
        try {
        	PrintWriter out = ServletActionContext.getResponse().getWriter();
			JSONArray jsonArray = courseService.courseByPage(pageNum, number, courseNo, courseName);
			System.out.println(jsonArray); 
			String str="{\"total\":"+courseService.findCount()+","+"\"rows\":"+new String(jsonArray.toString().getBytes("ISO8859-1"), "UTF-8")+"}";
			System.out.println(str);
			out.print(str);
			out.flush(); 
			out.close(); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return SUCCESS;
	}
	
	public String saveCourse(){
		if(rowNo.trim().length()>0){
			course.setCourseNo(rowNo);
			courseService.updateCourse(course);
		}else{
			courseService.addCourse(course);
		}		
		return SUCCESS;
	}
	
	public String allSection(){
		int pageNum=Integer.parseInt((page ==null || page=="0")? "1":page);
    	int number=Integer.parseInt((rows == null|| rows=="0")? "10":rows);
    	try {
        	PrintWriter out = ServletActionContext.getResponse().getWriter();
			JSONArray jsonArray = courseService.allSection(courseNo, pageNum, number);
			System.out.println(jsonArray); 
			String str="{\"total\":"+courseService.findSectionCount(courseNo)+","+"\"rows\":"+new String(jsonArray.toString().getBytes("ISO8859-1"), "UTF-8")+"}";
			System.out.println(str);
			out.print(str);
			out.flush(); 
			out.close(); 
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		return SUCCESS;
	}
	
	public String deleteCourse(){
		courseService.deleteCourse(courseNo);
		return SUCCESS;
	}
	
	public String saveSection(){
		section = new Section();
		section.setDayOfWeek(dayOfWeek);
		section.setProfessorSsn(professorSsn);
		section.setSeatingCapacity(Integer.parseInt(seatingCapacity));
		section.setRoom(room);
		section.setTimeOfDay(timeOfDay);
		courseService.saveSection(courseNo2, section);
		return SUCCESS;
	}
	
	public String deleteSection(){
		courseService.deleteSection(courseNo, sectionNo);
		return SUCCESS;
	}
	
	public String attend() throws Exception{
		String[] strs = fullNo.split(",");
		EnrollmentStatus sta = null;
		for(String s: strs){
			sta = courseService.attend(stuNo, s);
			if(sta!=EnrollmentStatus.success){
				inputStream = new ByteArrayInputStream(sta.value().getBytes("UTF-8"));
				return SUCCESS;
			}
		}
		try {
			inputStream = new ByteArrayInputStream(sta.value().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getErolledSections(){
		JSONArray json = courseService.getErolledSections(stuNo);
		try {
			inputStream = new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getGrades(){
		JSONArray json = courseService.getGrades(stuNo);
		try {
			inputStream = new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String drop(){
		courseService.unattend(stuNo, sectionNo);
		return SUCCESS;
	}
}
