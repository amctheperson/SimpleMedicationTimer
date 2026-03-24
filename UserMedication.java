import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Map;

public class UserMedication{

	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1-2	GET MOST RECENT		getMostRecentlyTakenMedication(
		MED TAKEN		User user)

	3	TODO


	This class is a work in progress.
	
	*/

































/*
				     Page 0

								    CTRL + F -->
*/


	// GET MOST RECENTLY TAKEN MED BY USER FUNCTION//

	// Returns Medication with most recent LocalDateTime value
	// in whenLastTaken HashMap of provided User


	public static Medication getMostRecentlyTakenMedication(User user){
	
		// If no Medication ever been reportedly taken by user
		// return default Medication

		if (user.getWhenLastTaken().isEmpty()){

			String error_message = 

			"\nFunction for getting User\'s most recently taken " +
			"Medication called on User with no reported history." +
			"\n" + DefaultMedicationData.
			DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX + "\n";

			System.err.print(new IllegalArgumentException(
			error_message));
			
			return DefaultMedicationData.DEFAULT_MEDICATION;	
		}

		// Default Medication used as a null placeholder here	
		Medication mostRecentMed = 
		DefaultMedicationData.DEFAULT_MEDICATION;
	

		// (function contd. on Page 2)


















/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/

	
	// GET MOST RECENTLY TAKEN MED BY USER FUNCTION//
	// (contd.)

		// Note to self: forEach functional interface not used here
		// because continue required for the first iteration
		// and local variable mostRecentMed is modified
		// (illegal in a lambda function)

		// therefore for each loop done instead 	
	
		for (Map.Entry<Medication, LocalDateTime> 
		user_wlt_Entry : user.getWhenLastTaken().entrySet()){

			Medication takenMed = user_wlt_Entry.getKey();
			LocalDateTime takenMedAt = user_wlt_Entry.getValue();

			if(mostRecentMed.equals(
				DefaultMedicationData.DEFAULT_MEDICATION)){

				mostRecentMed = takenMed;
				continue;
			}

			LocalDateTime mostRecentMedAt = 
			user.getWhenLastTaken().get(mostRecentMed);

			if (takenMedAt.isAfter(mostRecentMedAt)){

				mostRecentMed = takenMed;

			}	
		} 
		
		return mostRecentMed;	
	}

				













/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/

	

	/*

	TODO

	make this a minutes left of recent med, return 0 if negative
	
	public boolean isLastTakenMedicationStillActiveForUser(
	User user, Medication med) {

	isMostRecentlyTakenMedicationStillActive{

		Medication mostRecentMed = getMostRecentlyTakenMedication(user);
		
		LocalDateTime mostRecentMed_takenAt = user.getWhenLastTaken	
				

	}



	if (!hasUserTakenMedicationBefore(user, med)){

		String error_message = 
		"Function invo"

		return false;		


	}


		LocalDateTime whenMedLastTaken = whenLastTaken.get(med)	

	}
	
	lastMedTakenByUser <--static var?? why not

	make new exception cause why not forrrr last taken 


	shouldUserTakeMedNow

		returns boolean obv

		checks dailyRoutine to see next med

		to see prev med and check if lastTaken		

	takeMed

		update user
		update lastMedTakenByUser
	
	isLastMedActive
	
	remainingTimeOnActiveMed	


	*/



/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
}
