package Gateway;

import Car.Car;

public interface Lane {

    void moveToBarrier(Car car) throws InterruptedException;

    int checkLenghtOfQueue();
}