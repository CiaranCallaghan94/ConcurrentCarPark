package Gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExitBarrierSection extends BarrierSection {

    private static final Logger LOGGER = LogManager.getLogger("ExitBarrierSection");

    BarrierController barrier_controller;

    public ExitBarrierSection(BarrierController barrier_controller) {

        this.barrier_controller = barrier_controller;
    }

    public void openBarrier() {

        barrier_controller.openExitBarrier();
    }
}