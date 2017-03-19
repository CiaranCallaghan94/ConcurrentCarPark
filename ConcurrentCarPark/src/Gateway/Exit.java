package Gateway;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import Car.Car;

public class Exit implements Lane {

    private static final Logger LOGGER = Logger.getLogger( "Exit" );

    Queue<Car> queue = new LinkedList<Car>();

	@Override
	public void addCar(Car c) {
		// TODO Auto-generated method stub
		queue.add(c);
	}

	@Override
	public Car removerCar() {
		// TODO Auto-generated method stub
		
		//if the queue is not empty remove the car at the front of the queue
		//else return null
		if(!queue.isEmpty()){
					
			return queue.remove();
		} else
			return null;
	}

	@Override
	public int numOfCarsInQueue() {
		// TODO Auto-generated method stub
		return queue.size();
	}

}