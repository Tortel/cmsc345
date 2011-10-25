package controllers;

import play.mvc.Controller;

public class Dummy extends Controller {

	public static void index(){
		if(!Security.isConnected())
			render();
		else
			PageController.welcome();
	}
	
	public static void createAccount(){
		render();
	}
	
	
}
