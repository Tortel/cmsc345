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
	//All the exams they have conducted
	@OneToMany(mappedBy="physician", cascade=CascadeType.ALL)
	private List<Exam> exams;
	
	
	public Physician(String username, String password){
		super(username, password);
		exams = new ArrayList<Exam>();
	}
	
	
	public List<Exam> getExams(){
		return exams;
	}
}
