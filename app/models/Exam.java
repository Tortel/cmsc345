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
	//Requires the file name to be saved too
	private String videoFileName;
	
	/**
	 * Creates a new Exam object.<br>
	 * The date is the current date
	 * @param patient the patient
	 * @param physician the conducting physician
	 * @param physisianComments the physician's comments
	 * @param patientComments the patient's comments
	 * @param video the ultrasound file
	 */
	public Exam(Patient patient, Physician physician, String physicianComments, String patientComments, Blob video){
		this.patient = patient;
		this.patient.addExam(this);
		this.physician = physician;
		this.physician.addExam(this);
		this.physicianComments = physicianComments;
		this.patientComments = patientComments;
		this.date = new Date();
		this.video = video;
	}
	
	/**
	 * Creates a new exam object.
	 * @param patient the patient
	 * @param physician the physician
	 * @param date the date of the exam
	 * @param physicianComments the physician's comments
	 * @param patientComments the patient's comments
	 * @param video the ultrasound file
	 */
	public Exam(Patient patient, Physician physician, Date date, String physicianComments, String patientComments, Blob video){
		this(patient, physician, physicianComments, patientComments, video);
		this.date = date;
	}
	
	/**
	 * Get the patient
	 * @return the patient
	 */
	public Patient getPatient(){
		return patient;
	}
	
	/**
	 * Get the physician
	 * @return the physician
	 */
	public Physician getPhysician(){
		return physician;
	}
	
	/**
	 * Get the physician's comments
	 * @return the physician's comments
	 */
	public String getPhysicianComments(){
		return physicianComments;
	}
	
	/**
	 * Get the patient's comments
	 * @return the patient's comments
	 */
	public String getPatientComments(){
		return patientComments;
	}
	
	/**
	 * Get the date of the exam
	 * @return the date
	 */
	public Date getDate(){
		return date;
	}
	
	/**
	 * Get the ultrasound video
	 * @return the video
	 */
	public Blob getVideo(){
		return video;
	}
	
	/**
	 * Get the name of the ultrasound video file
	 * @return the name of the video file
	 */
	public String getVideoFileName(){
		return videoFileName;
	}
	
}
