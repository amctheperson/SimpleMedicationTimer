import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

import java.util.HashSet;
import java.util.Set;

public class MedicationDataSaveHelper {

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	Medication		medicationToJsonString(Medication med)
		-->
		JSON String
		
	2	New Med File Name	generateNewFileName()
	
	
	
	This class contains functions that assist in the save functionality
	of MedicationData.
	
	*/



























/*
				     Page 0

								    CTRL + F -->
*/


	// Medication to Json-Formatted String Function //
	// (Assists primarily in saveMedicationToNewJsonFile in 
	// MedicationDataHelper)

	// Returns JSON-formatted String of data 
	// representing provided Medication
	// and uses said JsonObject to generate JSON-formatted String	
	
	public static String medicationToJsonString(Medication med){

		// Put class properties of provided Medication 
		// into JsonObject Hashmap (sourced from json-simple package)
	
		JsonObject medJsonObject = new JsonObject();

		medJsonObject.put(
				"name", 
				med.getName()
		);

		medJsonObject.put(
				"dosage",
				med.getDosage()
		);

		medJsonObject.put(
				"type",
				med.getType()
		);

		medJsonObject.put(
				"totalHoursOfClarity", 
				med.getTotalHoursOfClarity()
		);

		// Generate JSON-formatted String of med data from jsonObject 

		String jsonString = medJsonObject.toJson();
	
		return jsonString;
		
	}








/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/
		
	
	// Generate New File Name For Medication JSON File //  
	// (Assists savedMedicationToNewJsonFile in MedicationDataHelper)

	// Returns valid, unused JSON File name 
	// in the Medication file format, derived from the lowest available int
	
	
	public static String generateNewFileName() throws Exception {

		// Get saved Med File names as set of Strings
		
		HashSet<String> savedMedFileNames = new HashSet<String>();

		MedicationData.SAVED_MEDICATION.forEach(

			(savedMed, savedMedFile) -> {
			
				savedMedFileNames.add(savedMedFile.getName());
	
			}
		);

		// Finding first unused Med file number		
		// Note: realistically this would be 1 through 3

		int newFileNumber = 1;	
		String newFileName = "Medication_1.json"; 

		while(savedMedFileNames.contains(newFileName)){

			newFileNumber++;
			newFileName = "Medication_" + 
			Integer.toString(newFileNumber) + ".json";

		}	
		
		return newFileName;	 	

	}











/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/	
}
