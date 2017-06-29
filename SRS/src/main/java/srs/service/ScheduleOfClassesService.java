package srs.service;

import java.util.HashMap;
import org.json.JSONArray;
import srs.dao.CourseDao;
import srs.dao.DaoFactory;
import srs.dao.ScheduleDao;
import srs.dao.SectionDao;
import srs.model.ScheduleOfClasses;

public class ScheduleOfClassesService {
	private static HashMap<String,ScheduleOfClasses> scheduleMap = new HashMap<String,ScheduleOfClasses>();
    private ScheduleOfClasses sc = new ScheduleOfClasses();
	private ScheduleDao ScheduleDao = (ScheduleDao) DaoFactory.createReleventDao("ScheduleDao");
	private CourseDao courseDao = (CourseDao)DaoFactory.createReleventDao("CourseDao");
	private SectionDao sectionDao = null;
    public ScheduleOfClassesService() {
		super();
	}

	public ScheduleOfClasses getScheduleOfClasses() {
		return sc;
	} 
	
	public ScheduleOfClassesService(String semester, SectionDao dao){
    	sc = new ScheduleOfClasses(semester, dao.findBySemester(semester));
    }
    
    public ScheduleOfClassesService(String semester){
    	if(scheduleMap.containsKey(semester)){
    		sc = scheduleMap.get(semester);
    	}else{
    		sc = new ScheduleOfClasses(semester, ScheduleDao.getScheduleOfClass(semester));
    		scheduleMap.put(semester, sc);
    	}
    }
    
    public void addToSchedule(String sectionNo, String semester){
    	ScheduleDao.addToSchedule(sectionNo, semester);
    	sc.addSection(courseDao.query(sectionNo));
    	scheduleMap.put(semester, sc);
    	System.out.println(sc.toString());
    }
    
    public JSONArray getSectionsBySemester(String semester){
    	return CourseService.toJson2(scheduleMap.get(semester).getSectionsOffered().values());
    }
    
}
