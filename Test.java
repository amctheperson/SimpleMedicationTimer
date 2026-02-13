import java.io.File;

public class Test{

	public static void main(String[] args) throws Exception{
								
		Medication testMed = new Medication(
							"Adderall",
							"20mg",
							"XR",
							3.0
		);
		
		File testMedFile = 
		MedicationData.saveMedicationToJsonFile(testMed);
					
		Medication otherTestMed = 
		MedicationData.loadMedicationFromJsonFile(testMedFile);

		boolean equivCheck = testMed.equals(otherTestMed);
		
		System.out.println(equivCheck);

		otherTestMed.setDosage("10mg");
		otherTestMed.setType("IR");
		otherTestMed.setTotalHoursOfClarity(2.0); 
		
		File otherTestMedFile = 
		MedicationData.saveMedicationToJsonFile(otherTestMed);
			

		// should raise just 1 IllegalArgumentException
		
				
		File notAMedFile = new File("resources" + File.separator + 
		"cheatsheet.txt");
		System.out.println(notAMedFile.getName());
		String testString = 
		MedicationDataLoadHelper.jsonFileToJsonString(notAMedFile);
		System.out.println(testString);
			
		
	
		
		// Should raise an Exception
		MedicationData.overwriteJsonFileWithNewMedication(testMedFile,
		otherTestMed);
		

				
		testMed = 
		MedicationData.loadMedicationFromJsonFile(otherTestMedFile);

		boolean secondEquivCheck = testMed.equals(otherTestMed);
		
		System.out.println(secondEquivCheck);

		MedicationData.deleteMedicationFile(testMedFile);
		MedicationData.deleteMedicationFile(otherTestMedFile);		
			
	}
}
