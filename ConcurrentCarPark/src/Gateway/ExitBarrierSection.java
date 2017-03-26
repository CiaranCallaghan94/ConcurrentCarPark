package Gateway;

public class ExitBarrierSection extends BarrierSection {

    private final BarrierController barrier_controller;

    public ExitBarrierSection(BarrierController barrier_controller) {

        this.barrier_controller = barrier_controller;
    }

    public void openBarrier() {

        barrier_controller.openExitBarrier();
    }
}