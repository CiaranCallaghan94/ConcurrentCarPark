package Gateway;

import java.util.logging.Logger;

public class EntranceBarrier implements Barrier {

    private static final Logger LOGGER = Logger.getLogger( "EntranceBarrier" );
    
    private boolean is_open;
    
    public boolean isBarrierOpen(){
    	
    	return is_open;
    }
}