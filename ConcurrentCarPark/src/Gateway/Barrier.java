package Gateway;

import java.util.concurrent.Semaphore;

public class Barrier implements Runnable {

    final Semaphore available = new Semaphore();

    public void run() {

    }
}
