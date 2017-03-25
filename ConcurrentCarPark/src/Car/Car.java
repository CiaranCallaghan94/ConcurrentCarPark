package Car;

import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import Gateway.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Car implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger( "Car" );

    protected int arrive_time;
    protected int stay_time;

    protected int width;
    protected int dexterity;

    Gateway gateway;
    Carpark carpark;
    Entrance entrance;
    Exit exit;

    List<Integer> occupied_spaces = new ArrayList<>(2);

    Car(Gateway gateway, Carpark carpark) {

        this.gateway = gateway;
        this.carpark = carpark;
    }

    public void setVariables(int arrive_time, int stay_time, int width, int dexterity) {

        this.arrive_time = arrive_time;
        this.stay_time = stay_time;
    }

    public void run() {

        LOGGER.info("Running car thread");
        try {

            LOGGER.info("Sleeping until arrive time (milliseconds): " + arrive_time + " -" + Thread.currentThread().getId());
            Thread.sleep(arrive_time);

            // ENTERING
            LOGGER.info("Car has now arrived, going to gateway -" + Thread.currentThread().getId());
            entrance = gateway.addCarToEntrance(this);
            LOGGER.info("Car is in the queue "+ (entrance.checkLenghtOfQueue()) +" cars back -" + Thread.currentThread().getId());
            entrance.moveToBarrier(this);
            LOGGER.info("Car is entering through the barrier -" + Thread.currentThread().getId());

            // CARPARK
            LOGGER.info("Car is entering the carpark -" + Thread.currentThread().getId());
            carpark.findASpace(this);
            LOGGER.info("Car is now parked -" + Thread.currentThread().getId());

            // PARKED
            LOGGER.info("Going to college...");
            Thread.sleep(stay_time);

            // LEAVING
            LOGGER.info("Finished college. Going home..");
            carpark.leaveTheCarpark(getSpaces());

            exit = gateway.addCarToExit(this);
            LOGGER.info("Car is in the queue "+ (entrance.checkLenghtOfQueue()) +" cars back -" + Thread.currentThread().getId());
            exit.moveToBarrier(this);
            LOGGER.info("Car is leaving through the barrier -" + Thread.currentThread().getId());
            LOGGER.info("IM GOING HOME");

        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract boolean isStudent();

    public void setSpace(List<Integer> occupied_spaces) {
        this.occupied_spaces = occupied_spaces;
    }

    public List<Integer> getSpaces() {
        return this.occupied_spaces;
    }

    public int getWidth() {
        return width;
    }

    public int getDexterity() {
        return dexterity;
    }
}
