package controllers;

import play.*;
import play.data.validation.*;
import play.db.jpa.Blob;
import play.mvc.*;

import java.io.File;
import java.util.*;

import models.*;
import repo.Repository;

@With(Secure.class)
public class PageController extends Controller {
	
	/**
	 * Displays the welcome page
	 */
    public static void welcome() {
        render();
    }
    
    /**
     * Displays the search page
     */
    public static void search(){
    	if(!Security.check("physician")){
    		//Direct the user to their own exams
    		results(null, Security.getUserId(), null, null);
    	}
    	
    	List<Physician> physicians = Physician.findAll();
    	List<Patient> patients = Patient.findAll();
    	
    	render(physicians, patients);
    }
    
    /**
     * Preforms the search, and displays the search results
     * @param physicianId the ID of the physician, if searching by physician
     * @param patientId the ID of patient, if searching by the patient
     * @param start the start date, if searching by date range
     * @param end the end date, if searching by date range
     */
    public static void results(Long physicianId, Long patientId, Date start, Date end){
    	if(!Security.check("physician")){
    		physicianId = null;
    		start = null;
    		end = null;
    		patientId = Security.getUserId();
    	}
    	//Need to check for each search condition: Physician, patient, or date
    	List<Exam> exams = null;
    	//Physician
    	if(physicianId != null){
    		exams = Repository.searchByPhysician(physicianId);
    		render( exams );
    		return;
    	}
    	
    	//Patient
    	if(patientId != null){
    		exams = Repository.searchByPatient(patientId);
    		render( exams );
    		return;
    	}
    	
    	exams = Repository.searchByDate(start, end);
    	//Date
    	render( exams );
    }
    
    /**
     * View an exam
     * @param examId
     */
    public static void exam(Long examId){
    	Exam exam = Exam.findById(examId);
    	render(exam);
    }
    
    /**
     * View a patient's details.<br>
     * Restricted to physicians only
     * @param id the patient's id
     */
    @Check("physician")
    public static void patient(Long id){
    	Patient patient = Patient.findById(id);
    	render(patient);
    }
    
    /**
     * View a physician's details.<br>
     * Restricted to physicians only
     * @param id the physician's id
     */
    @Check("physician")
    public static void physician(Long id){
    	Physician physician = Physician.findById(id);
    	render(physician);
    }
    
    /**
     * Displays the form to create a new ultrasound exam.<br>
     * Restricted to physicians
     */
    @Check("physician")
    public static void createExamForm(){
    	List<Patient> patients = Patient.findAll();
    	render(patients);
    }
    
    /**
     * Creates a new exam
     * @param video the video file
     * @param patientId the patient's ID
     * @param physicianComments the physician's comments
     * @param patientComments the patient's comments
     */
    @Check("physician")
    public static void createExam(@Required(message = "Ultrasound Video is required") Blob video,
    		@Required Long patientId, String physicianComments, String patientComments){
    	if(validation.hasErrors()){
    		List<Patient> patients = Patient.findAll();
    		render("PageController/createExamForm.html", physicianComments, patientComments, patientId, patients);
    	}
    	
    	//Get the patient and physician based on ID
    	Patient patient = Patient.findById(patientId);
    	Physician physician = Physician.findById(Security.getUserId());
    	
    	//Create and save the exam
    	Exam exam = new Exam(patient, physician, physicianComments, patientComments, video);
    	exam.save();
    	
    	exam(exam.id);
    }
    
    /**
     * Provides the video file to be downloaded
     * @param id
     */
    public static void downloadVideo(long id) {
    	   Exam exam = Exam.findById(id);
    	   notFoundIfNull(exam);
    	   response.setContentTypeIfNotSet(exam.getVideo().type());
    	   renderBinary(exam.getVideo().get(), exam.getVideoFileName());
    	}
    

}