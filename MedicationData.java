import com.github.cliftonlabs.json_simple.JsonObject;
import java.io.File;
import java.io.FileWriter;

public class MedicationData{


	/*

	PAGE	PURPOSE		FUNCTION SIGNATURE

	1	SAVE		saveMedicationToJsonFile(Medication med)

	2	LOAD		loadMedicationFromJsonFile(File jsonFile)

	3-4	OVERWRITE	overwriteJsonFileWithNewMedication(File
				jsonFile, Medication newMed)

	5	DELETE		deleteMedicationFile(File jsonFile)			
	6-7	save new	saveMedicationToNewJsonFile(Medication newMed)




	*/


























/*
				     Page 0

								    CTRL + F -->
*/


	// SAVING MEDICATION TO MEDICATION FILE // 

	// Saves Medication instance locally as a Medication JSON File

	// Returns newly created Medication file created OR 
	// existing Medication file if provided Medication instance
	// was saved already
		
	public static File saveMedicationToJsonFile(Medication med) 
	throws Exception{

		boolean medWasAlreadySaved =
		MedicationDataSaveHelper.checkIfSavedAlready(med);

		// If provided Medication instance has already been saved
		// then return the existing file
			
		if(medWasAlreadySaved){
				
			File equivalentMedFile =
			MedicationDataHelper.getExistingMedicationJsonFile(
			med);
		
			return equivalentMedFile;	
		}

		// Otherwise return the new JSON File created
		// when saving the new Medication instance
		
		File newMedFileCreated =
		saveMedicationToNewJsonFile(med);

		return newMedFileCreated;	
	}
















/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// LOADING MEDICATION FROM MEDICATION FILE //
 
	// File loading into Medication: Given a provided Medication JSON file
	// this function loads the file into a new Medication instance	

	public static Medication loadMedicationFromJsonFile(File jsonFile)
	throws Exception{
		
		// This function essentially does the following conversions:
		
		// JSON File --> JSON-formatted String --> JSON-Object
		// (basically a Hashmap<String, Object>) --> Medication
			
		String jsonString = 
		MedicationDataLoadHelper.jsonFileToJsonString(jsonFile);
		
		JsonObject jsonObject = 
		MedicationDataLoadHelper.jsonStringToJsonObject(jsonString);

		Medication loadedMed = 
		MedicationDataLoadHelper.jsonObjectToMedication(jsonObject);

		return loadedMed;
		
	}

























/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// OVERWRITING MEDICATION FILE WITH A NEW MEDICATION // 

	// Overwrites the provided Medication JSON file
	// with the provided Medication instance 

	public static boolean overwriteJsonFileWithNewMedication(File jsonFile,
	Medication newMed) throws Exception{
		
		// Checking if provided Medication has been saved already
 
		boolean newMedSavedAlready = MedicationDataSaveHelper.
			checkIfSavedAlready(newMed);

		if(newMedSavedAlready){

			String recoverable_error_message = 
			"The provided Medication instance has already been " +
			"saved locally, therefore the provided JSON file " + 
			"does not need to be overwritten. Returning false " + 
			"to overwriteJsonFileWithNewMedication.\n";

			Exception recoverable_error = 
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error);

			return false;
		}

		// Checking if provided Medication JSON file
		// and provided Medication instance are even different
	
		boolean newMedAndJsonFileAreSame = MedicationDataHelper.
			areJsonFileAndMedicationSame(jsonFile,newMed);

		if(newMedAndJsonFileAreSame){

			String recoverable_error_message = 
			"The provided Medication instance is equivalent " + 
			"to the also provided Medication JSON File, " +
			"therefore the file does not need to be overwritten." +
			"Returning false to " + 
			"overwriteJsonFileWithNewMedication.\n";

			Exception recoverable_error = 
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error);
			return false;
		}
