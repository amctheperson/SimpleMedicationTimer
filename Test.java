import java.time.LocalDateTime;
import java.util.HashMap;
//import java.lang.Record;

public class Test{

	public static Medication loadTestMedication() throws Exception{

		Medication testMed = null;

		User loadedUser = UserData.loadUserFromJsonFile();
		for (Medication med: loadedUser.getDailyRoutine()){

			if(	med.getName().equals("Adderall") &&
				med.getDosage().equals("20mg")
			){

				testMed = med;	
				break;
			}	

		}

		return testMed;
			
	}

	public static void main(String[] args) throws Exception{

		UserMedication.RemainingTime testRT = 
		new UserMedication.RemainingTime(1,27);

		System.out.println(testRT);
 
		/*	
		Medication testMed = loadTestMedication();

		System.out.println(testMed);
		
		String testTakenAt_String = "Mar 25 2026 10:03 PM";
		
		LocalDateTime testTakenAt = LocalDateTime.parse(
		testTakenAt_String, UserData.DISPLAY_FORMAT);

		RemainingTime testRemainingTime = 
		UserMedication.calculateRemainingTimeActive(
		testMed, testTakenAt);	

		System.out.println(testRemainingTime.remainingWholeHours());
		System.out.println(testRemainingTime.minutes());

		*/
	}
}
