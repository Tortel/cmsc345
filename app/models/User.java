package models;

import javax.persistence.*;

import play.db.jpa.*;
import repo.Repository;

/**
 * This class is an abstract class to represent users in the system.
 *
 */
@Entity
public abstract class User extends Model {
	private String username;
	private byte[] password;
	private String firstName;
	private String lastName;
	
	/**
	 * Creates a new user with the given username and password
	 * @param username The username (Email address)
	 * @param password The password
	 */
	protected User(String username, String password, String firstName, String lastName){
		this.username = username;
		this.password = Repository.encodePassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * Sends the user their password over email
	 */
	public void recoverPassword(){
		//TODO: Implement this
	}
	
	public String getUsername(){
		return username;
	}
	
	public byte[] getPassword(){
		return password;
	}
	
	/**
	 * Get the user's full (first and last) name
	 * @return 
	 */
	public String getName(){
		return firstName + " " + lastName;
	}
	
}
