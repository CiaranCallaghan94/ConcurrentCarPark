package Gateway;
import Car.Car;

public interface Lane {

	void addCar(Car c);

	Car removerCar();

	int numOfCarsInQueue();

	void advanceLane();

	void moveCarToBarrier();

	Car advanceBarrier();
	
}