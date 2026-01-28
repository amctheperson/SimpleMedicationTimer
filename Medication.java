import java.util.Objects;

public class Medication{

	/* Instance variables */

	private String name;
	private String dosage;
	private String type;
	private double totalHoursOfClarity;

	/* Constructor */

	public Medication(String n, String d, String t, double thoc){
		name = n;
		dosage = d;
		type = t;
		totalHoursOfClarity = thoc;
	}

	/* Accessor methods aka getter methods */	
	
	public String getName(){return name;}
	public String getDosage(){return dosage;}
	public String getType(){return type;}
	public double getTotalHoursOfClarity(){return totalHoursOfClarity;}
	
	// Note to self that this accessor method is slightly different in the sense
	// that it is a composite of the name, dosage, and type properties of
	// the class that will be probably be called from the front-end

	public String getCompleteInfo(){return name + " " +  dosage + " " + 
		type;}
		
	/* Modifier methods aka setter methods */

	public void setName(String new_name){name = new_name;}
	public void setDosage(String new_dosage){dosage = new_dosage;}
	public void setType(String new_type){type = new_type;}
	public void setTotalHoursOfClarity(double new_total_hoc){
		totalHoursOfClarity = new_total_hoc;}	
	
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
				Double.toString(totalHoursOfClarity) + "\n" +
				"\ttype: " + type + "\n";

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
		
		// Note to self that since the class property totalHoursOfClarity is a double
		// and therefore a primitive, we can compare the property of two Medication 
		// instances via equals signs aka through memory address codes
	
		return name.equals(otherMed.getName()) && dosage.equals(otherMed.getDosage()) &&
		totalHoursOfClarity == otherMed.getTotalHoursOfClarity() &&
		type.equals(otherMed.getType()); 

	}
	
	// Note to self that this function overrides the default hashCode function for objects
	// which we only have to do because we are overriding the equals functionality 
	// and therefore we should make sure the hashCode function also makes 
	// the hashcodes of two equivalent Medicine objects the same
		
	@Override
	public int hashCode(){
		int result = 1;
		result = 31 * result + (name == null ? 0 : name.hashCode());
		result = 31 * result + (dosage == null ? 0 : dosage.hashCode());
		result = 31 * result + ((Double) totalHoursOfClarity == null ? 0 : Double.hashCode(totalHoursOfClarity));
		result = 31 * result + (type == null ? 0 : type.hashCode());
		return result;
	}		
}
