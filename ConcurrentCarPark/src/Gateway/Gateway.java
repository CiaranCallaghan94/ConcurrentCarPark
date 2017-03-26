package Gateway;

import Car.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Gateway {

    private static final Logger LOGGER = LogManager.getLogger("Simulation");

    private static List<Lane> entrances;
    private static List<Lane> exits;

    public Gateway(int num_entrances, int num_exits) {

        entrances = new ArrayList<>(num_entrances);
        exits = new ArrayList<>(num_exits);

        BarrierController barrier_controller = new BarrierController();

        for (int i = 0; i < num_entrances; i++) {
            Lane entrance = new Entrance(barrier_controller);
            entrances.add(entrance);
        }

        for (int i = 0; i < num_exits; i++) {
            Lane exit = new Exit(barrier_controller);
            exits.add(exit);
        }
    }

    public Entrance addCarToEntrance(Car c) {

        LOGGER.info("Managing car arrival...  -" + Thread.currentThread().getId());

        return (Entrance) placeInShortestLane(c, entrances);
    }

    public Exit addCarToExit(Car c) {

        LOGGER.info("Managing car departure...  -" + Thread.currentThread().getId());
        return (Exit) placeInShortestLane(c, exits);
    }

    //Scans through all the entrances and adds the car to the shortest queue
    private synchronized Lane placeInShortestLane(Car c, List<Lane> lanes) {

        LOGGER.info("Placing in shortest lane... -" + Thread.currentThread().getId());

        Lane shortest_lane = lanes.get(0);

        for (int i = 1; i < lanes.size(); i++) {

            Lane test_shortest = lanes.get(i);
            if (test_shortest.checkLenghtOfQueue() < shortest_lane.checkLenghtOfQueue()) {

                shortest_lane = test_shortest;
            }
        }
        LOGGER.info("Car placed in shortest lane...  -" + Thread.currentThread().getId());
        return shortest_lane;
    }

}
