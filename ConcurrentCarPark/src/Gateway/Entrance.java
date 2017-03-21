package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;
import java.util.Queue;

import Car.Car;

public class Entrance implements Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Entrance" );

	Barrier barrier = new EntranceBarrier();
	Queue<Car> queue = new LinkedList<Car>();

	@Override
	public void addCar(Car c) {
		
		queue.add(c);
	}

	// If the queue is not empty remove the car at the front of the queue
	// else return null
	public Car removerCar() {	
		
		if(!queue.isEmpty()){
			
			return queue.remove();
		} else
			
			return null;
	}

	public int numOfCarsInQueue() {

		return queue.size();
	}

	// Returns the car at the front of the queue
	// if the barrier is open
	public Car checkForCarLeavingLane() {
		
		if(queue != null && !queue.isEmpty()){
			
			if(barrier.isBarrierOpen()){
				
				return queue.remove();
			}
		}
		return null;
	}

}