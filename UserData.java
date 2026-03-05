import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import java.time.LocalDateTime;

public class UserData{

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	STATIC VARIABLES

	2	Check for unsaved Meds	allMedicationsSavedCheck(User user)

	3	dailyRoutine:		serializeDailyRoutine(User user)
		Medication[] ->
		String[]	

	4	TODO				


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

		// Check for unsaved Medication in whenLastTaken

		HashMap<Medication,LocalDateTime> user_whenLastTaken =
		user.getWhenLastTaken();

		Iterator<Medication> user_whenLastTaken_keys_iter = 
		user_whenLastTaken.keySet().iterator();
		
		while(user_whenLastTaken_keys_iter.hasNext()){

			Medication takenMed = 
			user_whenLastTaken_keys_iter.next();
	
			boolean takenMedSavedCheck = MedicationDataSaveHelper.
			checkIfSavedAlready(takenMed);

			if (!takenMedSavedCheck){
				
				File takenMedFile = MedicationData.
				saveMedicationToNewJsonFile(takenMed);		
			}
		}	
	}
/*
				     Page 2

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















/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


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
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/	
	


}
