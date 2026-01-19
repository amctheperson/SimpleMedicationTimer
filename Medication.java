import java.time.LocalDateTime;
import java.util.Objects;

public class Medication{

	/* Instance variables */

	private String name;
	private String dosage;
	private String type;
	private long totalHoursOfClarity;
	private LocalDateTime lastReportedlyTaken;	

	/* Constructor */

	public Medication(String n, String d, String t, long thoc){
		name = n;
		dosage = d;
		type = t;
		totalHoursOfClarity = thoc;
		lastReportedlyTaken = null;
	}

	/* Accessor methods aka getter methods */	
	
	public String getName(){return name;}
	public String getDosage(){return dosage;}
	public String getType(){return type;}
	
	// Note to self that this accessor method is slightly different in the sense
	// that it is a composite of the name, dosage, and type properties of
	// the class that will be probably be called from the front-end

	public String getCompleteInfo(){return name + " " +  dosage + " " + 
		type;}
	
	public long getTotalHoursOfClarity(){return totalHoursOfClarity;}
	public LocalDateTime getLastReportedlyTaken(){return
		lastReportedlyTaken;}
	
	/* Modifier methods aka setter methods */

	public void setName(String new_name){name = new_name;}
	public void setDosage(String new_dosage){dosage = new_dosage;}
	public void setType(String new_type){type = new_type;}
	public void setTotalHoursOfClarity(long new_total_hoc){
		totalHoursOfClarity = new_total_hoc;}
	
	// Note to self that this modifier method is only being used in reloading a
	// Medication instance from a JSON String/file
	// and would not be called by the user since generally speaking we don't want to
	// freely set the time a Medication was used to any point in time

	public void setLastReportedlyTaken(LocalDateTime new_lrt){
		lastReportedlyTaken = new_lrt;
	}	
	
	// Note that this modifier method for updating lastReportedlyTaken of a Medication
	// object should only be used internally for testing purposes

	// It was mainly used to test the LocalDateTime object

	public boolean tryTakingAt(LocalDateTime someTime){

		if(lastReportedlyTaken != null){
			LocalDateTime acceptable_time = 
			lastReportedlyTaken.plusHours(totalHoursOfClarity);

			if(someTime.isBefore(acceptable_time)){return false;}

		}

		lastReportedlyTaken = someTime;
		return true;
				
	}
	
	// Note that this is the modifier method that should be called to update
	// a Medication object normally 
	
	public boolean tryTaking(){

		LocalDateTime right_now = LocalDateTime.now();

		if(lastReportedlyTaken != null){
			LocalDateTime acceptable_time = 
			lastReportedlyTaken.plusHours(totalHoursOfClarity);

			if(right_now.isBefore(acceptable_time)){return false;}

		}

		lastReportedlyTaken = right_now;
		return true;
				
	}
	

	// Note that this modifier method for lastReportedlyTaken should be called
	// from the UI, all other ones are defunct from now on (disregard earlier)

	// because trytaking only checks for conflicts if retaking the same Medication
	// as opposed to overlap of mental clarity from other Medication
	// can be retooled by making an order list of Medication

	// like an order to it or something idk
	// Medication[] routine = {adderall 20 mg xr, adderall 10 mg IR};

	// something like that yeah

	public void take(){

		lastReportedlyTaken = LocalDateTime.now(); 
				
	}


	

	// Note that the following functions were made for debugging purposes
	// and also are good review for myself - Andrew
	
	/* toString function */
	
	// Note to self that this toString function overrides the default object toString
	// to print the address in memory and makes the object "printable"

	@Override
	public String toString(){
		String printable = "Behold, a Medication object!\n";
		printable += 
				"\tname: " + name + "\n" +
				"\tdosage: " + dosage + "\n" + 
				"\ttotalHoursOfClarity: " + 
				Long.toString(totalHoursOfClarity) + "\n" +
				"\ttype: " + type + "\n" + 
				"\tlastReportedlyTaken: " + 
				lastReportedlyTaken.toString() + "\n";
		return printable; 
	}
	
	/* equals and hashCode functions */

	// Note to self that this function overrides the default equals function for objects
	// which checks if they point to the same memory address

	// We want to override this because we can check equality of two Medicine objects if their
	// properties are equivalent
	
	@Override
	public boolean equals(Object o){
		
		if(o == this){return true;}
		
		// check if other object is even a Medication object, also checks for null object
		if(!(o instanceof Medication)){return false;}

		Medication otherMed = (Medication) o;
		
		if(Objects.isNull(otherMed)){
			return false;
		}
		
		// Note to self that since the class property totalHoursOfClarity is a long
		// and therefore a primitive, we can compare the property of two Medication 
		// instances via equals signs aka through memory address codes

		// Note that we do not compare the class property lastReportedlyTaken here
		// as it is not an essential characteristic of a Medication 
		// and if two Medication objects equate in all the other properties
		// then logically they are equivalent Medications, just possibly taken
		// at two different times

		// Truthfully this is to prevent duplications of JSON files from being made
		// when checking if a Medication has already been saved (see LocalData.java
		// for more on this) 
			
		return name.equals(otherMed.getName()) && dosage.equals(otherMed.getDosage()) &&
		totalHoursOfClarity == otherMed.getTotalHoursOfClarity() &&
		type.equals(otherMed.getType()); 

	}
	
	// Note to self that this function overrides the default hashCode function for objects
	// which we only have to do because we are overriding the equals functionality and therefore
	// we should make sure the hashCode function also makes the hashcodes of two equivalent Medicine
	// objects the same
	
	// Note that similar to how we ignore the lastReportedlyTaken class property
	// in the overridden equals comparison function above,
	// we do not use this same class property in the overriden hashCode generation function
	// because two Medication objects equivalent in all other class properties
	// should be considered equivalent, and therefore generate the same hashcode
		
	@Override
	public int hashCode(){
		int result = 1;
		result = 31 * result + (name == null ? 0 : name.hashCode());
		result = 31 * result + (dosage == null ? 0 : dosage.hashCode());
		result = 31 * result + ((Long) totalHoursOfClarity == null ? 0 : Long.hashCode(totalHoursOfClarity));
		result = 31 * result + (type == null ? 0 : type.hashCode());
		return result;
	}		
}
