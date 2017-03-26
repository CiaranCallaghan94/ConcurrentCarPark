package Gateway;

import Car.Car;

import javax.swing.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

    private final String name;

    private final Lock barrierArea = new ReentrantLock(true);

    private final EntranceBarrierSection barrierSection;

    private int amountInQueue = 0;

    private JLabel entrancePanel;

    Entrance(BarrierController barrier_controller, int id, JLabel entrancePanel) {

        name = "Entrance " + id + ": ";
        barrierSection = new EntranceBarrierSection(barrier_controller);
        this.entrancePanel = entrancePanel;

        System.out.println(name + checkLenghtOfQueue());
    }

    public void moveToBarrier(Car car) {


        amountInQueue++;
        System.out.println(name + checkLenghtOfQueue());
        entrancePanel.setText(name + amountInQueue);

        barrierArea.lock();

        try {

            engageWithBarrier(car);
        }
        finally {

            amountInQueue--;
            System.out.println(name + checkLenghtOfQueue());
            entrancePanel.setText(name + amountInQueue);

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