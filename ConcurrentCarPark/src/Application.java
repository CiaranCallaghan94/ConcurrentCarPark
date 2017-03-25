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

public class Application {

    private static final Logger LOGGER = LogManager.getLogger( "Simulation" );

    private static List<Car> cars;

    public static void createCars() {

        cars = new ArrayList<>(XMLParser.NUM_CARS);

        Gateway gateway = new Gateway(XMLParser.NUM_ENTRANCES, XMLParser.NUM_EXITS);
        Carpark carpark = new Carpark(XMLParser.CARPARK_CAPACITY);

        float random_num;
        Random rand = new Random();

        Car car;
        for(int i=0; i<XMLParser.NUM_CARS; i++) {

            random_num = rand.nextFloat();

            if(random_num <= XMLParser.PROPORTION_STUDENTS)
                car = new StudentCar(gateway, carpark);
            else
                car = new LecturerCar(gateway, carpark);

            cars.add(car);
        }
    }

    public static void setAllCarVariables() {

        List<ArrivalRushHour> arrival_rush_hours = XMLParser.ARRIVAL_RUSH_HOURS;

        double percent_cars_this_rush_hour;
        int num_cars_this_rush_hour;
        int total_cars_after_this_rush_hour;

        int cars_processed = 0;
        for(ArrivalRushHour rush_hour: arrival_rush_hours) {

            percent_cars_this_rush_hour = rush_hour.PROPORTION / 100.0;

            num_cars_this_rush_hour = (int) Math.round( cars.size() * percent_cars_this_rush_hour );

            total_cars_after_this_rush_hour = cars_processed + num_cars_this_rush_hour;

            LOGGER.info("Proportion cars this rush hour: " + num_cars_this_rush_hour);

            while(cars_processed < total_cars_after_this_rush_hour ) {
                setSingleCarVars(cars_processed, rush_hour.TIME, rush_hour.STD_DEVIATION);
                cars_processed++;
            }
        }
    }

    public static void setSingleCarVars(int car_id, int rush_hour, int std_deviation) {

        int arrive_time;
        int stay_time;
        int dexterity;
        int car_width;

        Car car = cars.get(car_id);

        Random rand = new Random();
        arrive_time = (int)Math.round(rand.nextGaussian() * std_deviation + rush_hour);

        if(car.isStudent()) {
            stay_time = arrive_time + (int)Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_STAY_TIME);
            dexterity = (int)Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_DEXTERITY);
            car_width = (int)Math.round(rand.nextGaussian() + XMLParser.AVG_STUDENT_DEXTERITY);
        }
        else {
            stay_time = arrive_time + (int)Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_STAY_TIME);
            dexterity = (int)Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_DEXTERITY);
            car_width = (int)Math.round(rand.nextGaussian() + XMLParser.AVG_LECTURER_CAR_WIDTH);
        }

        car.setVariables(arrive_time, stay_time, car_width, dexterity);
    }
    
    public static void main(String [] args) {

        XMLParser.readInput();

        createCars();
        setAllCarVariables();

        for(Car c: cars) {
            new Thread(c).start();
        }
    }
}
