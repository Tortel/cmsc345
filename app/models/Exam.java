package models;

import java.io.File;
import java.util.Date;

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
	
	@ManyToOne
	private Physician physician;
	
	private Date date;
	
	//Lob makes these a large object in the database
	@Lob
	private String physicianComments;
	@Lob
	private String patientComments;
	
	//Either a File object for the video file, or a Blob object. Not sure yet
	private Blob video;
	
	/**
	 * Creates a new Exam object
	 * @param patient the patient
	 * @param physician the conducting physician
	 * @param physisianComments the physician's comments
	 * @param patientComments the patient's comments
	 */
	public Exam(Patient patient, Physician physician, String physicianComments, String patientComments, Blob video){
		this.patient = patient;
		this.patient.addExam(this);
		this.physician = physician;
		this.physician.addExam(this);
		//TODO:Strip HTML from comments
		this.physicianComments = physicianComments;
		this.patientComments = patientComments;
		this.date = new Date();
		this.video = video;
	}
	
	
	public Patient getPatient(){
		return patient;
	}
	
	public Physician getPhysician(){
		return physician;
	}
	
	public String getPhysicianComments(){
		return physicianComments;
	}
	
	public String getPatientComments(){
		return patientComments;
	}
	
	public Date getDate(){
		return date;
	}
	
	public Blob getVideo(){
		return video;
	}
}
