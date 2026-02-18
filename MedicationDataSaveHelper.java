import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.util.ArrayList;

import java.util.HashSet;

import java.lang.IllegalArgumentException;

public class MedicationDataSaveHelper {

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	CHECK IF MED SAVED	checkIfSavedAlready(Medication newMed)

	2	Medication		medicationToJsonString(Medication med)
		-->
		JSON String
		
	3	New Med File Num	generateNewFileName()

	4	Get Num of Med File	getFileNumber(File jsonFile)

	5	Make New File Num	generateNewFileNumber()
		
	
	
	This class contains functions involving Medication file
	save necessity, Medication serialization to String,
	and Medication file numbering.

	These functions are primarily meant to assist in the process of
	saving a Medication instance locally to a JSON file, 
	featured in the MedicationData class.
	
	*/













/*
				     Page 0

								    CTRL + F -->
*/


	// CHECKING IF MEDICATION HAS BEEN SAVED LOCALLY ALREADY // 

	// Checks if provided Medication instance is equivalent
	// to any of the existing Medication JSON files saved locally
	
	// Returns true if so, false if not

	public static boolean checkIfSavedAlready(Medication newMed)
	throws Exception{

		// Check if any existing local Medication file
		// logically equivalent to newMed

		ArrayList<File> allMedFiles = 
		MedicationDataHelper.getAllMedicationJsonFilesHere();
		
		for(File existingMedFile : allMedFiles){
				
			boolean existingMedFileMatchNewMed =
			MedicationDataHelper.
				areJsonFileAndMedicationSame(
					existingMedFile,
					newMed);

			// If an existing Medication JSON File
			// correlates to newMed

			// then newMed has been saved already
		
			if(existingMedFileMatchNewMed){
				return true;
			}
				
		}
		
		// If none of the existing Med files correlate to newMed
		// then newMed has NOT been saved already

		return false;

	}








/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// CONVERTING MEDICATION INTO JSON-FORMATTED STRING // 
	
	// Converts provided Medication instance 
	// into a returnable JSON-Formatted String
	// that represents the Medication
	
	public static String medicationToJsonString(Medication med){

		// Load individual class properties of Medication instance
		// into a JsonObject hashmap (from json-simple package)
				
		JsonObject medJsonObject = new JsonObject();

		medJsonObject.put("name", med.getName() );
		medJsonObject.put("dosage", med.getDosage() );
		medJsonObject.put("type", med.getType() );
		medJsonObject.put("totalHoursOfClarity", 
			med.getTotalHoursOfClarity() );

		// This JsonObject can then return 
		//the desired JSON-formatted String

		String jsonString = medJsonObject.toJson();
	
		return jsonString;
		
	}






















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/
		
	
	// Generating a New Medication JSON File Name //  
	// (this is a secondary function)

	// Returns a String representing a valid, unused file name
	// for a Medication JSON File
	
	public static String generateNewFileName() throws Exception {
		
		int newFileNumber = generateNewFileNumber();

		String newFileName = "Medication_" + 
		Integer.toString(newFileNumber) + ".json";

		return newFileName;	 	

	}

































/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
	
	
	// Getting File Number for a Medication JSON File //
	// (this is a secondary function)

	// Retrieves the returnable file number int
	// for a provided Medication JSON File

	public static int getFileNumber(File jsonFile) throws Exception{
	
		// Verify jsonFile is a Medication JSON File		
	
		if(!MedicationDataHelper.isMedFileByName(jsonFile)){
			
			String recoverable_error_message = "File that does " +
			"not fit the Medication jsonFile naming convention " + 
			"passed to getFileNumber(File jsonFile). " + 
			"Returning -1 as a jsonFile number.\n";
	
			IllegalArgumentException recoverable_error =
			new IllegalArgumentException(
				recoverable_error_message);

			System.err.print(recoverable_error);

			return -1;	
	
		}
	
		// Locate the file number via the file name 
			
		String jsonFileName = jsonFile.getName();
	
		// Use regex pattern that matches the characters surrounding
		// the jsonFile number ('_' and '.')

		String pattern = "_|\\.";

		// Splitting jsonFileName with this pattern will return
		// an array of 3
		// where the 2nd element is the jsonFile number 
 
		String[] matches = jsonFileName.split(pattern);
			
		int jsonFileNumber = Integer.valueOf(matches[1]);

		return jsonFileNumber;

	}


/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/

	
	// Generate File Number for a New Medication JSON File //	
	// (this is a secondary function)

	// Generates the next lowest int that be can used for naming
	// a Medication JSON file

	public static int generateNewFileNumber() throws Exception{
		
		// HashSet to efficiently keep track of existing file numbers

		HashSet<Integer> existingFileNumbers =
		new HashSet<Integer>(3);	

		// Iterate through every existing local Medication file
		// to get each existing file number
		// and add the number to the hash set

		ArrayList<File> allMedFiles = 
		MedicationDataHelper.getAllMedicationJsonFilesHere();	
		
		for(File existingMedFile : allMedFiles){
				
			int existingMedFileNumber = 
			getFileNumber(existingMedFile);
				
			existingFileNumbers.add(existingMedFileNumber);
		}
		
		// Once all the existing file numbers have been collected

		// Increment a new file number variable (starting at 1)
		// until it reaches a file number that doesn't exist already
		// (verified by checking if the int appears in the file numbers)
			
		int uniqueFileNumber = 1;
		
		while(existingFileNumbers.contains(
			(Integer) uniqueFileNumber)){

			uniqueFileNumber++;
		}	
		
		return uniqueFileNumber;	
	
	}




/*
				     Page 5

<-- CTRL + B							    
*/
}
