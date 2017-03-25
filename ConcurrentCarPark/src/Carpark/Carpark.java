package Carpark;

import Car.Car;
import Gateway.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Carpark {

	private static final Logger LOGGER = LogManager.getLogger( "Carpark" );
	private Gateway gateway;
	List<Space> spaces;
	List<Car> cars_searching_for_space;

	ReentrantLock space_lock = new ReentrantLock(true);

	public Carpark(int carpark_capacity) {

		spaces = new ArrayList<>(carpark_capacity);
		for(int i=0; i < carpark_capacity; i++) {
			Space space = new Space();
			spaces.add(space);
		}

		cars_searching_for_space = new ArrayList<>();
	}

	public void findASpace(Car car) {

		space_lock.lock();

		try {

			Space space;
			for(int i=0; i< spaces.size(); i++) {

				space = spaces.get(i);
				if(space.isFree()) {

					// IF both spaces occupied, you're forced to not park over white line
					if(previousSpaceOccupied(i) && nextSpaceOccupied(i)) {
						LOGGER.info("Both neighbours full. Car parked normally -" + Thread.currentThread().getId());
						space.addCar(car);
					}

					// Otherwise, car can POSSIBLY park over white line...
					else {

						Random rand = new Random();
						int random_num = rand.nextInt(100) + 1;

						// If true, park as normal. The lower the dexterity, the more likely this is false
						if(random_num <= car.getDexterity()) {
							LOGGER.info("Car parked normally -" + Thread.currentThread().getId());
							space.addCar(car);
						}

						// Car will park over white line and occupy 2 spaces
						else {
							parkOverWhiteLine(i, car);
						}
					}

					LOGGER.info("Car is now parked -" + Thread.currentThread().getId());
					break;
				}
			}

			// TODO: Manage case where car does not park here.
		}
		finally {
			space_lock.unlock();
		}
	}

	public boolean previousSpaceOccupied(int space_id) {

		// EDGE CASE: First space has no previous space
		if(space_id==0)
			return true;
		else
			return spaces.get(space_id-1).isFree();

	}

	public boolean nextSpaceOccupied(int space_id) {

		// EDGE CASE: Last space has no next space
		if(space_id == spaces.size()) {
			return true;
		}
		else {
			return spaces.get(space_id+1).isFree();
		}
	}

	public void parkOverWhiteLine(int space_id, Car car) {

		// If previous space occupied, take up this space and next space
		if(previousSpaceOccupied(space_id)) {

			LOGGER.info("Parking over white line onto next space -" + Thread.currentThread().getId());
			spaces.get(space_id).addCar(car);
			spaces.get(space_id+1).addCar(car);
		}

		// Else, take up this space and previous space
		else {

			LOGGER.info("Parking over white line onto previous space -" + Thread.currentThread().getId());
			spaces.get(space_id).addCar(car);
			spaces.get(space_id-1).addCar(car);
		}
	}

	/*
	// parks all the cars searching for spaces
	public void parkCars() {

		LOGGER.info("parking cars");

		// Iterate through all the cars looking for a space
		// Iterate through the Spaces in the Carpark
		// When a free space is found, the car won't necessarily park there:
		//      -   The chances of parking in a particular empty space
		//          is dependant on car_width, width of neighbour cars,
		//          dexterity, & probability

		// So that objects are not removed from the list whilst still iterating
		// through said list, add them to this list and remove them all at end
		List<Car> cars_to_be_removed = new ArrayList<>();

		//TODO: FIX concurrent modification exception
		if(!cars_searching_for_space.isEmpty() && cars_searching_for_space != null){

			for(Car car: cars_searching_for_space){

				for(Space space: spaces) {

					if(space.isFree()) {

						space.addCar(car);
						cars_to_be_removed.add(car);
						LOGGER.info("Added car to space and removed from cars searching for space");
						break;
					}
				}
			}
		}

		cars_searching_for_space.removeAll(cars_to_be_removed);

		if(!cars_searching_for_space.isEmpty()) {
			LOGGER.info("Num cars that did not park: " + cars_searching_for_space.size());
		}

		// TODO: Consider dealing with a scenario where a car can not find a space.
		//		- Should it wait x amount of time then leave if still no spaces.
		//		- Should it leave on that turn, etc.

	}*/
}