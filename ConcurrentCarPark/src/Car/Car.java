package Car;

import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import Gateway.Gateway;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all the main functionality for the car threads.
 * Variables:
 * arriv_time to specify when the car will arive at the carpark.
 * stay_time to specify how long the car will stay in the carpark.
 * dexterity to indicate how likely they will make a mistake when parking.
 */

public abstract class Car implements Runnable {

    protected int arrive_time;
    protected int stay_time;
    protected int dexterity;

    private Gateway gateway;
    private Carpark carpark;
    private Entrance entrance;
    private Exit exit;

    private List<Integer> occupied_spaces = new ArrayList<>(2);

    Car(Gateway gateway, Carpark carpark) {

        this.gateway = gateway;
        this.carpark = carpark;
    }

    public void setVariables(int arrive_time, int stay_time, int dexterity) {

        this.arrive_time = arrive_time;
        this.stay_time = stay_time;
        this.dexterity = dexterity;
    }

    // When the thread is started this method will be called.
    // It will run through all of the cars encounters in the simulation.
    public void run() {

        waitUntilArrivalTime();

        enterThroughGateway();

        parkInCarpark();

        goToCollege();

        exitThroughGateway();
    }

    // The thread will sleep until the specified arrival time
    public void waitUntilArrivalTime() {

        try {
            Thread.sleep(arrive_time);
        }
        catch(InterruptedException e) {}
    }

    // The car will enter in through the carparks gateway.
    // The car will find the shortest queue, and get into it.
    // When the car is at the top of the queue it will try to enter through the barrier.

    public void enterThroughGateway() {

        entrance = gateway.addCarToEntrance(this);
        entrance.moveToBarrier(this);
    }

    // The car will locate a free space and will Park.
    // There is a possibility the car will park poorly and will park over the line.
    public void parkInCarpark() {

        carpark.findASpace(this);
    }

    // The car will be parked for the duration of the stay time
    public void goToCollege() {

        try {
            Thread.sleep(stay_time);
        }
        catch(InterruptedException e) {

        }
    }

    // The car will find the shortest exit queue and will take the same steps as entering the carpark.
    public void exitThroughGateway() {

        carpark.leaveTheCarpark(getSpaces());

        exit = gateway.addCarToExit(this);
        exit.moveToBarrier(this);
    }

    public abstract boolean isStudent();

    public List<Integer> getSpaces() {
        return this.occupied_spaces;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setArrivalTime(int arrive_time) {
        this.arrive_time = arrive_time;
    }

    public int getArrivalTime() {
        return arrive_time;
    }
}
