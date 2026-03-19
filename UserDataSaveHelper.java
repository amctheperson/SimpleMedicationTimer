import com.github.cliftonlabs.json_simple.JsonObject;

import java.time.LocalDateTime;
import java.io.File;
import java.util.HashMap;

public class UserDataSaveHelper {


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	User -> JSON String	userToJsonString(User user)


	2	dailyRoutine:		serializeDailyRoutine(User user)
		Medication[] ->
		String[]	

	3-4	whenLastTaken:		serializeWhenLastTaken(User user)
		HashMap<Medication,
		LocalDateTime> ->
		HashMap<String,
		String>	



	This class is a work in progress.
	
	*/





















/*
				     Page 0

								    CTRL + F -->
*/


	// Convert User to JSON String Function //


	// When provided with a User instance, this function generates a
	// serialized, JSON-formatted String representing the User instance
  
	public static String userToJsonString(User user) throws Exception {

		String user_name = user.getName();

		String[] user_dailyRoutine_serialized = 
		serializeDailyRoutine(user);

		HashMap<String,String> user_whenLastTaken_serialized =
		serializeWhenLastTaken(user);

		// Load class properties of User instance
		// into JsonObject HashMap (from json-simple package)

		JsonObject userJsonObject = new JsonObject();

		userJsonObject.put(	"name",
					user_name);
		userJsonObject.put(	"dailyRoutine_serialized", 
					user_dailyRoutine_serialized);
		userJsonObject.put(	"whenLastTaken_serialized",
					user_whenLastTaken_serialized);

		// JsonObject organizes HashMap contents
		// into JSON-formatted String

		String user_jsonString = userJsonObject.toJson();

		return user_jsonString;
			
	}














/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/

	
	// Serialize dailyRoutine Medication Array Function //


	// Within a provided User, this function returns a String[]
	// representing the dailyRoutine array of Medication
	// where each String is the File name of a Medication JSON File
	// locally saved

	public static String[] serializeDailyRoutine(User user)
	throws Exception{

		UserData.allMedicationsSavedCheck(user);

		Medication[] user_dailyRoutine = user.getDailyRoutine();

		String[] user_dailyRoutine_asFileNames = 
		new String[user_dailyRoutine.length];

		for (int i = 0; i < user_dailyRoutine.length; i++){
			
			Medication routineMed = user_dailyRoutine[i];
			
			File routineMedFile =
			MedicationDataHelper.getExistingMedicationJsonFile(
				routineMed);

			String routineMedFileName = routineMedFile.getName();

			user_dailyRoutine_asFileNames[i] = routineMedFileName;	

		}

		return user_dailyRoutine_asFileNames;

	}















/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// Serialize whenLastTaken Medication-LocalDateTime HashMap Function //


	// Within a provided User, this function returns
	// a HashMap of String pairs representing the pairs of Medication,
	// LocalDateTime inside whenLastTaken

	// 1st String in the pair is a Medication JSON File name
	// 2nd String in pair is LocalDateTime's time and date
	// in a printable and parseable format

	public static HashMap<String,String> serializeWhenLastTaken(User user)
	throws Exception{

		HashMap<Medication,LocalDateTime> user_whenLastTaken =
		user.getWhenLastTaken();

		HashMap<String,String> user_whenLastTaken_asStrings =
		new HashMap<String,String>();

		user_whenLastTaken.forEach(

			(takenMed, takenMedAt) -> {

				try{
					File takenMedFile =
					MedicationDataHelper.
					getExistingMedicationJsonFile(
					takenMed);

					String takenMedFileName = 
					takenMedFile.getName();

					String takenMedAt_string =
					takenMedAt.format(
					UserData.DISPLAY_FORMAT);

					user_whenLastTaken_asStrings.put(
						takenMedFileName,
						takenMedAt_string
					);

				}







/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// Serialize whenLastTaken Medication-LocalDateTime HashMap Function //
	// (contd.)


				catch (Exception e){

					String error_message =
	 
					"An exception occured while " +
					"invoking equivalent Medication " + 
					"file retrieval to a Medication, " +
					"some entries in whenLastTaken " + 
					"may not have been serialized.\n";
					 
					System.err.print(error_message);			
				}
			}
		);	

		return user_whenLastTaken_asStrings;	
	}
	



























/*
				     Page 4

<-- CTRL + B
*/
}
