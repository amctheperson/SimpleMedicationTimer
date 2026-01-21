import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.cliftonlabs.json_simple.JsonKey;
import com.github.cliftonlabs.json_simple.JsonException; 

import java.time.LocalDateTime;

import java.lang.Exception;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.nio.CharBuffer;
import java.nio.ReadOnlyBufferException;

import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;

import java.util.ArrayList;

// Note that the purpose of this class is to hold all functionality involving saving and loading
// all data of objects locally

// In other words, this is where we save and load Medication objects to-and-from JSON files
// because it would be inefficient to make every Medication object being created have to import functions from an external package first

// plus the functionality of handling it's own conversion to a JSON file is beyond the scope
// of what a Medication object is supposed to represent

// In other words...they're just some ADHD meds dawg

public class LocalData{

	// For converting a Medication object to a JSON-formatted String

	public static String medicationToJSONString(Medication med){

		// Uses the JsonObject class from the json-simple package
		// to load class properties into a JSONObject hashmap
		// that can return a JSON-formatted String
		
		JsonObject jo = new JsonObject();

		jo.put("name", med.getName());
		jo.put("dosage", med.getDosage());
		jo.put("type", med.getType());
		jo.put("totalHoursOfClarity", med.getTotalHoursOfClarity());

		// Note that usually a property that is an object itself would usually
		// require its own JSON-formatted String before it can be serialized
		// but LocalDateTime objects can be converted to String and back easily
		// so we won't do that
		
		jo.put("lastReportedlyTaken", med.getLastReportedlyTaken().toString());

		return jo.toJson();
		
	}
	
	// For converting a JSON-formatted String into a new Medication object
	
	public static Medication jsonStringToMedication(String j_str) throws Exception{

		// This try-catch block will handle the possible error
		// of being provided a String that isn't in a valid 
		// JSON format
			
		try{
			JsonObject jo = (JsonObject) Jsoner.deserialize(j_str);

			// Handling possible error if provided JSON String does not have 
			// all the required fields of a Medication object 
			
			String[] fields = {"name", "dosage", "type", "totalHoursOfClarity",
					"lastReportedlyTaken"};

			for (String field : fields){
				if(!jo.containsKey(field)){
					throw new Exception("JSON string provided does" + 
					" not have the " + field + " property!\n");
				}	
			}

			// From this point on we can assume the JsonObject has processed
			// the JSON String into a JSONObject Hashmap
			
			// test if these have to be converted to String?
			
			// and thus we can yank class properties from the JSONObject

			String loaded_name = jo.get("name").toString();	
			String loaded_dosage = jo.get("dosage").toString();
			String loaded_type = jo.get("type").toString();
			long loaded_totalHoursOfClarity = 
				Long.valueOf(jo.get("totalHoursOfClarity").toString());
			LocalDateTime loaded_lastReportedlyTaken = 
				LocalDateTime.parse(jo.get("lastReportedlyTaken").toString());
			
			// then we can create a new Medication object with these class properties

			Medication someMed = new Medication(loaded_name, loaded_dosage,
				loaded_type, loaded_totalHoursOfClarity);

			someMed.setLastReportedlyTaken(loaded_lastReportedlyTaken);
			
			// and then return what we have done

			return someMed;
			
		}
		catch(JsonException je){
			System.out.print("Loading JSON string into JSONObject failed!" +
				" Invalid JSON format!");
		}
		
		// this will only be returned if exceptions occur
		// and is required since the method needs to return a Medication object
		// in this case, a Null Medication object has been returned due to the method
		// having an error 

		return null;

	
	}

	
	// This method takes in a File object and attempts to load it into a String

	// More specifically, it should be used for loading any JSON file into a JSON String

	public static String jsonFileToString(File someFile) throws Exception{

		String someJsonString = "";
		
		try{
			// Note that this line can cause a FileNotFound exception

			FileReader someFileReader = new FileReader(someFile);
			
			// Note that this line can cause an IllegalArgument exception

			// Also note that we chose 300 as a CharBuffer size on account of
			// our test JSON files being around 124 characters long

			// also MAX 300

			CharBuffer someCharBuffer = CharBuffer.allocate(300);

			// Note that this int keeps track of how many characters were read
			// in each pass of the read method by someFileReader

			// which gets repeatedly called in a while loop 
			// until it eventually sets charsReadInLastAttempt to -1
			// which means the end of someFile has been reached
			// aka the entire file has been read
			
			int charsReadInLastAttempt = 0;

		
			while(charsReadInLastAttempt != -1){
				

				// Note that this line can cause an IOException,
				// a NullPointerException, or a ReadOnlyBufferException

				charsReadInLastAttempt = someFileReader.read(someCharBuffer);

				someJsonString += someCharBuffer.toString();
				someCharBuffer.clear();
			}
			
			someFileReader.close();			
		}

		catch(FileNotFoundException e){new Exception("FileReader constructor could " + 
			"not be called on this file object.");}

		catch(IllegalArgumentException e){new Exception("CharBuffer instance could " +
			"not be created via allocate(int i) function because the provided " +
			"size for allocation is negative.");}

		catch(IOException e){new Exception("An I/O error occurred while invoking the " +
			"read(CharBuffer cb) of a FileReader instance.");}

		catch(NullPointerException e){new Exception("The provided CharBuffer is a " +
			"null object");}

		catch(ReadOnlyBufferException e){new Exception("Provided CharBuffer is set " +
			"to read-only");}

		return someJsonString;
	}
	
