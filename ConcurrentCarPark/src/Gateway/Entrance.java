package Gateway;

import Car.Car;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

    private final String name;

    private final Lock barrierArea = new ReentrantLock(true);

    private final EntranceBarrierSection barrierSection;

    private int amountInQueue = 0;

    Entrance(BarrierController barrier_controller, int id) {

        name = "Entrance " + id + ": ";
        barrierSection = new EntranceBarrierSection(barrier_controller);

        System.out.println(name + checkLenghtOfQueue());
    }

    public void moveToBarrier(Car car) {



        barrierArea.lock();

        amountInQueue++;
        System.out.println(name + checkLenghtOfQueue());

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