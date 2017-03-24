package Gateway;

import Car.Car;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.LinkedList;
import java.util.Queue;

public class Entrance implements Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Entrance" );

	EntranceBarrierSection barrierSection;
	Queue<Car> queue = new LinkedList<Car>();

	Entrance(Data data) {

		LOGGER.info("num cars Entrance: " + data);
		barrierSection = new EntranceBarrierSection(data);
	}

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
}