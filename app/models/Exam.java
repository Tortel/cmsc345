package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * Represents an exam in the system<br>
 * Contains references to the patient and the doctor
 */
@Entity
public class Exam extends Model {
	@ManyToOne
	private Patient patient;
	
	
}
