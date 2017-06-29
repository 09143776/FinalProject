package srs.dao.Impl.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import srs.dao.CourseDao;
import srs.dbutil.DbUtil;
import srs.model.Course;
import srs.model.Section;
import srs.model.Student;
import srs.model.TranscriptEntry;
import srs.model.WaitList;

public class CourseDaoImpl implements CourseDao {
	
	@Override
	public HashMap<String, Course> findAll() {
		String sql = "select * from course order by courseNo asc;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{});
		Course course = null;
		HashMap<String, Course> courses = new HashMap<String, Course>();
		try {
			while(rs!=null && rs.next()){
				course = new Course(rs.getString(1), rs.getString(2), rs.getDouble(3));
				sql = "select c.courseNo from preCourse pc left join course c on pc.preCourseNo=c.courseNo where pc.courseNo=?;";
				ResultSet rs2 = DbUtil.executeQuery(sql, new Object[]{course.getCourseNo()});
				String preCourses = "";
				while(rs2!=null && rs2.next()){
					preCourses += rs2.getString(1) + ",";
				}
				course.setPreCoursesNo(preCourses);
				courses.put(course.getCourseNo(), course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}
	

	@Override
	public List<Course> courseByPage(int page, int rows) {
		int beginNum = (page - 1) * rows;
		String sql = "select * from course order by courseNo asc LIMIT " + rows +  " OFFSET " + beginNum + ";";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{});
		Course course = null;
		List<Course> courses = new ArrayList<Course>();
		
		try {
			while(rs!=null && rs.next()){
				course = new Course(rs.getString(1), rs.getString(2), rs.getDouble(3));
				sql = "select c.courseNo from preCourse pc left join course c on pc.preCourseNo=c.courseNo where pc.courseNo=?;";
				ResultSet rs2 = DbUtil.executeQuery(sql, new Object[]{course.getCourseNo()});
				String preCourses = "";
				while(rs2!=null && rs2.next()){
					preCourses += rs2.getString(1) + ",";
				}
				course.setPreCoursesNo(preCourses);
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return courses;
	}

	@Override
	public void addCourse(Course course) {
		String sql = "insert into course(courseNo,courseName,credits) values(?,?,?);";
		DbUtil.executeUpdate(sql, new Object[]{course.getCourseNo(), course.getCourseName(), course.getCredits()});
		updatePrecourse(course);
	}

	@Override
	public void updateCourse(Course course, Boolean bool) {
		String sql = "update course set courseName=?,credits=? where courseNo=?;";
		DbUtil.executeUpdate(sql, new Object[]{course.getCourseName(),course.getCredits(), course.getCourseNo()});
		if(!bool){
			sql ="delete from preCourse where courseNo=?;";
			DbUtil.executeUpdate(sql, new Object[]{course.getCourseNo()});
			updatePrecourse(course);
		}
	}
	
	@Override
	public void updatePrecourse(Course course){
		String preCourse = course.getPreCoursesNo().trim();
		if(preCourse.endsWith(",")) preCourse.substring(0, preCourse.length()-1);
		if(preCourse.length() > 0){			
			String[] preCourses = preCourse.split(",");
			for(String pre: preCourses){
				String sql = "insert into preCourse(preCourseNo,courseNo) values(?,?);";
				DbUtil.executeUpdate(sql, new Object[]{pre, course.getCourseNo()});
			}	
		}	
	}
	
	@Override
	public void deleteCourse(String courseNo) {
		String sql = "delete from course where courseNo=?;";
		DbUtil.executeUpdate(sql, new Object[]{courseNo});
	}

	@Override
	public int findCount() {
		String sql = "select count(*) from course;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{});
		int num = 0;
		try {
			while(rs.next()){
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	@Override
	public List<Section> allSection(String courseNo, int... intArray) {
		if(intArray.length > 0){
			return allSections(courseNo, intArray[0], intArray[1]);
		}else{
			return allSections(courseNo);
		}
	}
	
	public List<Section> allSections(String courseNo, int page, int rows){
		int beginNum = (page - 1) * rows;
		String sql = "select * from sectionClass where courseNo=? order by sectionNo asc LIMIT " + rows +  " OFFSET " + beginNum + ";";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{courseNo});
		List<Section> sections = new ArrayList<Section>();
		Section section = null;
		try {
			while(rs!=null && rs.next()){
				section = new Section(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(9), rs.getString(8));
				sections.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}
	
	public List<Section> allSections(String courseNo){
		String sql = "select * from sectionClass where courseNo=? order by sectionNo asc;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{courseNo});
		List<Section> sections = new ArrayList<Section>();
		Section section = null;
		try {
			while(rs!=null && rs.next()){
				section = query(rs.getString(1));	
				sections.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}
	
	

	@Override
	public int findSectionCount(String courseNo) {
		String sql = "select count(*) from sectionClass where courseNo=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{courseNo});
		int num = 0;
		try {
			while(rs!=null && rs.next()){
				num = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}


	@Override
	public void saveSection(Section section, String courseNo) {
		String sql = "select name from teacher where ssn=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{section.getProfessorSsn()});
		String teacherName = "";
		try {
			while(rs!=null && rs.next()){
				teacherName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "insert into sectionClass(dayOfWeek, timeOfDay, room, seatingCapacity, courseNo, teacherSsn, teacherName) values(?,?,?,?,?,?,?);";
		DbUtil.executeUpdate(sql, new Object[]{section.getDayOfWeek(), section.getTimeOfDay(), section.getRoom(), section.getSeatingCapacity(), courseNo, section.getProfessorSsn(), teacherName});
	}


	@Override
	public void deleteSection(String sectionNo) {
		String sql = "delete from sectionClass where sectionNo=?;";
		DbUtil.executeUpdate(sql, new Object[]{sectionNo});
	}


	@Override
	public Section query(String sectionNo) {
		String sql = "select sc.*,c.* from sectionClass sc left join course c on sc.courseNo=c.courseNo where sc.sectionNo=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{sectionNo});
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Section section = null;
		Course course = null;
		Student stu = null;
		HashMap<String,Student> enrolledStu = null;
		HashMap<Student, TranscriptEntry> assignedGrades = null;
		TranscriptEntry transcriptEntry = new TranscriptEntry();
		ArrayList<Student> waitingStu = null;
		ArrayList<Course> preCourses = null;
			try {
				while(rs!=null && rs.next()){
					section = new Section(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(9), rs.getString(8));
					course = new Course(rs.getString(10), rs.getString(11), rs.getDouble(12));
					sql = "select s.* from attend a left join student s on a.studentSsn=s.ssn where a.sectionNo=? and a.isAttended=1";
					rs2 = DbUtil.executeQuery(sql, new Object[]{sectionNo});
					enrolledStu = new HashMap<String,Student>();
					while(rs2!=null && rs2.next()){
						stu = new Student();
						stu.setName(rs2.getString(2));
						stu.setSsn(rs2.getString(1));
						enrolledStu.put(stu.getSsn(), stu);
					}
					section.setEnrolledStudents(enrolledStu);
					waitingStu = new ArrayList<Student>();
					assignedGrades = new HashMap<Student, TranscriptEntry>();
					sql = "select s.*,a.grade from attend a left join student s on a.studentSsn=s.ssn where a.sectionNo=? and a.isAttended=0";
					rs2 = DbUtil.executeQuery(sql, new Object[]{sectionNo});
					enrolledStu = new HashMap<String,Student>();
					while(rs2!=null && rs2.next()){
						stu = new Student();
						stu.setName(rs2.getString(2));
						stu.setSsn(rs2.getString(1));
						waitingStu.add(stu);
						if(rs2.getString(5)!=null){
							transcriptEntry = new TranscriptEntry(stu,rs2.getString(5),section);
							assignedGrades.put(stu, transcriptEntry);
						}
					}
					sql = "select c.courseNo from preCourse pc left join course c on pc.preCourseNo=c.courseNo where pc.courseNo=?;";
					rs3 = DbUtil.executeQuery(sql, new Object[]{course.getCourseNo()});
					String preCoursesNo = "";
					while(rs3!=null && rs3.next()){
						preCoursesNo += rs3.getString(1) + ",";
					}
					course.setPreCoursesNo(preCoursesNo);
					section.setRepresentedCourse(course);
					section.setWaitList(new WaitList(waitingStu));
					section.setAssignedGrades(assignedGrades);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		return section;
	}


	@Override
	public void attend(String sectionNo, String studentSsn, int i) {
		String sql = "insert into attend(studentSsn,sectionNo,isAttended) values(?,?,?);";
			DbUtil.executeUpdate(sql, new Object[]{studentSsn,sectionNo,i});	

	}


	@Override
	public void drop(String stuNo, String sectionNo) {
		String sql = "delete from attend where studentSsn=? and sectionNo=?;";
		DbUtil.executeUpdate(sql, new Object[]{stuNo, sectionNo});
	}
	


	


	

}
