package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Entrance extends Lane {

	private static final Logger LOGGER = LogManager.getLogger( "Entrance" );

	public Entrance(Integer num_cars) {
		super(num_cars);
	}
}