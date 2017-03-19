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

    public Carpark() {
        spaces = new ArrayList<>(1000);
    }

    // Returns boolean depending on if car was successfully parked
    public boolean parkCar(Car car) {

        // Iterate through the Spaces in the Carpark
        // When a free space is found, the car won't necessarily park there:
        //      -   The chances of parking in a particular empty space
        //          is dependant on car_width, width of neighbour cars,
        //          dexterity, & probability

        for(Space space: spaces) {

            if(space.isFree()) {

                space.addCar(car);
                LOGGER.info("Added car to space");
                return true;
            }
        }

        LOGGER.info("Car wasn't added to space");
        return false;
    }

	public void manageArrival(Car car) {
		// TODO Auto-generated method stub
		gateway.manageArrival(car);
	}

	public void manageDeparture(Car car) {
		// TODO Auto-generated method stub
		gateway.manageDeparture(car);
	}


}
