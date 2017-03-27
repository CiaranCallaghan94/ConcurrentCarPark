package Gateway;

import GUI.SimulationGUI;
import config.XMLParser;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public void openEntranceBarrier() {

        barrier_lock.lock();
        try {
            while(num_cars_in_carpark >= carpark_capacity) {
                has_spaces.await();
            }

            num_cars_in_carpark++;
            GUI.updater.setTotalCarsInCarpark(num_cars_in_carpark);
        }
        catch(InterruptedException e) {}
        finally {
            barrier_lock.unlock();
        }
    }

    public void openExitBarrier() {

        barrier_lock.lock();

        num_cars_in_carpark--;
        GUI.updater.setTotalCarsInCarpark(num_cars_in_carpark);

        has_spaces.signal();
        barrier_lock.unlock();
    }
}
