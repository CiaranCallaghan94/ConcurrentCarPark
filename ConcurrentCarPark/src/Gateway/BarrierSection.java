package Gateway;

import Car.Car;
import config.XMLParser;

import java.util.Random;

/**
 * The BarrierSection holds one car at a time. This is the area
 * where a car will stop, put in their ticket before entering the
 * carpark.
 */

public abstract class BarrierSection {

    private int std_barrier_time;
    private int std_problem_time;
    private double probability_of_problem;

    protected Car car;

    private int service_time;

    public BarrierSection() {

        std_barrier_time = XMLParser.AVG_BARRIER_TIME;
        std_problem_time = XMLParser.AVG_BARRIER_PROBLEM_TIME;
        probability_of_problem = XMLParser.PROPORTION_BARRIER_PROBLEM;

        service_time = 0;
    }

    // Set normally distributed service time. Also check if
    // there is a problem and add that to the service time.
    private int setServiceTime() {

        Random rand = new Random();

        double service_time_dbl = -1;
        while (service_time_dbl <= 0)
            service_time_dbl = (rand.nextGaussian() * 1 + std_barrier_time);

        service_time = (int) Math.round(service_time_dbl);

        service_time += emulateProblem();

        return service_time;
    }

    // Emulate problem with payment / faulty barrierSection.
    // A problem occurs based on probability. This will
    // return 0 if there is no problem.
    public int emulateProblem() {

        int additional_time = 0;

        Random rand = new Random();

        // Here is essentially a "100-sided dice roll". If the "dice"
        // lands between 0 and "probability of problem", then create problem
        int random = rand.nextInt(100) + 1;
        if (random <= probability_of_problem) {

            // Normally distributed problem time
            double problem_time_dbl = rand.nextGaussian() + std_problem_time;

            additional_time = (int) Math.round(problem_time_dbl);
        }

        return additional_time;
    }

    // Add car to the barrier and calculate service time.
    // Then sleep for the service time.
    public void addCar(Car c) {

        car = c;
        int service_time = setServiceTime();

        try {
            Thread.sleep(service_time);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        openBarrier();
    }

    // Open the barrier and let car through
    public abstract void openBarrier();
}