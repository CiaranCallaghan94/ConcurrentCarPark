package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Lane {

    private final String name;
    private final Lock barrierArea = new ReentrantLock(true);
    private final ExitBarrierSection barrierSection;
    private int amountInQueue = 0;
    private int id;
    private SimulationGUI GUI;

    Exit(BarrierController barrier_controller, int id, SimulationGUI GUI) {

        name = "Exit " + id + ": ";
        barrierSection = new ExitBarrierSection(barrier_controller);
        this.id = id;
        this.GUI = GUI;

        System.out.println(name + checkLengthOfQueue());
    }

    public void moveToBarrier(Car car) {

        amountInQueue++;
        System.out.println(name + checkLengthOfQueue());
        GUI.updater.setExitNum(id, checkLengthOfQueue());

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            barrierArea.unlock();

            amountInQueue--;
            System.out.println(name + checkLengthOfQueue());
            GUI.updater.setExitNum(id, checkLengthOfQueue());
        }
    }

    public int checkLengthOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}