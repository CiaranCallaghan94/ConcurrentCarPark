package Gateway;
import Car.Car;

public interface Lane {

	void addCar(Car c);

	Car removerCar();

	int numOfCarsInQueue();

	void advanceLane(Car car) throws InterruptedException;

	void moveCarToBarrier(Car car) throws InterruptedException;

	Car advanceBarrier();
	
}