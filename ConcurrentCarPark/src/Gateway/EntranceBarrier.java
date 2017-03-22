package Gateway;

import Car.Car;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.Random;

public class EntranceBarrier extends Barrier {

    private static final Logger LOGGER = LogManager.getLogger( "EntranceBarrier" );


    public EntranceBarrier() {

        super();
    }
}