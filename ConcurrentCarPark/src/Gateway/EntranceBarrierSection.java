package Gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntranceBarrierSection extends BarrierSection {

    private static final Logger LOGGER = LogManager.getLogger("EntranceBarrierSection");

    private BarrierController barrier_controller;

    public EntranceBarrierSection(BarrierController barrier_controller) {

        super();
        this.barrier_controller = barrier_controller;
    }

    public void openBarrier() {

        barrier_controller.openEntranceBarrier();
    }
}