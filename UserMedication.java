import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Map;
import java.time.temporal.ChronoUnit;

public class UserMedication{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1-2	GET MOST RECENT		getMostRecentlyTakenMedication(
		MED TAKEN		User user)

	3-4	TIME LEFT OF		calculateRemainingTimeActive( 
		ACTIVE MED		Medication med, LocalDateTime takenAt)

	5	TODO
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


	// CALCULATING REMAINING TIME ACTIVE OF TAKEN MEDICATION

	// From current point in time
	// calculates remaining hours and minutes left 
	// of provided Medication taken at provided LocalDateTime
		
	// Returns data in Record (data-carrier) class RemainingTime

	// Note to self:
 
	// Record access in other classes should be treated
	// like accessing a function from another class

	// (i.e. 	UserMedication.RemainingTime = 
	// 		new UserMedication.RemainingTime(2, 45);


	public record RemainingTime(int remainingWholeHours, 
	int remainingMinutes) {}

	public static RemainingTime calculateRemainingTimeActive(
	Medication med, LocalDateTime takenAt){

		// Calculate total minutes of clarity med has

		// First compare total minutes of clarity med has
		// to minutes passed since med was last taken to now 
	
		double totalHoursOfClarity = 
		med.getTotalHoursOfClarity(); 
	
		int totalMinutesOfClarity = 
		UserMedicationHelper.calculateTotalMinutesOfClarity(
		totalHoursOfClarity);

		LocalDateTime currentTime = LocalDateTime.now();

		// LocalDateTime function "until" returns long 
		// casting to int for ease of implementation

		int minutesSinceMedTaken = 
		(int) takenAt.until(currentTime, ChronoUnit.MINUTES);
 
		// FUNCTION CONTINUES ON PAGE 4 -->






/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/


	// CALCULATING REMAINING TIME ACTIVE OF TAKEN MEDICATION
	// (contd.)


		// Return no time remaining (0h0m) if most recent 
		// Medication taken by user is no longer active

		if(minutesSinceMedTaken >= totalMinutesOfClarity){

			return new RemainingTime(0,0);	

		}
	
		int remainingMinutesOfClarity = 
		totalMinutesOfClarity - minutesSinceMedTaken; 
	
		int remainingTime_minutes = remainingMinutesOfClarity % 60;

		int remainingTime_wholeHours = 
		(remainingMinutesOfClarity - remainingTime_minutes) / 60;

		return new RemainingTime(
		remainingTime_wholeHours, remainingTime_minutes);
		
	}

























/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/

	
	/*

	TODO

	shouldUserTakeMedNow

		returns boolean obv

		checks dailyRoutine to see next med

		to see prev med and check if lastTaken		

	takeMed

		update user
		update lastMedTakenByUser
	
	*/
































/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/
}
