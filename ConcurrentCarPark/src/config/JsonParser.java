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

    // TIMES
	public static String OPEN_TIME              = "08:00";
    public static String CLOSE_TIME             = "19:00";
    public static String ARRIVAL_RUSH_HOUR      = "09:00";
	public static String STD_DEVIATION          = "00:30";
	public static int    SIMULATION_SPEED       = 10000;

	// CARPARK
    public static int    CARPARK_CAPACITY       = 1000;
    public static int    NUM_ENTRANCES          = 3;
    public static int    NUM_EXITS              = 3;

    // CARS
	public static int	 NUM_CARS              	= 2500;
	public static float  PROPORTION_STUDENTS	= 0.9f;

	// LECTURER CARS
    public static int 	 AVG_LECTURER_CAR_WIDTH  = 200;
    public static int 	 AVG_LECTURER_DEXTERITY  = 60;
	public static String AVG_LECTURER_STAY_TIME	 = "07:00";

    // STUDENT CARS
	public static int 	 AVG_STUDENT_CAR_WIDTH   = 170;
	public static int 	 AVG_STUDENT_DEXTERITY   = 40;
	public static String AVG_STUDENT_STAY_TIME	 = "03:00";

	public static void readInput(){
		
		File config_file = new File(file_path);
		JsonReader jsonReader;
		
		try {
			
			jsonReader = Json.createReader(new FileReader(config_file));
			JsonObject object = jsonReader.readObject();
			jsonReader.close();

			// TIMES
			if(object.containsKey("open_time")){
				OPEN_TIME = object.get("open_time").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("close_time")){
				CLOSE_TIME = object.get("close_time").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("arrival_rush_hour")){
				ARRIVAL_RUSH_HOUR = object.get("arrival_rush_hour").toString().replaceAll("^\"|\"$", "");
			}
			if(object.containsKey("std_deviation")){
				SIMULATION_SPEED = Integer.parseInt(object.get("std_deviation").toString());
			}
			if(object.containsKey("simulation_speed")){
				SIMULATION_SPEED = Integer.parseInt(object.get("simulation_speed").toString());
			}

			// CARPARK
			if(object.containsKey("carpark_capacity")){
				CARPARK_CAPACITY = Integer.parseInt(object.get("carpark_capacity").toString());
			}
			if(object.containsKey("num_entrances")){
				NUM_ENTRANCES = Integer.parseInt(object.get("num_entrances").toString());
			}
			if(object.containsKey("num_exits")){
				NUM_EXITS = Integer.parseInt(object.get("num_exits").toString());
			}

			// CARS
			if(object.containsKey("num_cars")){
				NUM_CARS = Integer.parseInt(object.get("num_cars").toString());
			}
			if(object.containsKey("proportion_students")){
				PROPORTION_STUDENTS = Integer.parseInt(object.get("proportion_students").toString());
			}

			// LECTURER CARS
			if(object.containsKey("avg_lecturer_car_width")){
				AVG_LECTURER_CAR_WIDTH = Integer.parseInt(object.get("avg_lecturer_car_width").toString());
			}
			if(object.containsKey("avg_lecturer_dexterity")){
				AVG_LECTURER_DEXTERITY = Integer.parseInt(object.get("avg_lecturer_dexterity").toString());
			}
			if(object.containsKey("avg_lecturer_stay_time")){
				AVG_LECTURER_STAY_TIME = object.get("avg_lecturer_stay_time").toString().replaceAll("^\"|\"$", "");
			}

			// STUDENT CARS
			if(object.containsKey("avg_student_car_width")){
				AVG_STUDENT_CAR_WIDTH = Integer.parseInt(object.get("avg_student_car_width").toString());
			}
			if(object.containsKey("avg_student_dexterity")){
				AVG_STUDENT_DEXTERITY = Integer.parseInt(object.get("avg_student_dexterity").toString());
			}
			if(object.containsKey("avg_student_stay_time")){
				AVG_STUDENT_STAY_TIME = object.get("avg_student_stay_time").toString().replaceAll("^\"|\"$", "");
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
}
