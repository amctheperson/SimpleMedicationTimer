import java.io.File;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class MedicationDataHelper{

	
	/*
	
	PAGE	PURPOSE				FUNCTION SIGNATURE

	1-2	ALL MED FILES GETTER		getAllMedicationJsonFilesHere() 

	3	MED FILE EQUIV TO MED GETTER	getExistingMedicationJsonFile(
						Medication med)
	
	4	MED FILE-MED COMPARATOR		areJsonFileAndMedicationSame(
						File jsonFile, Medication med)

	5	Med file validator		isMedFileByName(File file)
	
	
	
		
	This class contains functions involving Medication JSON files.

	These functions are primarily for assisting in the save and
	load features of the MedicationData class, 
	however they can also be seen used in the MedicationDataSaveHelper and 
	MedicationDataLoadHelper classes as well.
			
	*/




















/*
				     Page 0

								    CTRL + F -->
*/


	// GETTING ALL MEDICATION FILES SAVED LOCALLY // 
	
	// Retrieves all Medication JSON files locally saved
	// inside the current directory

	// Returns an ArrayList of File objects,
	// each representing a valid Medication JSON file

	public static ArrayList<File> getAllMedicationJsonFilesHere() 
	throws Exception{

		// ArrayList is used to collect the returnable File objects
		// because the number of incoming valid files is unknown
	
		// (Note to self: consider using a set here 
		// if application is running slow)
		
		ArrayList<File> fileArrayList = new ArrayList<File>();
					
		// Open the current directory as a File object

		File curDir = new File("./");
			
		// For all file/subfolders in the current directory
	
		for(File file : curDir.listFiles()){

			// Skip all subdirectories

			if(file.isDirectory()){
				continue;
			}

			// Skip the default Medication file,
			// as it is only used for error recovery

			File defaultMedicationFile = 
			DefaultMedicationData.DEFAULT_MEDICATION_FILE;

			if(file.equals(defaultMedicationFile)){
				continue;
			}








/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// GETTING ALL MEDICATION FILES SAVED LOCALLY // 
	// (contd.)

			// After all edge cases have been handled

			// Check if file is a Medication file

			if(MedicationDataHelper.isMedFileByName(file)){
				fileArrayList.add(file);
			}
		}

		return fileArrayList;
	}




































/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/
	

	// GETTING THE MEDICATION FILE EQUIVALENT TO A MEDICATION INSTANCE // 

	// Retrieves the Medication JSON File saved locally that is equivalent
	// to the provided Medication instance

	// Returns the default Medication file
	// if no equivalent Medication JSON File was found

	// NOTE: only Medication instances proven to be saved already via
	// MedicationDataSaveHelper.checkIfSavedAlready(Medication newMed)
	// should be passed to this function 

	public static File getExistingMedicationJsonFile(Medication med)
	throws Exception{
		
		// Check every existing Medication JSON file
		// until one equivalent to the provided Med is found

		ArrayList<File> allMedFiles = getAllMedicationJsonFilesHere();
		
		for (File existingMedFile : allMedFiles){

			boolean fileAndMedAreSame = 
			areJsonFileAndMedicationSame(
				existingMedFile, med);

			if(fileAndMedAreSame){
				return existingMedFile;
			}			
		}

		// If no existing and equivalent Med file ever found
		// raise a recoverable error
		// and return the default Medication file
	
		String recoverable_error_message = 
		"getExistingMedicationJsonFile(Medication med) was called on " +
		"a Medication instance that does not have an existing and " + 
		"equivalent JSON file already saved locally. " +
		DefaultMedicationData.
		DEFAULT_MEDICATION_FILE_ERROR_MESSAGE_SUFFIX + "\n"; 

		IllegalArgumentException recoverable_error = 
		new IllegalArgumentException(recoverable_error_message);

		System.err.print(recoverable_error);

		return DefaultMedicationData.DEFAULT_MEDICATION_FILE;

	}
/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
	
	
	// COMPARING A MEDICATION FILE TO A MEDICATION INSTANCE //
	
	// Checks if provided Medication JSON file
	// and provided Medication instance are equivalent

	public static boolean areJsonFileAndMedicationSame( 
	File jsonFile, Medication med) throws Exception{

		Medication medFromFile = 
		MedicationData.loadMedicationFromJsonFile(jsonFile);

		return medFromFile.equals(med);	
	
	}




































/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/
	
	
	// Validating a File is a Medication JSON file // 
	// (this is a secondary function)

	// Checks if a File object fits
	// the Medication JSON file naming convention
	// by attempting to matching it's name to a regex pattern

	public static boolean isMedFileByName(File file) throws Exception{

		String fileName = file.getName();
	
		// Accepting the default Medication file directly
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
				     Page 5

<-- CTRL + B							    
*/		
}
