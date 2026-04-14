import java.lang.IllegalArgumentException;
import java.lang.Integer;

import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserMedication{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE
					
	1-2	GET MOST RECENT		getMostRecentlyTakenMedication(
		MED TAKEN		HashMap<Medication, LocalDateTime> 
					whenLastTaken)

	3-4	TIME LEFT OF		calculateRemainingTimeActive( 
		ACTIVE MED		Medication med, LocalDateTime takenAt)

	5	CHECK IF MED		isMedicationStillActive(
		STILL ACTIVE		Medication med,
					HashMap<Medication, LocalDateTime> 
					whenLastTaken)
	
	6-7	GET NEXT MED TO TAKE	getNextMedicationToTake(
					Medication mostRecentMed, 
					Medication[] dailyRoutine,
					HashMap<Medication,LocalDateTime>
					whenLastTaken)
 	
	8	VERIFY NEXT MED		shouldNextMedicationBeTakenNow(
		OK TO TAKE NOW		Medication[] dailyRoutine,	
					HashMap<Medication,LocalDateTime>
					whenLastTaken)

	9	HAVE USER TAKE MED	takeMedication(User user, 
					Medication med)
 

	This class contains functions that use User class properties 
	to obtain information relevant to the front-end of this application.
			
	*/



/*
				     Page 0

								    CTRL + F -->
*/


	// GET MOST RECENTLY TAKEN MED FUNCTION//

	// Returns Medication with most recent LocalDateTime value
	// in whenLastTaken HashMap of provided User


	public static Medication getMostRecentlyTakenMedication(
	HashMap<Medication, LocalDateTime> whenLastTaken){

		// Error: No Medication ever reportedly taken	

		if (whenLastTaken.isEmpty()){

			String error_message = 

			"\nFunction for getting most recently taken " +
			"Medication called on HashMap with no reported " + 
			"history.\n" + DefaultMedicationData.
			DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX + "\n";

			System.err.print(new IllegalArgumentException(
			error_message));
			
			return DefaultMedicationData.DEFAULT_MEDICATION;	
		}

		// Default Medication used as a null placeholder here	
		Medication mostRecentMed = 
		DefaultMedicationData.DEFAULT_MEDICATION;
	

		// (function contd. on Page 2) -->


















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
		whenLastTaken_Entry : whenLastTaken.entrySet()){

			Medication takenMed = whenLastTaken_Entry.getKey();

			LocalDateTime takenMedAt = 
			whenLastTaken_Entry.getValue();

			if(mostRecentMed.equals(
				DefaultMedicationData.DEFAULT_MEDICATION)){

				mostRecentMed = takenMed;
				continue;
			}

			LocalDateTime mostRecentMedAt = 
			whenLastTaken.get(mostRecentMed);

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


	// CHECK IF MED TAKEN STILL ACTIVE FUNCTION
	
	// Checks if provided Medication still actively providing 
	// mental clarity, based on provided HashMap 


	public static boolean isMedicationStillActive(
		Medication med, 
		HashMap<Medication, LocalDateTime> whenLastTaken
	){

		// Error case: med not in keys of whenLastTaken
	
		if(!whenLastTaken.containsKey(med)){

			String error_message = 
			"Provided HashMap whenLastTaken does not contain " + 
			"provided Medication med as a key.\n";

			var error = new IllegalArgumentException(error_message);		
			System.err.print(error);	

			return false;

		}

		// Get time remaining active of med

		LocalDateTime med_takenAt = 
		whenLastTaken.get(med);

		RemainingTime timeLeftActive = 
		calculateRemainingTimeActive(
			med, 
			med_takenAt);

		// No time remaining active -> Med not still active

		if(	timeLeftActive.remainingWholeHours() == 0 &&
			timeLeftActive.remainingMinutes() == 0 	){

			return false;

		}

		// Any time remaining active -> Med still active
		
		return true; 
	}	
/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// GET NEXT MED USER SHOULD TAKE FUNCTION // 

	// Returns Medication that should be taken next according to
	// assorted provided data

	public static Medication getNextMedicationToTake(
	Medication mostRecentMed, Medication[] dailyRoutine, 
	HashMap<Medication, LocalDateTime> whenLastTaken) {

		// Error: mostRecentMed not in dailyRoutine_List

		// List conversion done for ease of implementation

		List<Medication> dailyRoutine_List = 
		Arrays.asList(dailyRoutine);
		
		if(!dailyRoutine_List.contains(mostRecentMed)){

			String error_message = 
			"Function for getting next Medication to take " +
			" was provided Medication that does not appear " +
			"in also provided Medication array. " + 
			DefaultMedicationData.
			DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX + "\n";

			System.err.print(new IllegalArgumentException(
			error_message)); 
			
			return DefaultMedicationData.DEFAULT_MEDICATION;
		}

		// Error: mostRecentMed not a key for whenLastTaken 

		if(!whenLastTaken.containsKey(mostRecentMed)){

			String error_message = 
			"Function for getting next Medication to take " +
			" was provided Medication that does not appear " +
			"as a key in also provided HashMap. " + 
			DefaultMedicationData.
			DEFAULT_MEDICATION_ERROR_MESSAGE_SUFFIX + "\n";

			System.err.print(new IllegalArgumentException(
			error_message)); 
			
			return DefaultMedicationData.DEFAULT_MEDICATION;
		}

		// (FUNCTION CONTINUES ON PAGE 7 -->)

/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/
	

	// GET NEXT MED USER SHOULD TAKE FUNCTION // 
	// (contd.)


		int mostRecentMed_index = 
		dailyRoutine_List.indexOf(mostRecentMed); 
		
		// Case 1: mostRecentMed has been taken more than a duration
		// of time ago ->
		// The routine starts anew 

		LocalDateTime mostRecentMed_takenAt = 
		whenLastTaken.get(mostRecentMed);

		if(UserMedicationHelper.hasDurationBetweenRoutinesPassed(
		mostRecentMed_takenAt)){

			return dailyRoutine_List.get(0);

		}

		// Case 2: mostRecentMed is last Medication of routine
		// and was taken as part of today's routine -> 
		// Today's routine has finished

		if( mostRecentMed_index == dailyRoutine_List.size() - 1){

			return DefaultMedicationData.DEFAULT_MEDICATION;

		}

		// Case 3: another Medication follows after mostRecentMed,
		// which was taken as part of today's routine ->
		// Continue with today's routine

		return dailyRoutine_List.get(mostRecentMed_index + 1);		
	}













/*
				     Page 7

<-- CTRL + B							    CTRL + F -->
*/



	// SHOULD NEXT MED BE TAKEN NOW FUNCTION //

	// Checks if next Medication to be taken should be taken now 
		
	public static boolean shouldNextMedicationBeTakenNow(Medication[] 
	dailyRoutine, HashMap<Medication,LocalDateTime> whenLastTaken){

		Medication mostRecentMed = 
		getMostRecentlyTakenMedication(whenLastTaken);

		boolean mostRecentMedStillActive =
		isMedicationStillActive(mostRecentMed, whenLastTaken);

		// Check 1: If most recently taken Medication still active 
		// then next Medication to be taken should not be taken yet

		if(mostRecentMedStillActive){

			return false;
		}	
		
		Medication nextMed = 
		getNextMedicationToTake(mostRecentMed, dailyRoutine, 
		whenLastTaken);
		
		// Check 2: Next Med is Default Med
		// Meaning the actual next Medication to take
		// is not available yet
	  
		if(nextMed.equals(DefaultMedicationData.DEFAULT_MEDICATION)){

			return false;

		}

		// If most recently taken Medication no longer active and
		// the next Medication either

			// continues the daily routine as expected

			// or starts new routine since duration of time passed
			// since last Medication was taken

		// then next Medication to take should be taken

		return true;
	
	}
	
/*
				     Page 8

<-- CTRL + B							    CTRL + F -->
*/


	// TAKE MEDICATION FUNCTION //
		
	// Updates provided User to reflect provided Medication being taken now
	// Returns true if update successful, false if not

	
	public static boolean takeMedication(User user, Medication med){

		// Error: med not in dailyRoutine of user

		// List conversion done for ease of implementation

		Medication[] user_dailyRoutine = user.getDailyRoutine();

		List<Medication> user_dailyRoutine_List = 
		Arrays.asList(user_dailyRoutine);
		
		if(!user_dailyRoutine_List.contains(med)){

			String error_message = 
			"Function for User to take Medication " +
			"was provided Medication that does not appear " +
			"in also-provided Medication array. " + 
			"Returning false to function takeMedication.\n"; 

			System.err.print(new IllegalArgumentException(
			error_message)); 
		
			return false;	
		}

		// Add pair to user's whenLastTaken HashMap to reflect
		// provided Medication being taken right now		
	
		HashMap<Medication,LocalDateTime> user_whenLastTaken = 
		user.getWhenLastTaken();
		
		// Formatting LocalDateTime pre-emptively

		String right_now_String = 
		LocalDateTime.now().format(UserData.DISPLAY_FORMAT);

		LocalDateTime right_now = 
		LocalDateTime.parse(right_now_String, UserData.DISPLAY_FORMAT);	

		user_whenLastTaken.put(med, right_now);
		
		return true;	

	}
/*
				     Page 9

<-- CTRL + B							    
*/
}
