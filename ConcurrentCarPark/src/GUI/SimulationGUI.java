package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ciarancallaghan on 26/03/2017.
 */
public class SimulationGUI extends JFrame {

    public JLabel title;
    public List<JLabel> entrances_panels = new LinkedList<>();
    public List<JLabel> exits_panels = new LinkedList<>();
    public JLabel total_cars_in_scene_panel;
    public JLabel cars_in_carpark_and_capacity;
    public JLabel total_cars_at_entrances_panel;
    public JLabel total_cars_at_exits_panel;
    public JLabel max_cars_in_simulation_panel;

    public int total_cars_in_simulation = 0;
    public int max_cars_in_simulation = 0;
    public int total_cars_in_scene = 0;
    public int total_cars_in_carpark = 0;
    public int carpark_capacity = 0;
    public int total_cars_at_entrances = 0;
    public int total_cars_at_exits = 0;
    public List<Integer> entrances_nums = new LinkedList<>();
    public List<Integer> exits_nums = new LinkedList<>();


    public SimulationGUI(int num_entrances, int num_exits, int carpark_capacity, int max_cars){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Carpark Simulation");

        // Panel
        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        // Title
        title = new JLabel("Carpark Simulation");
        title.setForeground(Color.BLUE);
        mainBox.add(title);
        mainBox.add(Box.createVerticalStrut(10));

        // Cars in scene
        total_cars_in_scene_panel = new JLabel("Cars in scene: " + total_cars_in_scene);
        mainBox.add(total_cars_in_scene_panel);
        mainBox.add(Box.createVerticalStrut(10));
        // Carpark Occupancy Capacity
        this.carpark_capacity = carpark_capacity;
        cars_in_carpark_and_capacity = new JLabel("Carpark Occupancy: " + total_cars_in_carpark + "/" +carpark_capacity);
        mainBox.add(cars_in_carpark_and_capacity);
        mainBox.add(Box.createVerticalStrut(10));

        // Entrances
        total_cars_at_entrances_panel =  new JLabel("Amount of cars at all entrances: "+ total_cars_at_entrances);
        mainBox.add(total_cars_at_entrances_panel);
        mainBox.add(Box.createVerticalStrut(10));
        for (int i = 0; i < num_entrances; i++){
            entrances_nums.add(0);
            entrances_panels.add( new JLabel("Cars at Entrance: 0"));
            mainBox.add(entrances_panels.get(i));
            mainBox.add(Box.createVerticalStrut(2));
        }
        mainBox.add(Box.createVerticalStrut(10));
        // Exits
        total_cars_at_exits_panel =  new JLabel("Amount of cars at all entrances: "+ total_cars_at_exits);
        mainBox.add(total_cars_at_exits_panel);
        mainBox.add(Box.createVerticalStrut(10));
        for (int i = 0; i < num_exits; i++){
            exits_nums.add(0);
            exits_panels.add( new JLabel("Cars at Exit: 0"));
            mainBox.add(exits_panels.get(i));
            mainBox.add(Box.createVerticalStrut(2));
        }

        mainBox.add(Box.createVerticalStrut(10));
        // Max cars in simulation
        max_cars_in_simulation = max_cars;
        max_cars_in_simulation_panel = new JLabel("Total cars in simulation: "+ total_cars_in_simulation +"/"+ max_cars_in_simulation);
        mainBox.add(max_cars_in_simulation_panel);
        // Centers Frame
        this.setLocationRelativeTo(null);
        this.setSize(300,300);
        this.setVisible(true);
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
    }
}


