package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import config.JsonParser;

import Car.Car;

public class EntryBarrier extends Thread {

    private static final Logger LOGGER = LogManager.getLogger( "EntryBarrier" );

    int num_cars_in_carpark = 0;
    final int carpark_capacity = JsonParser.CARPARK_CAPACITY;

    Car car;

    public EntryBarrier(Car car, int num_cars_in_carpark) {

        this.car = car;
        this.num_cars_in_carpark = num_cars_in_carpark;

        LOGGER.info("Car arg in ENTRYbarrier: " + car);
        LOGGER.info("Car read in ENTRYbarrier: " + this.car);
    }

    public void setCar(Car car) {
        this.car = car;
        LOGGER.info("Car received: " + car.isReadyToPark());
    }

    public void run() {
        LOGGER.info("running entry barrier thread...");
        letCarEnterCarpark();
    }

    public synchronized void letCarEnterCarpark() {

        LOGGER.info("In letcarentercarpark");

        while (!Thread.currentThread().isInterrupted()) {

            if (num_cars_in_carpark >= carpark_capacity) {
                try {
                    LOGGER.info("Carpark full, waiting...");
                    wait();
                } catch (InterruptedException e) {
                    LOGGER.info("Interrupt");
                    return;
                }
            }

            if(car.isReadyToPark()) {
                LOGGER.info("Car passing through ENTRY barrier!");
                num_cars_in_carpark++;
            }
        }
    }
}
