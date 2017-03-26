package Gateway;

import Car.Car;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Exit implements Lane {

    private final String name;
    private final Lock barrierArea = new ReentrantLock(true);
    private final ExitBarrierSection barrierSection;
    private int amountInQueue = 0;
    private JLabel exitPanel;

    Exit(BarrierController barrier_controller, int id, JLabel exitPanel) {

        name = "Exit " + id + ": ";
        barrierSection = new ExitBarrierSection(barrier_controller);
        this.exitPanel = exitPanel;

        System.out.println(name + checkLenghtOfQueue());
    }

    public void moveToBarrier(Car car) {

        amountInQueue++;
        System.out.println(name + checkLenghtOfQueue());
        exitPanel.setText(name + amountInQueue);

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            amountInQueue--;
            System.out.println(name + checkLenghtOfQueue());
            exitPanel.setText(name + amountInQueue);

            barrierArea.unlock();

        }
    }

    public int checkLenghtOfQueue() {

        return amountInQueue;
    }

    public void engageWithBarrier(Car car) {

        barrierSection.addCar(car);
    }

}