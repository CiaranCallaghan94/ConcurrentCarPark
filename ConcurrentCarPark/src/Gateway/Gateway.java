package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.List;
import Car.*;

public class Gateway {

    private static final Logger LOGGER = LogManager.getLogger( "Simulation" );

    private static List<Lane> entrances;
    private static List<Lane> exits;    

    public Gateway(int num_entrances, int num_exits) {

    	LOGGER.info("Init gateway: " + num_entrances);

		entrances = new ArrayList<>(num_entrances);
		exits = new ArrayList<>(num_exits);

    	for(int i=0; i< num_entrances; i++) {
    		Lane entrance = new Entrance();
    		entrances.add(entrance);
		}

		for(int i=0; i<num_exits; i++) {
    		Lane exit = new Exit();
    		exits.add(exit);
		}
	}

	public void addArrivalsToEntrance(Car c)  {

        LOGGER.info("Managing car arrival...");
        LOGGER.info("Entrances size: " + entrances.size());
		placeInShortestLane(c, entrances);
    }

	public void addDeparturesToExit(Car c) {

        LOGGER.info("Managing car departure...");
		placeInShortestLane(c, exits);
    }
	
	//Scans through all the entrances and adds the car to the shortest queue
	 private void placeInShortestLane(Car c, List<Lane> lanes) {

    	LOGGER.info("Placing in shortest lane...");

		 Lane shortest_lane = lanes.get(0);

		 for(int i = 1; i < lanes.size(); i++){

		 	Lane test_shortest = lanes.get(i);
			 if(test_shortest.numOfCarsInQueue() < shortest_lane.numOfCarsInQueue()){

				 shortest_lane = test_shortest;
			 }
		}

		 shortest_lane.addCar(c);
	}

	 // Check if car is done with barrierSection
	public List<Car> advanceEntranceBarriers() {
		
		List<Car> cars_to_return = new ArrayList<Car>();

		Car car = null;
		for (Lane entrance : entrances){

			// TODO: Possible better implementation
			car = entrance.advanceBarrier();
			
			if(car != null){
				cars_to_return.add(car);
			}
		}
		
		return cars_to_return;
	}

	// Check if car is done with barrierSection
	public void advanceExitBarriers() {

		List<Car> cars_to_exit = new ArrayList<Car>();

		Car car = null;
		for (Lane exit : exits){

			// TODO: Possible better implementation
			car = exit.advanceBarrier();

			if(car != null){
				cars_to_exit.add(car);
				LOGGER.info("Car exited carpark");
			}
		}

		// TODO: Maybe want to do something with exiting cars

	}

	public void advanceLanes() {

		for(Lane entrance: entrances) {
			entrance.moveCarToBarrier();
		}

		for(Lane exit: exits) {
			exit.moveCarToBarrier();
		}
	}

}
