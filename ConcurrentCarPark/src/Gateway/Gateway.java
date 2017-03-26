package Gateway;

import Car.Car;
import GUI.SimulationGUI;

import java.util.ArrayList;
import java.util.List;

public class Gateway {

    private static List<Lane> entrances;
    private static List<Lane> exits;

    public SimulationGUI gui;

    public Gateway(int num_entrances, int num_exits, SimulationGUI simGUI) {

        this.gui = simGUI;
        entrances = new ArrayList<>(num_entrances);
        exits = new ArrayList<>(num_exits);

        BarrierController barrier_controller = new BarrierController(gui);

        for (int i = 0; i < num_entrances; i++) {
            Lane entrance = new Entrance(barrier_controller,i);
            entrances.add(entrance);
        }

        for (int i = 0; i < num_exits; i++) {
            Lane exit = new Exit(barrier_controller,i);
            exits.add(exit);
        }
    }

    public Entrance addCarToEntrance(Car c) {

        return (Entrance) placeInShortestLane(c, entrances);
    }

    public Exit addCarToExit(Car c) {

        return (Exit) placeInShortestLane(c, exits);
    }

    //Scans through all the entrances and adds the car to the shortest queue
    private synchronized Lane placeInShortestLane(Car c, List<Lane> lanes) {

        Lane shortest_lane = lanes.get(0);

        for (int i = 1; i < lanes.size(); i++) {

            Lane test_shortest = lanes.get(i);
            if (test_shortest.checkLenghtOfQueue() < shortest_lane.checkLenghtOfQueue()) {

                shortest_lane = test_shortest;
            }
            // Adds a random chance of which lane the car enters if the queues are the same
            else if(test_shortest.checkLenghtOfQueue() == shortest_lane.checkLenghtOfQueue()) {

                int temp = (Math.random() < 0.5) ? 1 : 2;

                if (temp == 2){
                    shortest_lane = test_shortest;
                }
            }
        }
        return shortest_lane;
    }

}
