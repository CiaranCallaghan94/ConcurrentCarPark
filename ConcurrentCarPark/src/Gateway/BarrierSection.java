package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public abstract class BarrierSection {

	private static final Logger LOGGER = LogManager.getLogger( "BarrierSection" );

	private int std_barrier_time;
	private int std_problem_time;
	private double probability_of_problem;

	private boolean is_open;
	private boolean is_free;

	private Car car;
	private int service_time;
	private int progress_through_service;

	public BarrierSection() {

		std_barrier_time = 5;
		std_problem_time = 60;
		probability_of_problem = 0.01;

		is_free = true;
		service_time = 0;
		progress_through_service = 0;
	}

	private void setServiceTime() {

		Random rand = new Random();

		double service_time_dbl = -1;
		while(service_time_dbl <= 0)
			service_time_dbl = (rand.nextGaussian() * 1 + std_barrier_time);

		service_time = (int)Math.round(service_time_dbl);

		service_time += emulateProblem();

		LOGGER.info("Service time set as: " + service_time);
	}

	// Emulate problem with payment / faulty barrierSection
	public int emulateProblem() {

		int additional_time = 0;

		Random rand = new Random();

		int random = rand.nextInt();
		if(random <= probability_of_problem) {

			double problem_time_dbl = -1;
			while(problem_time_dbl <= 0) {
				problem_time_dbl = rand.nextGaussian() * 1 + std_problem_time;
			}

			additional_time = (int)Math.round(problem_time_dbl);

			LOGGER.info("BarrierSection has problem. Additional time: " + additional_time);
		}

		return additional_time;
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
				LOGGER.info("Barrier opening...!");


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