package Gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class EntryBarrier implements Callable<Boolean> {

    private static final Logger LOGGER = LogManager.getLogger("EntryBarrier");

    Data data;

    public EntryBarrier(Data data) {
        this.data = data;
    }

    public Boolean call() throws Exception {
        return letCarEnterCarpark();
    }

    public synchronized Boolean letCarEnterCarpark() {

        while (!Thread.currentThread().isInterrupted()) {

            if (data.getNumCars() >= data.getCarparkCapacity()) {
                try {
                    LOGGER.info("Carpark full, waiting...");
                    wait();
                } catch (InterruptedException e) {
                    LOGGER.info("Interrupt");
                    return false;
                }
            }

            data.incrementNumCars();
            LOGGER.info("Barrier opened! num_cars incremented: " + data.getNumCars());
            return true;
        }
        return false;
    }
}
