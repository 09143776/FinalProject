package srs.model;
// Course.java - Chapter 14, Java 5 version.

// Copyright 2005 by Jacquie Barker - all rights reserved.

// A MODEL class.


import java.util.ArrayList;
import java.util.Collection;

public class Course {
	//------------
	// Attributes.
	//------------
	private String courseNo;
	private String courseName;
	private double credits;
	private ArrayList<Section> offeredAsSection; 
	private ArrayList<Course> prerequisites; 
	private String preCoursesNo;
	
	//----------------
	// Constructor(s).
	//----------------

	public Course(String cNo, String cName, double credits) {
		setCourseNo(cNo);
		setCourseName(cName);
		setCredits(credits);

		// Note that we're instantiating empty support Collection(s).

		offeredAsSection = new ArrayList<Section>();
		prerequisites = new ArrayList<Course>();
	}



	public Course() {
		super();
	}

	//------------------
	// Accessor methods.
	//------------------

	public void setCourseNo(String cNo) {
		courseNo = cNo;
	}
	
	public String getCourseNo() {
		return courseNo;
	}
	
	public void setCourseName(String cName) {
		courseName = cName;
	}
	
	public String getCourseName() {
		return courseName;
	}
	
	public void setCredits(double c) {
		credits = c;
	}
	
	public double getCredits() {
		return credits;
	}
	
	//-----------------------------
	// Miscellaneous other methods.
	//-----------------------------


	public ArrayList<Section> getOfferedAsSection() {
		return offeredAsSection;
	}



	public void setOfferedAsSection(ArrayList<Section> offeredAsSection) {
		this.offeredAsSection = offeredAsSection;
	}



	public String getPreCoursesNo() {
		String s = "";
		if(prerequisites!=null && prerequisites.size()>0){
			for(Course course : prerequisites){
				s += course.getCourseNo() + ",";
			}
			return s;
		}	
		return preCoursesNo;
	}

	public void setPreCoursesNo(String preCoursesNo) {
		this.preCoursesNo = preCoursesNo;
	}

	public void display() {
		System.out.println("Course Information:");
		System.out.println("\tCourse No.:  " + getCourseNo());
		System.out.println("\tCourse Name:  " + getCourseName());
		System.out.println("\tCredits:  " + getCredits());
		System.out.println("\tPrerequisite Courses:");

		for (Course c : prerequisites) {
			System.out.println("\t\t" + c.toString());
		}

		System.out.print("\tOffered As Section(s):  ");
		for (Section s : offeredAsSection) {
			System.out.print(s.getSectionNo() + " ");
		}

		System.out.println();
	}
	
	@Override
	public String toString() {
		return getCourseNo() + ":  " + getCourseName();
	}

	public void addPrerequisite(Course c) {
		if(prerequisites==null){
			prerequisites = new ArrayList<Course>();
		}
		prerequisites.add(c);
	}
	
	public void removePrerequisite(Course c) {
		prerequisites.remove(c);
	}

	public boolean hasPrerequisites() {
		if (prerequisites.size() > 0) return true;
		else return false;
	}

	public Collection<Course> getPrerequisites() {
		return prerequisites;
	}

	public Section scheduleSection(String day, String time, String room,
				       int capacity) {
		Section s = new Section(offeredAsSection.size() + 1, 
				day, time, this, room, capacity);
		addSection(s);
		
		return s;
	}

	public void addSection(Section s) {
		offeredAsSection.add(s);
	}
	
	public void removeSection(String sectionNo){
		for(Section section: offeredAsSection){
			if(sectionNo.equals(section.getSectionNo())){
				offeredAsSection.remove(section);
				break;
			} 
		}		
	}
}
