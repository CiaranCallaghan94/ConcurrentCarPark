package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public abstract class Barrier {

	private static final Logger LOGGER = LogManager.getLogger( "Barrier" );

	private boolean is_open;
	private boolean is_free;

	private Car car;
	private int service_time;
	private int progress_through_service;

	public Barrier() {

		is_free = true;
		service_time = 0;
		progress_through_service = 0;
	}

	private void setServiceTime() {

		Random rand = new Random();

		double service_time_dbl = -1;
		while(service_time_dbl <= 0)
			service_time_dbl = (rand.nextGaussian() + 1);

		service_time = (int)Math.round(service_time_dbl);

		LOGGER.info("Service time set as: " + service_time);
	}

	private void clearTheBarrier() {

		is_free = true;
		service_time = 0;
		progress_through_service = 0;
	}

	public void addCar(Car c) {

		is_free = false;
		car = c;
		setServiceTime();
	}

	public Car advanceBarrierService() {

		if(!isFree()) {

			if(progress_through_service == service_time) {
				LOGGER.info("Car finished service!");
				return removeCar();
			}
			else {
				LOGGER.info("Car still in service");
				progress_through_service++;
			}
		}
		return null;
	}

	public Car removeCar() {

		clearTheBarrier();
		return car;
	}

	public boolean isFree() {
		return is_free;
	}
}