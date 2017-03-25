import Car.Car;
import Car.LecturerCar;
import Car.StudentCar;
import Gateway.Gateway;
import Carpark.Carpark;
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

    public static void setCarVariables() {

        int arrive_time;
        int stay_time;
        int dexterity;
        int car_width;

        Random rand = new Random();

        for(Car car: cars) {

            arrive_time = (int)Math.round(rand.nextGaussian() * XMLParser.STD_DEVIATION + XMLParser.ARRIVAL_RUSH_HOUR);

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
    }
    
    public static void main(String [] args) {

        XMLParser.readInput();

        createCars();
        setCarVariables();

        for(Car c: cars) {
            new Thread(c).start();
        }
    }
}
