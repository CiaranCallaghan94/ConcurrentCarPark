package Gateway;

import GUI.SimulationGUI;
import config.XMLParser;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The BarrierController maintains the number of cars in the carpark
 * A lock is used here to enforce mutual exclusion when changing the
 * num_cars_in_carpark variable
 */

public class BarrierController {

    private SimulationGUI GUI;

    private final int carpark_capacity = XMLParser.CARPARK_CAPACITY;
    private final Lock barrier_lock = new ReentrantLock(true);
    private final Condition has_spaces = barrier_lock.newCondition();

    Integer num_cars_in_carpark;

    public BarrierController(SimulationGUI GUI) {
        this.GUI = GUI;
        num_cars_in_carpark = 0;
    }

    // This opens the exit barrier. Thus the number
    // of cars in the carpark is incremented.
    public void openEntranceBarrier() {

        // Try acquire lock, if not go into waiting queue
        barrier_lock.lock();

        // Have acquired lock - in critical section
        try {

            // Wait if carpark is full. If waiting, others can grab the lock.
            while(num_cars_in_carpark >= carpark_capacity) {
                has_spaces.await();
            }

            num_cars_in_carpark++;
            GUI.updater.setTotalCarsInCarpark(num_cars_in_carpark);
        }
        catch(InterruptedException e) {}
        finally {
            // Release lock
            barrier_lock.unlock();
        }
    }

    // This opens the exit barrier. Thus the number
    // of cars in the carpark is decremented.
    public void openExitBarrier() {

        // Try acquire lock, if not go into waiting queue
        barrier_lock.lock();

        // Have acquired lock - in critical section
        num_cars_in_carpark--;
        GUI.updater.setTotalCarsInCarpark(num_cars_in_carpark);

        // Signal that carpark has spaces
        has_spaces.signal();

        // Release lock
        barrier_lock.unlock();
    }
}