	// Function for getting all Medication JSON Files in the current directory
	// Returns an ArrayList of Files

	public static ArrayList<File> getAllMedicationJsonFilesHere(){
		
		// Note that we invoke an ArrayList
		// because we do not know how many Medication JSON files are in
		// the current directory

		// and therefore do not know how large of an array of Medication objects
		// we will need

		// and furthermore we need an array that can dynamically-change in size
		
		// Note to self that we don't choose to convert this list to an array
		// or use a set as an alternative since order of the JSON files (doesn't matter)
		// after it is loaded because we are not working with an immense amount of data

		// However we should consider converting these options if the app runs slow
		// in the future

		ArrayList<File> fileArrayList = new ArrayList<File>();
					
		// Open the current directory as a File object

		File curDir = new File("./");
		
		// This is a for-each block called on a list of all File objects that could
		// be any file or subfolder in the current directory

		// and iterates through each of these File objects	
	
		for(File someFile : curDir.listFiles()){

			// Skip all subdirectories
			if(someFile.isDirectory()){ // Skip all subdirectories
				continue;
			}

			String someFileName = someFile.getName();
			
			// We are looking for files that start with "Medication_"
			// and end in ".json"
			
			// and since the prefix is 11 chars and this suffix is 5 chars
			
			// we can conclude that the filenames we're looking for
			// are at least 11 + 5 chars, or 16 chars, long
			// conversely we can exclude any filenames shorter than 16 chars

			// so this conditional skips any Files
			// whose names aren't at least 16 chars long
			// because it would not be possible for them to be valid JSON
			// files due to the above prefix and suffix
				
			if(someFileName.length() < 16){
				continue;
			}

			// this also filters out any File objects that would cause
			// index out of bound exceptions 
			// when we grab substrings from the File name in the next block here

			// Note that we could also exclude Strings that are 16 char long 
			// aka changing the "< 16" to "<= 16" 
			// but we are leaving it in, just in case the hashcode returns as nothing
			
			// These lines grab substrings of the File name 
			// that could equate to the prefix and suffix that we mentioned earlier

			// Note that the second line starts at the index 5 chars from the end
			// and then goes to the end of the string

			String prefix_check = someFileName.substring(0,11);
			String suffix_check = someFileName.substring(someFileName.length() - 5);

			// This conditional filters out any files without the correct prefix
			// or without the correct suffix mentioned earlier ("Medication_", ".json")
				
			if(!prefix_check.equals("Medication_") || !suffix_check.equals(".json")){
				//System.out.println(someFileName);
				continue;
			}
			
			// We can assume that the only remaining File objects here are named like
			// "Medication_[some hashcode integer].json"

			// Note to self that in actual use of this app
			// this collection of remaining File objects should be at most...2-3 files

			
			fileArrayList.add(someFile);

		}

		return fileArrayList;
		
	}	


	// TO-DO: Finish this method, see paper notes

	public static void saveMedicationToFile(Medication med) throws Exception{
			
		String jsonString = medicationToJSONString(med);

		// we need a unique identifier for each Medication json
		// why not use the hashcode?

		// we should iterate over each json file before saving and check for duplicates
		// remove duplicate object files actually

		// Note that we make use of the hashcode of the provided Medication object 
		// as a unique identifier in the name of the file
		// and based on our equals/hashcode functions for the Medication object
		// (see Medication.java) we can assume that any overwriting of existing
		// JSON files with the same hashcode in its name will only occur
		// with the same file  

		String jsonFileName = "Medication_" + med.hashCode() + ".json";		

		File jsonFile = new File("./" + jsonFileName);
		jsonFile.createNewFile();
		jsonFile.setWritable(true);
		
		try{
			FileWriter jsonFileWriter = new FileWriter(jsonFile);
			jsonFileWriter.write(jsonString);
			jsonFileWriter.close();
		}
		catch(IOException ie){
			new Exception("Couldn't open the specified file or write to said file!");
		}
		
	}
	


}
