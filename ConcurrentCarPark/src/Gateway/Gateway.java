package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The Gateway maintains fairness with its ReentrantLock.
 * The lock is used to ensure that the car can choose accurately which lane it should enter
 * The fairness parameter in the Reentrant lock enables us to make a queue like
 * feature, The longest waiting car can then look for the shortest queue.
 * A lock is used here to enforce mutual exclusion choosing the shortest lane.
 */

public class Gateway {

    private final List<Entrance> entrances;
    private final List<Exit> exits;

    private final Lock gatewayInLock = new ReentrantLock(true);
    private final Lock gatewayOutLock = new ReentrantLock(true);

    public SimulationGUI GUI;

    public Gateway(int num_entrances, int num_exits, SimulationGUI GUI) {

        this.GUI = GUI;

        entrances = new ArrayList<>(num_entrances);
        exits = new ArrayList<>(num_exits);

        BarrierController barrier_controller = new BarrierController(GUI);

        // Creates the amount of entrances specified in the input.
        for (int i = 0; i < num_entrances; i++) {
            Entrance entrance = new Entrance(barrier_controller, i, GUI);
            entrances.add(entrance);
        }

        // Creates the amount of exits specified in the input.
        for (int i = 0; i < num_exits; i++) {
            Exit exit = new Exit(barrier_controller, i, GUI);
            exits.add(exit);
        }
    }

    // Scans through all the entrances to find the shortest queue.
    // Adds the car to that queue.
    private Entrance placeInShortestEntrance(Car c, List<Entrance> lanes) {

        // Try acquire lock, if not go into waiting queue
        gatewayInLock.lock();

        // Acquired lock, in critical section

        GUI.updater.increaseTotalCarsInSimulation();

        try {

            Entrance shortest_lane = lanes.get(0);

            for (int i = 1; i < lanes.size(); i++) {

                Entrance test_shortest = lanes.get(i);
                if (test_shortest.checkLengthOfQueue() < shortest_lane.checkLengthOfQueue()) {

                    shortest_lane = test_shortest;
                }
                // // Adds a random decision which lane the car enters if the queues are the same
                else if (test_shortest.checkLengthOfQueue() == shortest_lane.checkLengthOfQueue()) {

                    int temp = (Math.random() < 0.5) ? 1 : 2;

                    if (temp == 2) {
                        shortest_lane = test_shortest;
                    }
                }
            }
            return shortest_lane;
        } finally {
            // Release lock
            gatewayInLock.unlock();
        }
    }

    //Scans through all the entrances and adds the car to the shortest queue
    private Exit placeInShortestExit(Car c, List<Exit> lanes) {

        // Try acquire lock, if not go into waiting queue
        gatewayOutLock.lock();

        // Acquired lock, in critical section

        try {

            Exit shortest_lane = lanes.get(0);

            for (int i = 1; i < lanes.size(); i++) {

                Exit test_shortest = lanes.get(i);
                if (test_shortest.checkLengthOfQueue() < shortest_lane.checkLengthOfQueue()) {

                    shortest_lane = test_shortest;
                }
                // Adds a random decision which lane the car enters if the queues are the same.
                else if (test_shortest.checkLengthOfQueue() == shortest_lane.checkLengthOfQueue()) {

                    int temp = (Math.random() < 0.5) ? 1 : 2;

                    if (temp == 2) {
                        shortest_lane = test_shortest;
                    }
                }
            }
            return shortest_lane;
        } finally {
            // Release lock
            gatewayOutLock.unlock();
        }
    }

    public Entrance addCarToEntrance(Car c) {

        return placeInShortestEntrance(c, entrances);
    }

    public Exit addCarToExit(Car c) {

        return placeInShortestExit(c, exits);
    }


}
