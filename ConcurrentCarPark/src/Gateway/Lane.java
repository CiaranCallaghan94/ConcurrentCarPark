package Gateway;
import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Lane" );

	Barrier barrier = new EntranceBarrier();
	Queue<Car> queue = new LinkedList<Car>();

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

	public void advanceLane() {

		moveCarToBarrier();
	}

	public void moveCarToBarrier() {

		if(barrier.isFree()) {

			Car first_in_queue = removerCar();

			if(first_in_queue != null) {
				barrier.addCar(first_in_queue);
			}
		}
	}

	public Car advanceBarrier() {

		return barrier.advanceBarrierService();
	}

	/*
	// Returns the car at the front of the queue
	// if the barrier is open
	public Car checkForCarLeavingLane() {

		if(queue != null && !queue.isEmpty()){

			if(barrier.isBarrierOpen()){

				return queue.remove();
			}
		}
		return null;
	}*/
	
}