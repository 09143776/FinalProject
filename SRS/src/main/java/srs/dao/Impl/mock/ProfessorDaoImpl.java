package srs.dao.Impl.mock;

import java.util.HashMap;
import java.util.Map;

import srs.dao.ProfessorDao;
import srs.model.Professor;
import srs.model.Section;

public class ProfessorDaoImpl implements ProfessorDao {

	@Override
	public HashMap<String, Professor> findAllProfessors() {
		
		HashMap<String, Professor> professors = new HashMap<String, Professor>();
		
		Professor p1, p2, p3;
		p1 = new Professor("Jacquie Barker", "123-45-6789", "Adjunct Professor", "Information Technology");
		professors.put(p1.getSsn(), p1);
		p2 = new Professor("John Smith", "567-81-2345", "Full Professor", "Chemistry");
		professors.put(p2.getSsn(), p2);
		p3 = new Professor("Snidely Whiplash", "987-65-4321", "Full Professor", "Physical Education");
		professors.put(p3.getSsn(), p3);	
		return professors;
	}

	@Override
	public Professor selectByLoginnameAndPassword(String loginname, String password) {
		return null;
	}

	@Override
	public void saveTeacher(Professor tea) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTeacher(String ssn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTeacher(Professor tea) {
		// TODO Auto-generated method stub
		
	}

	

}
