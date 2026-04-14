import java.io.File;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class MedicationDataHelper{


	
	/*
	
	PAGE	PURPOSE			FUNCTION SIGNATURE
	
	1	Save Med to New File	saveMedicationToNewJsonFile(
					Medication newMed)
	
	2	Med-Med File Pair	validateOverwritePair(
		Valid for Overwrite	File savedMedFile, 
					Medication newMed) 

	3	Get Saved Med File	getSavedMedicationFromSaved 
		via Saved Med		MedicationFile(
					File target_savedMedFile){
	
	4	Verify file is in	isMedFileByName(File file)
		Med File Name Format
		
	
		
	This class contains functions that assist in 
	MedicationData functionality.
	
	*/




















/*
				     Page 0

								    CTRL + F -->
*/
	

	// Saving to New Medication File Function //
	// (Assists mainly in saveMedicationToJsonFile in MedicationData)
	
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
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// Validate Medication File and Medication for Overwrite Function //
	// (Assists in overwriteJsonFileWithNewMedication in MedicationData)
	
	// Checks if provided Medication file and provided Medication
	// are a valid pair for an overwrite by verifying
		
		// Medication has not been saved already
		// Medication and Medication file are actually different
		
	// Throws IllegalArgumentException if either check is failed


	public static void validateOverwritePair(File savedMedFile, 
	Medication newMed) throws Exception{

		// First Check: newMed not saved already 
 
		boolean newMedSavedAlready = 
		MedicationData.SAVED_MEDICATION.containsKey(newMed);		

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
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/

	
	// Get Saved Medication From Saved Medication File Function //
	// (Assist function used throughout MedicationData and its related
	// helper classes)

	// Retrieves saved Medication key for provided saved Medication file 
	// value in MedicationData.SAVED_MEDICATION
	

	public static Medication getSavedMedicationFromSavedMedicationFile(
	File target_savedMedFile){

		for(Medication savedMed : 
		MedicationData.SAVED_MEDICATION.keySet()){

			File savedMedFile = 
			MedicationData.SAVED_MEDICATION.get(savedMed);

			if(savedMedFile.equals(target_savedMedFile)){
			
				return savedMed;

			}
		}

		// SAVED_MEDICATION is a one-to-one mapping of key, values
		// so this will always be accurate

		return DefaultMedicationData.DEFAULT_MEDICATION;	

	}




















/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/

		
	// Validating a File is a Medication JSON file // 
	// (Assists in getAllMedicationJsonFilesHere in MedicationData)

	// Checks if a File object fits
	// the Medication JSON file naming convention
	// by attempting to matching it's name to a regex pattern

	public static boolean isMedFileByName(File file) throws Exception{

		String fileName = file.getName();
	
		// Accepting default Medication file directly
		// because it does not follow the naming convention

		// but is still a valid Medication file
	
		// and is needed to be accepted for the other default data types
		// derived from it to be built
		
		if(fileName.equals("DefaultMedication.json")){

			return true;

		}
		
		// Aside from the above edge case,
		// Medication files are identified by matching the file name
		// to a regex pattern corresponding to 
		// "Medication_[1 or more characters].json"
				
		String pattern = "Medication_.+\\.json";

		return fileName.matches(pattern);
	}
















/*
				     Page 4

<-- CTRL + B							    
*/	
}
