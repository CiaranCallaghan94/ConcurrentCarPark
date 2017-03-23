package Gateway;

import Car.Car;
import config.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExitBarrier extends Thread {

    private static final Logger LOGGER = LogManager.getLogger( "EntryBarrier" );

    int num_cars_in_carpark = 0;
    final int carpark_capacity = JsonParser.CARPARK_CAPACITY;

    Car car = null;

    public ExitBarrier(Car car, int num_cars_in_carpark) {

        this.car = car;
        this.num_cars_in_carpark = num_cars_in_carpark;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void run() {
        LOGGER.info("running exit barrier thread...");
        letCarLeaveCarpark();
    }

    public synchronized void letCarLeaveCarpark() {

        while (num_cars_in_carpark == 0) {
            try {
                LOGGER.info("Carpark empty, waiting...");
                wait();
            } catch (InterruptedException e) {
                LOGGER.info("Interrupt");
                return;
            }
        }

        if(car != null) {
            LOGGER.info("Car passing through EXIT barrier!");
            num_cars_in_carpark--;
        }
    }
}
