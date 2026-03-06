import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.lang.StringBuilder;

import java.time.LocalDateTime;
import java.time.DateTimeException;
	
import java.time.format.DateTimeFormatter;

public class User{

	/*
	
	PAGE	CONTENTS

	1	Instance Variables

	2	Constructors

	3	Accessor Methods

	4	Modifier Methods

	5	equals(Object o)

	6	toString()
	
	7-8	toString() helper functions

	7		getDailyRoutineString()
	8		getWhenLastTakenString()
	
	9	hashcode() -- equals() helper function	
	

	
	This class defines the User object, used to represent
	how and when the user of this application takes their ADHD
	medication(s).
	
	*/









/*
				     Page 0

								    CTRL + F -->
*/


	// INSTANCE VARIABLES //


	// Name of the User (first name is sufficient)

	private String name;


	// Array of Medication objects representing
	// Medications that the User takes on a daily basis

	// This will likely be only 1-3 elements long

	// Order of which Medication to take first represented by array order

	private Medication[] dailyRoutine;


	// Hashmap contains timestamps for when 
	// each Medication was last taken by User

	// Each timestamp is paired with the Medication
	// that the User took at that time

	private HashMap<Medication, LocalDateTime> whenLastTaken;

























/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/
	
	
	// CONSTRUCTORS // 


	// Constructor for brand-new User (no history of taking Meds)
	// (Note to self: use this for initial class testing)


	// No med taking history = not loaded from file, initialized
	// in-line...for now

	// no Med taking history = no hashmap in the constructor args
	// whenLastTaken gets initialized as an empty hashmap though

	// because whenLastTaken needs to be initialized as a class variable
	// it just represents a User with no med taking history

	public User(
			String instance_name,
			Medication[] instance_dailyRoutine
	){

		name = instance_name;

		dailyRoutine = instance_dailyRoutine;

		whenLastTaken = new HashMap<Medication, LocalDateTime>();
 
	}

	// Constructor for User with history of taking their meds
	// (Note to self: probably used in loading a local JSON file
	// about an existing User)

	public User(
			String instance_name,
			Medication[] instance_dailyRoutine,
			HashMap<Medication, LocalDateTime> 
			instance_whenLastTaken
		
	){

		name = instance_name;

		dailyRoutine = instance_dailyRoutine;

		whenLastTaken = instance_whenLastTaken;
	}	



/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// ACCESSOR METHODS //


	public String getName(){
	
		return name;

	}

	public Medication[] getDailyRoutine(){

		return dailyRoutine;

	}
	

	public HashMap<Medication, LocalDateTime> getWhenLastTaken(){

		return whenLastTaken;

	}





























/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// MODIFIER METHODS // 


	public void setName(String newName){
		
		name = newName;	
	
	}

	public void setDailyRoutine(Medication[] newDailyRoutine){

		dailyRoutine = newDailyRoutine;

	}

	public void setWhenLastTaken(HashMap<Medication, LocalDateTime>
	newWhenLastTaken){

		whenLastTaken = newWhenLastTaken;

	} 




























	
/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/


