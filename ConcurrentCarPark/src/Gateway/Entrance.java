package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

public class Entrance implements Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Entrance" );

	ReentrantLock barrierArea = new ReentrantLock(true);

	EntranceBarrierSection barrierSection;

	Entrance(Data data) {

		LOGGER.info("num cars Entrance: " + data);
		barrierSection = new EntranceBarrierSection(data);
	}

	public void moveToBarrier(Car car) throws InterruptedException {

		barrierArea.lock();

		try {

			engageWithBarrier(car);
		}
		finally {

			barrierArea.unlock();
		}
	}

	public int checkLenghtOfQueue(){

		return barrierArea.getHoldCount();
	}

	public void engageWithBarrier(Car car){

		barrierSection.addCar(car);
	}
}