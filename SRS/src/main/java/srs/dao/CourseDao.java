package srs.dao;

import java.util.HashMap;
import java.util.List;

import srs.model.Course;
import srs.model.EnrollmentStatus;
import srs.model.Section;

public interface CourseDao extends BaseDao{
	
	public HashMap<String, Course> findAll();
	public List<Course> courseByPage(int page, int rows);
	public void addCourse(Course course);
	public void updateCourse(Course course, Boolean bool);
	public void deleteCourse(String courseNo);
	public int findCount();
	public void updatePrecourse(Course course);
	public List<Section> allSection(String courseNo, int... intArray);
	public int findSectionCount(String courseNo);
	public void saveSection(Section section, String courseNo);
	public void deleteSection(String sectionNo);
	public Section query(String sectionNo);
	public void attend(String sectionNo, String studentSsn, int i);
	public void drop(String stuNo, String sectionNo);
}
