package models;

import java.util.*;
import javax.persistence.*;
import play.db.jpa.*;

/**
 * Represents a Physician in the system<br>
 */
@Entity
public class Physician extends User {
	//All the exams they have conducted
	@OneToMany(mappedBy="physician", cascade=CascadeType.ALL)
	private List<Exam> exams;
	
	/**
	 * Creates a new Physician
	 * @param username the physician's username (Email)
	 * @param password the physician's password
	 */
	public Physician(String username, String password, String firstName, String lastName){
		super(username, password, firstName, lastName);
		exams = new ArrayList<Exam>();
	}
	
	/**
	 * Returns all the exams this physician has conducted.
	 * @return the list of exams
	 */
	public List<Exam> getExams(){
		return exams;
	}
	
	/**
	 * Add an exam to the physician's records
	 * @param e the exam
	 */
	public void addExam(Exam e){
		exams.add(e);
		this.save();
	}
}
