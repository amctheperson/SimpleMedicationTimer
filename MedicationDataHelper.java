import java.io.File;
import java.util.ArrayList;
import java.lang.IllegalArgumentException;

public class MedicationDataHelper{

	// File access: Returns existing Medication JSON file
	// that is equivalent to the provided Medication instance

	public static File getExistingMedicationJsonFile(Medication med)
	throws Exception{
		
		// Check every existing Med file

		ArrayList<File> allMedFiles = getAllMedicationJsonFilesHere();
		
		for (File existingMedFile : allMedFiles){

			// until one equivalent to the provided Med is found

			boolean fileAndMedAreSame = 
			areJsonFileAndMedicationSame(
				existingMedFile, med);

			if(fileAndMedAreSame){
				return existingMedFile;
			}			
		}

		// If no existing and equivalent Med file ever found
		// raise a recoverable error
	
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
	
	
	// File/Medication comparator: Checks if provided Medication JSON file
	// and provided Medication instance are equivalent

	public static boolean areJsonFileAndMedicationSame( 
	File jsonFile, Medication med) throws Exception{

		Medication medFromFile = 
		MedicationData.loadMedicationFromJsonFile(jsonFile);

		return medFromFile.equals(med);	
	
	}

	// File validation: Checks if provided File object
	// is a valid JSON file representing a Medication instance	

	public static boolean isMedFileByName(File file) throws Exception{

		String fileName = file.getName();
	
		// Making an exception to the file name regex for the 
		// default Medication JSON file, as all the other default
		// data types are derived from it

		if(fileName.equals("DefaultMedication.json")){
			return true;
		}
	
		// regex pattern that matches file names
		// "Medication_[1 or more characters].json"
				
		String pattern = "Medication_.+\\.json";

		return fileName.matches(pattern);
	}

	// File retrieval: Gets all Medication JSON files
	// inside current directory

	public static ArrayList<File> getAllMedicationJsonFilesHere() 
	throws Exception{

		// ArrayList used because number of valid files returned
		// is unknown
		
		// TODO: Consider using a set if Android app is running slow		
		ArrayList<File> fileArrayList = new ArrayList<File>();
					
		// Open the current directory as a File object

		File curDir = new File("./");
			
		// For all file/subfolders in the current directory
	
		for(File file : curDir.listFiles()){

			// Skip all subdirectories

			if(file.isDirectory()){continue;}

			// Skip the default Medication file
			// only used as error recovery

			File defaultMedicationFile = 
			DefaultMedicationData.DEFAULT_MEDICATION_FILE;

			if(file.equals(defaultMedicationFile)){continue;}

			// check if file is a Medication file
			if(MedicationDataHelper.isMedFileByName(file)){
				fileArrayList.add(file);
			}
		}
		return fileArrayList;
	} 
		


}
