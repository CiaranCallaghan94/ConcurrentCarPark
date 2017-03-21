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
			
			if(object.containsKey("open_time")){
				OPEN_TIME = object.get("open_time").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("close_time")){
				CLOSE_TIME = object.get("close_time").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("arrival_rush_hour")){
				ARRIVAL_RUSH_HOUR = object.get("arrival_rush_hour").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("departure_rush_hour")){
				DEPARTURE_RUSH_HOUR = object.get("departure_rush_hour").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("num_cars")){
				NUM_CARS = object.get("num_cars").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("carpark_capacity")){
				CARPARK_CAPACITY = object.get("carpark_capacity").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("num_entrances")){
				NUM_ENTRANCES = object.get("num_entrances").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("num_exits")){
				NUM_EXITS = object.get("num_exits").toString().replaceAll("^\"|\"$", "");
			}
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
