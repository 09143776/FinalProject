package serviceTest;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Map;
import org.junit.Test;
import srs.dao.DaoFactory;
import srs.dao.SectionDao;
import srs.model.Professor;
import srs.model.ScheduleOfClasses;
import srs.model.Section;
import srs.model.Student;
import srs.service.ScheduleOfClassesService;
import srs.specification.ISpecification;
import srs.specification.SectionIsFull;
import srs.specification.SectionIsPrevEnrolled;
import srs.specification.SectionPrereqIsSatisfied;

public class ScheduleOfClassesServiceTest {
	
	static class listBoxDTO{
		private String id;
		private String text;
		private listBoxDTO(String id, String text){
			this.id = id;
			this.text = text;
		}
		
	}
	
//	//输出SP2005学期的全部课程
//	@Test
//	public void test1(){
//		String semester = "SP2005";		
//		Gson g = new Gson();			
//		try {
//			ScheduleOfClassesService scs = new ScheduleOfClassesService(semester, DaoFactory.createSectioneDao());
//			Map<String, Section> sections = scs.getScheduleOfClasses().getSectionsOffered();
//			List<listBoxDTO> dtos = new ArrayList<listBoxDTO>();
//			for(String key : sections.keySet()){
//				dtos.add(
//						new listBoxDTO(sections.get(key).getFullSectionNo()
//								       , sections.get(key).getFullSectionInfo()));
//			}			
//			String json = g.toJson(dtos);
//			System.out.println(json);
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail();
//		}
//	}
	
	
	@Test
	public void test2(){
		String semester = "SP2005";	
		Student stu = new Student("hudie", "09143672", "电子商务", "本科");
		Student stu2 = new Student("hudie2", "09143673", "电子商务", "本科");
		Student stu3 = new Student("hudie3", "09143674", "电子商务", "本科");
		
		Professor professor = new Professor("Mark", "0914367272", "教师", "管理学院");
		
		ScheduleOfClassesService scs = new ScheduleOfClassesService(semester, (SectionDao)DaoFactory.createReleventDao("SectionDao"));
		Map<String, Section> sections = scs.getScheduleOfClasses().getSectionsOffered();
		
		ArrayList<ISpecification> specifications = new ArrayList<ISpecification>();	
		Section section1 = sections.get("OBJ101-2");
		specifications.add(new SectionIsFull(section1));
		specifications.add(new SectionIsPrevEnrolled(section1));
		specifications.add(new SectionPrereqIsSatisfied(section1));
		
		System.out.println("========================================================");
		System.out.println("stu选课：section1的选修课程没有完成，所以选课会提示失败");
		section1.setSpecifications(specifications);
		section1.enroll(stu);	//选课：section1的选修课程没有完成，所以选课会提示失败
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		System.out.println("========================================================");
		System.out.println("stu选课：section2选课成功");
		Section section2 = sections.get("CMP101-1");
		specifications = new ArrayList<ISpecification>();
		specifications.add(new SectionIsFull(section2));
		specifications.add(new SectionIsPrevEnrolled(section2));
		specifications.add(new SectionPrereqIsSatisfied(section2));
		section2.setSpecifications(specifications);
		section2.enroll(stu);	//选课：section2选课成功
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		
		System.out.println("========================================================");
		System.out.println("stu选课情况：");
		ArrayList<Section> sectionsList = stu.getAttends();
		for(Section section : sectionsList){
			System.out.println(section.toString());
		}
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		System.out.println("========================================================");
		System.out.println("设定section2的选课最大人数为2时，stu3也选修section2:");
		section2.enroll(stu3);
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		System.out.println("========================================================");
		System.out.println("section2的选课人数已满时，stu2选修section2，stu2选课失败:");		
		section2.enroll(stu2);
		sectionsList = stu2.getAttends();
		for(Section section : sectionsList){
			System.out.println(section.toString());
		}
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		System.out.println("========================================================");
		System.out.println("stu3退选section2之后，stu2选课成功:");	
		section2.drop(stu3);
		sectionsList = stu2.getAttends();
		for(Section section : sectionsList){
			System.out.println(section.toString());
		}
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		professor.agreeToTeach(section1);
		professor.agreeToTeach(section2);
		professor.displayTeachingAssignments();
		
		System.out.println("========================================================");
		System.out.println("学生的section2课程分数为A,section1的选修课程已完成，所以section1选课成功:");	
		section2.postGrade(stu, "A");	
		section1.enroll(stu);
		sectionsList = stu.getAttends();
		for(Section section : sectionsList){
			System.out.println(section.toString());
		}
		System.out.println("========================================================");
		System.out.println("\t");
		
		
		System.out.println("========================================================");
		System.out.println("打印成绩:");	
		section1.postGrade(stu, "A");	//学生的section1课程分数为A
		ScheduleOfClasses scheduleOfClasses = new ScheduleOfClasses(semester); 	//SP2005学期的教学计划
		scheduleOfClasses.addSection(section1);
		scheduleOfClasses.addSection(section2);		
		stu.printTranscript();	//	打印成绩单
		System.out.println("========================================================");
		System.out.println("\t");
	}
	
	

}
