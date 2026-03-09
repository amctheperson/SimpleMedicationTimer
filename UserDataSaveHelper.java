import com.github.cliftonlabs.json_simple.JsonObject;
import java.util.HashMap;

public class UserDataSaveHelper {


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	User -> JSON String	userToJsonString(User user)




	This class is a work in progress.
	
	*/


	// TODO

	// JsonString to JSON file
	
	// Migrate serialization methods of User from UserData to here
	
	// check if user_file exists, if so, overwrite it (if needed)

























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
		UserData.serializeDailyRoutine(user);

		HashMap<String,String> user_whenLastTaken_serialized =
		UserData.serializeWhenLastTaken(user);

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




















































/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/
}
