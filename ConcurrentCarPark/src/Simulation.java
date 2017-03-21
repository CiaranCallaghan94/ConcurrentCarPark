import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import Carpark.Carpark;
import Car.Car;
import java.util.List;

public class Simulation {

    private static final Logger LOGGER = LogManager.getLogger( "Simulation" );

    // Variables
    private int open_time;
    private int close_time;

    // Simulation objects
    private Carpark carpark;
    private static List<Car> cars_by_arrival;
    private static List<Car> car_by_departure;

    private int arrival_index = 0;
    private int departure_index = 0;

    public Simulation(List<Car> cars_by_arrival0, List<Car> car_by_departure0, int open_time0, int close_time0, int carpark_capacity, int num_entrances, int num_exits) {

        cars_by_arrival = cars_by_arrival0;
        car_by_departure = car_by_departure0;

        open_time = open_time0;
        close_time = close_time0;
        
        carpark = new Carpark(carpark_capacity, num_entrances, num_exits);
    }

    // Simulate every second from the carpark opening until it closes. Each second, check and manage events.
    public void runSimulation() {

        int time = open_time;
        
        // While loop from open_time to close_time
        // Checks for cars arriving and departing the carpark
        // Checks for cars entering or exiting the barriers
        
        while( time < close_time) {

            manageArrivals(time);
            manageDepartures(time);
            
            // Checks the gateway for new cars 
            carpark.checkGateway();
            // Parks the cars looking for a spot
            carpark.parkCars();
            
            time++;
        }

        LOGGER.info("Total cars that arrived: " + arrival_index);
        LOGGER.info("Total cars that departed: " + departure_index);
    }

    // Arrivals just go straight to the gateway.
    public void manageArrivals(int time) {

        if(arrival_index < cars_by_arrival.size()) {

            Car car = cars_by_arrival.get(arrival_index);
            while(car.getArriveTime() == time) {
                LOGGER.info("Car arrived, sending to gateway: " + Application.secondsToTime(time));
                carpark.manageArrival(car);
                arrival_index++;

                if(arrival_index < cars_by_arrival.size())
                    car = cars_by_arrival.get(arrival_index);
                else
                    break;
            }
        }
    }

    // Departures leave the car park and then go to the gateway
    public void manageDepartures(int time) {

        if(departure_index < car_by_departure.size()) {

            Car car = car_by_departure.get(departure_index);
            while(car.getLeaveTime() == time) {
                LOGGER.info("Car departed: " + Application.secondsToTime(time));
                carpark.manageDeparture(car);
                departure_index++;
                
                if(departure_index < car_by_departure.size())
                    car = car_by_departure.get(departure_index);
                else
                    break;
            }
        }
    }
}
