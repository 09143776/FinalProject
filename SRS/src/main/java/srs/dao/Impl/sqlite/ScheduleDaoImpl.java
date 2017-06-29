package srs.dao.Impl.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import srs.dao.DaoFactory;
import srs.dao.ScheduleDao;
import srs.dao.SectionDao;
import srs.dbutil.DbUtil;
import srs.model.Course;
import srs.model.ScheduleOfClasses;
import srs.model.Section;

public class ScheduleDaoImpl implements ScheduleDao {
	private SectionDao sectionDao = (SectionDao)DaoFactory.createReleventDao("SectionDao");
	@Override
	public HashMap<String, Section> getScheduleOfClass(String semester) {
		String sql = "select sc.* from scheduleOfClasses soc left join sectionClass sc on soc.sectionNo=sc.sectionNo where soc.semester=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{semester});
		ResultSet rs2 = null;
		Section section = null;
		Course course = null;
		HashMap<String, Section> sections = new HashMap<String, Section>();
		try {
			while(rs!=null && rs.next()){
				section = new Section(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(9), rs.getString(8));
				sql = "select c.* from sectionClass sc left join course c on sc.courseNo=c.courseNo where sectionNo=?;";
				rs2 = DbUtil.executeQuery(sql, new Object[]{section.getSectionNo()});
				while(rs2!=null && rs2.next()){
					course = new Course(rs2.getString(1), rs2.getString(2), rs2.getDouble(3));
				}
				section.setRepresentedCourse(course);
				section.setFullSectionNo(rs.getString(6)+"-"+rs.getInt(1));
				section.setEnrolledStudents(sectionDao.enrolledStudent(String.valueOf(rs.getInt(1))));
				sections.put(section.getFullSectionNo(), section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sections;
	}

	@Override
	public void addToSchedule(String sectionNo, String semester) {
		String sql = "insert into scheduleOfClasses(semester,sectionNo) values(?,?);";
		DbUtil.executeUpdate(sql, new Object[]{semester, sectionNo});
	}
}
