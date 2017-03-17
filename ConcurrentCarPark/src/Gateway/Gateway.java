package Gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import Car.*;

public class Gateway {

    private static final Logger LOGGER = Logger.getLogger( "Simulation" );

    private static List<Entrance> entrances   = new ArrayList<>(3);
    private static List<Exit>     exits       = new ArrayList<>(3);

    public static void manageArrival(Car c)  {

        LOGGER.info("Managing car arrival...");
    }

    public static void manageDeparture(Car c) {

        LOGGER.info("Managing car departure...");
    }
}
