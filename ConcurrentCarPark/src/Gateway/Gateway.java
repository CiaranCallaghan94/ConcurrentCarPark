package Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import Car.*;

public class Gateway {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );

    private static List<Lane> entrances;
    private static List<Lane> exits;    

    public Gateway(int num_entrances, int num_exits) {
		
    	entrances = new ArrayList<>(num_entrances);
    	exits = new ArrayList<>(num_exits);
    	
	}

	public void manageArrival(Car c)  {

        LOGGER.info("Managing car arrival...");
        placeInShortestQueue(c,entrances); 
    }

	public void manageDeparture(Car c) {

        LOGGER.info("Managing car departure...");
        placeInShortestQueue(c,exits); 
    }
	
	//Scans through all the entrances and adds the car to the shortest queue
	 private static void placeInShortestQueue(Car c, List<Lane> queues) {
			
		 if(queues.size()>0){
			 
			 int index_of_smallest_queue = 0;
			 int smallest_queue = queues.get(0).numOfCarsInQueue();
			 
			 for(int i = 0; i < queues.size(); i++){
				 
				 if(smallest_queue > queues.get(i).numOfCarsInQueue()){
					 
					 index_of_smallest_queue = i;
					 smallest_queue = queues.get(i).numOfCarsInQueue();
				 }
			}
			 
			 entrances.get(index_of_smallest_queue).addCar(c);
		 }
	}
	 // Check the lanes and if a car can pass through a barrier place 
	 // it into the List
	public List<Car> checkEntrances() {
		
		List<Car> cars_to_return = new ArrayList<Car>();
		
		for (Lane entrance : entrances){
			// TODO: Possible better implementation
			Car car = null;
			car = entrance.checkForCarLeavingLane() ;
			
			if(car != null){
				
				cars_to_return.add(car);
			}
		}
		
		return cars_to_return;
	}

	public void checkExits() {
		// TODO Auto-generated method stub
		
	}
}
