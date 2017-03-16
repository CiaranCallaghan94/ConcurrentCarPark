import Car.Car;
import Car.LectureCar;
import Car.StudentCar;
import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Simulation {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );

    // Variables
    private static int start_time       = timeToSeconds("08:00");
    private static int end_time         = timeToSeconds("19:00");
    private static int arrive_rush_hour = timeToSeconds("09:00");
    private static int leave_rush_hour  = timeToSeconds("17:00");

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

    public static void main(String [] args) {


    }
}