	// EQUALS FUNCTION //

	
	@Override
	public boolean equals(Object o){
		
		// Accept equivalency if comparing to itself, the User instance

		if(o == this){
			return true;
		}
		
		// Reject equivalency if comparing to a non-User object
		
		if(!(o instanceof User)){
			return false;
		}

		// Provided Object is validated as User instance
		// Can be casted to User instance now

		User otherUser = (User) o;

		// Reject equivalency though if User casting returns null User
		
		if(Objects.isNull(otherUser)){
			return false;
		}

		// After all edge cases have been addressed

		// Evaluate the equivalency of the two User instances
		// based off the equivalency of their class properties

		String otherUserName = otherUser.getName();
	
		Medication[] otherUserDR = otherUser.getDailyRoutine();
	
		HashMap<Medication, LocalDateTime> otherUserWLT =
		otherUser.getWhenLastTaken();
			
		return 	name.equals(otherUserName) &&

			Arrays.deepEquals(dailyRoutine, otherUserDR) &&

			whenLastTaken.equals(otherUserWLT);


	}

/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// TO STRING FUNCTION //  

	
	@Override
	public String toString(){

		String user_string = "User defined as follows:\n\n";

		user_string += "Name: " + name 
				+ "\n\n";

		user_string += "Daily Routine of Medications: " + 
				"\n\n" + 
				getDailyRoutineString() + 
				"\n\n";

		user_string += "When Each Of User's Medications Was Last " + 
				"Taken: " + 
				"\n\n";

		try {
	
			user_string += getWhenLastTakenString();

		} catch (Exception e){
	
			// Try-catch used to handle possible exceptions
			// from helper function getWhenLastTakenString()
			// because a function that overrides toString()
			// cannot throw an Exception (because toString()
			// does not throw one)
 
			String error_message = "Helper function " + 
			"getWhenLastTaken() returned an Exception, returning " +
			"default toString for the whenLastTaken hashmap " +
			"as an alternative.\n";
			
			user_string += whenLastTaken.toString();
		}

		user_string += "\n\n"; 

		return user_string;		


	}




/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// Class property dailyRoutine toString Function //
	// This secondary function assists toString()


	// Returns String representing contents of class property dailyRoutine,
	// which is an array of Medication objects

	public String getDailyRoutineString(){
		
		String dailyRoutineString = "[";

		for(int i = 0; i < dailyRoutine.length; i++){
	
			Medication routineMed = dailyRoutine[i];
			
			dailyRoutineString += "\t" + 
			routineMed.getCompleteInfo();
		
			if(i != dailyRoutine.length - 1){
				dailyRoutineString += ",\n";
			}		
		}

		dailyRoutineString += "\t]";

		return dailyRoutineString;

	}






















/*
				     Page 7

<-- CTRL + B							    CTRL + F -->
*/


	// Class property whenLastTaken toString Function //
	// This is secondary function assists toString()


	// Returns String representing
	// contents of class property whenLastTaken,
	// which is a HashMap containing pairs of Medication and LocalDateTime

	public String getWhenLastTakenString() throws Exception{

		// Utilizing the forEach method that takes in
		// a BiConsumer functional interface
		// which can be assigned to a lambda expression

		// StringBuilder used because lambda expressions can only
		// reference final or effectively final variables
		// so modifying a String variable like "+=" will not compile
				
		StringBuilder whenLastTakenStringBuilder = new StringBuilder();

		whenLastTakenStringBuilder.append("{");
	
		whenLastTaken.forEach(

			(takenMed, takenMedAt) -> {
				
				whenLastTakenStringBuilder.append("\t" + 
				takenMed.getCompleteInfo() + 
				" | " +	
				takenMedAt.format(UserData.DISPLAY_FORMAT) +
				",\n");				
			}
		);

		String whenLastTakenString = 
		whenLastTakenStringBuilder.toString();
			
		// Removing the comma and line break appended after final entry

		if (whenLastTaken.size() > 0){
		
			whenLastTakenString = whenLastTakenString.substring(
			0, whenLastTakenString.length() - 2);

		}
		whenLastTakenString += "\t}";

		return whenLastTakenString;

	}	
/*
				     Page 8

<-- CTRL + B							    CTRL + F -->
*/


	// Hashcode Class Function //
	// This secondary function assists equals()

	 
	// Function derived from this StackOverflow post

	// https://stackoverflow.com/questions/18965374/
	// overriding-hashcode-in-java
		
	@Override
	public int hashCode(){

		int result = 1;

		result = 31 * result + 
		(name == null ? 0 : name.hashCode());

		// deepHashcode here should be used to compare
		// Medication[] based on Java docs saying that
		// the deepHashcode numbers should equate if two
		// arrays are equivalent according to deepEquals(a,b)

		result = 31 * result + 
		(whenLastTaken == null ? 0 : Arrays.deepHashCode(dailyRoutine)
		);

		result = 31 * result + 
		(whenLastTaken == null ? 0 : whenLastTaken.hashCode()
		);

		return result;
	}


















/*
				     Page 9

<-- CTRL + B							    
*/	 
}
