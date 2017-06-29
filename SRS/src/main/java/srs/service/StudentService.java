package srs.service;

import srs.dao.DaoFactory;
import srs.dao.StudentDao;
import srs.model.Student;

public class StudentService {
	private StudentDao studentDao = (StudentDao) DaoFactory.createReleventDao("StudentDao");
	
	public Student login(String userName, String password){
		return studentDao.selectByLoginnameAndPassword(userName, password);
	}
	
}
