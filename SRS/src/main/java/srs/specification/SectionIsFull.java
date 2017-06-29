package srs.specification;

import srs.model.EnrollmentStatus;
import srs.model.Section;
import srs.model.Student;

public class SectionIsFull implements ISpecification<Student> {
	private Section section;
	
	
	public Section getSection() {
		return section;
	}


	public void setSection(Section section) {
		this.section = section;
	}


	public SectionIsFull(Section section) {
		super();
		this.section = section;
	}


	@Override
	public EnrollmentStatus  isSatified(Student student) {
		if (section.confirmSeatAvailability()) {
			return EnrollmentStatus.success;
		}else{
			section.getWaitList().addStudent(student);
			System.out.println(section.getFullSectionNo() + "人数已满！！！");
			return EnrollmentStatus.secFull;
		}		
	}

}
