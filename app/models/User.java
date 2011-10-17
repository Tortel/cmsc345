package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * This class is an abstract class to represent users in the system.
 *
 */
@Entity
public abstract class User extends Model {
	private String username;
	private String password;
	
	/**
	 * Creates a new user with the given username and password
	 * @param username The username (Email address)
	 * @param password The password
	 */
	protected User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Sends the user their password over email
	 */
	public void recoverPassword(){
		//TODO: Implement this
	}
	
	
	
	
}
