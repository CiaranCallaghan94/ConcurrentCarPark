package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;
import java.util.Queue;

import Car.Car;

public class Exit implements Lane {

    private static final Logger LOGGER = LogManager.getLogger( "Exit" );

    Barrier barrier = new ExitBarrier();
    Queue<Car> queue = new LinkedList<Car>();

	public void addCar(Car c) {

		queue.add(c);
	}

	@Override
	public Car removerCar() {
		// If the queue is not empty remove the car at the front of the queue
		// else return null
		if(!queue.isEmpty()){
					
			return queue.remove();
		} else
			return null;
	}

	public int numOfCarsInQueue() {
		
		return queue.size();
	}

	public Car checkForCarLeavingLane() {
		
		if(queue != null && !queue.isEmpty()){
			
			if(barrier.isBarrierOpen()){
				
				return queue.remove();
			}
		}
		return null;
	}

}