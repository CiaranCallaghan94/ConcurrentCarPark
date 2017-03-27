package Gateway;

/**
 * This class contains anything that is specific to the
 * Entrance barrier.
 */

public class EntranceBarrierSection extends BarrierSection {

    private final BarrierController barrier_controller;

    public EntranceBarrierSection(BarrierController barrier_controller) {

        super();
        this.barrier_controller = barrier_controller;
    }

    // Open Entrance barrier
    public void openBarrier() {

        barrier_controller.openEntranceBarrier();
    }
}