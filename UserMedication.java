import java.lang.IllegalArgumentException;
import java.time.LocalDateTime;
import java.util.Map;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;

public class UserMedication{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE
					
	1-2	GET MOST RECENT		getMostRecentlyTakenMedication(
		MED TAKEN		HashMap<Medication, LocalDateTime> 
					whenLastTaken)

	3-4	TIME LEFT OF		calculateRemainingTimeActive( 
		ACTIVE MED		Medication med, LocalDateTime takenAt)

	5	CHECK IF MOST RECENT	isMostRecentMedicationStillActive(
		MED STILL ACTIVE	User user)
	
	6	GET NEXT MED TO TAKE	getNextMedicationToTake(
					Medication[] dailyRoutine, 
					Medication mostRecentMed)

	7	TODO			
 											This class is a work in progress.
	
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


	// CHECK IF MOST RECENT MED TAKEN STILL ACTIVE FUNCTION
	
	// Checks if most recent Medication still actively providing 
	// mental clarity for user	


	public static boolean isMostRecentMedicationStillActive(User user){
		
		Medication mostRecentMed = 
		getMostRecentlyTakenMedication(user.getWhenLastTaken());

		// Edge case: Default Medication returned as most recent Med
  
		// Indicates that the most recent Medication does not exist
		// therefore no Medication is still active

		if(mostRecentMed.equals(
		DefaultMedicationData.DEFAULT_MEDICATION)){

			return false;

		}		

		// mostRecentMed guaranteed to exist as key inside
		// whenLastTaken of user

		LocalDateTime mostRecentMed_takenAt = 
		user.getWhenLastTaken().get(mostRecentMed);

		RemainingTime timeLeftActive = 
		calculateRemainingTimeActive(
			mostRecentMed, 
			mostRecentMed_takenAt
		);

		// No active time remains on mostRecentMed

		if(	timeLeftActive.remainingWholeHours() == 0 &&
			timeLeftActive.remainingMinutes() == 0 	){

			return false;

		}
		
		return true; 

	}


	
/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// GET NEXT MED USER SHOULD TAKE FUNCTION // 

	// Returns Medication that should be taken next according to
	// provided Medication array dailyRoutine
	// and provided Medication representing most recent Med


	public static Medication getNextMedicationToTake(
	Medication[] dailyRoutine, Medication mostRecentMed) {

		// For convenience of implementation

		List<Medication> dailyRoutine_List = 
		Arrays.asList(dailyRoutine);
		
		int mostRecentMed_index = 
		dailyRoutine_List.indexOf(mostRecentMed);

		// Error: mostRecentMed not in dailyRoutine_List

		if(mostRecentMed_index == -1){

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

		// Edge case: mostRecentMed is last Medication 
		// in dailyRoutine Medication List

		if(mostRecentMed_index == dailyRoutine_List.size() - 1){

			return dailyRoutine_List.get(0);

		}

		// Next Medication to take 
		// is next Medication in dailyRoutine Medication List

		return dailyRoutine_List.get(mostRecentMed_index + 1);		
	}

/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/

	
	/*

	TODO

	refactor method that takes User lol, should only be taking
	whenLastTaken and.... Medication

		obv exception and return false if provided Medication
		not in whenLastTaken
	
	public static boolean HasDurationSinceLastRoutinePassed(LocalDateTime)
		// define static DURATION BETWEEN ROUTINES

		// set to 12 hrs for now


		// check if LocalDateTime is at least 12 hours ago
		
		// taken....at least 12 hours ago

		// at least

	shouldUserTakeAnotherMedNow --> UserMed

		returns boolean obv

		gets most recent med
	
		// checks if still active

		if not
		
		gets next Med

		// if next med is first in routine

		//checks if duration has passed

		// returns true

			
		
	
	takeMed --> userMed

		update user
		update lastMedTakenByUser
	
	*/
































/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/
}
