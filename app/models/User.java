package models;

import javax.persistence.*;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import play.db.jpa.*;
import play.libs.Mail;
import repo.Repository;

/**
 * This class is an abstract class to represent users in the system.
 *
 */
@Entity
public abstract class User extends Model {
	private String username;
	private String password;
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
		try {
			//Will need to set up email prefs in the conf to be able to use
			SimpleEmail toSend = new SimpleEmail();
			toSend.setFrom("noreply@something.com");
			toSend.addTo(username, this.getName());
			toSend.setSubject("Ultra Password");
			toSend.setMsg("Your password to Ultra is: "+ Repository.decodePassword( this.getPassword() ) );
			Mail.send(toSend);
		} catch (EmailException e) {
			e.printStackTrace(System.out);
		} 
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String firstName(){
		return firstName;
	}
	
	public String lastName(){
		return lastName;
	}
	
	/**
	 * Get the user's full (first and last) name
	 * @return 
	 */
	public String getName(){
		return firstName + " " + lastName;
	}
	
}
