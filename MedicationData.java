import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

import java.lang.RuntimeException;
import java.lang.SecurityException;

import java.util.HashMap;
import java.util.ArrayList;

public class MedicationData{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	STATIC VARIABLES	

	2	SAVE MED TO FILE	saveMedicationToJsonFile(Medication med)

	3	LOAD MED FROM FILE	loadMedicationFromJsonFile(
					File jsonFile)

	4	OVERWRITE MED FILE	overwriteJsonFileWithNewMedication(
		WITH NEW MED		File jsonFile, Medication newMed)

	5	DELETE MED FILE		deleteMedicationFile(File jsonFile)			
	6	Save Med to New File	saveMedicationToNewJsonFile(
					Medication newMed)
	
	7	Med-Med File Pair	validateOverwritePair(
		Valid for Overwrite	File savedMedFile, 
					Medication newMed) 

	8	Get Saved Med File	getSavedMedicationFromSaved
		via Saved Med		MedicationFile(
					File target_savedMedFile){
 
	
	
	This class contains the primary save and load functionality
	for Medication objects.

	This allows Medication data to be stored and accessed locally.

	*/




/*
				     Page 0

								    CTRL + F -->
*/

	
	// STATIC VARIABLES // 

	
	// One-to-One Mapping of Medication
	// and local JSON Files that represent Medication

	// For ease of access of all Med files

	// Must be manually updated per file action
	
	public static HashMap<Medication,File> SAVED_MEDICATION;

	static {

		try{
			SAVED_MEDICATION = new HashMap<Medication,File>();

			ArrayList<File> allMedicationJsonFiles = 
			MedicationDataHelper.getAllMedicationJsonFilesHere();

			for(File savedMedFile : allMedicationJsonFiles){

				Medication savedMed = 
				loadMedicationFromJsonFile(savedMedFile);

				SAVED_MEDICATION.put(savedMed, savedMedFile);	
		
			}	
		}
		catch(Exception e){

			String error_message = "Could not initialize static " +
			"variable SAVED_MEDICATION either because of " + 
			" an Exception getting all the Medication Files " +
			"or loading one of them into a Medication instance./n";

			System.err.print(new RuntimeException(error_message));

		} 
	}










/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// SAVING MEDICATION TO MEDICATION FILE FUNCTION // 

	// Saves provided Medication to local JSON File and returns it

	// Note: This function also checks 
	// if provided Medication has been saved already
	// and returns existing JSON file if so
 
		
	public static File saveMedicationToJsonFile(Medication med) 
	throws Exception{

		// Check if med saved already

		boolean medWasAlreadySaved =
		SAVED_MEDICATION.containsKey(med);
	
		if(medWasAlreadySaved){

			return SAVED_MEDICATION.get(med);
				
		}

		// Save to new JSON File and return said File

		// and update SAVED_MEDICATION HashMap accordingly
		
		File new_medFile = saveMedicationToNewJsonFile(med);
		
		SAVED_MEDICATION.put(med, new_medFile);

		return new_medFile;

	}
















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// LOADING MEDICATION FROM MEDICATION FILE FUNCTION //
 
	// Loads provided Medication JSON file into new Medication instance

	// Note: May return default Medication instance 
	// if any exceptions occurred during the conversion process

	public static Medication loadMedicationFromJsonFile(File jsonFile)
	throws Exception{
		
		// Conversion process:
 
		// JSON File --> JSON-formatted String --> 
		// JSON-Object --> Medication
			
		String jsonString = 
		LocalData.jsonFileToJsonString(jsonFile);
		
		JsonObject jsonObject = 
		LocalData.jsonStringToJsonObject(jsonString);

		Medication loadedMed = 
		MedicationDataLoadHelper.jsonObjectToMedication(jsonObject);

		return loadedMed;
		
	}























/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// OVERWRITE MEDICATION FILE WITH NEW MEDICATION // 


	// Overwrites only unsaved new Medication onto a saved Medication file
	// that is logically different
	

	public static boolean overwriteJsonFileWithNewMedication(
	File savedMedFile, Medication newMed) throws Exception{

		// Check if provided pair is valid for overwrite

		try{

			validateOverwritePair(savedMedFile, newMed);

		}

		catch(IllegalArgumentException e){

			System.err.println(e);

			return false;

		}
		
		Medication savedMed = 
		getSavedMedicationFromSavedMedicationFile(savedMedFile);

		String savedMedFileName = savedMedFile.getName();

		String jsonString = 
		MedicationDataSaveHelper.medicationToJsonString(newMed);
	
		// This line does the actual overwriting of saveMedFile
		// with data from newMed
	
		File newMedFile =
		LocalData.jsonStringToJsonFile(jsonString, savedMedFileName);

		// Update SAVED_MEDICATION accordingly

		MedicationData.SAVED_MEDICATION.remove(savedMed);
		MedicationData.SAVED_MEDICATION.put(newMed, newMedFile);

		return true;	
	}



/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/


	// DELETING A MEDICATION FILE // 

	// Deletes provided Medication file from local directory
	
	
	public static boolean deleteMedicationFile(File medFile)
	throws Exception{
		
		try{
			medFile.delete();

			// Update SAVED_MEDICATION upon successful deletion

			Medication savedMed = 
			getSavedMedicationFromSavedMedicationFile(medFile);

			SAVED_MEDICATION.remove(savedMed);
	
			return true;
		}
		catch(SecurityException e){	
		
			String error_message =
			medFile.getName() + " was not deleted because " + 
			"Security manager on device denies write access to " +
			"it.\n"; 

			SecurityException error = 
			new SecurityException(error_message);

			System.err.print(error);

			return false;
		}
	}















/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// Saving to New Medication File //

	// Saves provided Medication to new JSON file and returns said file

	// Note: if an Exception prevents saving from occurring
	// this function returns the default Medication file 
	
	
	public static File saveMedicationToNewJsonFile(Medication newMed)
	throws Exception{
				
		try{

			String newMedFileName = 
			MedicationDataSaveHelper.generateNewFileName();

			String jsonString = 
			MedicationDataSaveHelper.medicationToJsonString(newMed);
			
			File newMedFile =
			LocalData.jsonStringToJsonFile(jsonString, 
			newMedFileName);

			return newMedFile;

		}
		
		catch(Exception e){

			String error_message =
			"The following Medication as follows was not saved " +
			" due to an Exception:\n" + newMed.toString() + "\n" +
			DefaultMedicationData.
			DEFAULT_MEDICATION_FILE_ERROR_MESSAGE_SUFFIX; 
			
			Exception error = 
			new Exception(error_message);		
		
			System.err.print(error);
				
			return DefaultMedicationData.DEFAULT_MEDICATION_FILE;
		}	
	}







/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// Validate Medication File and Medication for Overwrite Function //
	
	// Checks if provided Medication file and provided Medication
	// are a valid pair for an overwrite by verifying
		
		// Medication has not been saved already
		// Medication and Medication file are actually different
		
	// Throws IllegalArgumentException if either check is failed


	public static void validateOverwritePair(File savedMedFile, 
	Medication newMed) throws Exception{

		// First Check: newMed not saved already 
 
		boolean newMedSavedAlready = 
		SAVED_MEDICATION.containsKey(newMed);		

		if(newMedSavedAlready){

			String error_message = 

			"Request to overwrite an existing Medication file " + 
			"with a different Medication that has already been " + 
			"saved detected. Request deemed unnecessary and " +
			"denied.\n";
 
			throw new IllegalArgumentException(error_message);
		}

		// Second Check: savedMedFile and newMed are different 
	
		Medication savedMed = 
		getSavedMedicationFromSavedMedicationFile(savedMedFile);
	
		boolean newMedAndSavedMedFileAreSame = newMed.equals(savedMed);
 
		if(newMedAndSavedMedFileAreSame){

			String error_message =

			"Request to overwrite an existing Medication file " +
			"with an equivalent Medication detected. Request " + 
			" deemed unnecessary and denied.\n";
 
			throw new IllegalArgumentException(error_message);
		}		
	}

/*
				     Page 7

<-- CTRL + B							    CTRL + F -->
*/

	
	// Get Saved Medication From Saved Medication File Function //

	// Essentially retrieves saved Medication key 
	// for provided saved Medication file value in SAVED_MEDICATION
	

	public static Medication getSavedMedicationFromSavedMedicationFile(
	File target_savedMedFile){

		for(Medication savedMed : SAVED_MEDICATION.keySet()){

			File savedMedFile = 
			SAVED_MEDICATION.get(savedMed);

			if(savedMedFile.equals(target_savedMedFile)){
			
				return savedMed;

			}
		}

		// SAVED_MEDICATION is a one-to-one mapping of key, values
		// so this will always be accurate

		return DefaultMedicationData.DEFAULT_MEDICATION;	

	}























/*
				     Page 8

<-- CTRL + B							      
*/
}
