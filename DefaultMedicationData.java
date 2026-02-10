import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

public class DefaultMedicationData { 
	
	// Contains variables used as backup data in case exceptions occur
	// in the process of saving and loading from local data files

	// Meant to avoid disruption of back-end functionality

	// Backup data is derived from a JSON file locally saved
	// titled "DefaultMedication.json"
	// and it represents the alternative/supplement to ADHD meds that I use:
	// Caffeine, baby!

	// TODO
	// Rename: DEFAULT_MEDICATION_FILE

	protected static final File DEFAULT_MEDICATION_FILE;

	static{

		File tempFileVariable = null;

		try{
			tempFileVariable = new File("./DefaultMedication.json"); 
		}
		catch (NullPointerException e){

			String critical_error_message = "Default File object " +
				"could not be initialized! Check if file " +
				"DefaultMedication.json exists in the " +
				"current folder.\n";

			new RuntimeException(critical_error_message); 
		}

		DEFAULT_MEDICATION_FILE = tempFileVariable;
	}

	protected static final String 
	DEFAULT_MEDICATION_FILE_ERROR_MESSAGE_SUFFIX = 
	"Returning default Medication file as an alternative.";
	

	protected static final Medication DEFAULT_MEDICATION;

	static{

		Medication tempMedicationVariable = null;

		try{
			tempMedicationVariable = 
			MedicationData.loadMedicationFromJsonFile(
				DEFAULT_MEDICATION_FILE);
		}
		catch (Exception e){

			String critical_error_message = "Default Medication " +
				"object could not be initialized!\n";
			new RuntimeException(critical_error_message); 
		}

		DEFAULT_MEDICATION = tempMedicationVariable; 
	}

	protected static final String DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX = 
	"Returning the default Medication instance as an alternative.";

	protected static final String DEFAULT_JSON_STRING;

	static{

		String tempJsonString = null;
	
		try{
			tempJsonString = 
			MedicationDataLoadHelper.jsonFileToJsonString(
				DEFAULT_MEDICATION_FILE);		
		}
		catch (Exception e){

			String critical_error_message = "Default JSON-"+
				"formatted String could not initialized!\n";
			
			new RuntimeException(critical_error_message);
		}

		DEFAULT_JSON_STRING = tempJsonString;
	}	

	protected static final String DEFAULT_JSON_STRING_MESSAGE_SUFFIX = 
	"Returning a JSON-formatted String representing the default " + 
	"Medication instance as an alternative.";


	protected static final JsonObject DEFAULT_JSON_OBJECT; 

	static{
		JsonObject tempJsonObject = null;

		try{
			tempJsonObject =
			(JsonObject) Jsoner.deserialize(DEFAULT_JSON_STRING); 
		}
		catch (Exception e){

			String critical_error_message = "Default JsonObject " +
				"could not be initialized!\n";
			new RuntimeException(critical_error_message); 
		}

		DEFAULT_JSON_OBJECT = tempJsonObject; 
	}

	protected static final String DEFAULT_JSON_OBJECT_MESSAGE_SUFFIX = 
	"Returning a JSONObject hashmap representing the default Medication " + 
	"instance as an alternative.";
	
}
