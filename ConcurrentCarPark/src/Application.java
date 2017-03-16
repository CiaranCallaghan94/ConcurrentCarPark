import Car.Car;
import Car.StudentCar;
import Car.LectureCar;
import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Created by Anglim on 16/03/2017.
 */
public class Application {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );

    // Variables
    private static int start_time       = timeToSeconds("08:00");
    private static int end_time         = timeToSeconds("19:00");
    private static int arrive_rush_hour = timeToSeconds("09:00");
    private static int leave_rush_hour  = timeToSeconds("17:00");

    // Standard distribution
    private static int std_distribution = 10*60;

    // Average width of a car
    private static int avg_width = 180;

    // Dexterity range from 1-100
    private static int avg_dexterity = 50;

    private static int total_cars = 2500;

    // Simulation objects
    private static Carpark          carpark     = new Carpark();
    private static List<Entrance>   entrances   = new ArrayList<>(3);
    private static List<Exit>       exits       = new ArrayList<>(3);
    private static List<Car>        cars        = new ArrayList<>();

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

    public static void setupCars() {

        // Normal distribution with std deviation of 3 minutes
        NormalDistribution arrival_dist = new NormalDistribution(arrive_rush_hour, std_distribution);
        NormalDistribution leave_dist = new NormalDistribution(leave_rush_hour, std_distribution);

        Car new_car;
        int arrive_time;
        int leave_time;
        int random_num;
        Random rand = new Random();

        // Create the cars
        for(int i=0; i<total_cars; i++) {
            arrive_time = (int)Math.round(arrival_dist.sample());
            leave_time = (int)Math.round(leave_dist.sample());

            random_num = rand.nextInt((10 - 1) + 1) + 1;
            LOGGER.info("Random num: " + random_num);

            if(random_num <= 8)
                new_car = new StudentCar(arrive_time, leave_time, avg_width, avg_dexterity);
            else
                new_car = new LectureCar(arrive_time, leave_time, avg_width, avg_dexterity);

            cars.add(new_car);
        }
    }

    public static void main(String [] args) {

        setupCars();

        for(Car c: cars) {
            LOGGER.info("Arrive time: " + secondsToTime(c.getArriveTime()));
            LOGGER.info("Leave time: " + secondsToTime(c.getLeaveTime()));
            secondsToTime(c.getLeaveTime());
        }
    }
}
