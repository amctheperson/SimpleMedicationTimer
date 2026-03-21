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

	public static void main(String[] args) throws Exception{

		MedicationData.SAVED_MEDICATION.forEach(

			(savedMed, savedMedFile) -> {

				System.out.println(
				savedMedFile.getName() + " represents:\n" + 
				savedMed.toString()); 

			}	
		);		


		Medication newMed = 
		new Medication(
				"Concerta",
				"27mg",
				"XR",
				2.0
		);

		File knownMedFile = new File("Medication_3.json"); 
 
		MedicationData.overwriteJsonFileWithNewMedication(
		knownMedFile, newMed);
	
		MedicationData.SAVED_MEDICATION.forEach(

			(savedMed, savedMedFile) -> {

				System.out.println(
				savedMedFile.getName() + " represents:\n" + 
				savedMed.toString()); 

			}	
		);		
		
		/*
		Medication otherTestMed = 
		MedicationData.loadMedicationFromJsonFile(testMedFile);

		boolean equalityTest = testMed.equals(otherTestMed);

		System.out.println(equalityTest);	
		*/
	}
}
