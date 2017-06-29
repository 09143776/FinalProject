package srs.dao;

import java.util.HashMap;

import srs.model.Professor;;

public interface ProfessorDao  extends BaseDao{
	public HashMap<String, Professor> findAllProfessors();
	public Professor selectByLoginnameAndPassword(String loginname,String password);
	public void saveTeacher(Professor tea);
	public void deleteTeacher(String ssn);
	public void updateTeacher(Professor tea);
}
