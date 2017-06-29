package srs.model;
// Section.java - Chapter 14, Java 5 version.

import java.util.ArrayList;

// Copyright 2005 by Jacquie Barker - all rights reserved.

// A MODEL class.


import java.util.HashMap;

import srs.specification.ISpecification;

public class Section {
	//------------
	// Attributes.
	//------------
	private int sectionNo;
	private String dayOfWeek;
	private String timeOfDay;
	private String room;
	private int seatingCapacity;
	private String professorName;
	private String professorSsn;
	private Course representedCourse;
	private ScheduleOfClasses offeredIn;
	private Professor instructor;
	private String fullSectionNo;

	@SuppressWarnings("rawtypes")
	private ArrayList<ISpecification> specifications = new ArrayList<ISpecification>();
	private HashMap<String, Student> enrolledStudents; 
	private WaitList waitList; 
	// The assignedGrades HashMap stores TranscriptEntry object
	// references, using a reference to the Student to whom it belongs 
	// as the key.

	private HashMap<Student, TranscriptEntry> assignedGrades; 
	
	//----------------
	// Constructor(s).
	//----------------

	public Section(int sNo, String day, String time, Course course,
		       String room, int capacity) {
		setSectionNo(sNo);
		setDayOfWeek(day);
		setTimeOfDay(time);
		setRepresentedCourse(course);
		setRoom(room);
		setSeatingCapacity(capacity);

		// A Professor has not yet been identified.

		setInstructor(null);

		// Note that we're instantiating empty support Collection(s).

		enrolledStudents = new HashMap<String, Student>();
		assignedGrades = new HashMap<Student, TranscriptEntry>();
		waitList = new WaitList(new ArrayList<Student>());
	}
	
	
									
	//------------------
	// Accessor methods.
	//------------------

	public HashMap<String, Student> getEnrolledStudents() {
		return enrolledStudents;
	}



	public void setEnrolledStudents(HashMap<String, Student> enrolledStudents) {
		this.enrolledStudents = enrolledStudents;
	}



	public Section(int sectionNo, String dayOfWeek, String timeOfDay, String room, int seatingCapacity,
			String professorName, String professorSsn) {
		super();
		this.sectionNo = sectionNo;
		this.dayOfWeek = dayOfWeek;
		this.timeOfDay = timeOfDay;
		this.room = room;
		this.seatingCapacity = seatingCapacity;
		this.professorName = professorName;
		this.professorSsn = professorSsn;
		setInstructor(null);
		enrolledStudents = new HashMap<String, Student>();
		assignedGrades = new HashMap<Student, TranscriptEntry>();
		waitList = new WaitList(new ArrayList<Student>());
	}



	public Section() {
		super();
	}



	public void setSectionNo(int no) {
		sectionNo = no;
	}
	
	public int getSectionNo() {
		return sectionNo;
	}
	
	public void setDayOfWeek(String day) {
		dayOfWeek = day;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
		
	public void setTimeOfDay(String time) {
		timeOfDay = time;
	}
	
	public String getTimeOfDay() {
		return timeOfDay;
	}

	public void setInstructor(Professor prof) {
		instructor = prof;
	}
	
	public Professor getInstructor() {
		return instructor;
	}
	
	public void setRepresentedCourse(Course c) {
		representedCourse = c;
	}
	
	public Course getRepresentedCourse() {
		return representedCourse;
	}		

	public void setRoom(String r) {
		room = r;
	}

	public String getRoom() {
		return room;
	}

	public String getProfessorName() {
		return professorName;
	}



	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}



	public void setSeatingCapacity(int c) {
		seatingCapacity = c;
	}

	public int getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setOfferedIn(ScheduleOfClasses soc) {
		offeredIn = soc;
	}

	public ScheduleOfClasses getOfferedIn() {
		return offeredIn;
	}	
	
	
	
	

	//-----------------------------
	// Miscellaneous other methods.
	//-----------------------------

	// For a Section, we want its String representation to look
	// as follows:
	//
	//	MATH101 - 1 - M - 8:00 AM

	@SuppressWarnings("rawtypes")
	public ArrayList<ISpecification> getSpecifications() {
		return specifications;
	}

	public WaitList getWaitList() {
		return waitList;
	}

	public void setWaitList(WaitList waitList) {
		this.waitList = waitList;
	}

	@SuppressWarnings("rawtypes")
	public void setSpecifications(ArrayList<ISpecification> specifications) {
		this.specifications = specifications;
	}

