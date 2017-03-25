package Gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Callable;

public class ExitBarrier implements Callable<Boolean> {

    private static final Logger LOGGER = LogManager.getLogger("EntryBarrier");

    Data data;

    public ExitBarrier(Data data) {

        this.data = data;
    }

    public Boolean call() throws Exception {

        return letCarLeaveCarpark();
    }

    public synchronized Boolean letCarLeaveCarpark() {

        data.decrementNumCars();
        LOGGER.info("Barrier opened! num_cars decremented: " + data.getNumCars());
        notifyAll();
        return true;
    }
}

