package srs.specification;

import srs.model.EnrollmentStatus;
import srs.model.Section;
import srs.model.Student;
import srs.model.Transcript;

public class SectionIsPrevEnrolled implements ISpecification<Student> {
	private Section section;
	
	@Override
	public EnrollmentStatus  isSatified(Student student) {
		Transcript transcript = student.getTranscript();
		if (student.isCurrentlyEnrolledInSimilar(section) || 
		    transcript.verifyCompletion(section.getRepresentedCourse())) {
			System.out.println(section.getFullSectionNo() + "这门课已选过/不能选已经选过的课程!!!");
			return EnrollmentStatus.prevEnroll;
		}
		return EnrollmentStatus.success;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public SectionIsPrevEnrolled(Section section) {
		super();
		this.section = section;
	}

	
	

}
