package Gateway;

		import org.apache.logging.log4j.Logger;
		import org.apache.logging.log4j.LogManager;

public class ExitBarrierSection extends BarrierSection {

	private static final Logger LOGGER = LogManager.getLogger( "ExitBarrierSection" );

	ExitBarrier exit_barrier;

	public ExitBarrierSection(Integer num_cars) {

		super(num_cars);

		exit_barrier = new ExitBarrier(car, num_cars);
		exit_barrier.start();
	}

	public void sendCarThroughBarrier() {
		exit_barrier.setCar(car);
	}

}