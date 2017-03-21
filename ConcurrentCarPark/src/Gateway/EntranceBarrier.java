package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EntranceBarrier implements Barrier {

    private static final Logger LOGGER = LogManager.getLogger( "EntranceBarrier" );
    
    private boolean is_open;
    
    public boolean isBarrierOpen(){
    	
    	return is_open;
    }
}