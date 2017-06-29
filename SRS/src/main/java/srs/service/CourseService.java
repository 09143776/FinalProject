package srs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import srs.dao.CourseDao;
import srs.dao.DaoFactory;
import srs.dao.ProfessorDao;
import srs.dao.StudentDao;
import srs.model.Course;
import srs.model.CourseCatalog;
import srs.model.EnrollmentStatus;
import srs.model.Professor;
import srs.model.Section;
import srs.model.Student;
import srs.model.Transcript;
import srs.model.TranscriptEntry;
import srs.specification.SectionIsFull;
import srs.specification.SectionIsPrevEnrolled;
import srs.specification.SectionPrereqIsSatisfied;

public class CourseService {
	
	private static CourseCatalog  courses;
	private CourseDao courseDao = (CourseDao)DaoFactory.createReleventDao("CourseDao");
	private HashMap<String, Course> courseMap = new HashMap<String, Course>();
	private HashMap<Integer, Section> sectionMap = new HashMap<Integer, Section>();
	private StudentDao studentDao = (StudentDao) DaoFactory.createReleventDao("StudentDao");
	private HashMap<String, Student> studentMap = new HashMap<String, Student>();
	
	private List<Course> toList(HashMap<String, Course> courseMap){
		List<Course> courseList = new ArrayList<Course>();
		courseMap = courses.getCourses();
		for(String courseNo : courseMap.keySet()){
			courseList.add(courseMap.get(courseNo));
		}
		return courseList;
	}
	
	
	public CourseService(){
		courses = new CourseCatalog(courseDao.findAll());
		for(Course course: courseDao.findAll().values()){
			if(course.getPreCoursesNo().length()>0){
				String[] preCoursesNo = course.getPreCoursesNo().split(",");
				for(String str: preCoursesNo){
					course.addPrerequisite(courses.findCourse(str));
				}
				courses.addCourse(course);
			}
		}	
	}

	public CourseCatalog getCourseCatalog() {
		return courses;
	}
	
	public Course findCourse(String courseNo){
	   return courses.findCourse(courseNo);
	}
	
	public JSONArray courseByPage(int page, int rows, String courseNo, String courseName){
		int start = (page-1)*rows;
		int end = start+rows;	
		List<Course> courseList = (List<Course>) search(courseNo, courseName);
		int lastIndex = end>courseList.size()?courseList.size():end;
		courseList = courseList.subList(start, lastIndex);
		System.out.println("======"+courseList.toString());
		return toJson(courseList);
	}
	
	public Collection<Course> search(String courseNo, String courseName){
		List<Course> courseList = new ArrayList<Course>();
		if(courseNo!=null && courseName!=null){
			for(Course course: toList(courses.getCourses())){
				if((course.getCourseNo().equalsIgnoreCase(courseNo) || courseNo.trim().length()<0) && (course.getCourseName().equalsIgnoreCase(courseName) || courseName.trim().length()==0)){
					courseList.add(course);
				}
			}
			return courseList;
		}else{
			return toList(courses.getCourses());
		}	
	}
	
	public void addCourse(Course course){
		courseDao.addCourse(course);
		courses.addCourse(course);
	}
	
	
	public void updateCourse(Course course){
		String newPreCoursesNo = (course.getPreCoursesNo()!=null&&course.getPreCoursesNo().length()>0) ? course.getPreCoursesNo().trim() : "";
		Course oldCourse = courses.findCourse(course.getCourseNo());
		String oldPreCoursesNo = ( oldCourse.getPreCoursesNo()!=null&&oldCourse.getPreCoursesNo().length()>0) ? oldCourse.getPreCoursesNo().trim() : "";;
		Boolean bool = newPreCoursesNo.equals(oldPreCoursesNo);
		if(!bool){
			if(newPreCoursesNo.endsWith(",")) newPreCoursesNo.substring(0, newPreCoursesNo.length()-1);
			if(newPreCoursesNo.length() > 0){			
				String[] preNewCourses = newPreCoursesNo.split(",");
				for(String pre: preNewCourses){
					course.addPrerequisite(courses.findCourse(pre));
				}
			}
		}
		courseDao.updateCourse(course, bool);
		courses.addCourse(course);
	}
	
	public void deleteCourse(String courseNo){
		courseDao.deleteCourse(courseNo);	
		courses.getCourses().remove(courseNo);
	}
	
	public int findCount(){
		return courses.getCourses().size();
		//return courseDao.findCount();
	}
	
	
	public void allTheSection(String courseNo){
		List<Section> sections = courseDao.allSection(courseNo);
		Course course = courses.getCourses().get(courseNo);
		course.setOfferedAsSection((ArrayList<Section>) sections);
		for(Section section: sections){
			section.setRepresentedCourse(course);
		}
	}
	
	//添加section,即开设新的课程
	public void saveSection(String courseNo,Section section){
		courseDao.saveSection(section, courseNo);
		courses.getCourses().get(courseNo).addSection(section);
	}
	
