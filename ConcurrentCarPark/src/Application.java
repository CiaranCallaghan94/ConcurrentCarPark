import Car.Car;
import Car.LecturerCar;
import Car.StudentCar;
import Gateway.Gateway;
import Carpark.Carpark;
import config.ArrivalRushHour;
import config.XMLParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This Application runs a multithreaded simulation of a university car park
 */

public class Application {

    private static final Logger LOGGER = LogManager.getLogger("Simulation");

    private static List<Car> cars;

    // Create the student and lecturer cars based on the inputted 'proportion students'
    public static void createCars(Gateway gateway, Carpark carpark) {

        cars = new ArrayList<>(XMLParser.NUM_CARS);

        // Calculate number of student cars
        double percent_student_cars = XMLParser.PROPORTION_STUDENTS / 100.0;
        int num_student_cars = (int) Math.round(XMLParser.NUM_CARS * percent_student_cars);

        Car car;
        int num_cars_created = 0;

        // CREATE student cars
        while (num_cars_created < num_student_cars) {

            car = new StudentCar(gateway, carpark);
            cars.add(car);
            num_cars_created++;
        }

        // CREATE lecturer cars (the remaining proportion after creating students)
        while (num_cars_created < XMLParser.NUM_CARS) {

            car = new LecturerCar(gateway, carpark);
            cars.add(car);
        }
    }

    // Setup all the variables for each car
    public static void setAllCarVariables() {

        List<ArrivalRushHour> arrival_rush_hours = XMLParser.ARRIVAL_RUSH_HOURS;

        double percent_cars_this_rush_hour;
        int num_cars_this_rush_hour;
        int total_cars_after_this_rush_hour;
        int cars_processed = 0;

        // Each rush hour has a % of cars based on the 'proportion' parameter.
        // For all the cars in a particular rush hour, they are set a
        // normally distributed arrival time around that rush hour time.
        for (ArrivalRushHour rush_hour : arrival_rush_hours) {

            percent_cars_this_rush_hour = rush_hour.PROPORTION / 100.0;
            num_cars_this_rush_hour = (int) Math.round(cars.size() * percent_cars_this_rush_hour);
            total_cars_after_this_rush_hour = cars_processed + num_cars_this_rush_hour;

            LOGGER.info("Proportion cars this rush hour: " + num_cars_this_rush_hour);

            while (cars_processed < total_cars_after_this_rush_hour) {
                setSingleCarVars(cars_processed, rush_hour.TIME, rush_hour.STD_DEVIATION);
                cars_processed++;
            }
        }
    }

    // Setup the variables for a single car - dexterity, arrival time, stay time, etc...
    public static void setSingleCarVars(int car_id, int rush_hour, int std_deviation) {

        int arrive_time;
        int stay_time;
        int dexterity;
        int car_width;

        Car car = cars.get(car_id);

        Random rand = new Random();
        arrive_time = (int) Math.round(rand.nextGaussian() * std_deviation + rush_hour);

        // Normally distribute time, depening on whether car is student or lecturer
        if (car.isStudent()) {
            stay_time = arrive_time + (int) Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_STAY_TIME);
            dexterity = (int) Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_DEXTERITY);
            car_width = (int) Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_DEXTERITY);

        } else {
            stay_time = arrive_time + (int) Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_STAY_TIME);
            dexterity = (int) Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_DEXTERITY);
            car_width = (int) Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_CAR_WIDTH);
        }

        car.setVariables(arrive_time, stay_time, car_width, dexterity);
    }

    public static void main(String[] args) {

        XMLParser.readInput();

        Gateway gateway = new Gateway(XMLParser.NUM_ENTRANCES, XMLParser.NUM_EXITS);
        Carpark carpark = new Carpark(XMLParser.CARPARK_CAPACITY);

        createCars(gateway, carpark);
        setAllCarVariables();

        // Start the car threads
        for (Car c : cars) {
            new Thread(c).start();
        }
    }
}
