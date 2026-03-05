import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import java.time.LocalDateTime;

public class UserData{

	/*
	
	PAGE	CONTENTS

	1	Static Variables

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


	// STATIC VARIABLES //
 
	public static final DateTimeFormatter DISPLAY_FORMAT = 
	DateTimeFormatter.ofPattern("MMM dd yyyy h':'mm' 'a");














































/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// Check for unsaved Medications from User and Save Function //

	// Within a provided User instance, this function checks to see
	// if any Medication objects inside the dailyRoutine array
	// or the whenLastTaken HashMap key set have not been saved yet

	// If so, this function saves them locally
	// thus, verifying that all Medication represented in this User
	// have been saved already
 
	public static void allMedicationsSavedCheck(User user)
	throws Exception{

		// Check for unsaved Medication in dailyRoutine

		for (Medication routineMed: user.getDailyRoutine()){
			
			boolean routineMedSavedCheck = 
			MedicationDataSaveHelper.checkIfSavedAlready(
				routineMed);

			if (!routineMedSavedCheck){
				
				File routineMedFile = 
				MedicationData.saveMedicationToNewJsonFile(
					routineMed);
			
			}
		}





















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// Check for unsaved Medications from User and Save Function //
	// (contd.)


		// Check for unsaved Medication in whenLastTaken

		HashMap<Medication,LocalDateTime> user_whenLastTaken =
		user.getWhenLastTaken();

		Iterator<Medication> user_whenLastTaken_keys_iter = 
		user_whenLastTaken.keySet().iterator();
		
		while(user_whenLastTaken_keys_iter.hasNext()){

			Medication takenMed = 
			user_whenLastTaken_keys_iter.next();
	
			boolean takenMedSavedCheck = 
			MedicationDataSaveHelper.checkIfSavedAlready(
				takenMed);

			if (!takenMedSavedCheck){
				
				File takenMedFile = 
				MedicationData.saveMedicationToNewJsonFile(
					takenMed);
			
			}
		}	
	}




















/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// Serialize dailyRoutine Medication Array Function //


	// Within a provided User, this function returns a String[]
	// representing the dailyRoutine array of Medication
	// where each String is the File name of a Medication JSON File
	// locally saved

	public static String[] serializeDailyRoutine(User user)
	throws Exception{

		allMedicationsSavedCheck(user);

		Medication[] user_dailyRoutine = user.getDailyRoutine();

		String[] user_dailyRoutine_asFileNames = 
		new String[user_dailyRoutine.length];

		for (int i = 0; i < user_dailyRoutine.length; i++){
			
			Medication routineMed = user_dailyRoutine[i];
			
			File routineMedFile =
			MedicationDataHelper.getExistingMedicationJsonFile(
				routineMed);

			String routineMedFileName = routineMedFile.getName();

			user_dailyRoutine_asFileNames[i] = routineMedFileName;	

		}

		return user_dailyRoutine_asFileNames;

	}


	// TO DO TODAY PREFERABLY 
	
	// Serialize whenLastTaken into Hashmap of String, String pairs
	// with Med file names and json

	// Serialize entire class into jsonable hashmap



	// prepare a string version of User and basically make sure every
	// Medication saved

	// Local date time can be formatted to string,
	// display_format should probably
	// just be defined somewhere, static property of UserData perhaps








	
	
/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/














/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
	
	


}
