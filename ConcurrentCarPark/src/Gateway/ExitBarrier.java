package Gateway;

import java.util.logging.Logger;

public class ExitBarrier implements Barrier {

    private static final Logger LOGGER = Logger.getLogger( "ExitBarrier" );

    private boolean is_open;
    
	public boolean isBarrierOpen() {

		return is_open;
	}

}