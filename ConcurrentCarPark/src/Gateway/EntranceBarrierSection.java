package Gateway;

public class EntranceBarrierSection extends BarrierSection {

    private BarrierController barrier_controller;

    public EntranceBarrierSection(BarrierController barrier_controller) {

        super();
        this.barrier_controller = barrier_controller;
    }

    public void openBarrier() {

        barrier_controller.openEntranceBarrier();
    }
}