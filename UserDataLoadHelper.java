import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonKey;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.IllegalArgumentException;

public class UserDataLoadHelper{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	dailyRoutine:		deserializeDailyRoutine(
					String[] serialized_dailyRoutine)
		String[] ->
		Medication[]

	2-3	whenLastTaken:		deserializeWhenLastTaken(
					HashMap<String, String> 	
		HashMap<String,		serialized_whenLastTaken)		
		String> ->				
		HashMap<Medication,
		LocalDateTime>
		
	4	Check all User class	validateJsonObjectForUser(
		properties present in	JsonObject jsonObject)	
		JsonObject		

	5-6	Validated JsonObject	validatedJsonObjectToUser(	
		-> User			JsonObject validated_jsonObject)  


	
	This class contains functions that assist in the primary loading of
	the User JSON file.

	The deserialization functions for the dailyRoutine and whenLastTaken
	class properties can be found here.
	
	*/






/*
				     Page 0

								    CTRL + F -->
*/


	// DAILY ROUTINE DESERIALIZATION //


	// Given String[] corresponding to Medication File names

	// This function loads Medication from String[]
	// and returns Medication[]	


	public static Medication[] deserializeDailyRoutine(
	String[] serialized_dailyRoutine) throws Exception{
	
		int dailyRoutineCount = serialized_dailyRoutine.length;

		Medication[] dailyRoutine = 
		new Medication[dailyRoutineCount];

		for(int i = 0; i < dailyRoutineCount; i++){

			// Med File Name -> Med File -> Med

			File routineMedFile = 
			new File(serialized_dailyRoutine[i]);

			Medication routineMed = 
			MedicationData.loadMedicationFromJsonFile(
			routineMedFile);
 
			dailyRoutine[i] = routineMed; 

		}

		return dailyRoutine;

	}















/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// WHEN LAST TAKEN DESERIALIZATION //


	// Given a HashMap of paired String entries correlating to
	// Medication file names and formatted date-time-stamps

	// This function loads each Medication from File name
	// and each LocalDateTime from each formatted date-time-stamp

	// and returns a HashMap of Medication-LocalDateTime entries 


	public static HashMap<Medication, LocalDateTime> 
	deserializeWhenLastTaken(HashMap<String, String> 
	serialized_whenLastTaken){
		
		HashMap<Medication, LocalDateTime> whenLastTaken = 
		new HashMap<Medication, LocalDateTime>();

		serialized_whenLastTaken.forEach(
	
			(serialized_takenMed, serialized_takenMedAt) -> {

				try{

					// Med File Name -> Med File -> Med

					File takenMedFile = 
					new File(serialized_takenMed);

					Medication takenMed =
					MedicationData.
					loadMedicationFromJsonFile(
					takenMedFile); 

					// String -> LocalDateTime

					LocalDateTime takenMedAt = 
					LocalDateTime.parse(
					serialized_takenMedAt,
					UserData.DISPLAY_FORMAT);		

					whenLastTaken.put(takenMed,
					takenMedAt);	

				}




/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// WHEN LAST TAKEN DESERIALIZATION //
	// (contd.)


				catch (Exception e){

					String error_message =
	
					"An exception occurred while either " + 
					"loading a Medication from " + 
					"a File name or parsing a String " + 
					"into a LocalDateTime; Entries in " +
					"serialized_whenLastTaken may have " +
					"been omitted\n";

					System.err.print(error_message);
	
				}				

			}
	);

	return whenLastTaken;
 
	}

























/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// Validate JsonObject For User Conversion Function //
	// (Secondary function for validatedJsonObjectToUser)

		
	// Validates provided JsonObject HashMap
	// intended for conversion to User instance
	// by checking if all class properties of User are present
	// and raises IllegalArgumentException if not 

	public static void validateJsonObjectForUser(JsonObject jsonObject)
	throws Exception{

		// Validate if jsonObject has all data needed
		// for User constructor 
	
		String[] required_properties = 	{
						"name",
						"dailyRoutine_serialized",
						"whenLastTaken_serialized",
				  		};
			
		ArrayList<String> missing_properties = new ArrayList<String>();

		for (String property : required_properties){
						
			if(!jsonObject.containsKey(property)){
				
				missing_properties.add(property);
					
			}
		}
				
		if (missing_properties.size() > 0){
				
			String error_message =
				"Cannot load jsonObject data into User " +
				"instance because jsonObject is missing " + 
				"the following key(s):\n "; 

			for (String property : missing_properties){
				
				error_message += property + "\n";

			}

			throw new IllegalArgumentException(error_message);

		}	
	}

/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/


	// VALIDATED JSON OBJECT -> USER FUNCTION //

	
	// Deserializes class properties from provided JsonObject 
	// (which has passed validation) 
	// and returns new User instance containing said data 

	
	public static User validatedJsonObjectToUser(
	JsonObject validated_jsonObject) throws Exception{

		// name deserialization:
		// Object -> String		

		String loaded_name = 
		validated_jsonObject.get("name").toString();

		// dailyRoutine deserialization:
		// JsonArray of Objects | new String[] -> Medication[]

		JsonKey jsonKeyForDR_serialized =
		Jsoner.mintJsonKey("dailyRoutine_serialized", null);

		JsonArray dailyRoutine_serialized_jsonArray =
		validated_jsonObject.getCollection(jsonKeyForDR_serialized);
		
		int dailyRoutineCount = 
		dailyRoutine_serialized_jsonArray.size();

		String[] dailyRoutine_serialized = 
		new String[dailyRoutineCount];

		for (int i = 0; i < dailyRoutineCount; i++){

			String routineMed_serialized =
			dailyRoutine_serialized_jsonArray.getString(i);
			
			dailyRoutine_serialized[i] = routineMed_serialized;

		}
	
		Medication[] loaded_dailyRoutine = 
		deserializeDailyRoutine(dailyRoutine_serialized);






	
/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// VALIDATED JSON OBJECT -> USER FUNCTION //
	// (contd.)
	

		// whenLastTaken deserialization:
		// HashMap<String,String> -> HashMap<Medication,LocalDateTime>	
	
		JsonKey jsonKeyForWLT_serialized = 
		Jsoner.mintJsonKey("whenLastTaken_serialized", 
		new HashMap<String,String>());
	
		HashMap<String,String> loaded_whenLastTaken_serialized = 
		validated_jsonObject.getMap(jsonKeyForWLT_serialized);
	
		HashMap<Medication,LocalDateTime> loaded_whenLastTaken = 
		deserializeWhenLastTaken(loaded_whenLastTaken_serialized);


		User loadedUser = new User(
						loaded_name,
						loaded_dailyRoutine,
						loaded_whenLastTaken
		);		
		
		
		return loadedUser;
		
	}





















	
/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/
}
