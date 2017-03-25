package Gateway;

import config.XMLParser;

public class Data {

    Integer num_cars_in_carpark;
    final int carpark_capacity = XMLParser.CARPARK_CAPACITY;

    public Data() {
        num_cars_in_carpark = 0;
    }

    public void incrementNumCars() {
        num_cars_in_carpark++;
    }

    public void decrementNumCars() {
        num_cars_in_carpark--;
    }

    public Integer getNumCars() {
        return num_cars_in_carpark;
    }

    public Integer getCarparkCapacity() {
        return carpark_capacity;
    }
}
