package Gateway;

import Car.Car;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

    String name;

    final Lock barrierArea = new ReentrantLock(true);

    EntranceBarrierSection barrierSection;

    int amountInQueue = 0;

    Entrance(BarrierController barrier_controller, int id) {

        name = "Entrance " + id + ": ";
        barrierSection = new EntranceBarrierSection(barrier_controller);
    }

    public void moveToBarrier(Car car) {

        System.out.println(name + checkLenghtOfQueue());
        addToQueue();
        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            barrierArea.unlock();
            removeFromQueue();
        }
    }

    public int checkLenghtOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

    public void addToQueue() {

        amountInQueue++;
    }

    public void removeFromQueue() {

        amountInQueue--;
    }
}