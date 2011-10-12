package models;

import javax.persistence.*;

import play.db.jpa.*;

/**
 * This class is an abstract class to represent users in the system.
 *
 */
@Entity
public abstract class User extends Model {
	private String userName;
	private String password;
	
	
	
}
