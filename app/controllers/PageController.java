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
    	render();
    }

}