package srs.specification;

import srs.model.Course;
import srs.model.EnrollmentStatus;
import srs.model.Section;
import srs.model.Student;

public class SectionPrereqIsSatisfied implements ISpecification<Student> {
	private Section section;

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}


	public SectionPrereqIsSatisfied(Section section) {
		super();
		this.section = section;
	}

	@Override
	public EnrollmentStatus  isSatified(Student student) {
		if (section.getRepresentedCourse().hasPrerequisites()) {
			for (Course pre : section.getRepresentedCourse().getPrerequisites()) {
				if (!student.getTranscript().verifyCompletion(pre)) {
					System.out.println(section.getFullSectionNo() + "先修课程还未通过！！！");
					return EnrollmentStatus.prereq;
				}
			}
		}
		return EnrollmentStatus.success;
	}

}
