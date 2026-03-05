import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

public class Test{

	public static void main(String[] args) throws Exception{
				
		Medication testMed1 = 	new Medication(
						"Adderall",
						"20mg",
						"XR",
						3.0
					);

		Medication testMed2 = 	new Medication(
						"Adderall",
						"10mg",
						"IR",
						2.0
					);

		LocalDateTime right_now = 
		LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
	
		Medication[] testDailyRoutine = {testMed1, testMed2};


		User testUser = new User(
				"Andrew",
				testDailyRoutine
		);

		testUser.getWhenLastTaken().put(testMed1, right_now);
		testUser.getWhenLastTaken().put(testMed2, right_now);

		HashMap<String,String> test_serialized_WLT = 
		UserData.serializeWhenLastTaken(testUser);				
		for (HashMap.Entry<String,String> serialized_pair : 
		test_serialized_WLT.entrySet()){

			String serialized_med = serialized_pair.getKey();
			String serialized_ldt = serialized_pair.getValue();

			System.out.println("(" + serialized_med + " , " + 
			serialized_ldt + ")"); 		

		}	
			
	}
}
