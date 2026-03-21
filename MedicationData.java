import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

import java.lang.RuntimeException;
import java.lang.SecurityException;

import java.util.HashMap;
import java.util.ArrayList;

public class MedicationData{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	Static Variables	

	2	SAVE MED TO FILE	saveMedicationToJsonFile(
					Medication med)

	3	LOAD MED FROM FILE	loadMedicationFromJsonFile(
					File jsonFile)

	4	OVERWRITE MED FILE	overwriteJsonFileWithNewMedication(
		WITH NEW MED		File jsonFile, Medication newMed)

	5	DELETE MED FILE		deleteMedicationFile(File jsonFile)			
	6	Get All Med Files	getAllMedicationJsonFilesHere() 
 
	
	
	This class contains the primary save and load functionality
	for Medication objects.

	This allows Medication data to be stored and accessed locally.

	*/












/*
				     Page 0

								    CTRL + F -->
*/

	
	// Static Variables // 

	
	// One-to-One Mapping of Medication
	// and local JSON Files that represent Medication

	// For ease of access of all Med files

	// Must be manually updated per file action
	
	public static HashMap<Medication,File> SAVED_MEDICATION;

	static {

		try{
			SAVED_MEDICATION = new HashMap<Medication,File>();

			ArrayList<File> allMedicationJsonFiles = 
			getAllMedicationJsonFilesHere();

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
		
		File new_medFile = 
		MedicationDataHelper.saveMedicationToNewJsonFile(med);
		
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

			MedicationDataHelper.validateOverwritePair(
			savedMedFile, newMed);

		}

		catch(IllegalArgumentException e){

			System.err.println(e);

			return false;

		}
		
		Medication savedMed = MedicationDataHelper.
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

			Medication savedMed = MedicationDataHelper.
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


	// Get All Locally Saved Medication Files Function // 
	// (Assists in populating SAVED_FILES upon initialization)

	// Returns ArrayList of Files representing all Medication JSON Files
	// in the local directory

	// Ths function excludes the default Medication file
	// in its retrieval as it should not be accessible directly
	
	// Note: This function is privated
	// as it should only be used once in this class 
	
	private static ArrayList<File> getAllMedicationJsonFilesHere() 
	throws Exception{

		// ArrayList used for collecting Files instead of Array
		// because number of incoming Files is unknown
		
		ArrayList<File> fileArrayList = new ArrayList<File>();
					
		File currentDirectory = new File("./");
				
		for(File file : currentDirectory.listFiles()){

			// Skip all subdirectories

			if(file.isDirectory()){
				continue;
			}

			// Skip default Medication file

			File defaultMedicationFile = 
			DefaultMedicationData.DEFAULT_MEDICATION_FILE;

			if(file.equals(defaultMedicationFile)){
				continue;
			}

			// Include Files that fit Medication file name format

			if(MedicationDataHelper.isMedFileByName(file)){
				fileArrayList.add(file);
			}
		}

		return fileArrayList;
	}


/*
				     Page 6

<-- CTRL + B							    
*/
}
