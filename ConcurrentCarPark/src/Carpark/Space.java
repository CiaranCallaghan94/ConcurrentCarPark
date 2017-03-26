package Carpark;

import Car.Car;

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

    public void addCar(Car c) {
        is_free = false;
        car = c;
    }

    public Car removeCar() {
        is_free = true;
        return car;
    }

    public boolean isFree() {
        return is_free;
    }

}
