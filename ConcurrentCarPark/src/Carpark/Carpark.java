package Carpark;

import Car.Car;
import Gateway.Gateway;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Carpark {

	private static final Logger LOGGER = LogManager.getLogger( "Carpark" );
	private Gateway gateway;
	List<Space> spaces;
	List<Car> cars_searching_for_space;

	public Carpark(int carpark_capacity) {

		spaces = new ArrayList<>(carpark_capacity);
		for(int i=0; i < carpark_capacity; i++) {
			Space space = new Space();
			spaces.add(space);
		}

		cars_searching_for_space = new ArrayList<>();
	}

	public synchronized void addCarToCarpark(Car c) {

		cars_searching_for_space.add(c);
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