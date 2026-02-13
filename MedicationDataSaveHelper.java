import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;
import java.util.ArrayList;

import java.util.HashSet;

import java.util.regex.PatternSyntaxException;

import java.lang.IllegalArgumentException;


public class MedicationDataSaveHelper {


	// File validation: checks if a file fits 
	// Medication file naming convention

	public static int getFileNumber(File file) throws Exception{
		
		if(!MedicationDataHelper.isMedFileByName(file)){
			
			String recoverable_error_message = "File that does " +
			"not fit the Medication file naming convention " + 
			"passed to getFileNumber(File file). Returning -1 " +
			"as a file number.\n";
	
			IllegalArgumentException recoverable_error =
			new IllegalArgumentException(
				recoverable_error_message);

			System.err.print(recoverable_error);

			return -1;	
	
		}
	
		String fileName = file.getName();
	
		// regex pattern that matches the characters surrounding
		// the file number ('_' and '.')

		String pattern = "_|\\.";

		// Splits into an array of 3
		// with the second element being the file number 
 
		String[] matches = fileName.split(pattern);
			
		int fileNumber = Integer.valueOf(matches[1]);

		return fileNumber;

	}
		
	// also tested and should work just fine lfggg
	
	public static int generateNewFileNumber() throws Exception{
		
		// HashSet to efficiently keep track of existing file numbers

		HashSet<Integer> existingFileNumbers =
		new HashSet<Integer>(3);	

		// Iterate through every existing local Medication file

		ArrayList<File> allMedFiles = 
		MedicationDataHelper.getAllMedicationJsonFilesHere();	
		
		for(File existingMedFile : allMedFiles){
			
			// Get each existing file number
				
			int existingMedFileNumber = 
			getFileNumber(existingMedFile);
			
			// and add it to the set
				
			existingFileNumbers.add(existingMedFileNumber);
		}
		
		// Begin file number counting at 1

		int uniqueFileNumber = 1;
		
		// Keep incrementing the unique file number variable
		// until it becomes a number 
		// not seen in the existing file names

		while(existingFileNumbers.contains(
			(Integer) uniqueFileNumber)){

			uniqueFileNumber++;
		}	
		
		return uniqueFileNumber;	
	
	}

	public static String generateNewFileName() throws Exception {
		
		int newFileNumber = generateNewFileNumber();

		String newFileName = "Medication_" + 
		Integer.toString(newFileNumber) + ".json";

		return newFileName;	 	

	}

	public static boolean checkIfSavedAlready(Medication newMed)
	throws Exception{

		ArrayList<File> allMedFiles = 
		MedicationDataHelper.getAllMedicationJsonFilesHere();
	
		// Iterate through every existing local Medication file
		
		for(File existingMedFile : allMedFiles){
			
			// Comparison if the existing Medication file and
			// the new Medication are equivalent in nature
	
			boolean existingMedFileMatchNewMed =
			MedicationDataHelper.
				areJsonFileAndMedicationSame(
					existingMedFile,
					newMed);
				
			// If an existing Med file loads into a Medication
			// equivalent to the new Medication
			// then the new Medication has been saved already
 	
			if(existingMedFileMatchNewMed){
				return true;
			}
			
			// Otherwise, resume search of existing files 	
		
		}
		
		// If none of the existing Med files loaded to an
		// Medication equivalent to the new Medication
		// then the new Medication has NOT been saved already

		return false;

	}
	
	// Conversion: Medication instance --> JSON-formatted String

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

}


