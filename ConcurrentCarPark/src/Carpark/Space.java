package Carpark;

import Car.Car;

/**
 * This class contains a single Space. A space may contain or
 * may not contain a car.
 */

public class Space {

    private Car car = null;
    private boolean is_free = true;

    public Car getCar() {
        if (!isFree())
            return car;
        else {
            return null;
        }

    }

    /** Package-private methods **/

    void addCar(Car c) {
        is_free = false;
        car = c;
    }

    Car removeCar() {
        is_free = true;
        return car;
    }

    boolean isFree() {
        return is_free;
    }

}
