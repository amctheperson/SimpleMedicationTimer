import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonException;
import java.time.LocalDateTime;
import java.lang.Exception;

public class JsonSimpleTest{

	public static void main(String[] args) throws Exception{
		
		/* Creating a test Medication object and setting it to actual values */ 
	
		Medication testMed = new Medication(
			"Adderall",
			"20 mg",
			"XR",
			3
		);
		
		LocalDateTime right_now = LocalDateTime.now();

		testMed.tryTakingAt(right_now);

		System.out.print(testMed);
		
		// Reminder to self that JSONObjects extend HashMaps aka they load String keys and
		// serializable values

		/* Creating a test JSONObject, essentially a hashmap to place Medication object
		   data entries in to convert to a JSON string */
			
		JsonObject testJsonObject = new JsonObject();

		testJsonObject.put("name", testMed.getName());
		testJsonObject.put("dosage", testMed.getDosage());
		testJsonObject.put("type", testMed.getType());
		testJsonObject.put("totalHoursOfClarity", testMed.getTotalHoursOfClarity());

		// Note to self that serialization = the process of turning a data structure into
		// something that can be stored (aka text like a JSON file....)

		// Another note to self about how when using JSONObjects to store Java classes, 
		// every data property of an object must be serializable (aka toString()) 

		// and if any properties are objects themselves then they should be converted
		// into a JSON string on its own first before putting into a JSONObject

		// in other words, it should be serialized first 
		// to create a nested JSONObject (heck yeahhhhh that's kinda cool)
		
		// for this project's usecase, LocalDateTime objects can be converted to String and back
		// so we will use that instead of making a LocalDateTime JSONObject

		testJsonObject.put("lastReportedlyTaken", testMed.getLastReportedlyTaken().toString());	
	
		String testJson = testJsonObject.toJson();	
		
		/* Loading a Medication object from a JSON string via a JSONObject */ 

		System.out.print("\nNow attempting to load JSON string into a new Medication object...\n");
		
		// Reminder to self that JSONObjects when deserializing (loading from a String) must handle
		// the exception (aka have a try-catch block as a plan when the deserialization fails
		// because the String is not in a valid JSON format)

		try{
			JsonObject anotherJsonObject = (JsonObject) Jsoner.deserialize(testJson);
			
			String[] fields = 	{
							"dosage", "name", "totalHoursOfClarity", "type", 
							"lastReportedlyTaken"
						};
			for (String field : fields){

				// This is a quick check if all the fields of a Medication object are inside
				// the JSONObject

				// if not then we raise an Exception

				if(!anotherJsonObject.containsKey(field)){
					throw new Exception("JSON string provided does not have the " + field 						+ " property!\n");
				}

				String test_value = anotherJsonObject.get(field).toString();
				
			}

			String loaded_name = anotherJsonObject.get("name").toString();
			String loaded_dosage = anotherJsonObject.get("dosage").toString();
			long loaded_totalHoursOfClarity = 
				Long.valueOf(anotherJsonObject.get("totalHoursOfClarity").toString());
			String loaded_type = anotherJsonObject.get("type").toString();
			LocalDateTime loaded_lastReportedlyTaken = 
				LocalDateTime.parse(anotherJsonObject.get("lastReportedlyTaken").toString());
			
			Medication anotherMed =	new Medication(loaded_name, loaded_dosage, loaded_type,
						loaded_totalHoursOfClarity);
			
			anotherMed.setLastReportedlyTaken(loaded_lastReportedlyTaken);
			
			System.out.print(anotherMed);
			if(testMed.equals(anotherMed)){
				System.out.print("Medication object successfully loaded from JSON string!");
			}
			else{
				System.out.print("Medication object loaded from JSON string is not the same" 				     + " as the original object....consider this a failure");
			}		

		}
		catch(JsonException je){
			System.out.print("Loading JSON string into JSONObject failed! Invalid JSON string.");			}			
		
	}
}
