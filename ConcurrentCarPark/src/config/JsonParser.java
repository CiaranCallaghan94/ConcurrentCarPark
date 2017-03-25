package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonParser {

    private static final Logger LOGGER = LogManager.getLogger( "JsonParser" );

    public static String file_path   = "./ConcurrentCarPark/src/config/input.json";
    public static String file_path_2   = "src/config/input.json";

    // DEFAULTS
	public static String OPEN_TIME              = "08:00";
    public static String CLOSE_TIME             = "19:00";
    public static String ARRIVAL_RUSH_HOUR      = "09:00";
    public static String DEPARTURE_RUSH_HOUR    = "17:00";
    public static int    SIMULATION_SPEED       = 1000;
	public static String STD_DEVIATION          = "00:30";

    public static int    CARPARK_CAPACITY       = 1000;
    public static int    NUM_CARS               = 2500;
    public static int    NUM_ENTRANCES          = 3;
    public static int    NUM_EXITS              = 3;

    // NON-USER INPUTS
    public static final int 	AVG_CAR_WIDTH          = 180;
    public static final int 	AVG_STUDENT_DEXTERITY  = 40;
    public static final int 	AVG_LECTURER_DEXTERITY = 60;

	public static void readInput(){
		
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
			if(object.containsKey("simulation_speed")){
				SIMULATION_SPEED = Integer.parseInt(object.get("simulation_speed").toString());
			}
			if(object.containsKey("num_cars")){
				NUM_CARS = Integer.parseInt(object.get("num_cars").toString());
			}
			if(object.containsKey("carpark_capacity")){
				CARPARK_CAPACITY = Integer.parseInt(object.get("carpark_capacity").toString());
			}
			if(object.containsKey("num_entrances")){
				NUM_ENTRANCES = Integer.parseInt(object.get("num_entrances").toString());
			}
			if(object.containsKey("num_exits")){
				NUM_EXITS = Integer.parseInt(object.get("num_exits").toString());
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
