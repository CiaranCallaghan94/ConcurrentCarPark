package Gateway;

import config.XMLParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarrierController {

    private static final Logger LOGGER = LogManager.getLogger("BarrierController");

    final int carpark_capacity = XMLParser.CARPARK_CAPACITY;
    final Lock barrier_lock = new ReentrantLock(true);
    final Condition has_spaces = barrier_lock.newCondition();

    Integer num_cars_in_carpark;

    public BarrierController() {
        num_cars_in_carpark = 0;
    }

    public void openEntranceBarrier() {

        barrier_lock.lock();

        try {
            while(num_cars_in_carpark >= carpark_capacity) {
                LOGGER.info("Car park full -" + Thread.currentThread().getId());
                has_spaces.await();
                LOGGER.info("Car park no longer full -" + Thread.currentThread().getId());
            }
            num_cars_in_carpark++;
        }
        catch(InterruptedException e) {}
        finally {
            barrier_lock.unlock();
        }
    }

    public void openExitBarrier() {

        barrier_lock.lock();

        num_cars_in_carpark--;

        has_spaces.signal();
        LOGGER.info("Car park cars decremented -" + Thread.currentThread().getId());
        barrier_lock.unlock();
    }
}
