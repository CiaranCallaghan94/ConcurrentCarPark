package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.*;

public class JsonParser {

	public static final String file_path = "./ConcurrentCarPark/src/config/input.json";
	
	public void readInput(){
		try {

			File config_file = new File(file_path);

			FileReader reader = new FileReader(config_file);
			System.out.println(reader.toString());
			JSONObject jsonObject = new JSONObject(reader);
			
			System.out.println(jsonObject.length());
			jsonObject.get("num_cars");
			System.out.println("TESTING");
			System.out.println(jsonObject.get("num_cars"));

		} catch (FileNotFoundException | JSONException e) {
			e.printStackTrace();
		}
	}
}
