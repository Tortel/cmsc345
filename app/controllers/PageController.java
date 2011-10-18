package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

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
    
    public static void searchByPhysician(Long id){
    	render();
    }
    
    public static void searchByPatient(Long id){
    	render();
    }

}