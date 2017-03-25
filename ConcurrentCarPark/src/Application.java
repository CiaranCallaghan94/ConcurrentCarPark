import Car.Car;
import Car.LecturerCar;
import Car.StudentCar;
import Gateway.Gateway;
import Carpark.Carpark;
import config.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Application {

    private static final Logger LOGGER = LogManager.getLogger( "Simulation" );

    // TIMES
    private static int open_time;
    private static int close_time;
    private static int arrive_rush_hour;
    private static int std_deviation;
    private static int simulation_speed;

    // CARPARK
    private static int carpark_capacity;
    private static int num_entrances;
    private static int num_exits;

    // CARS
    private static int   num_cars;
    private static float proportion_students;

    // LECTURER CARS
    private static int   avg_lecturer_car_width;
    private static int   avg_lecturer_dexterity;
    private static int   avg_lecturer_stay_time;

    // STUDENT CARS
    private static int   avg_student_car_width;
    private static int   avg_student_dexterity;
    private static int   avg_student_stay_time;


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

    public static void createCars() {

        cars = new ArrayList<>(num_cars);

        Gateway gateway = new Gateway(num_entrances, num_exits);
        Carpark carpark = new Carpark(carpark_capacity);

        float random_num;
        Random rand = new Random();

        Car car;
        for(int i=0; i<num_cars; i++) {

            random_num = rand.nextFloat();

            if(random_num <= proportion_students)
                car = new StudentCar(gateway, carpark);
            else
                car = new LecturerCar(gateway, carpark);

            cars.add(car);
        }
    }

    public static void setCarVariables() {

        int arrive_time;
        int stay_time;
        int dexterity;
        int car_width;

        Random rand = new Random();

        for(Car car: cars) {

            arrive_time = (int)Math.round(rand.nextGaussian() * std_deviation + arrive_rush_hour);

            if(car.isStudent()) {
                stay_time = arrive_time + (int)Math.round(rand.nextGaussian() + avg_student_stay_time);
                dexterity = (int)Math.round(rand.nextGaussian() + avg_student_dexterity);
                car_width = (int)Math.round(rand.nextGaussian() + avg_student_car_width);
            }
            else {
                stay_time = arrive_time + (int)Math.round(rand.nextGaussian() + avg_lecturer_stay_time);
                dexterity = (int)Math.round(rand.nextGaussian() + avg_lecturer_dexterity);
                car_width = (int)Math.round(rand.nextGaussian() + avg_lecturer_car_width);
            }

            car.setVariables(arrive_time, stay_time, car_width, dexterity);
        }
    }

    // Reads in the input file and its values
    public static void readInputFromJSONFile(){

        JsonParser.readInput();

    	// TIMES
        open_time               = timeToSimulationTime(JsonParser.OPEN_TIME);
        close_time              = timeToSimulationTime(JsonParser.CLOSE_TIME);
        arrive_rush_hour        = timeToSimulationTime(JsonParser.ARRIVAL_RUSH_HOUR);
        std_deviation           = timeToSimulationTime(JsonParser.STD_DEVIATION);
        simulation_speed        = JsonParser.SIMULATION_SPEED;

        // CARPARK
        carpark_capacity        = JsonParser.CARPARK_CAPACITY;
        num_entrances           = JsonParser.NUM_ENTRANCES;
        num_exits               = JsonParser.NUM_EXITS;

        // CARS
        num_cars                = JsonParser.NUM_CARS;
        proportion_students     = JsonParser.PROPORTION_STUDENTS;

        // LECTURER CARS
        avg_lecturer_car_width  = JsonParser.AVG_LECTURER_CAR_WIDTH;
        avg_lecturer_dexterity  = JsonParser.AVG_LECTURER_DEXTERITY;
        avg_lecturer_stay_time  = timeToSimulationTime(JsonParser.AVG_LECTURER_STAY_TIME);

        // STUDENT CARS
        avg_student_car_width   = JsonParser.AVG_STUDENT_CAR_WIDTH;
        avg_student_dexterity   = JsonParser.AVG_STUDENT_DEXTERITY;
        avg_student_stay_time   = timeToSimulationTime(JsonParser.AVG_STUDENT_STAY_TIME);

    }
    
    public static void main(String [] args) {

        readInputFromJSONFile();

        createCars();
        setCarVariables();

        for(Car c: cars) {
            new Thread(c).start();
        }
    }
}
