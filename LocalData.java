import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonException;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;

import java.nio.CharBuffer;

import java.util.ArrayList;

public class LocalData{


	/*

	PAGE	PURPOSE		FUNCTION SIGNATURE

	1	JSON String	jsonStringToJsonFile(	String jsonString,
		-->					String jsonFileName)	
		JSON File

	2	JSON File	jsonFileToJsonString(File jsonFile)
		-->
		JSON String

	3	JSON String	jsonStringToJsonObject(String jsonString)
		-->
		JSON Object

	
	
	
	
	This class contains functions that convert JSON-related data types
	into each other.

	These functions are primarily for assisting in saving and loading
	JSON files for Medication and User instances.

	*/










/*
				     Page 0

								    CTRL + F -->
*/


	// CONVERTING JSON-FORMATTED STRING TO JSON FILE

	// Given provided JSON-formatted String, creates (or overwrites)
	// and returns a JSON file with the provided File name


	public static File jsonStringToJsonFile(String jsonString, 
	String jsonFileName) throws Exception {

		// Write JSON-formatted String to new File

		File jsonFile = new File("./" + jsonFileName);
		jsonFile.createNewFile();
		jsonFile.setWritable(true);

		FileWriter jsonFileWriter = new FileWriter(jsonFile);
		jsonFileWriter.write(jsonString);
		jsonFileWriter.close();

		return jsonFile;

	}




























/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// CONVERTING JSON FILE TO JSON-FORMATTED STRING

	// Reads provided JSON File into, and returns JSON-formatted String
	
	public static String jsonFileToJsonString(File jsonFile)
	throws Exception{
		
		String jsonString = "";
		
		FileReader jsonFileReader = new FileReader(jsonFile);

		// JSON files are typically around 150 chars
		// according to initial testing

		// Therefore doubling this as a buffer size
		// is sufficiently optimal

		CharBuffer charBuffer = CharBuffer.allocate(300);
	
		int charsReadInLastAttempt = 0;
	
		// While loop pipelines the following: 
		// jsonFile contents -> charBuffer -> jsonString

		// Until charBuffer reads -1 chars (the end of jsonFile)
	
		while(charsReadInLastAttempt != -1){
			
			// This line can cause an IO Exception

			charsReadInLastAttempt = 
				jsonFileReader.read(charBuffer);

			jsonString += charBuffer.toString();

			charBuffer.clear();
		}
		
		jsonFileReader.close();
	
		// Sanitize jsonString by removing control characters
		// (non-printable characters)
					
		jsonString = jsonString.replaceAll("[\\p{C}]", "");					
		return jsonString;	

	}


/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// CONVERTING JSON-FORMATTED STRING TO JSON OBJECT HASHMAP
	
	// Loads provided JSON-formatted String into JSONObject instance
	// whose data can be accessed like a Hashmap


	public static JsonObject jsonStringToJsonObject(String jsonString)
	throws Exception{

		// Use Jsoner and JsonObject classes (from json-simple package)
		// to load JSON-formatted String into JsonObject HashMap

		// Note that if jsonString is not in a valid JSON format
		// this line will throw a JsonException

		JsonObject jsonObject = 
			(JsonObject) Jsoner.deserialize(jsonString);

		return jsonObject;
	
	}





























/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/

	// Validate JsonObject for Conversion to a Certain Class Function // 
	

	// Checks if provided JsonObject has all the required data needed to
	// call a constructor for a certain class 

	// Throws IllegalArgumentException if not

	public static void validateJsonObjectForClassConversion(
	JsonObject jsonObject, String[] class_required_properties)
	throws Exception{
			
		ArrayList<String> missing_properties = new ArrayList<String>();

		for (String property : class_required_properties){
						
			if(!jsonObject.containsKey(property)){
				
				missing_properties.add(property);
					
			}
		}
				
		if (missing_properties.size() > 0){
				
			String error_message =
				"jsonObject invalid for class conversion " +
				"due to missing " + 
				"the following class properties:\n "; 

			for (String property : missing_properties){
				
				error_message += property + "\n";

			}

			throw new IllegalArgumentException(error_message);

		}	
	}
}
