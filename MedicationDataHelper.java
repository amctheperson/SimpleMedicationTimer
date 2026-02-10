import java.io.File;
import java.util.ArrayList;

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

		new Exception(recoverable_error_message);

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

			// check if file is a Medication file
			if(MedicationDataHelper.isMedFileByName(file)){
				fileArrayList.add(file);
			}
		}
		return fileArrayList;
	} 
		


}
