import java.io.File;
import java.io.FileWriter;

public class LocalData{

	public static File jsonStringToJsonFile(String jsonString, 
	String jsonFileName) throws Exception {

		// Write JSON-formatted String to new File

		File jsonFile = new File("./" + jsonFileName);
		jsonFile.createNewFile();
		jsonFile.setWritable(true);

		FileWriter jsonFileWriter = new FileWriter(jsonFile);
		jsonFileWriter.write(jsonString);
		jsonFileWriter.close();

		return jsonFile;

	}
	
}
