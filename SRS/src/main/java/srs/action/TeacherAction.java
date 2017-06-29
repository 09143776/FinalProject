package srs.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.JSONArray;

import com.opensymphony.xwork2.ActionSupport;

import srs.model.Professor;
import srs.service.ProfessorService;

public class TeacherAction extends ActionSupport {
	private static ProfessorService professorService = new ProfessorService();
	private String ssn;
	private String title;
	private String dept;
	private String name;
	private String teaSsn;
	
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeaSsn() {
		return teaSsn;
	}

	public void setTeaSsn(String teaSsn) {
		this.teaSsn = teaSsn;
	}

	static{
		HttpServletResponse response=ServletActionContext.getResponse();  
        response.setContentType("text/html");
        response.setContentType("text/plain; charset=utf-8");
	}
	
	public String getAll(){
		PrintWriter out;
		try {
			out = ServletActionContext.getResponse().getWriter();
			JSONArray jsonArray = professorService.getAll(dept, name);
			out.print(jsonArray.toString());
			out.flush(); 
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
		return SUCCESS;
	}
	
	public String saveTeacher(){
		Professor tea = null;
		if(teaSsn.length()>0){
			tea = new Professor(name, teaSsn, title, dept);
			professorService.updateTeacher(tea);
		}else{
			tea = new Professor(name, ssn, title, dept);
			professorService.addTea(tea);
		}	
		return SUCCESS;
	}
	
	public String deleteTeacher(){
		professorService.deleteTea(ssn);
		return SUCCESS;
	}
}
