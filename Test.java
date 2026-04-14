import java.time.LocalDateTime;
import java.util.HashMap;
import java.io.File; 
//import org.apache.commons.collections4.bidimap.DualHashBidiMap;


public class Test extends DefaultMedicationData{

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

		Medication testMed = loadTestMedication();
	
		User loadedUser = UserData.loadUserFromJsonFile();

		System.out.println(loadedUser);

		UserMedication.takeMedication(loadedUser, 
		DefaultMedicationData.DEFAULT_MEDICATION);

		System.out.println(loadedUser);

	}
}
