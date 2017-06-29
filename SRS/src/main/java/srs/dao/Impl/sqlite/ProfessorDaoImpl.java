package srs.dao.Impl.sqlite;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import srs.dao.ProfessorDao;
import srs.dbutil.DbUtil;
import srs.model.Professor;
import srs.model.Student;

public class ProfessorDaoImpl implements ProfessorDao {
	@Override
	public Professor selectByLoginnameAndPassword(String loginname,String password){
		String sql = "select * from teacher where ssn=? and password=?;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{loginname, password});
		Professor professor = null;
		try {
			while(rs!=null && rs.next()){
				professor = new Professor(rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(5));
				professor.setPassword(rs.getString(3));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return professor;
	};

	@Override
	public HashMap<String, Professor> findAllProfessors() {
		String sql = "select * from teacher order by ssn asc;";
		ResultSet rs = DbUtil.executeQuery(sql, new Object[]{});
		HashMap<String, Professor> professors = new HashMap<String, Professor>();
		Professor professor = null;
		try {
			while(rs!=null && rs.next()){
				professor = new Professor(rs.getString(2), rs.getString(1), rs.getString(4), rs.getString(5));
				professors.put(professor.getSsn(), professor);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return professors;
	}

	@Override
	public void saveTeacher(Professor tea) {
		String sql = "insert into teacher(ssn, name, title, dept) values(?,?,?,?);";
		DbUtil.executeUpdate(sql, new Object[]{tea.getSsn(), tea.getName(), tea.getTitle(), tea.getDepartment()});		
	}

	@Override
	public void deleteTeacher(String ssn) {
		String sql = "delete from teacher where ssn=?;";
		DbUtil.executeUpdate(sql, new Object[]{ssn});		
	}

	@Override
	public void updateTeacher(Professor tea) {
		String sql = "update teacher set name=?,title=?,dept=? where ssn=?;";
		DbUtil.executeUpdate(sql, new Object[]{ tea.getName(), tea.getTitle(), tea.getDepartment(), tea.getSsn()});
		
	}
}
