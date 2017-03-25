package Gateway;
import Car.Car;

public interface Lane {

	void addToQueue(Car c);

	void removeFromQueue();

	int numOfCarsInQueue();

	void moveToBarrier(Car car) throws InterruptedException;
	
}