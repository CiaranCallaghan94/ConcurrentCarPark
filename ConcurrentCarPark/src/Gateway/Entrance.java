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

        System.out.println(name + checkLenghtOfQueue());
    }

    public void moveToBarrier(Car car) {


        amountInQueue++;
        System.out.println(name + checkLenghtOfQueue());
        GUI.setEntranceNum(id,checkLenghtOfQueue());

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            barrierArea.unlock();

            amountInQueue--;
            System.out.println(name + checkLenghtOfQueue());
            GUI.setEntranceNum(id,checkLenghtOfQueue());


        }
    }

    public int checkLenghtOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}