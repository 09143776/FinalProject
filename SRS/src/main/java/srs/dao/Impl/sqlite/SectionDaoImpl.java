package srs.dao.Impl.sqlite;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import srs.dao.DaoFactory;
import srs.dao.SectionDao;
import srs.dao.StudentDao;
import srs.dbutil.DbUtil;
import srs.model.Section;
import srs.model.Student;

public class SectionDaoImpl implements SectionDao{
	private StudentDao studentDao = (StudentDao) DaoFactory.createReleventDao("StudentDao");
	@Override
	public List<Section> findAll() {
		
		return null;
	}

	@Override
	public HashMap<String, Section> findBySemester(String semester) {
		return null;
	}

	@Override
	public HashMap<String,Student> enrolledStudent(String sectionNo) {
		String sql = "select a.studentSsn from attend a left join sectionClass sc on a.sectionNo=sc.sectionNo where a.sectionNo=?;";
		Student stu = null;
		HashMap<String,Student> stus = new HashMap<String,Student>();
		String ssn = null;
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{sectionNo});
		try {
			while(rs!=null&&rs.next()){
				ssn = rs.getString(1);
				if(ssn!=null){
					stu = studentDao.findBySsn(ssn);
					stus.put(stu.getSsn(), stu);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stus;
	}


}