	//删除section
	public void deleteSection(String courseNo, String sectionNo){
		courseDao.deleteSection(sectionNo);
		courses.getCourses().get(courseNo).removeSection(sectionNo);
	}
	
	//section分页实现
	public JSONArray allSection(String courseNo, int page, int rows){
		List<Section> sections = courses.getCourses().get(courseNo).getOfferedAsSection();
		if(sections.isEmpty()){
			allTheSection(courseNo);
			sections = courses.getCourses().get(courseNo).getOfferedAsSection();
		}else{
			int start = (page-1)*rows;
			int end = start+rows;	
			int lastIndex = end>sections.size()?sections.size():end;
			sections = sections.subList(start, lastIndex);						
		}
		return toJson2(sections);
	}
	
	//获取在一特定课堂下开设的section的数量
	public int findSectionCount(String courseNo){
		List<Section> sections = courses.getCourses().get(courseNo).getOfferedAsSection();
		return sections.size();
	}
	
	//选课代码
	public EnrollmentStatus attend(String stuNo, String fullNo){
		String[] str = fullNo.trim().split("-");
		Student stu = studentDao.findBySsn(stuNo);
		Section section = courseDao.query(str[1]);
		section.setRepresentedCourse(courses.findCourse(section.getRepresentedCourse().getCourseNo()));
		section.addSpecification(new SectionIsFull(section));
		section.addSpecification(new SectionIsPrevEnrolled(section));
		section.addSpecification(new SectionPrereqIsSatisfied(section));
		studentMap.put(stu.getSsn(), stu);
		sectionMap.put(section.getSectionNo(), section);
		EnrollmentStatus st = section.enroll(stu);
		if(st==EnrollmentStatus.success){
			courseDao.attend(str[1], stuNo, 1);
		}else if(st==EnrollmentStatus.secFull){
			courseDao.attend(str[1], stuNo, 0);
		}
		return st;
	}
	
	//退课代码
	public void unattend(String stuNo, String sectionNo){
		Student stu = studentMap.get(stuNo);
		if(stu==null){
			stu = studentDao.findBySsn(stuNo);
			studentMap.put(stu.getSsn(), stu);
		}
		Section section = sectionMap.get(sectionNo);
		if(section==null){
			section = courseDao.query(sectionNo);
			sectionMap.put(section.getSectionNo(), section);
		}
		courseDao.drop(stuNo, sectionNo);
		stu = section.drop(stu);
		if(stu!=null){
			courseDao.attend(sectionNo, stu.getSsn(), 1);
		}	
	}
	
	
	
	public JSONArray getErolledSections(String ssn){
		Student stu = studentMap.get(ssn);
		if(stu==null){
			stu = studentDao.findBySsn(ssn);
		}	
		return toJson2(stu.getAttends());
	}
	
	public JSONArray getGrades(String ssn){
		Student stu = studentMap.get(ssn);
		if(stu==null){
			stu = studentDao.findBySsn(ssn);
		}
		Transcript transcript = stu.getTranscript();
		Section section = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jo = null;
		for(TranscriptEntry transcriptEntry:transcript.getTranscriptEntries()){
			section = transcriptEntry.getSection();
			jo = new JSONObject();
			jo.put("fullNo", section.getFullSectionNo());
			jo.put("courseName", section.getRepresentedCourse().getCourseName());
			jo.put("credits", section.getRepresentedCourse().getCredits());
			jo.put("professorName", section.getProfessorName());
			jo.put("grade", transcriptEntry.getGrade());		
			jsonArray.put(jo);
		}
		return jsonArray;
	}
	
	public static JSONArray toJson(Collection col){
		JSONArray jsonArray = new JSONArray();
		JSONObject jo = null;
		Course course = null;
		Iterator<Course> it = col.iterator();
		while(it.hasNext()){
			course = it.next();
			jo = new JSONObject();
			jo.put("courseName", course.getCourseName());
			jo.put("courseNo", course.getCourseNo());
			jo.put("credits", course.getCredits());
			jo.put("preCourses", course.getPreCoursesNo());
			jsonArray.put(jo);
		}
		return jsonArray;
	}
	
	public static JSONArray toJson2(Collection col){
		JSONArray jsonArray = new JSONArray();
		JSONObject jo = null;
		Section section = null;
		Iterator<Section> it = col.iterator();
		while(it.hasNext()){
			section = it.next(); 
			jo = new JSONObject();
			jo.put("sectionNo", section.getSectionNo());
			jo.put("seatingCapacity", section.getSeatingCapacity());
			jo.put("dayOfWeek", section.getDayOfWeek());
			jo.put("room", section.getRoom());
			jo.put("presentCapacity", section.getTotalEnrollment());
			jo.put("timeOfDay", section.getTimeOfDay());
			jo.put("professorSsn", section.getProfessorSsn());
			jo.put("professorName", section.getProfessorName());
			jo.put("fullNo", section.getFullSectionNo());
			jo.put("crdits", section.getRepresentedCourse().getCredits());
			jo.put("courseName", section.getRepresentedCourse().getCourseName());
			jsonArray.put(jo);
		}
		return jsonArray;
	}
	
	
	

}
