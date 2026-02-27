import java.util.Objects;
import java.util.Arrays;


import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;	

public class User{

	/*
	
	PAGE	CONTENTS

	1	Instance Variables

	2	Constructors

	3	Accessor Methods

	4	Modifier Methods

	5	toString() Function

	6-7	equals(Object o) Function
	
	8	hashcode() Function	
	

	
	This class defines the Medication object, used to represent
	the individual ADHD medications that the user takes.
	
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


	// TO STRING FUNCTION //  

	
	@Override
	public String toString(){

		String user_string = "User defined as follows:\n\n";

		user_string += "Name: " + name + "\n\n";

		user_string += "Daily Routine of Medications: " + "\n\n" + 
				getDailyRoutineString() + "\n\n";

		user_string += "When Each Of User's Medications Was Last " + 
				"Taken:\n\n";
		try{
			user_string += getWhenLastTakenString();
		} catch (Exception e){
			System.out.print("nah son!\n");
			
			user_string += whenLastTaken.toString();
		}

		user_string += "\n\n"; 

		return user_string;		


	}





















/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// Class property dailyRoutine toString Function //
	// (this is a secondary function)

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
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// Class property whenLastTaken toString Function //
	// (this is a secondary function)

	public String getWhenLastTakenString() throws Exception{

		String whenLastTakenString = "{";

		Iterator<Medication> whenLastTaken_keys_iter = 
		whenLastTaken.keySet().iterator();
		
		while(whenLastTaken_keys_iter.hasNext()){

			Medication takenMed = whenLastTaken_keys_iter.next();

			LocalDateTime takenMedTime = 
			whenLastTaken.get(takenMed);

			DateTimeFormatter display_format = 
			DateTimeFormatter.ofPattern("MMM dd yyyy h':'mm' 'a");

			String takenMedTimeString =  
			takenMedTime.format(display_format);

			whenLastTakenString += "\t" + 
			takenMed.getCompleteInfo() + " | " + takenMedTimeString;

			if(whenLastTaken_keys_iter.hasNext()){

				whenLastTakenString += ",\n";
			}	

		}	
		
		whenLastTakenString += "\t}";

		return whenLastTakenString;

	}	












/*
				     Page 7

<-- CTRL + B							    CTRL + F -->
*/


	// EQUALS FUNCTION //
	
	@Override
	public boolean equals(Object o){
		
		// Accept equivalency if the provided object
		// is the User instance it is comparing itself to

		if(o == this){

			return true;

		}
		
		// Reject equivalency if the provided object
		// is not a User instance
		
		if(!(o instanceof User)){

			return false;

		}

		// Provided object can be validly casted
		// to a User instance now

		User otherUser = (User) o;

		// Reject equivalency if provided User is a null object
		
		if(Objects.isNull(otherUser)){

			return false;

		}















/*
				     Page 8

<-- CTRL + B							    CTRL + F -->
*/


	// EQUALS FUNCTION //
	// (contd.)	
	
		// After all edge cases have been addressed

		// Evaluate the equivalency of the two User instances
		// based off the equivalency of their class properties

		String otherUserName = otherUser.getName();
	
		Medication[] otherUserDR = otherUser.getDailyRoutine();
	
		HashMap<Medication, LocalDateTime> otherUserWLT =
		otherUser.getWhenLastTaken();
			
		return 	name.equals(otherUserName) &&

			// Note to self:
			
			// Because there is no non-static equals() method
			// for the Arrays class, calling equals() from
			// a non-static context of an Arrays instance
			// leads to the Object class default equals()
			// which is to compare references in memory
			// which is what == does to non-primitives

			// call Arrays.equals(obj, obj_2) instead
			// however note that this will check if each
			// matching pair of elements in the two arrays
			// are the same reference, not contents
			
			// Arrays.deepEquals(obj, obj_2) does this

			// it recursively checks the contents of each
			// object in the array 

			// We don't need this in the HashMaps because
			// mutable keys aka arrays are not allowed

			// ...i think	

			// but not HashMaps of Medications, LocalDateTime??	
			

			Arrays.deepEquals(dailyRoutine, otherUserDR) &&
			//dailyRoutine.equals(otherUserDR) &&

			whenLastTaken.equals(otherUserWLT);

	}
/*
				     Page 9

<-- CTRL + B							    CTRL + F -->
*/


	// Hashcode Class Function //
	// (This is a secondary function)

	 
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


	 
}
