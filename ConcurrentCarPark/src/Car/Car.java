package Car;

import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import Gateway.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public abstract class Car implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger("Car");

    protected int arrive_time;
    protected int stay_time;

    protected int width;
    protected int dexterity;

    Gateway gateway;
    Carpark carpark;
    Entrance entrance;
    Exit exit;

    List<Integer> occupied_spaces = new ArrayList<>(2);

    Car(Gateway gateway, Carpark carpark) {

        this.gateway = gateway;
        this.carpark = carpark;
    }

    public void setVariables(int arrive_time, int stay_time, int width, int dexterity) {

        this.arrive_time = arrive_time;
        this.stay_time = stay_time;

        this.width = width;
        this.dexterity = dexterity;
    }

    public void run() {

        waitUntilArrivalTime();

        enterThroughGateway();

        parkInCarpark();

        goToCollege();

        exitThroughGateway();
    }

    public void waitUntilArrivalTime() {

        LOGGER.info("Car:" + Thread.currentThread().getId() + " is sleeping until arrive time (milliseconds): " + arrive_time);
        try {
            Thread.sleep(arrive_time);
        }
        catch(InterruptedException e) {}
    }

    public void enterThroughGateway() {

        LOGGER.info("Car:" + Thread.currentThread().getId() + " has now arrived, going to gateway");
        entrance = gateway.addCarToEntrance(this);
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is in the queue " + (entrance.checkLenghtOfQueue()) + " cars back");
        entrance.moveToBarrier(this);
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is entering through the barrier");
    }

    public void parkInCarpark() {

        LOGGER.info("Car:" + Thread.currentThread().getId() + " is entering the carpark");
        carpark.findASpace(this);
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is now parked");
    }

    public void goToCollege() {

        LOGGER.info("Car:" + Thread.currentThread().getId() + " is going into college...");
        try {
            Thread.sleep(stay_time);
        }
        catch(InterruptedException e) {

        }
    }

    public void exitThroughGateway() {

        LOGGER.info("Car:" + Thread.currentThread().getId() + " is finished college. Going home..");
        carpark.leaveTheCarpark(getSpaces());

        exit = gateway.addCarToExit(this);
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is in the queue " + (exit.checkLenghtOfQueue()) + " cars back");
        exit.moveToBarrier(this);
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is leaving through the barrier");
        LOGGER.info("Car:" + Thread.currentThread().getId() + " is going home");
    }

    public abstract boolean isStudent();

    public void setSpace(List<Integer> occupied_spaces) {
        this.occupied_spaces = occupied_spaces;
    }

    public List<Integer> getSpaces() {
        return this.occupied_spaces;
    }

    public int getWidth() {
        return width;
    }

    public int getDexterity() {
        return dexterity;
    }
}
