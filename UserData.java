import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.time.LocalDateTime;

public class UserData{

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	STATIC VARIABLES

	2-3	Check for unsaved Meds	allMedicationsSavedCheck(User user)

	4	dailyRoutine:		serializeDailyRoutine(User user)
		Medication[] ->
		String[]	

	5	whenLastTaken:		serializeWhenLastTaken(User user)
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


	// STATIC VARIABLES //

 
	public static final DateTimeFormatter DISPLAY_FORMAT = 
	DateTimeFormatter.ofPattern("MMM dd yyyy h':'mm' 'a");













































/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// Check for Unsaved Medication from User Function //


	// This function checks if any Medication instances 
	// from within the provided User class properties
	// have not been saved yet

	// If so, this function saves them locally
 
	public static void allMedicationsSavedCheck(User user)
	throws Exception{

		// Check for unsaved Medication in dailyRoutine

		for (Medication routineMed: user.getDailyRoutine()){
			
			boolean routineMedSavedCheck = MedicationDataSaveHelper.			checkIfSavedAlready(routineMed);

			if (!routineMedSavedCheck){
				
				File routineMedFile = MedicationData.
				saveMedicationToNewJsonFile(routineMed);
			
			}
		}
























/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/

	
	// Check for Unsaved Medication from User Function //
	// (contd.)


	// Check for unsaved Medication in whenLastTaken

	HashMap<Medication,LocalDateTime> user_whenLastTaken =
	user.getWhenLastTaken();

	user_whenLastTaken.forEach(

		// Trade-off of implementing lambda expression for Biconsumer
		// functional interface applied to each entry during forEach
		// is that Exceptions must be handled via try-catch

		(takenMed, takenMedAt) -> {

			try{
		
				boolean takenMedSavedCheck = 
				MedicationDataSaveHelper.
				checkIfSavedAlready(takenMed);

				if (!takenMedSavedCheck){
					
					MedicationData.
					saveMedicationToNewJsonFile(
					takenMed);		
				}
			}

			catch (Exception e){

				String error_message =
 
				"An exception occured either while " +
				"verifying a Medication inside whenLastTaken " +				"was saved already, or saving an unsaved " +
				"Medication to a new Medication JSON File; " +
				"Medication inside whenLastTaken may remain " + 				"unsaved.\n";

				System.err.print(error_message);			
			}
		}
	);

	}

/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/

		
	// Serialize dailyRoutine Medication Array Function //


	// Within a provided User, this function returns a String[]
	// representing the dailyRoutine array of Medication
	// where each String is the File name of a Medication JSON File
	// locally saved

	public static String[] serializeDailyRoutine(User user)
	throws Exception{

		allMedicationsSavedCheck(user);

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
				     Page 4

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
					takenMedAt.format(DISPLAY_FORMAT);

					user_whenLastTaken_asStrings.put(
						takenMedFileName,
						takenMedAt_string
					);

				}







/*
				     Page 5

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
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// TO DO 

	// UserDataSave class?
		
		// User class -> JSON String

	// UserDataLoad class

		// JSON String -> User class	

	// Continue learning about Functional Interfaces








	
	

























/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/	
	


}
