package Gateway;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class EntranceBarrierSection extends BarrierSection {

    private static final Logger LOGGER = LogManager.getLogger( "EntranceBarrierSection" );

    private EntryBarrier entry_barrier;

    public EntranceBarrierSection(Integer num_cars) {

        super(num_cars);

        LOGGER.info("Car read in entranceBarrier: " + car);
        entry_barrier = new EntryBarrier(car, num_cars);
    }

    public void sendCarThroughBarrier() {
        LOGGER.info("Sending car through barrier");
        entry_barrier.setCar(car);
        entry_barrier.start();
    }
}