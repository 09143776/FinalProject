package srs.dao;

public class DaoFactory {
	private static String daoName = "srs.dao.Impl.mock";
//	private static String daoName = "srs.dao.Impl.sqlite";
	
	public static BaseDao createReleventDao(String daoClass) {
		BaseDao result = null;
		try {
			Object o = Class.forName(daoName + "." + daoClass + "Impl").newInstance();
			result = (BaseDao)o;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
