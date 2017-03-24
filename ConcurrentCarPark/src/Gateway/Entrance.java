package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

	public void advanceLane(Car car) throws InterruptedException {

		while(!Thread.currentThread().isInterrupted()) {

			if (car == queue.peek()) {

				LOGGER.info("Car is at the top of the queue");
				moveCarToBarrier(car);
				break;

			} else {
				LOGGER.info("Car is waiting its turn in the queue");
				wait();
			}
		}
	}

	public void moveCarToBarrier(Car car) throws InterruptedException {

		while(!Thread.currentThread().isInterrupted()) {

			if (barrierSection.isFree()) {

				removerCar();
				barrierSection.addCar(car);
			} else {
				wait();
			}
		}
	}

	public Car advanceBarrier() {

		return barrierSection.advanceBarrierService();
	}
}