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

    Entrance(Data data) {

        barrierSection = new EntranceBarrierSection(data);
    }

    public void moveToBarrier(Car car) {


        try {
            addToQueue();
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