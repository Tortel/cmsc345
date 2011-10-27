package repo;

import play.*;
import play.jobs.*;
import play.test.*;
 
import models.*;
 
@OnApplicationStart
public class Bootstrap extends Job {
 
	@SuppressWarnings("unused")
	public void doJob() {
        // Check if the database is empty
        if(User.count() == 0) {
        	//The really handy YML file doesnt handle inheritance well
            //Fixtures.loadModels("data.yml");
        	Physician bob = new Physician("bob@gmail.com", "secret", "Bob", "Doctor").save();
        	Physician george = new Physician("george@gmail.com", "secret", "George", "Test").save();
        	Patient bill = new Patient("bill@gmail.com", "secret", "Bill", "Sick", "123 Main Street", "410-000-0000", 'M').save();
        	Patient harriet = new Patient("harriet@gmail.com","secret", "Harriet", "Blarg", "124 Main Street", "410-000-0001", 'F').save();
        	
			Exam exam1 = new Exam(bill, george, "Bill Sucks!", "Bob is awesome!", null).save();
        	Exam exam2 = new Exam(harriet, bob, "Harriet is awesome!", "Bob is awesome!", null).save();
        	
        }
    }
 
}