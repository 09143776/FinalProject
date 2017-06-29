package srs.model;
// Person.java - Chapter 14, Java 5 version.

// Copyright 2005 by Jacquie Barker - all rights reserved.

// A MODEL class.


// We are making this class abstract because we do not wish for it
// to be instantiated.

public class Person {
	private String name;
	private String ssn;
	private String password;

    public Person(String ssn, String password) {
		super();
		this.ssn = ssn;
		this.password = password;
	}

	public Person() {
		this.setName("?");
		this.setSsn("???-??-????");
    }

	public void setName(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSsn(String s) {
		ssn = s;
	}
	
	public String getSsn() {
		return ssn;
	}

	public void display() {
		System.out.println("Person Information:");
		System.out.println("\tName:  " + this.getName());
		System.out.println("\tSoc. Security No.:  " + this.getSsn());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}	
