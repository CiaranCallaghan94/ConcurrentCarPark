package Gateway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExitBarrierSection extends BarrierSection {

	private static final Logger LOGGER = LogManager.getLogger( "ExitBarrierSection" );

	ExitBarrier exit_barrier;

	public ExitBarrierSection(Data data) {

		exit_barrier = new ExitBarrier(data);
	}

	public void openBarrier() {

		ExecutorService executorService = Executors.newFixedThreadPool(1);
		Future future = executorService.submit(exit_barrier);

		try {
			LOGGER.info("Calling EXIT future.get");
			future.get();
			LOGGER.info("Get EXIT complete");
		}
		catch(InterruptedException e) {}
		catch(ExecutionException e) {}

	}
}