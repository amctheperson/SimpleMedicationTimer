import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDateTime;

public class UserData{

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	STATIC VARIABLES

	2-3	Check for unsaved Meds	allMedicationsSavedCheck(User user)



	This class is a work in progress.
	
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


	// Check for Unsaved Medication from User Function //


	// This function checks if any Medication instances 
	// from within the provided User class properties
	// have not been saved yet

	// If so, this function saves them locally
 
	public static void allMedicationsSavedCheck(User user)
	throws Exception{

		// Check for unsaved Medication in dailyRoutine

		for (Medication routineMed: user.getDailyRoutine()){
			
			boolean routineMedSavedCheck = MedicationDataSaveHelper.			checkIfSavedAlready(routineMed);

			if (!routineMedSavedCheck){
				
				File routineMedFile = MedicationData.
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
				MedicationDataSaveHelper.
				checkIfSavedAlready(takenMed);

				if (!takenMedSavedCheck){
					
					MedicationData.
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


	public static File saveUserToJsonFile(User user) throws Exception {

		// Save any unsaved Medication inside user

		allMedicationsSavedCheck(user);

		// User instance -> JSON-formatted String -> JSON File

		String user_jsonString = 
		UserDataSaveHelper.userToJsonString(user);

		// Since this is (ultimately going to be) a phone application

		// It is safe to assume that only one User exists

		// Therefore overwriting the existing User.json file is okay

		File jsonFile = 
		LocalData.jsonStringToJsonFile(user_jsonString, "User.json");

		return jsonFile;	


	}

	// TO DO 

	// Move user class property functions to UserDataSaveHelper
	
	// jsonFileTojsonString should be Local Data too lol

	// UserDataLoad class

		// JSON String -> User class	

	// Continue learning about Functional Interfaces








	
	

























/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/	
	


}
