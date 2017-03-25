package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Lane {

    private static final Logger LOGGER = LogManager.getLogger( "Exit" );
    ReentrantLock barrierArea = new ReentrantLock(true);

    ExitBarrierSection barrierSection;
    Queue<Car> queue = new LinkedList<Car>();

    Exit(Data data) {

        barrierSection = new ExitBarrierSection(data);
    }

    public void addToQueue(Car c) {

        queue.add(c);
    }

    // If the queue is not empty remove the car at the front of the queue
    // else return null
    public void removeFromQueue() {

        queue.remove();
    }

    public int numOfCarsInQueue() {

        return queue.size();
    }



    public void moveToBarrier(Car car) throws InterruptedException {

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            removeFromQueue();
            barrierArea.unlock();
        }
    }

    public void engageWithBarrier(Car car){

        barrierSection.addCar(car);
    }
}