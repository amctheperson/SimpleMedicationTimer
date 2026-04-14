import java.time.LocalDateTime;
import java.lang.Math;

public class UserMedicationHelper{


	/*

	PAGE	PURPOSE			FUNCTION SIGNATURE

	1	Static Variables

	1	Calc total minutes	calculateTotalMinutesOfClarity( 
		of clarity		double totalHoursOfClarity)

	2	Check if entire		hasDurationBetweenRoutinesPassed( 
		duration passed 	LocalDateTime mostRecentMed_takenAt) 
		since med taken	


	This class contains functions that assist in 
	UserMedication functionality, aka functions involving 
	User class properties and Medication.

	These are also functions that the front-end is unlikely to call directly	either.
	
	*/
























/*
				     Page 0

								    CTRL + F -->
*/
	

	// Static Variables

	// static int value representing how many hours should have passed
	// between end of last routine taking Medication (typically yesterday)

	// and before application permits starting new routine (today) 

	public static int DURATION_BETWEEN_ROUTINES = 12;










































/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// Calculate Total Minutes of Clarity Function //

	// Assist function for calculateRemainingTimeActive
	// (UserMedication : PG 3) 

	// Converts provided double representing total hours of mental clarity
	// to an int representing total minutes of mental clarity	

 
	public static int calculateTotalMinutesOfClarity(
	double totalHoursOfClarity){
		
		// Fractional hours handled separately from whole hours
		// during conversion to minutes

		int tHOC_whole_part = 
		Double.valueOf(totalHoursOfClarity).intValue();

		double tHOC_fractional_part = totalHoursOfClarity - 
		tHOC_whole_part;

		int minutesOfClarity_from_whole = 
		tHOC_whole_part * 60;

		// Use Math.round to handle any remaining decimal minutes
		// Then cast resulting long to int

		int minutesOfClarity_from_fractional = 
		(int) Math.round(tHOC_fractional_part * 60);

		int totalMinutesOfClarity = minutesOfClarity_from_whole + 
		minutesOfClarity_from_fractional;

		return totalMinutesOfClarity; 

	}














/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// Check if Duration Between Routines Has Passed Function //
 
	// Assist function for getNextMedicationToTake
	// (UserMedication : PG 6-7)

	// Checks if alotted hours set by DURATION_BETWEEN_ROUTINES 
	// has passed since provided LocalDateTime until now

 
	public static boolean hasDurationBetweenRoutinesPassed(
	LocalDateTime med_takenAt){

		// Checking if 1 duration past med_takenAt
		// occured already or is right now

		// In LocalDateTime logic this would be
		// checking if med_takenAt + duration <= rightNow

		LocalDateTime durationAfter_med_takenAt = 
		med_takenAt.plusHours(
			Integer.valueOf(DURATION_BETWEEN_ROUTINES).longValue()
		);

		LocalDateTime rightNow = LocalDateTime.now();

		boolean durationPassed = 
		durationAfter_med_takenAt.isBefore(rightNow) ||
		durationAfter_med_takenAt.isEqual(rightNow);

		return durationPassed;
			
	}


















/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/
}
