package Carpark;
import Car.Car;
import Gateway.Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Carpark {

    private static final Logger LOGGER = Logger.getLogger( "Carpark" );
    private Gateway gateway = new Gateway();
    List<Space> spaces;
    List<Car> cars_searching_for_space;
    
    public Carpark() {
        spaces = new ArrayList<>(1000);
    }

    // parks all the cars searching for spaces
    public void parkCars() {
    	
    	// Iterate through all the cars looking for a space
        // Iterate through the Spaces in the Carpark
        // When a free space is found, the car won't necessarily park there:
        //      -   The chances of parking in a particular empty space
        //          is dependant on car_width, width of neighbour cars,
        //          dexterity, & probability

    	//TODO: Test that this code works correctly
    	if(!cars_searching_for_space.isEmpty() && cars_searching_for_space != null){
    		
	    	for(Car car: cars_searching_for_space){
	    	
		        for(Space space: spaces) {
		
		            if(space.isFree()) {
		
		                space.addCar(car);
		                cars_searching_for_space.remove(car);
		                LOGGER.info("Added car to space and removed from cars searching for space");
		                break;
		            }
		        }
	    	}
    	}
    	//else {LOGGER.info("No cars need to be parked");}
    	
    	// TODO: Consider dealing with a scenario where a car can not find a space.
    	//		- Should it wait x amount of time then leave if still no spaces.
    	//		- Should it leave on that turn, etc.
        
    }

	public void manageArrival(Car car) {
		
		gateway.manageArrival(car);
	}

	public void manageDeparture(Car car) {

		gateway.manageDeparture(car);
	}

	// Checks the Gateway lanes to see if the barrier's are open 
	// and the queue is not empty
	// adds the cars to a list of cars looking for a space
	public void checkGateway() {
	
		cars_searching_for_space = gateway.checkEntrances();
		// TODO: Decide whether storing the leaving cars is necessary
		gateway.checkExits();
	}


}
