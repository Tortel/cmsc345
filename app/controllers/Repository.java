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
	
	
	public List<Exam> searchByDate(Date first, Date last){
		
		return null;
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
	
}
