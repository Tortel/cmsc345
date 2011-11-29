package models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * Class that represents a Patient in the system.<br>
 * Patients have addresses, phone numbers, sex,<br>
 * and their exam history.
 */
@Entity
public class Patient extends User {
	private String address;
	private String phoneNumber;
	private char sex;
	
	@OneToMany(mappedBy="patient", cascade=CascadeType.ALL)
	private List<Exam> exams;
	
	/**
	 * Creates a new patient with the given information
	 * @param username the username (Email address)
	 * @param password the password
	 * @param address the address
	 * @param phoneNumber the phone number
	 * @param sex the sex (M/F/I)
	 */
	public Patient(String username, String password, String firstName, String lastName, 
				   String address, String phoneNumber, char sex) {
		//Superclass constructor
		super(username, password, firstName, lastName);
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.sex = Character.toUpperCase(sex);
		exams = new ArrayList<Exam>();
	}
	
	/**
	 * Get the patient's address
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Get the patient's phone number
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Get the patient's sex.<br>
	 * M, F, or I
	 * @return the sex
	 */
	public char getSex() {
		return sex;
	}
	
	/**
	 * Adds an exam for the patient.<br>
	 * Patient is automatically saved.
	 * @param exam the exam to add
	 */
	public void addExam(Exam exam){
		exams.add(exam);
		this.save();
	}
	
	/**
	 * Returns all the exams this patient has been in
	 * @return a list of all the exams
	 */
	public List<Exam> getExams(){
		return exams;
	}

}
