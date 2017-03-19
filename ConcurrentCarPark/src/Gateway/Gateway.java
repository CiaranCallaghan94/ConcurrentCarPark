package Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import Car.*;

public class Gateway {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );

    private static List<Lane> entrances   = new ArrayList<>(3);
    private static List<Lane> exits       = new ArrayList<>(3);

    public static void manageArrival(Car c)  {

        LOGGER.info("Managing car arrival...");
        placeInShortestQueue(c,entrances); 
    }

	public static void manageDeparture(Car c) {

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
}
