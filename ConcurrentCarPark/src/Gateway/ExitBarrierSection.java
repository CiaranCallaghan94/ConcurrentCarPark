package Gateway;

/**
 * This class contains anything that is specific to the
 * Exit barrier.
 */

public class ExitBarrierSection extends BarrierSection {

    private final BarrierController barrier_controller;

    public ExitBarrierSection(BarrierController barrier_controller) {

        this.barrier_controller = barrier_controller;
    }

    // Open Exit barrier
    public void openBarrier() {

        barrier_controller.openExitBarrier();
    }
}