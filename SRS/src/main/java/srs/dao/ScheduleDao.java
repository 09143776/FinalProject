package srs.dao;

import java.util.HashMap;

import srs.model.ScheduleOfClasses;
import srs.model.Section;

public interface ScheduleDao  extends BaseDao{
	
	public HashMap<String, Section> getScheduleOfClass(String semester);
	public void addToSchedule(String sectionNo, String semester);
	
}
