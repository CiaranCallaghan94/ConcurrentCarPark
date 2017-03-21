import Car.Car;
import Car.StudentCar;
import Car.LectureCar;
import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import config.JsonParser;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Anglim on 16/03/2017.
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );
    private static final JsonParser jsonParser = new JsonParser();
    private static final String file_path = "./src/config/input.json";

    // Parameters for Simulation (given DEFAULT values in case input is null)
    private static int open_time        = timeToSeconds("08:00");
    private static int close_time       = timeToSeconds("19:00");
    private static int arrive_rush_hour = timeToSeconds("09:00");
    private static int departure_rush_hour  = timeToSeconds("17:00");
    
    private static int carpark_capacity = 1000;
    private static int num_entrances = 3;
    private static int num_exits = 3;

    // Variable for setUpCars()
    private static int num_cars = 1;
    
    // Standard distribution
    private static int std_distribution = 10*60;

    // Average width of a car
    private static int avg_width = 180;

    // Dexterity ranges from 1-100
    private static int avg_student_dexterity = 40;
    private static int avg_lecture_dexterity = 60;


    // Separate lists for arrival and departure so that we can sort on
    // arrival / departure times and massively speed up simulation
    // as do not have to search through the list, can just keep indexes
    // of latest cars in the two lists to arrive/depart.
    // Every time car arrive/departs, increment index. (See Simulation).
    private static List<Car> cars_by_arrival    = new ArrayList<>(1);
    private static List<Car> cars_by_departure  = new ArrayList<>(1);

    public static int timeToSeconds(String time) {

        String [] time_arr = time.split(":");

        int hours = Integer.parseInt(time_arr[0]);
        int mins = Integer.parseInt(time_arr[1]);

        return hours*60*60 + mins*60;
    }

    public static String secondsToTime(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    private static List<Car> sortCarsByArrival(List<Car> cars) {
        Collections.sort(cars, (c1, c2) -> Integer.compare(c1.getArriveTime(), c2.getArriveTime()));
        return cars;
    }

    private static List<Car>  sortCarsByDeparture(List<Car> cars) {
        Collections.sort(cars, (c1, c2) -> Integer.compare(c1.getLeaveTime(), c2.getLeaveTime()));
        return cars;
    }

    public static void setupCars() {

        // Normal distribution with std deviation of 3 minutes
        NormalDistribution arrival_dist = new NormalDistribution(arrive_rush_hour, std_distribution);
        NormalDistribution leave_dist = new NormalDistribution(departure_rush_hour, std_distribution);

        Car new_car;
        int arrive_time;
        int leave_time;
        int random_num;
        Random rand = new Random();

        // Create the cars
        int i=0;
        while(i < num_cars) {

            arrive_time = (int)Math.round(arrival_dist.sample());
            leave_time = (int)Math.round(leave_dist.sample());

            // Only add car if arrive time is less than leave time, otherwise get a new sample
            if(arrive_time < leave_time) {

                random_num = rand.nextInt((10 - 1) + 1) + 1;
                LOGGER.info("Random num: " + random_num);

                if(random_num <= 8)
                    new_car = new StudentCar(arrive_time, leave_time, avg_width, avg_student_dexterity);
                else
                    new_car = new LectureCar(arrive_time, leave_time, avg_width, avg_lecture_dexterity);

                cars_by_arrival.add(new_car);
                cars_by_departure.add(new_car);

                i++;
            } else {
                LOGGER.info("Arrive time after departure time. Arrive: " + arrive_time  + ". Leave: " + leave_time);
            }
        }

        cars_by_arrival = sortCarsByArrival(cars_by_arrival);
        cars_by_departure = sortCarsByDeparture(cars_by_departure);
    }
    
    // TODO: Choose a suitable name for this method
    // Reads in the input file and its values
    public static void readInputFromJSONFile(){
    	
    	jsonParser.readInput(file_path);
    	
    	// Parameters for Simulation
    	// TODO: null checking should be completed in the parser not here FIX:
    	if(jsonParser.OPEN_TIME != null){
    		open_time = timeToSeconds(jsonParser.OPEN_TIME);
    	}
    	if(jsonParser.CLOSE_TIME != null){
    		close_time = timeToSeconds(jsonParser.CLOSE_TIME);
    	}
    	if(jsonParser.ARRIVAL_RUSH_HOUR != null){
    		arrive_rush_hour = timeToSeconds(jsonParser.ARRIVAL_RUSH_HOUR);
    	}
    	if(jsonParser.DEPARTURE_RUSH_HOUR != null){
    		departure_rush_hour = timeToSeconds(jsonParser.DEPARTURE_RUSH_HOUR);
    	}
    	if(jsonParser.CARPARK_CAPACITY != null){
    		carpark_capacity = Integer.parseInt(jsonParser.CARPARK_CAPACITY);
    	}
    	if(jsonParser.NUM_ENTRANCES != null){
    		num_entrances = Integer.parseInt(jsonParser.NUM_ENTRANCES);
    	}
    	if(jsonParser.NUM_EXITS != null){
    		num_exits = Integer.parseInt(jsonParser.NUM_EXITS);
    	}
    	// Variable used before simulation in setUpCars()
    	if(jsonParser.NUM_CARS != null){
    		num_cars = Integer.parseInt(jsonParser.NUM_CARS);
    	}
    	
    	
    	
    	
    }
    
    public static void main(String [] args) {
    	
    	readInputFromJSONFile();
    	
        setupCars();

        for(Car c: cars_by_arrival) {
            LOGGER.info("Arrive time: " + secondsToTime(c.getArriveTime()));
        }

        Simulation sim = new Simulation(cars_by_arrival, cars_by_departure, open_time, close_time, carpark_capacity, num_entrances, num_exits);
        sim.runSimulation();
    }
}
