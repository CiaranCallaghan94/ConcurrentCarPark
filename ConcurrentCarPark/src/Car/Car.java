package Car;

import Carpark.Carpark;
import Gateway.Gateway;
import Gateway.Entrance;
import Gateway.BarrierSection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Car implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger( "Car" );

    protected int arrive_time;
    protected int leave_time;

    protected int width;
    protected int dexterity;

    Gateway gateway;
    Carpark carpark;
    Entrance entrance;
    BarrierSection barrierSection;

    Car(Gateway gateway, Carpark carpark) {

        this.gateway = gateway;
        this.carpark = carpark;
    }

    public void setVariables(int arrive_time, int leave_time, int width, int dexterity) {

        this.arrive_time = arrive_time;
        this.leave_time = leave_time;
    }

    public void run() {

        LOGGER.info("Running car thread");
        try {

            LOGGER.info("Sleeping until arrive time (milliseconds): " + arrive_time + " -" + Thread.currentThread().getId());
            Thread.sleep(arrive_time);

            LOGGER.info("Car has now arrived, going to gateway -" + Thread.currentThread().getId());
            entrance = gateway.addCarToEntrance(this);
            LOGGER.info("Car is in the queue - " + Thread.currentThread().getId());
            entrance.advanceLane(this);
            LOGGER.info("Car has passed that toxic barrier  -" + Thread.currentThread().getId());

        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void park() {

        // Iterate through the Spaces in the Carpark
        // When a free space is found, the car won't necessarily park there:
        //      -   The chances of parking in a particular empty space
        //          is dependant on car_width, width of neighbour cars,
        //          dexterity, & probability

        // This method should prob be in Carpark class.
    }

    public void leaveCarpark() {

        // Go straight to exit queue
    }
}
