import java.time.LocalDateTime;
import java.lang.Math;

public class UserMedicationHelper{

	public static int calculateTotalMinutesOfClarity(
	double totalHoursOfClarity){

		int tHOC_whole_part = 
		Double.valueOf(totalHoursOfClarity).intValue();

		double tHOC_fractional_part = totalHoursOfClarity - 
		tHOC_whole_part;

		int minutesOfClarity_from_whole = 
		tHOC_whole_part * 60;

		// Math.round will round up decimal minutes
		// but to a long which we can cast to an int

		int minutesOfClarity_from_fractional = 
		(int) Math.round(tHOC_fractional_part * 60);

		int totalMinutesOfClarity = minutesOfClarity_from_whole + 
		minutesOfClarity_from_fractional;

		System.out.println(totalMinutesOfClarity);

		return totalMinutesOfClarity; 


	}





	/*
	public static long calculateTotalMinutesOfClarity(
	double totalHoursOfClarity){

		long tHOC_whole_part = 
		Double.valueOf(totalHoursOfClarity).longValue();

		double tHOC_fractional_part = totalHoursOfClarity - 
		tHOC_whole_part;

		long minutesOfClarity_from_whole = 
		tHOC_whole_part * 60;

		long minutesOfClarity_from_fractional = 
		Math.round(tHOC_fractional_part * 60);

		long totalMinutesOfClarity = minutesOfClarity_from_whole + 
		minutesOfClarity_from_fractional;

		return totalMinutesOfClarity; 

	}

	*/

}
