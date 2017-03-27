package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Entrance maintains a queue of cars waiting to access an entrance barrier.
 * The lock is used to ensure only one car approaches the barrier at a time.
 * The fairness parameter in the Reentrant lock enables us to make a queue like
 * feature, The longest waiting car moves to the barrier next.
 */

public class Entrance {

    private final String name;
    private final Lock barrierArea = new ReentrantLock(true);
    private final EntranceBarrierSection barrierSection;
    private int amountInQueue = 0;
    private int id = 0;

    private SimulationGUI GUI;

    Entrance(BarrierController barrier_controller, int id, SimulationGUI GUI) {

        name = "Entrance " + id + ": ";
        barrierSection = new EntranceBarrierSection(barrier_controller);
        this.id = id;
        this.GUI = GUI;

        System.out.println(name + checkLengthOfQueue());
    }

    // The car will request to move up to the barrier.
    // If it is successful the car will engage with the barrier.
    // If it is not it will wait its turn.
    // This will also update the GUI when a car enters or leaves the queue.
    public void moveToBarrier(Car car) {

        amountInQueue++;
        System.out.println(name + checkLengthOfQueue());
        GUI.updater.setEntranceNum(id, checkLengthOfQueue());

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        } finally {

            barrierArea.unlock();

            amountInQueue--;
            System.out.println(name + checkLengthOfQueue());
            GUI.updater.setEntranceNum(id, checkLengthOfQueue());
        }
    }

    public int checkLengthOfQueue() {

        return amountInQueue;
    }

    // The car is added into the barrier section.
    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}