package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

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

    public void moveToBarrier(Car car) {


        amountInQueue++;
        System.out.println(name + checkLengthOfQueue());
        GUI.updater.setEntranceNum(id,checkLengthOfQueue());

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            barrierArea.unlock();

            amountInQueue--;
            System.out.println(name + checkLengthOfQueue());
            GUI.updater.setEntranceNum(id,checkLengthOfQueue());


        }
    }

    public int checkLengthOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}