	public String getProfessorSsn() {
		return professorSsn;
	}



	public void setProfessorSsn(String professorSsn) {
		this.professorSsn = professorSsn;
	}



	public HashMap<Student, TranscriptEntry> getAssignedGrades() {
		return assignedGrades;
	}



	public void setAssignedGrades(HashMap<Student, TranscriptEntry> assignedGrades) {
		this.assignedGrades = assignedGrades;
	}



	@Override
	public String toString() {
		return getRepresentedCourse().getCourseNo() + " - " +
		       getSectionNo() + " - " +
		       getDayOfWeek() + " - " +
		       getTimeOfDay();
	}

	// The full section number is a concatenation of the
	// course no. and section no., separated by a hyphen;
	// e.g., "ART101 - 1".

	public String getFullSectionNo() {
		if(fullSectionNo!=null){
			return fullSectionNo;
		}else{
			return getRepresentedCourse().getCourseNo() + 
				       " - " + getSectionNo();
		}
	}
	
	
	public void setFullSectionNo(String fullSectionNo) {
		this.fullSectionNo = fullSectionNo;
	}

	public String getFullSectionInfo() {
		return getRepresentedCourse().getCourseName() + 
			   "-" + getDayOfWeek() + "-" +
		       "" + getTimeOfDay() +
		       "-" + this.getRoom();
	}
	

	// We use an enum -- EnrollmentStatus -- to return an indication of the
	// outcome of the request to enroll Student s.  See EnrollmentStatus.java
	// for details on this enum.

	public EnrollmentStatus enroll(Student s) {
		if(validate(s)==EnrollmentStatus.success){
			enrolledStudents.put(s.getSsn(), s);
			s.addSection(this);
			return EnrollmentStatus.success;
		}else{
			return validate(s);
		}
	}
	
	// A private "housekeeping" method.

	public boolean confirmSeatAvailability() {
		if (enrolledStudents.size() < getSeatingCapacity()) return true;
		else return false;
	}

	public Student drop(Student s) {
		Student student = null;
		if (!s.isEnrolledIn(this)) return student;
		else {
			enrolledStudents.remove(s.getSsn());
			s.dropSection(this);
			if(!waitList.isEmpty()){
				student = waitList.removeStudent();
				enrolledStudents.put(student.getSsn(), student);
				student.addSection(this);
			}				
			return student;
		}
	}

	public int getTotalEnrollment() {
		return enrolledStudents.size();
	}	

	public void display() {
		System.out.println("Section Information:");
		System.out.println("\tSemester:  " + getOfferedIn().getSemester());
		System.out.println("\tCourse No.:  " + 
				   getRepresentedCourse().getCourseNo());
		System.out.println("\tSection No:  " + getSectionNo());
		System.out.println("\tOffered:  " + getDayOfWeek() + 
				   " at " + getTimeOfDay());
		System.out.println("\tIn Room:  " + getRoom());
		if (getInstructor() != null) 
			System.out.println("\tProfessor:  " + 
				   getInstructor().getName());
		displayStudentRoster();
	}
	
	public void displayStudentRoster() {
		System.out.print("\tTotal of " + getTotalEnrollment() + 
				   " students enrolled");
		if (getTotalEnrollment() == 0) System.out.println(".");
		else System.out.println(", as follows:");

		// Iterate through all of the values stored in the HashMap.

		for (Student s : enrolledStudents.values()) {
			System.out.println("\t\t" + s.getName());
		}
	}

	public String getGrade(Student s) {
		String grade = null;
		TranscriptEntry te = assignedGrades.get(s);
		if (te != null) {
			grade = te.getGrade(); 
		}	
		return grade;
	}

	public boolean postGrade(Student s, String grade) {
		if (!TranscriptEntry.validateGrade(grade)) return false;

		if (assignedGrades.get(s) != null) return false;

		TranscriptEntry te = new TranscriptEntry(s, grade, this);

		assignedGrades.put(s, te);

		return true;
	}
	
	public boolean isSectionOf(Course c) {
		if (c.getCourseName().equals(representedCourse.getCourseName())) return true;
		else return false;
	}
	
	public void addSpecification(@SuppressWarnings("rawtypes") ISpecification specification){
		specifications.add(specification);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EnrollmentStatus validate(Student stu){
		for(ISpecification specification: specifications){
			if(specification.isSatified(stu)!=EnrollmentStatus.success){
				return specification.isSatified(stu);
			}
		}
		return EnrollmentStatus.success;
	}
}
