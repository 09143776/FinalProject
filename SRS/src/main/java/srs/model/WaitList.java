package srs.model;

import java.util.ArrayList;

public class WaitList {
	private ArrayList<Student> queueStudents;

	public ArrayList<Student> getQueueStudents() {
		return queueStudents;
	}

	public void setQueueStudents(ArrayList<Student> queueStudents) {
		this.queueStudents = queueStudents;
	}

	public WaitList(ArrayList<Student> queueStudents) {
		super();
		this.queueStudents = queueStudents;
	} 
	
	public void display() {
		// Iterate through the HashMap and display all entries.

		for (Student s : queueStudents) {
			s.display();
			System.out.println();
		}
	}
	
	public void addStudent(Student stu){
		queueStudents.add(stu);
	}
	
	public Student removeStudent(){
		return queueStudents.remove(0);
	}
	
	public boolean isEmpty() {
		if (queueStudents.size() == 0) return true;
		else return false;
	}
}
