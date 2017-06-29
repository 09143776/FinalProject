package srs.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DbUtil {
    private static String  dbName;
	 static {
		try {
			dbName= "jdbc:sqlite:db/srs.db";
		    Class.forName("org.sqlite.JDBC");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		Connection connection=null;
		try {
			connection = DriverManager.getConnection(dbName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	public static boolean executeUpdate(String sql,Object[] args){
		boolean sign=false;
		 Connection conn=null;
		 PreparedStatement pst=null;
		 try{
			 conn=DriverManager.getConnection(dbName);
			 pst=conn.prepareStatement(sql);
			 if(args!=null&&args.length>0){
				 for(int i=0;i<args.length;i++){
					 pst.setObject(i+1, args[i]);
				 }
			 }
			 int rows=pst.executeUpdate();
			 sign=rows>0?true:false;
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return sign;
	 }
	public static ResultSet executeQuery(String sql,Object[] args){
		Connection conn=null;
		 PreparedStatement pst=null;
		 ResultSet rs=null;
		 try{
			 conn=DriverManager.getConnection(dbName);
			 pst=conn.prepareStatement(sql);
			 if(args!=null&&args.length>0){
				 for(int i=0;i<args.length;i++){
					 pst.setObject(i+1, args[i]);
				 }
			 }
			 rs=pst.executeQuery();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 return rs;
	 }
	public static void closeAll(ResultSet rs,Statement st,Connection conn){
		 try{
			 if(rs!=null) rs.close();
			 if(st!=null) st.close();
			 if(conn!=null) conn.close();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	 }
	
}

