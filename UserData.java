import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Set;

public class UserData{

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	STATIC VARIABLES

	2-3	Check for unsaved Meds	allMedicationsSavedCheck(User user)

	4	SAVE USER		saveUserToJsonFile(User user) 

	5	LOAD USER FROM FILE	loadUserFromJsonFile() 

	
	This class contains the primary save and load functionality
	for User objects.

	This allows User data to be stored and accessed locally, after
	some initial serialization 

	(check UserDataSaveHelper class for more on this process)
	
	*/


















/*
				     Page 0

								    CTRL + F -->
*/


	// STATIC VARIABLES //

		
	// For printing LocalDateTime objects to console
 
	public static final DateTimeFormatter DISPLAY_FORMAT = 
	DateTimeFormatter.ofPattern("MMM dd yyyy h':'mm' 'a");

	// For accessing the sole User JSON file locally saved
	
	// Note to self: This static final variable
	// has been tested and proven to change its modification
	// date when
	
	public static final File USER_FILE = new File("User.json");



































/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// Check for Unsaved Medication from User Function //


	// This function checks if any Medication instances 
	// from within the provided User class properties
	// have not been saved yet

	// If so, this function saves them locally
 
	public static void allMedicationsSavedCheck(User user)
	throws Exception{

		// Check for unsaved Medication in dailyRoutine

		for (Medication routineMed: user.getDailyRoutine()){
			
			boolean routineMedSavedCheck = 
			MedicationData.SAVED_MEDICATION.containsKey(
			routineMed);

			if (!routineMedSavedCheck){
				
				File routineMedFile = MedicationDataHelper.
				saveMedicationToNewJsonFile(routineMed);
			
			}
		}























/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/

	
	// Check for Unsaved Medication from User Function //
	// (contd.)


	// Check for unsaved Medication in whenLastTaken

	HashMap<Medication,LocalDateTime> user_whenLastTaken =
	user.getWhenLastTaken();

	user_whenLastTaken.forEach(

		// Trade-off of implementing lambda expression for Biconsumer
		// functional interface applied to each entry during forEach
		// is that Exceptions must be handled via try-catch

		(takenMed, takenMedAt) -> {

			try{
		
				boolean takenMedSavedCheck = 
				MedicationData.SAVED_MEDICATION.containsKey(
				takenMed);

				if (!takenMedSavedCheck){
					
					MedicationDataHelper.
					saveMedicationToNewJsonFile(
					takenMed);		
				}
			}

			catch (Exception e){

				String error_message =
 
				"An exception occured either while " +
				"verifying a Medication inside whenLastTaken " +				"was saved already, or saving an unsaved " +
				"Medication to a new Medication JSON File; " +
				"Medication inside whenLastTaken may remain " + 				"unsaved.\n";

				System.err.print(error_message);			
			}
		}
	);

	}

/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// USER SAVE FUNCTION //

	// Saves a provided User instance locally under "User.json"


	public static void saveUserToJsonFile(User user) throws Exception {

		// Ignore overwrite request if existing User file equates
		// to provided User instance, as overwrite would then be
		// unnecessary

		if(USER_FILE.exists()){

			User currentlySavedUser = loadUserFromJsonFile();

			if(user.equals(currentlySavedUser)){

				System.out.println("Equivalent User file " + 
				"already saved, cancelling overwrite."); 

			}

		}

		// First, save any unsaved Medication from user 

		allMedicationsSavedCheck(user);

		// Then perform the following
		// User instance -> JSON-formatted String -> JSON File

		String user_jsonString = 
		UserDataSaveHelper.userToJsonString(user);

		// It is safe to assume that only one User exists at a time
		// Therefore overwriting existing User JSON file is acceptable

		LocalData.jsonStringToJsonFile(user_jsonString, "User.json");

	}










/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/


	// USER LOAD FUNCTION //

	// Returns User instance loaded from locally saved "User.json" file

	public static User loadUserFromJsonFile() throws Exception {
	
		String user_JsonString = LocalData.jsonFileToJsonString(
		USER_FILE);

		JsonObject user_JsonObject = 
		LocalData.jsonStringToJsonObject(user_JsonString);

		UserDataLoadHelper.validateJsonObjectForUser(user_JsonObject);

		User user = UserDataLoadHelper.validatedJsonObjectToUser(
		user_JsonObject);

		return user; 

	}






























/*
				     Page 5

<-- CTRL + B 
*/
}
