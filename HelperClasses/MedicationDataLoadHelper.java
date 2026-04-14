import com.github.cliftonlabs.json_simple.JsonObject;

import java.io.File;

public class MedicationDataLoadHelper {

	/*



	PAGE	PURPOSE		FUNCTION SIGNATURE

	1-2	JSON Object	jsonObjectToMedication(JsonObject jsonObject)	
		-->
		Medication	
	
	
	
	
	This class contains functions that assist in the 
	loading function of MedicationData.

	*/





























/*
				     Page 0

								    CTRL + F -->
*/

	
	// JSON OBJECT HASHMAP TO MEDICATION FUNCTION // 


	// Validates provided JsonObject has required data
	// for instantiating a Medication then calls Medication constructor

	// If provided JsonObject is invalid for Medication instantiation
	// default Medication is returned


	public static Medication jsonObjectToMedication(JsonObject jsonObject)
	throws Exception {
		
		// First check: jsonObject has all Medication class properties
			
		String[] required_properties_Medication = 

		{
			"name",
			"dosage",
			"type", 
			"totalHoursOfClarity"
		};
		
		try{

			LocalData.validateJsonObjectForClassConversion(
			jsonObject, required_properties_Medication);
		}

		// If check failed, switch to default Medication

		catch (IllegalArgumentException e){

			System.err.println(e);
			System.err.println(new Exception(
				DefaultMedicationData.
				DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX));
			
			return DefaultMedicationData.DEFAULT_MEDICATION;	

		}

		// (contd. on Page 2)
		




		
/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/	


	// JSON OBJECT HASHMAP TO MEDICATION FUNCTION // 
	// (contd.)


		// Retrieve and deserialize Medication data
	
		String loaded_name = jsonObject.get("name").toString();
	
		String loaded_dosage = jsonObject.get("dosage").toString();

		String loaded_type = jsonObject.get("type").toString();
	
		String loaded_totalHoursOfClarity_string =
		jsonObject.get("totalHoursOfClarity").toString();
	 
		double loaded_totalHoursOfClarity = 
			Double.valueOf(loaded_totalHoursOfClarity_string);
		
		// Construct new Medication

		Medication loadedMed = new Medication(
						loaded_name,
						loaded_dosage,
						loaded_type,
						loaded_totalHoursOfClarity
						);

		return loadedMed;	

	}




















/*
				     Page 2

<-- CTRL + B							      
*/
}
