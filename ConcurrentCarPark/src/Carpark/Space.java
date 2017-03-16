package Carpark;
import Car.Car;

import java.util.logging.Logger;

public class Space {

    private static final Logger LOGGER = Logger.getLogger( "Space" );

    private Car car = null;
    private boolean is_free = true;

    public Car getCar() {
        LOGGER.info("Getting Car");
        if(!isFree())
            return car;
        else {
            LOGGER.info("Space is empty");
            return null;
        }

    }

    public void addCar(Car c) {
        LOGGER.info("Adding car");
        is_free = false;
        car = c;
    }

    public Car removeCar() {
        LOGGER.info("Removing car");
        is_free = true;
        return car;
    }

    public boolean isFree() {
        return is_free;
    }

}