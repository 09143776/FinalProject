package srs.dao;

import java.util.ArrayList;

import srs.model.Section;
import srs.model.Student;

public interface StudentDao  extends BaseDao{
	
	public Student selectByLoginnameAndPassword(String loginname, String password);
	public Student findBySsn(String ssn);
	public ArrayList<Section> getAttends(String ssn);
}
