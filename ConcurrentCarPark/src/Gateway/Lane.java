package Gateway;
import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public abstract class Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Lane" );

	Gateway.BarrierSection barrierSection = new Gateway.EntranceBarrierSection();
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

		if(barrierSection.isFree()) {

			Car first_in_queue = removerCar();

			if(first_in_queue != null) {
				barrierSection.addCar(first_in_queue);
			}
		}
	}

	public Car advanceBarrier() {

		return barrierSection.advanceBarrierService();
	}

	/*
	// Returns the car at the front of the queue
	// if the barrierSection is open
	public Car checkForCarLeavingLane() {

		if(queue != null && !queue.isEmpty()){

			if(barrierSection.isBarrierOpen()){

				return queue.remove();
			}
		}
		return null;
	}*/
	
}