package Gateway;
import Car.Car;

public interface Lane {
	
	public void addCar(Car c);
	
	public Car removerCar();
	
	public int numOfCarsInQueue();
	
}