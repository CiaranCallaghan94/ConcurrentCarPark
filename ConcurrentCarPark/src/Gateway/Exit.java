package Gateway;

import Car.Car;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Lane {

    String name;
    final Lock barrierArea = new ReentrantLock(true);

    ExitBarrierSection barrierSection;

    int amountInQueue = 0;

    Exit(BarrierController barrier_controller, int id) {

        name = "Exit " + id + ": ";

        barrierSection = new ExitBarrierSection(barrier_controller);

        System.out.println(name + checkLenghtOfQueue());
    }

    public void moveToBarrier(Car car) {

        amountInQueue++;
        System.out.println(name + checkLenghtOfQueue());
        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            amountInQueue--;
            System.out.println(name + checkLenghtOfQueue());

            barrierArea.unlock();
        }
    }

    public int checkLenghtOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}