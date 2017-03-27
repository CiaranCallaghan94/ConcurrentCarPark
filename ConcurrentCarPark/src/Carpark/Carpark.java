package Carpark;

import Car.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Carpark holds a list of spaces that cars can park in.
 * A lock is used here to enforce mutual exclusion on these
 * spaces for when cars enter and leave a space.
 */

public class Carpark {

    private List<Space> spaces;

    private final ReentrantLock spaces_lock = new ReentrantLock(true);
    private final Condition has_spaces = spaces_lock.newCondition();

    private List<Car> cars_searching_for_space;

    public Carpark(int carpark_capacity) {

        spaces = new ArrayList<>(carpark_capacity);
        for (int i = 0; i < carpark_capacity; i++) {
            Space space = new Space();
            spaces.add(space);
        }
    }

    // Find a space for the car
    public void findASpace(Car car) {

        // Try acquire lock, if not go into waiting queue
        spaces_lock.lock();

        // Lock acquired, in critical section

        // Wait for a space to come available if none available
        if(!spacesAvailable()) {
            try {
                System.out.println("Car park full, waiting around...");
                has_spaces.await();
            }
            catch(InterruptedException e) {}
        }

        // Space available so find a space!
        try {

            Space space;
            for (int i = 0; i < spaces.size(); i++) {

                space = spaces.get(i);
                if (space.isFree()) {

                    // IF both spaces occupied, you're forced to not park over white line
                    if (previousSpaceOccupied(i) && nextSpaceOccupied(i)) {
                        space.addCar(car);
                    }

                    // Otherwise, car can POSSIBLY park over white line...
                    else {

                        // Do a "100-sided dice roll".
                        Random rand = new Random();
                        int random_num = rand.nextInt(100) + 1;

                        // If true, park as normal. The lower the dexterity, the more likely this is false
                        if (random_num <= car.getDexterity()) {
                            space.addCar(car);
                        }

                        // Car will park over white line and occupy 2 spaces
                        else {
                            parkOverWhiteLine(i, car);
                        }
                    }
                    break;
                }
            }

        } finally {
            // Release lock
            spaces_lock.unlock();
        }
    }

    public boolean spacesAvailable() {

        for(Space space: spaces) {
            if(space.isFree())
                return true;
        }

        return false;
    }

    // Remove the car from it's occupied spaces.
    // Signal that spaces are available
    public void leaveTheCarpark(List<Integer> occupied_spaces) {

        // Try acquire lock, if not go into waiting queue
        spaces_lock.lock();

        for (int space_id : occupied_spaces) {
            spaces.get(space_id).removeCar();
        }

        has_spaces.signal();

        // Release lock
        spaces_lock.unlock();
    }

    public boolean previousSpaceOccupied(int space_id) {

        // EDGE CASE: First space has no previous space
        if (space_id == 0)
            return true;
        else
            return spaces.get(space_id - 1).isFree();

    }

    public boolean nextSpaceOccupied(int space_id) {

        // EDGE CASE: Last space has no next space
        if (space_id == spaces.size()) {
            return true;
        } else {
            return spaces.get(space_id + 1).isFree();
        }
    }

    public void parkOverWhiteLine(int space_id, Car car) {

        // If previous space occupied, take up this space and next space
        if (previousSpaceOccupied(space_id)) {

            spaces.get(space_id).addCar(car);
            spaces.get(space_id + 1).addCar(car);
        }

        // Else, take up this space and previous space
        else {

            spaces.get(space_id).addCar(car);
            spaces.get(space_id - 1).addCar(car);
        }
    }
}