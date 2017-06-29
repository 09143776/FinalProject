package srs.dao.Impl.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import srs.dao.CourseDao;
import srs.dao.DaoFactory;
import srs.dao.StudentDao;
import srs.dbutil.DbUtil;
import srs.model.Section;
import srs.model.Student;
import srs.model.Transcript;
import srs.model.TranscriptEntry;

public class StudentDaoImpl implements StudentDao {
	private CourseDao courseDao = (CourseDao)DaoFactory.createReleventDao("CourseDao");
	
	@Override
	public Student selectByLoginnameAndPassword(String loginname,String password){
		String sql = "select * from student where ssn=? and password=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{loginname, password});
		Student stu = null;
		try {
			while(rs.next()){
				stu = new Student();
				stu.setName(rs.getString(2));
				stu.setSsn(rs.getString(1));
				stu.setPassword(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return stu;
	}


	@Override
	public Student findBySsn(String ssn){
		String sql = "select s.*,t.* from student s "+ 
				"left join "+
				"(select a.* from attend a left join sectionClass sc on a.sectionNo=sc.sectionNo where a.isAttended=1) t  on s.ssn=t.studentSsn  where s.ssn=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{ssn});
		Student stu = null;
		Section section = new Section();
		ArrayList<Section> sectionList = new ArrayList<Section>();
		TranscriptEntry transcriptEntry = new TranscriptEntry();
		Transcript transcript = new Transcript();		
		ArrayList<TranscriptEntry> transcriptEntries = new ArrayList<TranscriptEntry>();
		transcript.setTranscriptEntries(transcriptEntries);
			try {
				while(rs!=null&&rs.next()){
					if(stu == null){
						stu = new Student();
						stu.setName(rs.getString(2));
						stu.setSsn(rs.getString(1));
						transcript.setStudentOwner(stu);
						stu.setTranscript(transcript);
					}		
					System.out.println(rs.getInt(7)+"========================="+rs.getString(8));
					section = courseDao.query(String.valueOf(rs.getInt(7)));
					if(rs.getString(8)!=null){
						section.postGrade(stu, rs.getString(8));
					}		
					sectionList.add(section);
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		stu.setAttends(sectionList);
		return stu;
	}

	@Override
	public ArrayList<Section> getAttends(String ssn) {
		String sql = "select sc.* from attend a left join sectionClass sc on a.sectionNo=sc.sectionNo where a.studentNo=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{ssn});
		ArrayList<Section> sectionList = new ArrayList<Section>();
		Section section = null;
		try {
			while(rs!=null&&rs.next()){
				section = new Section(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getString(9), rs.getString(8));
				sectionList.add(section);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sectionList;
	}

}
