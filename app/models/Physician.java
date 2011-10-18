package models;

import java.util.*;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * Represents a Physician in the system<br>
 * 
 */
@Entity
public class Physician extends Model {
	//All the exams they have conducted
	@OneToMany(mappedBy="physician", cascade=CascadeType.ALL)
	private List<Exam> exams;
	
	
	//From User
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	//End from User
	
	/**
	 * Creates a new Physician
	 * @param username the physician's username (Email)
	 * @param password the physician's password
	 */
	public Physician(String username, String password, String firstName, String lastName){
		//super(username, password, firstName, lastName);
		exams = new ArrayList<Exam>();
		//from User
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		//End from User
	}
	
	/**
	 * Returns all the exams this phyisician<br>
	 * has conducted.
	 * @return
	 */
	public List<Exam> getExams(){
		return exams;
	}
	
	//From User
	/**
	 * Sends the user their password over email
	 */
	public void recoverPassword(){
		//TODO: Implement this
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getName(){
		return firstName + " " + lastName;
	}
	
	//End from User
	
}
