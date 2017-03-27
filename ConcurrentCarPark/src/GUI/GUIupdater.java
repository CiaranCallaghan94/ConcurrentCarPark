package GUI;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Anglim on 27/03/2017.
 */
public class GUIupdater {

    public List<JLabel> entrances_panels = new LinkedList<>();
    public List<JLabel> exits_panels = new LinkedList<>();
    public JLabel total_cars_in_scene_panel;
    public JLabel cars_in_carpark_and_capacity;
    public JLabel total_cars_at_entrances_panel;
    public JLabel total_cars_at_exits_panel;
    public JLabel max_cars_in_simulation_panel;

    private int num_entrances;
    private int num_exits;

    private int max_cars_in_simulation = 0;
    private int carpark_capacity = 0;

    private int max_cars;
    private int total_cars_in_simulation = 0;
    private int total_cars_in_scene = 0;
    private int total_cars_in_carpark = 0;
    private int total_cars_at_entrances = 0;
    private int total_cars_at_exits = 0;

    private List<Integer> entrances_nums = new LinkedList<>();
    private List<Integer> exits_nums = new LinkedList<>();

    public GUIupdater(List<JLabel> entrances_panels, List<JLabel> exits_panels, JLabel total_cars_in_scene_panel,
                      JLabel cars_in_carpark_and_capacity, JLabel total_cars_at_entrances_panel,
                      JLabel total_cars_at_exits_panel, JLabel max_cars_in_simulation_panel,
                      List<Integer> entrances_nums, List<Integer> exit_nums,
                      int num_entrances, int num_exits, int max_cars_in_simulation, int carpark_capacity, int max_cars) {

        this.num_entrances = num_entrances;
        this.num_exits = num_exits;

        this.max_cars_in_simulation = max_cars_in_simulation;
        this.carpark_capacity = carpark_capacity;

        this.max_cars = max_cars;
        this.entrances_panels = entrances_panels;
        this.exits_panels = exits_panels;
        this.total_cars_in_scene_panel = total_cars_in_scene_panel;
        this.cars_in_carpark_and_capacity = cars_in_carpark_and_capacity;
        this.total_cars_at_entrances_panel = total_cars_at_entrances_panel;
        this.total_cars_at_exits_panel = total_cars_at_exits_panel;
        this.max_cars_in_simulation_panel = max_cars_in_simulation_panel;

        this.entrances_nums = entrances_nums;
        this.exits_nums = exit_nums;
    }

    public void setEntranceNum(int id, int i) {

        entrances_nums.set(id, i);
    }

    public void setExitNum(int id, int i) {
        exits_nums.set(id, i);
    }

    public void updateStats() {

        total_cars_in_scene = 0;
        total_cars_at_entrances = 0;
        total_cars_at_exits = 0;

        //total
        cars_in_carpark_and_capacity.setText("Carpark Occupancy: " + total_cars_in_carpark + "/" +carpark_capacity);

        //entrances
        for(int i = 0 ; i < entrances_nums.size(); i++){

            total_cars_at_entrances += entrances_nums.get(i);
            entrances_panels.get(i).setText("entrance: "+ entrances_nums.get(i));
        }
        total_cars_at_entrances_panel.setText("Amount of cars at all entrances: " + total_cars_at_entrances);

        //exits
        for(int i = 0 ; i < exits_nums.size(); i++){

            total_cars_at_exits += exits_nums.get(i);
            exits_panels.get(i).setText("exit: "+ exits_nums.get(i));
        }
        total_cars_at_exits_panel.setText("Amount of cars at all exits: "+ total_cars_at_exits);

        // cars in scene
        total_cars_in_scene = total_cars_in_carpark + total_cars_at_exits + total_cars_at_entrances;
        total_cars_in_scene_panel.setText("Cars in scene: " + total_cars_in_scene);

        // total
        max_cars_in_simulation_panel.setText("Total cars in simulation: "+ total_cars_in_simulation +"/"+ max_cars_in_simulation);
    }

    public void setTotalCarsInCarpark(int i) {

        total_cars_in_carpark = i;
    }

    public void increaseTotalCarsInSimulation(){

        total_cars_in_simulation++;
        System.out.println("Increasing total cars in simulation: " + total_cars_in_simulation);
    }
}
