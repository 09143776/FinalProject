package srs.dao.Impl.mock;

import java.util.ArrayList;

import srs.dao.StudentDao;
import srs.model.Section;
import srs.model.Student;

public class StudentDaoImpl implements StudentDao {
	public Student selectByLoginnameAndPassword(String loginname,String password) {
		return null;
	}

	@Override
	public Student findBySsn(String ssn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Section> getAttends(String ssn) {
		// TODO Auto-generated method stub
		return null;
	}

}
