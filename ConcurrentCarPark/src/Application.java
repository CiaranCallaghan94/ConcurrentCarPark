import Car.Car;
import Car.LecturerCar;
import Car.StudentCar;
import Gateway.Gateway;
import Carpark.Carpark;
import config.JsonParser;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Application {

    private static final Logger LOGGER = LogManager.getLogger( "Simulation" );

    private static int open_time;
    private static int close_time;
    private static int arrive_rush_hour;
    private static int departure_rush_hour;
    private static int simulation_speed;

    private static int carpark_capacity;
    private static int num_entrances;
    private static int num_exits;
    private static int num_cars;
    private static int std_deviation;
    private static int avg_car_width;
    private static int avg_student_dexterity;
    private static int avg_lecturer_dexterity;

    private static List<Car> cars;

    public static int timeToSimulationTime(String time) {

        String [] time_arr = time.split(":");

        int hours = Integer.parseInt(time_arr[0]);
        int mins = Integer.parseInt(time_arr[1]);

        int time_in_seconds = hours*60*60 + mins*60;
        int time_in_milliseconds = time_in_seconds * 1000;

        LOGGER.info("Time in millis: " + time_in_milliseconds);
        LOGGER.info("Time divided by speed: " + time_in_milliseconds / simulation_speed);

        return time_in_milliseconds / simulation_speed;
    }

    public static void setupCars() {

        int arrive_time;
        int leave_time;
        int random_num;
        Random rand = new Random();

        // Create the cars
        for(Car car: cars) {

            arrive_time = (int)Math.round(rand.nextGaussian() * std_deviation + arrive_rush_hour);
            leave_time = (int)Math.round(rand.nextGaussian() * std_deviation + departure_rush_hour);

            // Only add car if arrive time is less than leave time, otherwise get a new sample
            if(arrive_time < leave_time) {

                random_num = rand.nextInt((10 - 1) + 1) + 1;

                if(random_num <= 8)
                    car.setVariables(arrive_time, leave_time, avg_car_width, avg_student_dexterity);//
                else
                    car.setVariables(arrive_time, leave_time, avg_car_width, avg_lecturer_dexterity);//

            } else {
                LOGGER.info("Arrive time after departure time. Arrive: " + arrive_time  + ". Leave: " + leave_time);
            }
        }

    }

    // Reads in the input file and its values
    public static void readInputFromJSONFile(){

        JsonParser.readInput();

    	// Parameters for Simulation
        simulation_speed        = JsonParser.SIMULATION_SPEED;
        open_time               = timeToSimulationTime(JsonParser.OPEN_TIME);
        close_time              = timeToSimulationTime(JsonParser.CLOSE_TIME);
        arrive_rush_hour        = timeToSimulationTime(JsonParser.ARRIVAL_RUSH_HOUR);
        departure_rush_hour     = timeToSimulationTime(JsonParser.DEPARTURE_RUSH_HOUR);
        std_deviation           = timeToSimulationTime(JsonParser.STD_DEVIATION);

        carpark_capacity        = JsonParser.CARPARK_CAPACITY;
        num_cars                = JsonParser.NUM_CARS;
        num_entrances           = JsonParser.NUM_ENTRANCES;
        num_exits               = JsonParser.NUM_EXITS;

        avg_car_width           = JsonParser.AVG_CAR_WIDTH;
        avg_student_dexterity   = JsonParser.AVG_STUDENT_DEXTERITY;
        avg_lecturer_dexterity  = JsonParser.AVG_LECTURER_DEXTERITY;
    }
    
    public static void main(String [] args) {

        readInputFromJSONFile();

        cars = new ArrayList<>(num_cars);

        Gateway gateway = new Gateway(num_entrances, num_exits);
        Carpark carpark = new Carpark(carpark_capacity);

        Car car;
        for(int i=0; i<num_cars; i++) {
            car = new StudentCar(gateway, carpark);
            cars.add(car);
        }

        setupCars();

        for(Car c: cars) {
            new Thread(c).start();
        }
    }
}
