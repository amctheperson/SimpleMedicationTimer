import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.lang.RuntimeException;

import java.util.ArrayList;

import java.util.regex.PatternSyntaxException;

import java.util.ArrayList;

public class MedicationDataLoadHelper {
	
	// Conversion: JSON File --> JSON-formatted String

	public static String jsonFileToJsonString(File file)
	throws Exception{

		String jsonString = "";
	
		// Suffix appended to all recoverable_error_message variables
		// inside all catch blocks following the below try block
	
		String notification_default = 
		"Returning default jsonString as an alternative.\n";
		
		try{
			// This line can cause a FileNotFound exception [1]

			FileReader fileReader = new FileReader(file);

			// JSON files for Medications are typically <150 chars

			// This line can cause an IllegalArgument exception [2]

			CharBuffer charBuffer = CharBuffer.allocate(300);

			// Keeps track of how many characters were read
			// in each pass of fileReader.read(somecharBuffer)
			
			int charsReadInLastAttempt = 0;
			
			// When -1 characters are read
			// the end of file has been reached

			while(charsReadInLastAttempt != -1){
				
				// Note that this ONE line can cause
				// any of the following to occur [3]:
				// 	-IOException
				// 	-NullPointerException
				//	-ReadOnlyBufferException

				charsReadInLastAttempt = 
					fileReader.read(charBuffer);

				jsonString += charBuffer.toString();

				charBuffer.clear();
			}
			
			fileReader.close();
			
			// Sanitizing recently read-from-file string
			// of control characters (aka non-printable characters)
			// that may trip up any function not expecting them

			// This line can cause a PatternSyntaxException
			// which...has already been caught
			//despite no explicit catch of it

			// not entirely sure why, will look into some other time

			jsonString = jsonString.replaceAll("[\\p{C}]", "");					
			return jsonString;	

		}
		// [1]
		catch(FileNotFoundException e){

			String recoverable_error_message = 
			"The File provided for jsonFileToJsonString(File " + 
			"jsonFile) could not be found. " +
			notification_default; 
		
			new Exception(recoverable_error_message);
			
			return DefaultMedicationData.DEFAULT_JSON_STRING;
		}
		// [2]
		catch(IllegalArgumentException e){

			String recoverable_error_message = 
			"Provided int in CharBuffer.allocate() is negative, " +
			"and therefore invalid." + notification_default;
			
			new Exception(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_STRING;

		}
		// [3]
		catch(IOException e){

			String recoverable_error_message =
			"An I/O error occurred while calling fileReader." +
			"read(charBuffer)." + notification_default;
			
			new Exception(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_STRING;

		}
		catch(NullPointerException e){

			String recoverable_error_message = 
			"charBuffer is a null object, which cannot be " + 
			"invoked in fileReader.read(charBuffer)." +
			notification_default;

			new Exception(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_STRING;

		}
		catch(ReadOnlyBufferException e){

			String recoverable_error_message = 
			"charBuffer is set to read-only, and therefore " + 
			"cannot be used like fileReader.read("+
			"charBuffer)" + notification_default;

			new Exception(recoverable_error_message);

			return DefaultMedicationData.DEFAULT_JSON_STRING;

		}
	}	
	
	// Conversion: JSON-formatted String --> JsonObject hashmap

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

			// JsonException occurs whenever jsonString
			// is not in a valid JSON format

			String 	recoverable_error_message = 
				"String provided for Jsoner in " + 
				"jsonStringToMedication(String jsonString) " +
				"is not in a valid JSON format. Returning " + 
				"backup Medication object from " +
				"jsonStringToMedication(String jsonString).\n";	

			new Exception(recoverable_error_message);

			// Handled by switching to default Medication object

			return DefaultMedicationData.DEFAULT_JSON_OBJECT;
		}	
	}


	// Conversion: JsonObject hashmap --> Medication instance

	public static Medication jsonObjectToMedication(JsonObject jsonObject)
	throws Exception {

		// Check if the jsonObject has all the required properties
		// of a Medication object 
		
		String[] required_properties = 	{
						"name", "dosage", "type", 
						"totalHoursOfClarity"
				  		};
		
		// For keeping track of any missing properties
		// not seen in the jsonObject hashmap keys
		
		ArrayList<String> missing_properties = new ArrayList<String>();

		for (String property : required_properties){
						
			if(!jsonObject.containsKey(property)){
				
				missing_properties.add(property);
					
			}
		}

		// If any properties added to missing_properties

		if (missing_properties.size() > 0){
			
			// then provided JsonObject is not valid
			// so notify user about the missing properties
			
			String recoverable_error_message =
				"Provided JsonObject for " + 
				"jsonObjectToMedication(JsonObject jsonObject" +
				") does not have the following required " +
				"properties:\n";

			for (String property : missing_properties){
				
				recoverable_error_message += property + "\n";

			}
			
			// and return default Medication as a backup

			recoverable_error_message += "Returning default " +
				"Medication as an alternative.\n";	
			
			new Exception(recoverable_error_message);
				
			return DefaultMedicationData.DEFAULT_MEDICATION;		
		}	
			
		// Provided JsonObject has been validated from here on out
	
	
		// Pull class properties from the JSONObject

		String loaded_name = jsonObject.get("name").toString();	
		String loaded_dosage = jsonObject.get("dosage").toString();
		String loaded_type = jsonObject.get("type").toString();
		
		// Temp var made to shorten expression after the following line
	
		String loaded_totalHoursOfClarity_string =
			jsonObject.get("totalHoursOfClarity").toString();
		 
		double loaded_totalHoursOfClarity = 
			Double.valueOf(loaded_totalHoursOfClarity_string);
		
		// Then create new Medication object with these class properties

		Medication newMed = new Medication(loaded_name, loaded_dosage,
			loaded_type, loaded_totalHoursOfClarity);

		// and then return said Medication

		return newMed;	
	}

}
