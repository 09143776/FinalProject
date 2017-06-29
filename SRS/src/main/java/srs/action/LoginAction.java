package srs.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import srs.model.Person;
import srs.model.Professor;
import srs.model.Student;
import srs.service.StudentService;
import srs.service.ProfessorService;

public class LoginAction extends ActionSupport {
	private StudentService studentService = new StudentService();
	private ProfessorService teacherService = new ProfessorService();
	
	public String userName;
	public String password;
	public String identity;
	private InputStream inputStream;

	public InputStream getResult()
	{
		return inputStream;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String login() throws Exception{
		System.out.println("================================");
		Person person = null;
		if(identity != null){
			if("student".equals(identity)){
				person = studentService.login(userName, password);	
			}else if("teacher".equals(identity)){
				person = teacherService.login(userName, password);
			}
			if(person == null){
				inputStream = new ByteArrayInputStream("fail1".getBytes("UTF-8"));
			}else{
				HttpSession session = ServletActionContext.getRequest().getSession();
				session.setAttribute("user", person);
				if(person instanceof Student){
					inputStream = new ByteArrayInputStream("student".getBytes("UTF-8"));
				}else if((person instanceof Professor)){
					inputStream = new ByteArrayInputStream("teacher".getBytes("UTF-8"));
				}
			}
		}else{
			inputStream = new ByteArrayInputStream("fail2".getBytes("UTF-8"));
		}	
		return SUCCESS;
	}

}
