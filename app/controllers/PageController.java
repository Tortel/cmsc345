package controllers;

import play.*;
import play.data.validation.*;
import play.db.jpa.Blob;
import play.mvc.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import models.*;
import repo.Repository;

/**
 * Controls all the secured pages.<br>
 * This handles viewing and creating exams, viewing patient and physician details, and searching
 * for exams.
 */
@With(Secure.class)
public class PageController extends Controller {
	
	/**
	 * Displays the welcome page
	 */
    public static void welcome() {
    	User user = User.findById(Security.getUserId());
        render(user);
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
    public static void results(Long physicianId, Long patientId, String start, String end){
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
    		render(exams);
    		return;
    	}
    	
    	//Patient
    	if(patientId != null){
    		exams = Repository.searchByPatient(patientId);
    		render(exams);
    		return;
    	}
    	
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			Date startDate = formatter.parse(start);
			System.out.println("Start date: " + startDate);
			Date endDate = formatter.parse(end);
			System.out.println("End date: " + endDate);
			exams = Repository.searchByDate(startDate, endDate);
			render(exams);
			return;
		} catch (ParseException e) {
			System.out.println("\n\nError parsing dates");
			e.printStackTrace(System.out);
			//Error trying date
			exams = new ArrayList<Exam>(0);
		}
		
    	//Render something
    	render( exams );
    }
    
    /**
     * View an exam
     * @param examId
     */
    public static void exam(Long examId){
    	Exam exam = Exam.findById(examId);
    	//Display 404 if not found
    	notFoundIfNull(exam);
    	
    	//If they are a physician, don't care
    	if(Security.check("physician")){
	    	render(exam);
    	} else {
    		//A patient. Make sure its one of their exams
    		if(Security.getUserId() != exam.getPatient().id){
    			forbidden();
    		}
    		
    		//Their exam, display it
    		render(exam);
    	}
    }
    
    /**
     * View a patient's details.<br>
     * Restricted to physicians only
     * @param id the patient's id
     */
    @Check("physician")
    public static void patient(Long id){
    	Patient patient = Patient.findById(id);
    	//404 if not found
    	notFoundIfNull(patient);
    	
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
    	//404 if null
    	notFoundIfNull(physician);
    	
    	render(physician);
    }
    
    /**
     * Displays the form to create a new ultrasound exam.<br>
     * Restricted to physicians
     */
    @Check("physician")
    public static void createExamForm(){
    	List<Patient> patients = Patient.findAll();
    	List<Physician> physicians = Physician.findAll();
    	render(patients, physicians);
    }
    
    /**
     * Creates a new exam
     * @param video the video file
     * @param patientId the patient's ID
     * @param physicianId the physician's id
     * @param start the date of the exam (In MM/dd/yyyy format)
     * @param physicianComments the physician's comments
     * @param patientComments the patient's comments
     */
    @Check("physician")
    public static void createExam(@Required(message = "Ultrasound Video is required") Blob video,
					    		  @Required Long patientId, @Required Long physicianId,
					    		  @Required(message="Date is required") String start,
					    		  String physicianComments, String patientComments){
    	
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try{
			date = formatter.parse(start);
		} catch(Exception e){
			validation.addError(start, "Invalid date", start);
		}
    	
    	if(validation.hasErrors()){
    		List<Patient> patients = Patient.findAll();
    		List<Physician> physicians = Physician.findAll();
    		render("PageController/createExamForm.html", physicianId, physicianComments, physicians, patientComments,
    				patientId, patients);
    	}
    	
    	//Get the patient and physician based on ID
    	Patient patient = Patient.findById(patientId);
    	Physician physician = Physician.findById(physicianId);
    	
    	//Create and save the exam
    	Exam exam = new Exam(patient, physician, date, physicianComments, patientComments, video);
    	exam.save();
    	//Redirect them to the exam page, after its created
    	exam(exam.id);
    }
    
    /**
     * Provides the video file to be downloaded
     * @param id
     */
    public static void downloadVideo(long id) {
    	User user = User.findById(Security.getUserId());
    	Exam exam = Exam.findById(id);
    	notFoundIfNull(exam);
    	//If they are a physician, don't care
    	if(user.getClass() == Physician.class){
	    	response.setContentTypeIfNotSet(exam.getVideo().type());
	    	renderBinary(exam.getVideo().get(), exam.getVideoFileName());
    	} else {
    		//They are the patient in the exam, allow them
    		if(user.id == exam.getPatient().id){
    	    	response.setContentTypeIfNotSet(exam.getVideo().type());
    	    	renderBinary(exam.getVideo().get(), exam.getVideoFileName());
    		} else {
    			forbidden();
    		}
    	}
    }
    
    /**
     * Displays a printable view for an exam
     * @param id the exam ID
     */
    public static void printExam(Long id){
    	Exam exam = Exam.findById(id);
    	notFoundIfNull(exam);
    	
    	//Checks to prevent patients from printing someone else's exam
    	User user = User.findById(Security.getUserId());
    	if(user.getClass() == Physician.class){
    		//Physician, don't really care
    		render(exam);
    	} else {
    		//Patient, do care. Make sure they are the one on the exam
    		if(user.id != exam.getPatient().id){
    			forbidden();
    		} else {
    			render(exam);
    		}
    		
    	}
    	
    	
    }
    

}