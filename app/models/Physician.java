package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * Represents a Physician in the system<br>
 * 
 */
@Entity
public class Physician extends User {
	
	
	public Physician(String username, String password){
		super(username, password);
		
	}
}
