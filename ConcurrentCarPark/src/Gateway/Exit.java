package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Lane {

    private static final Logger LOGGER = LogManager.getLogger("Exit");

    final Lock barrierArea = new ReentrantLock(true);

    ExitBarrierSection barrierSection;

    int amountInQueue = 0;

    Exit(BarrierController barrier_controller) {
        barrierSection = new ExitBarrierSection(barrier_controller);
    }

    public void moveToBarrier(Car car) {

        try {

            barrierArea.lock();

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