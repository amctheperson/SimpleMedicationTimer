import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.nio.CharBuffer;

import java.util.ArrayList;

public class MedicationDataLoadHelper {


	/*

	PAGE	PURPOSE		FUNCTION SIGNATURE

	1-2	JSON File	jsonFileToJsonString(File jsonFile)
		-->
		JSON String

	3	JSON String	jsonStringToJsonObject(String jsonString)
		-->
		JSON Object

	4-5	JSON Object	jsonObjectToMedication(JsonObject jsonObject)	
		-->
		Medication	
	
	
	
	
	This class contains functions that convert JSON-related data types
	into each other.

	These functions are primarily for assisting in the loading a Medication
	JSON file into a Medication feature of the MedicationData class.

	*/











/*
				     Page 0

								    CTRL + F -->
*/


	// CONVERTING MEDICATION JSON FILE TO JSON-FORMATTED STRING

	// Takes the provided Medication JSON File
	// and reads it into a returnable JSON-formatted String

	// If any IO exception occurs while reading the file into a String
	// returns the default JSON-formatted String

	// Note: The Medication JSON file provided to this function
	// should be verified first
	// via MedicationDataHelper.isMedFileByName(File file)
	
	public static String jsonFileToJsonString(File jsonFile)
	throws Exception{
		
		String jsonString = "";
		
		try{
			FileReader jsonFileReader = new FileReader(jsonFile);

			// JSON files for Medications are typically <150 chars
			// therefore sizing the CharBuffer to 300 characters
			// is sufficient

			CharBuffer charBuffer = CharBuffer.allocate(300);

			// This variable keeps track of
			// how many characters were read into charBuffer
			// by jsonFileReader
			
			int charsReadInLastAttempt = 0;
			
			// When -1 characters have been read in an attempt
			// the end of jsonFile has been reached

			while(charsReadInLastAttempt != -1){
				
				// This line can cause an IO Exception

				charsReadInLastAttempt = 
					jsonFileReader.read(charBuffer);

				jsonString += charBuffer.toString();

				charBuffer.clear();
			}
			
			jsonFileReader.close();


/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// CONVERTING MEDICATION JSON FILE TO JSON-FORMATTED STRING
	// (contd.)	
	
			// Sanitize jsonString of control characters
			// (non-printable characters)
						
			jsonString = jsonString.replaceAll("[\\p{C}]", "");					
			return jsonString;	

		}
		
		// If jsonFileReader raises an IO Exception
		// return the default JSON-formatted String
 
		catch(IOException e){

			String recoverable_error_message =
			"An I/O error occurred while calling jsonFileReader." +
			"read(charBuffer). ";

			recoverable_error_message +=
			DefaultMedicationData.
				DEFAULT_JSON_STRING_MESSAGE_SUFFIX +
			"\n";
			
			Exception recoverable_error =
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_STRING;

		}
	}	















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// CONVERTING JSON-FORMATTED STRING TO JSON OBJECT HASHMAP
	
	// Takes provided JSON-formatted String
	// and loads into JSONObject instance
	// whose data can be accessed like a Hashmap

	// Note: The provided jsonString should be a JSON-formatted String
	// otherwise a JsonException will be raised by this function
	// and the default JSON Object will be returned 

	public static JsonObject jsonStringToJsonObject(String jsonString){

		// Use Jsoner and JsonObject classes (from json-simple package)
		// to load JSON-formatted String into a JsonObject hashmap
	
		try{
			// Note that this line can cause a JsonException

			JsonObject jsonObject = 
				(JsonObject) Jsoner.deserialize(jsonString);

			return jsonObject;
		}
		catch(JsonException je){

			// If jsonString is not in a valid JSON format
			// return the default JSONObject

			String 	recoverable_error_message = 
				"String provided for Jsoner in " + 
				"jsonStringToMedication(String jsonString) " +
				"is not in a valid JSON format. Returning " + 
				"backup Medication object from " +
				"jsonStringToMedication(String jsonString).\n";	

			Exception recoverable_error =
			new Exception(recoverable_error_message);

			recoverable_error_message +=
			DefaultMedicationData.
				DEFAULT_JSON_OBJECT_MESSAGE_SUFFIX +
			"\n";

			System.err.print(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_OBJECT;
		}	
	}


/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
	
	
	// CONVERTING JSON OBJECT HASHMAP TO MEDICATION INSTANCE // 

	// Takes a provided JSONObject instance
	// and loads its class properties that correlate to a Medication object
	// into a returnable new Medication instance 

	public static Medication jsonObjectToMedication(JsonObject jsonObject)
	throws Exception {

		// Check if jsonObject has all the required properties
		// of a Medication object first 
	
		// If it does not, raise a checked Exception
		// and return the default Medication
	
		String[] required_properties = 	{
						"name", "dosage", "type", 
						"totalHoursOfClarity"
				  		};
		
		// This keeps track of any missing properties
		// not seen in the jsonObject hashmap keys
		// (mainly for debugging)
		
		ArrayList<String> missing_properties = new ArrayList<String>();

		for (String property : required_properties){
						
			if(!jsonObject.containsKey(property)){
				
				missing_properties.add(property);
					
			}
		}
				
		if (missing_properties.size() > 0){
				
			String recoverable_error_message =
				"Provided JsonObject for " + 
				"jsonObjectToMedication(JsonObject jsonObject" +
				") does not have the following required " +
				"properties:\n";

			for (String property : missing_properties){
				
				recoverable_error_message += property + "\n";

			}
			
		
/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/	


	// CONVERTING JSON OBJECT HASHMAP TO MEDICATION INSTANCE // 
	// (contd.)

			recoverable_error_message += "Returning default " +
				"Medication as an alternative.\n";	
			
			Exception recoverable_error =
			new Exception(recoverable_error_message);

			System.err.print(recoverable_error_message);
			return DefaultMedicationData.DEFAULT_MEDICATION;		
		}	
	
		// jsonObject has been validated
		// to load into a new Medication instance		

		String loaded_name = jsonObject.get("name").toString();
	
		String loaded_dosage = jsonObject.get("dosage").toString();

		String loaded_type = jsonObject.get("type").toString();
			
		String loaded_totalHoursOfClarity_string =
			jsonObject.get("totalHoursOfClarity").toString();
	 
		double loaded_totalHoursOfClarity = 
			Double.valueOf(loaded_totalHoursOfClarity_string);
		
		Medication newMed = new Medication(
						loaded_name,
						loaded_dosage,
						loaded_type,
						loaded_totalHoursOfClarity
						);

		return newMed;	
	}












/*
				     Page 5

<-- CTRL + B							      
*/
}
