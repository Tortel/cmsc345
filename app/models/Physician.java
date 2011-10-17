package models;

import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * Represents a Physician in the system<br>
 * 
 */
@Entity
public class Physician extends User {
	private String firstName;
	private String lastName;
	
	//All the exams they have conducted
	@OneToMany(mappedBy="physician", cascade=CascadeType.ALL)
	private List<Exam> exams;
	
	/**
	 * Creates a new Physician
	 * @param username the physician's username (Email)
	 * @param password the physician's password
	 */
	public Physician(String username, String password, String firstName, String lastName){
		super(username, password);
		exams = new ArrayList<Exam>();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Returns all the exams this phyisician<br>
	 * has conducted.
	 * @return
	 */
	public List<Exam> getExams(){
		return exams;
	}
	
	/**
	 * Returns the physician's name
	 * @return the name
	 */
	public String getName(){
		return firstName+" "+lastName;
	}
	
}
