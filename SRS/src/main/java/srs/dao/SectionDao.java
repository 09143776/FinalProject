package srs.dao;

import java.util.HashMap;
import java.util.List;

import srs.model.Section;
import srs.model.Student;

public interface SectionDao extends BaseDao{
	public List<Section> findAll();
	public HashMap<String,Section> findBySemester(String semester);
	public HashMap<String,Student> enrolledStudent(String sectionNo);
}
