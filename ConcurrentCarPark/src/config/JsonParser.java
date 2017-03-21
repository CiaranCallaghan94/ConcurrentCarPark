package config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.json.*;

public class JsonParser {
	
	public static String OPEN_TIME, CLOSE_TIME, ARRIVAL_RUSH_HOUR, DEPARTURE_RUSH_HOUR,
							CARPARK_CAPACITY, NUM_CARS, NUM_ENTRANCES, NUM_EXITS;
	public void readInput(String file_path){
		
		File config_file = new File(file_path);
		
		JsonReader jsonReader;
		
		try {
			
			jsonReader = Json.createReader(new FileReader(config_file));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();
			
			OPEN_TIME = object.get("open_time").toString().replaceAll("^\"|\"$", "");
			CLOSE_TIME = object.get("close_time").toString().replaceAll("^\"|\"$", "");
			ARRIVAL_RUSH_HOUR = object.get("arrival_rush_hour").toString().replaceAll("^\"|\"$", "");
			DEPARTURE_RUSH_HOUR = object.get("departure_rush_hour").toString().replaceAll("^\"|\"$", "");
			NUM_CARS = object.get("num_cars").toString().replaceAll("^\"|\"$", "");
			CARPARK_CAPACITY = object.get("carpark_capacity").toString().replaceAll("^\"|\"$", "");
			NUM_ENTRANCES = object.get("num_entrances").toString().replaceAll("^\"|\"$", "");
			NUM_EXITS = object.get("num_exits").toString().replaceAll("^\"|\"$", "");
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
