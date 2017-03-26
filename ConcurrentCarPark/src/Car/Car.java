package Car;

import Carpark.Carpark;
import Gateway.Entrance;
import Gateway.Exit;
import Gateway.Gateway;

import java.util.ArrayList;
import java.util.List;

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

    public void run() {

        waitUntilArrivalTime();

        enterThroughGateway();

        parkInCarpark();

        goToCollege();

        exitThroughGateway();
    }

    public void waitUntilArrivalTime() {

        try {
            Thread.sleep(arrive_time);
        }
        catch(InterruptedException e) {}
    }

    public void enterThroughGateway() {

        entrance = gateway.addCarToEntrance(this);
        entrance.moveToBarrier(this);
    }

    public void parkInCarpark() {

        carpark.findASpace(this);
    }

    public void goToCollege() {

        try {
            Thread.sleep(stay_time);
        }
        catch(InterruptedException e) {

        }
    }

    public void exitThroughGateway() {

        carpark.leaveTheCarpark(getSpaces());

        exit = gateway.addCarToExit(this);
        exit.moveToBarrier(this);
    }

    public abstract boolean isStudent();

    public void setSpace(List<Integer> occupied_spaces) {
        this.occupied_spaces = occupied_spaces;
    }

    public List<Integer> getSpaces() {
        return this.occupied_spaces;
    }

    public int getDexterity() {
        return dexterity;
    }
}
