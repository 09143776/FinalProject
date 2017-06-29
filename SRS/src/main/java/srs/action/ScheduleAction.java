package srs.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;
import srs.service.ScheduleOfClassesService;

public class ScheduleAction extends ActionSupport {
	private ScheduleOfClassesService scheduleService = new ScheduleOfClassesService();
	private String semester;
	private String sectionNo;	
	private InputStream inputStream;

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	
	public InputStream getResult()
	{
		return inputStream;
	}
	
	static{
		HttpServletResponse response=ServletActionContext.getResponse();  
        response.setContentType("text/html");
        response.setContentType("text/plain; charset=utf-8");
	}
	
	public String addToSchedule(){
		scheduleService = new ScheduleOfClassesService(semester);
		scheduleService.addToSchedule(sectionNo, semester);
		return SUCCESS;
	}
	
	public String getSectionsBySemester(){
		scheduleService = new ScheduleOfClassesService(semester);
        JSONArray jsonArray = scheduleService.getSectionsBySemester(semester);
		try {
			inputStream =new ByteArrayInputStream(jsonArray.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
