package srs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import srs.dao.DaoFactory;
import srs.dao.ProfessorDao;
import srs.model.Course;
import srs.model.Professor;

public class ProfessorService {
	private ProfessorDao professorDao;
	private HashMap<String, Professor> professors;
	
	public ProfessorService() {
		super();
		this.professorDao = (ProfessorDao)DaoFactory.createReleventDao("ProfessorDao");
		this.professors = professorDao.findAllProfessors();
	}

	public JSONArray getAll(String dept, String name){
		if(professors.isEmpty()){
			professors = professorDao.findAllProfessors();
		}	
		return toJson(search(dept, name));	
	}
	
	public Collection<Professor> search(String dept, String name){
		if(dept!=null && name!=null){
			List<Professor> teas = new ArrayList<Professor>();
			for(Professor professor: professors.values()){
				if((professor.getDepartment().equalsIgnoreCase(dept) || dept.trim().length()<0) && (professor.getName().equalsIgnoreCase(name) || name.trim().length()==0)){
					teas.add(professor);
				}
			}
			return teas;
		}else{
			return professors.values();
		}	
	}
	
	public Professor login(String userName, String password){
		return professorDao.selectByLoginnameAndPassword(userName, password);
	}
	
	public JSONArray toJson(Collection col){
		JSONArray jsonArray = new JSONArray();
		JSONObject jo = null;
		Professor professor = null;
		Iterator<Professor> it = col.iterator();
		while(it.hasNext()){
			professor = it.next();
			jo = new JSONObject();
			jo.put("ssn", professor.getSsn());
			jo.put("name", professor.getName());
			jo.put("title", professor.getTitle());
			jo.put("dept", professor.getDepartment());
			jsonArray.put(jo);
		}
		return jsonArray;
	}
	
	public void addTea(Professor tea){
		professorDao.saveTeacher(tea);
		professors.put(tea.getSsn(), tea);
	}
	
	public void deleteTea(String ssn){
		professorDao.deleteTeacher(ssn);
		professors.remove(ssn);
	}
	
	public void updateTeacher(Professor tea){
		professorDao.updateTeacher(tea);
		professors.put(tea.getSsn(), tea);
	}
}
