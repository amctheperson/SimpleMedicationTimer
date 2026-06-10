import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

public class DefaultMedicationData { 
	
	/*

	
	PAGE	TYPE		VARIABLE

	1	File		DEFAULT_MEDICATION_FILE

	2	Medication	DEFAULT_MEDICATION

	3	String		DEFAULT_JSON_STRING

	4	JsonObject	DEFAULT_JSON_OBJECT
	
	
	
	This class contains backup data variables meant to be used
	when unchecked exceptions are raised during the typical save
	and load process.

	The intent is to avoid disruption of back-end
	functionality (i.e. system crashes when the front-end UI is running).

	Backup data is derived from the locally saved JSON Medication file
	"DefaultMedication.json" representing the supplement/alternative to
	the medication that I typically use (caffeine)

	Note that this default Medication file is not counted as a Medication
	file or treated as such as it should only be displayed and used 
	in the case of a save or load error.
			
	*/














/*
				     Page 0

								    CTRL + F -->
*/


	// DEFAULT MEDICATION FILE //

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
			
			RuntimeException critical_error = 
			new RuntimeException(critical_error_message);
			
			System.err.print(critical_error);
		}

		DEFAULT_MEDICATION_FILE = tempFileVariable;
	}

	protected static final String 
	DEFAULT_MEDICATION_FILE_ERROR_MESSAGE_SUFFIX = 
	"Returning default Medication file as an alternative.";
	


















/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// DEFAULT MEDICATION //

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

			RuntimeException critical_error = 
			new RuntimeException(critical_error_message);
			
			System.err.print(critical_error);
 
		}

		DEFAULT_MEDICATION = tempMedicationVariable; 
	}

	protected static final String DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX = 
	"Returning the default Medication instance as an alternative.";




















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// DEFAULT JSON-FORMATTED STRING //

	protected static final String DEFAULT_JSON_STRING;

	static{

		String tempJsonString = null;
	
		try{
			tempJsonString = 
			LocalData.jsonFileToJsonString(
				DEFAULT_MEDICATION_FILE);		
		}
		catch (Exception e){

			String critical_error_message = "Default JSON-"+
				"formatted String could not initialized!\n";

			RuntimeException critical_error = 
			new RuntimeException(critical_error_message);
			
			System.err.print(critical_error);
			
		}

		DEFAULT_JSON_STRING = tempJsonString;
	}	

	protected static final String DEFAULT_JSON_STRING_MESSAGE_SUFFIX = 
	"Returning a JSON-formatted String representing the default " + 
	"Medication instance as an alternative.";



















/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// DEFAULT JSON OBJECT //

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

			RuntimeException critical_error = 
			new RuntimeException(critical_error_message);
			
			System.err.print(critical_error);

		}

		DEFAULT_JSON_OBJECT = tempJsonObject; 
	}

	protected static final String DEFAULT_JSON_OBJECT_MESSAGE_SUFFIX = 
	"Returning a JSONObject hashmap representing the default Medication " + 
	"instance as an alternative.";





















/*
				     Page 4

<-- CTRL + B							    
*/
	
}
