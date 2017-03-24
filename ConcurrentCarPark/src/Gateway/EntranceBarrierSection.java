package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class EntranceBarrierSection extends BarrierSection {

    private static final Logger LOGGER = LogManager.getLogger( "EntranceBarrierSection" );

    private EntryBarrier entry_barrier;

    public EntranceBarrierSection(Data data) {

        super();
        LOGGER.info("num cars EntranceBarrierSection: " + data);
        entry_barrier = new EntryBarrier(data);
    }

    public void openBarrier() {

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future future = executorService.submit(entry_barrier);

        try {
            LOGGER.info("**********Calling future.get and waiting for barrier to open...**********");
            future.get();
            LOGGER.info("**********Get complete. Barrier is open.**********");
        }
        catch(InterruptedException e) {}
        catch(ExecutionException e) {}

    }
}