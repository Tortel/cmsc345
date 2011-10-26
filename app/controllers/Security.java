package controllers;

import models.*;

import repo.*;

/**
 * Class to handle security-related items
 *
 */
public class Security extends Secure.Security {

	/**
	 * Method to authenticate users
	 * @param username the username
	 * @param password the password
	 * @return true if able to login
	 */
	static boolean authenticate(String username, String password) {
		return Repository.login(username, password);	
	}
	
	/**
	 * Redirect user to the welcome page on login
	 */
	public static void onAuthenticated(){
		PageController.welcome();
	}
	
	/**
	 * Redirect the user to the 'landing' page on logout
	 */
	public static void onDisconnected(){
		Dummy.index();
	}
	
	/**
	 * Checks if the connected user is the given role
	 * @param profile the role to check
	 * @return true if user is the given role, false otherwise
	 */
	public static boolean check(String profile){
		//Check if they are even connected
		if(!isConnected())
			return false;
		
		//Check if user is a physician
		if("physician".equals(profile)){
			User user = User.find("byUsername", connected() ).<User>first();
			if(user != null && user.getClass() == Physician.class)
				return true;
		}
		
		return false;
	}
	
	/**
	 * Get the user ID of the logged in user
	 * @return the user ID
	 */
	public static long getUserId(){
		User user = User.find("byUsername", connected() ).<User>first();
		if(user != null){
			return user.id;
		}
		return 0;
	}
	
}
