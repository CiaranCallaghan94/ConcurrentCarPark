package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

    private static final Logger LOGGER = LogManager.getLogger("Entrance");

    ReentrantLock barrierArea = new ReentrantLock(true);

    EntranceBarrierSection barrierSection;

    int amountInQueue = 0;

    Entrance(BarrierController barrier_controller) {

        barrierSection = new EntranceBarrierSection(barrier_controller);
    }

    public void moveToBarrier(Car car) {

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