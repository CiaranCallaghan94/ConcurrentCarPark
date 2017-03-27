package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Gateway {

    private final List<Lane> entrances;
    private final List<Lane> exits;

    private final Lock gatewayInLock = new ReentrantLock(true);
    private final Lock gatewayOutLock = new ReentrantLock(true);

    public SimulationGUI GUI;

    public Gateway(int num_entrances, int num_exits, SimulationGUI GUI) {

        this.GUI = GUI;

        entrances = new ArrayList<>(num_entrances);
        exits = new ArrayList<>(num_exits);

        BarrierController barrier_controller = new BarrierController(GUI);

        for (int i = 0; i < num_entrances; i++) {
            Lane entrance = new Entrance(barrier_controller,i,GUI);
            entrances.add(entrance);
        }

        for (int i = 0; i < num_exits; i++) {
            Lane exit = new Exit(barrier_controller,i,GUI);
            exits.add(exit);
        }
    }

    public Entrance addCarToEntrance(Car c) {

        return (Entrance) placeInShortestEntrance(c, entrances);
    }

    public Exit addCarToExit(Car c) {

        return (Exit) placeInShortestExit(c, exits);
    }

    //Scans through all the entrances and adds the car to the shortest queue
    private Lane placeInShortestEntrance(Car c, List<Lane> lanes) {

        gatewayInLock.lock();

        GUI.updater.increaseTotalCarsInSimulation();

        try {

            Lane shortest_lane = lanes.get(0);

            for (int i = 1; i < lanes.size(); i++) {

                Lane test_shortest = lanes.get(i);
                if (test_shortest.checkLengthOfQueue() < shortest_lane.checkLengthOfQueue()) {

                    shortest_lane = test_shortest;
                }
                // Adds a random chance of which lane the car enters if the queues are the same
                else if (test_shortest.checkLengthOfQueue() == shortest_lane.checkLengthOfQueue()) {

                    int temp = (Math.random() < 0.5) ? 1 : 2;

                    if (temp == 2) {
                        shortest_lane = test_shortest;
                    }
                }
            }
            return shortest_lane;
        }
        finally {
            gatewayInLock.unlock();
        }
    }

    //Scans through all the entrances and adds the car to the shortest queue
    private Lane placeInShortestExit(Car c, List<Lane> lanes) {

        gatewayOutLock.lock();

        try {

            Lane shortest_lane = lanes.get(0);

            for (int i = 1; i < lanes.size(); i++) {

                Lane test_shortest = lanes.get(i);
                if (test_shortest.checkLengthOfQueue() < shortest_lane.checkLengthOfQueue()) {

                    shortest_lane = test_shortest;
                }
                // Adds a random chance of which lane the car enters if the queues are the same
                else if (test_shortest.checkLengthOfQueue() == shortest_lane.checkLengthOfQueue()) {

                    int temp = (Math.random() < 0.5) ? 1 : 2;

                    if (temp == 2) {
                        shortest_lane = test_shortest;
                    }
                }
            }
            return shortest_lane;
        }
        finally {
            gatewayOutLock.unlock();
        }
    }
}
