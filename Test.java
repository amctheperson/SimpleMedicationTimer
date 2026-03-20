import com.github.cliftonlabs.json_simple.JsonObject;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Test{

	public static String getLastModifiedString(File file) throws Exception{

		long mod_epoch_milliseconds = file.lastModified();
		long mod_epoch_seconds = mod_epoch_milliseconds / 1000;

		LocalDateTime mod_LDT = 
		LocalDateTime.ofEpochSecond(
			
			mod_epoch_seconds,
			0,
			ZoneOffset.of("-7")
		);
	
		String mod_string = mod_LDT.format(UserData.DISPLAY_FORMAT);	

		return mod_string;

	}


	public static void main(String[] args) throws Exception{

		File userFile = UserData.USER_FILE;
		
		String firstModifiedTime = getLastModifiedString(userFile); 

		Medication testMed1 =
		new Medication(
	
			"Adderall",
			"20 mg",
			"XR",
			3.0

		);

		Medication testMed2 =
		new Medication(
	
			"Adderall",
			"10 mg",
			"IR",
			2.0

		);


		Medication[] testDailyRoutine = {testMed1, testMed2};				
		User newUser = new User("Andrew", testDailyRoutine);
 	
		UserData.saveUserToJsonFile(newUser);
		
		User loadedNewUser = UserData.loadUserFromJsonFile(); 

		String secondModifiedTime = getLastModifiedString(userFile);

		//System.out.println(oldUser);	
		System.out.println(firstModifiedTime);
		System.out.println(loadedNewUser);
		System.out.println(secondModifiedTime);		
		
	}
}
