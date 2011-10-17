package controllers;

import java.util.*;

import models.*;

/**
 * Class that contains many general functions for the system.
 */
public class Repository {
	
	/**
	 * Checks if the given username/password is valid
	 * @param username the username
	 * @param password the password
	 * @return true if the username/password are valid
	 */
	public boolean login(String username, String password){
		User tmp = User.find("byUsernameAndPassword", username, encodePassword(password)).first();
		if(tmp != null)
			return true;
		else
			return false;
	}
	
	
	/**
	 * Searches for exams within the given dates
	 * @param first the beginning of the date range
	 * @param last the end of the test range
	 * @return a list of all the results
	 */
	public List<Exam> searchByDate(Date first, Date last){
		ArrayList<Exam> exams = new ArrayList<Exam>();
		
		//TODO: Search for exams within the range
		
		return exams;
	}
	
	/**
	 * Gets all the exams for a given patient
	 * @param patientId the patient's ID
	 * @return a list of all their exams
	 */
	//Maybe search by name? Im thinking a dropdown for this
	public List<Exam> searchByPatient(Long patientId){
		Patient patient = User.findById(patientId);
		if(patient != null)
			return patient.getExams();
		else
			return new ArrayList<Exam>(0);
	}
	
	/**
	 * Gets all the exams by a given physician
	 * @param physicianId the physician's ID
	 * @return a list of all their exams
	 */
	public List<Exam> searchByPhysician(Long physicianId){
		Physician physician = Physician.findById(physicianId);
		if(physician != null)
			return physician.getExams();
		else
			return new ArrayList<Exam>(0);
	}
	
	
	
	/**
	 * Encodes the given string
	 * @param plaintext the string to encode
	 * @return the encoded string
	 */
	public String encodePassword(String plaintext){
		//TODO: Come up with some encoding process
		return plaintext;
	}
	
	/**
	 * Decodes the given string
	 * @param encoded the encoded string
	 * @return the decoded string
	 */
	public String decodePassword(String encoded){
		//TODO: Come up with the decoding process
		return encoded;
	}
	
	//Might not need this
	public void makePhysician(){
		
	}
	
	//Might not need
	public void makePatient(){
		
	}
	
}
