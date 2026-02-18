import java.util.Objects;

public class Medication{


	/*
	
	PAGE	CONTENTS

	1	Instance Variables

	2	Constructor

	3	Accessor Methods

	4	Modifier Methods

	5	toString() Function

	6-7	equals(Object o) Function
	
	8	hashcode() Function	
	

	
	This class defines the Medication object, used to represent
	the individual ADHD medications that the user takes.
	
	*/






















/*
				     Page 0

								    CTRL + F -->
*/


	// INSTANCE VARIABLES // 
	

	// Name of the medication

	private String name;


	// Dosage of the medication

	// Units of measurement should be included (i.e. "10mg")

	private String dosage;


	// Type of dosage (i.e. "XR")
	
	private String type;


	// Hours of mental clarity this medication provides the user

	// User-determined

	private double totalHoursOfClarity;
























/*
				     Page 1

<-- CTRL + B							    CTRL + F -->
*/


	// CONSTRUCTOR //


	public Medication(
				String n,
				String d,
				String t,
				double thoc
	){

		name = n;

		dosage = d;

		type = t;

		totalHoursOfClarity = thoc;
	}































/*
				     Page 2

<-- CTRL + B							    CTRL + F -->
*/


	// ACCESSOR METHODS // 

	
	public String getName(){

		return name;

	}

	public String getDosage(){

		return dosage;

	}
	
	public String getType(){

		return type;

	}

	public double getTotalHoursOfClarity(){

		return totalHoursOfClarity;

	}

	// This non-boilerplate accessor method returns a composite of
	// the name, dosage, and type properties of the class instance
	
	// (Note to self: This will likely get called a lot from the front-end)
		
	public String getCompleteInfo(){

		return name + " " +  dosage + " " + type;

	}












/*
				     Page 3

<-- CTRL + B							    CTRL + F -->
*/

	
	// MODIFIER METHODS // 


	public void setName(String new_name){
	
		name = new_name;

	}

	public void setDosage(String new_dosage){

		dosage = new_dosage;

	}

	public void setType(String new_type){

		type = new_type;

	}

	public void setTotalHoursOfClarity(double new_total_hoc){

		totalHoursOfClarity = new_total_hoc;

	}	























/*
				     Page 4

<-- CTRL + B							    CTRL + F -->
*/
	

	// TO STRING FUNCTION // 


	@Override
	public String toString(){

		String printable = "Behold, a Medication object!\n";

		printable += 
				"\tName: " + name + "\n" +
				"\tDosage: " + dosage + "\n" +
				"\tType: " + type + "\n" +
				"\tTotal Hours Of Clarity: " + 
				Double.toString(totalHoursOfClarity) + "\n";

		return printable; 
	}
































/*
				     Page 5

<-- CTRL + B							    CTRL + F -->
*/


	// EQUALS FUNCTION // 		

	
	@Override
	public boolean equals(Object o){
		
		// Accept equivalency if the provided object
		// is the Medication instance it is comparing itself to

		if(o == this){

			return true;

		}
		
		// Reject equivalency if the provided object
		// is not a Medication instance
		
		if(!(o instanceof Medication)){

			return false;

		}

		// Provided object can be validly casted
		// to a Medication instance now

		Medication otherMed = (Medication) o;

		// Reject equivalency if provided Medication is a null object
		
		if(Objects.isNull(otherMed)){

			return false;

		}
	












/*
				     Page 6

<-- CTRL + B							    CTRL + F -->
*/


	// EQUALS FUNCTION //
	// (contd.)        //


		// After all edge cases have been addressed

		// Evaluate the equivalency of the two Medication instances
		// based off the equivalency of their class properties
				
		String otherMedName = otherMed.getName();

		String otherMedDosage = otherMed.getDosage();

		String otherMedType = otherMed.getType();

		double otherMed_thoc = otherMed.getTotalHoursOfClarity();
		
		return 	name.equals(otherMedName) &&

			dosage.equals(otherMedDosage) &&

			type.equals(otherMedType) &&

			// Medication class property totalHoursOfClarity
			// is type double, therefore a primitive
			// so comparing their memory address codes is valid

			totalHoursOfClarity == otherMed_thoc;

	}



















/*
				     Page 7

<-- CTRL + B							    CTRL + F -->
*/

	
	// HASHCODE FUNCTION //
	
	// Function derived from this StackOverflow post

	// https://stackoverflow.com/questions/18965374/
	// overriding-hashcode-in-java
		
	@Override
	public int hashCode(){

		int result = 1;

		result = 31 * result + 
		(name == null ? 0 : name.hashCode());

		result = 31 * result +
		(dosage == null ? 0 : dosage.hashCode());

		result = 31 * result + 
		((Double) totalHoursOfClarity == null ? 0 : 
		Double.hashCode(totalHoursOfClarity));

		result = 31 * result + (type == null ? 0 : type.hashCode());
		return result;
	}
























/*
				     Page 8

<-- CTRL + B							    
*/
	
}
