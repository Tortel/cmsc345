package controllers;

import play.*;
import play.data.validation.*;
import play.mvc.*;

import java.util.*;

import models.*;
import repo.Repository;

public class PageController extends Controller {
	
	/**
	 * Displays the index (Main) page
	 */
    public static void index() {
        render();
    }
    
    /**
     * Displays the search page
     */
    public static void search(){
    	List<Physician> physicians = Physician.findAll();
    	List<Patient> patients = Patient.findAll();
    	
    	render(physicians, patients);
    }
    
    
    public static void results(Long physicianId, Long patientId, Date start, Date end){
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
     * Searches for all exams by the given physician
     * @param physicianId the physician's ID
     *
    public static void searchByPhysician(@Required Long physicianId){
    	System.out.println("Searching for id "+physicianId);
    	Physician tmp = Physician.findById(physicianId);
    	
    	results( tmp.getExams() );
    }
    
    /**
     * Searches for all exams for the given patient
     * @param patientId the patient's ID
     *
    public static void searchByPatient(@Required Long patientId){
    	System.out.println("Searching for id "+patientId);
    	results( Repository.searchByPatient(patientId) );
    }*/

}