/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// OVERWRITING MEDICATION FILE WITH A NEW MEDICATION // 
	// (contd.)

		// This try-catch block is similar to the one done in 
		// secondary save function 'writeMedicationToNewJsonFile'
		// but without creating a new Medication JSON file 
	
		try{
			String jsonString = 
			MedicationDataSaveHelper.medicationToJsonString(newMed);

			FileWriter jsonFileWriter = new FileWriter(jsonFile);
			jsonFileWriter.write(jsonString);
			jsonFileWriter.close();

			return true;

		}




		// Any exceptions raised while saving
		// in the try block on the last page
		// lead to false being returned 
		// because jsonFile was not modified

		catch(Exception e){

			String recoverable_error_message = "An exception " +
			"occurred in the regular saving process. " +
			"Returning false to function " +
			"\'overwriteJsonFileWithNewMedication\', as file " +
			jsonFile.getName() + " was not overwritten.\n";
			
			Exception recoverable_error =
			new Exception(recoverable_error_message);
		
			System.err.print(recoverable_error);
			
			return false;	
		}
	}







/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/


	// DELETING A MEDICATION FILE // 

	// Deletes the provided locally saved Medication file 
		
	public static boolean deleteMedicationFile(File jsonFile)
	throws Exception{
	
		try{
			jsonFile.delete();
			return true;

		}
		catch(SecurityException e){	
		
			String recoverable_error_message =
			"Security manager on device denies write access to " +
			"file " + jsonFile.getName() + ". Returning false " + 
			"to deleteMedicationFile(File jsonFile) since " + 
			"jsonFile " + jsonFile.getName() + 
			" could not be deleted.\n";

			Exception recoverable_error = 
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error);

			return false;
		}
	}





















/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// Saving to New Medication File //
	// (this is a secondary function)

	// Returns newly-created JSON file for provided Medication instance OR 
	// default JSON file if exception occurred during saving
	
	public static File saveMedicationToNewJsonFile(Medication newMed)
	throws Exception{
	
		// This conditional is similar to the initial check
		// if the provided Medication has been saved already
		// that is done in the primary save function

		// however this check raises an Exception
		// as the check indicates a misuse of this function
		
		boolean newMedAlreadySaved =
		MedicationDataSaveHelper.checkIfSavedAlready(newMed);
	
		if(newMedAlreadySaved){
		
			File equivalentMedFile =
			MedicationDataHelper.getExistingMedicationJsonFile(
			newMed);
	
			String recoverable_error_message = 
			"Provided Medication instance to function \'" +
			"saveMedicationToNewJsonFile\' has already been " +
			"saved. Returning file \'" + 
			equivalentMedFile.getName() + "\', as it represents " +
			" the provided Medication already.\n";

			Exception recoverable_error = 
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error);

			return equivalentMedFile;	
		}











/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// Saving to New Medication File //
	// (contd.)
			
		try{

			// Medication instance --> JSON-formatted String

			String jsonString = 
			MedicationDataSaveHelper.medicationToJsonString(newMed);

			String jsonFileName = 
			MedicationDataSaveHelper.generateNewFileName();

			// Write JSON-formatted String to new File

			File jsonFile = new File("./" + jsonFileName);
			jsonFile.createNewFile();
			jsonFile.setWritable(true);

			FileWriter jsonFileWriter = new FileWriter(jsonFile);
			jsonFileWriter.write(jsonString);
			jsonFileWriter.close();

			return jsonFile;

		}

		// Any exceptions raised in the above saving process lead to
		// a default file being returned

		catch(Exception e){

			String recoverable_error_message = "An exception " +
			"occurred while saving. Returning default JSON file.\n";
			
			Exception recoverable_error = 
			new Exception(recoverable_error_message);		
		
			System.err.print(recoverable_error);
				
			return DefaultMedicationData.DEFAULT_MEDICATION_FILE;
		}

	}






/*
				     Page 7

<-- CTRL + B							      
*/
}
