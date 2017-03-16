package Gateway;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

import Car.Car;

public class Exit implements Lane {

    private static final Logger LOGGER = Logger.getLogger( "Exit" );

    Queue<Car> queue = new LinkedList<Car>();

}