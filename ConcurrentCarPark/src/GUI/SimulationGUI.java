package GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ciarancallaghan on 26/03/2017.
 */
public class SimulationGUI extends JFrame {

    Box mainBox;

    private JLabel title;
    private List<JLabel> entrances_panels = new LinkedList<>();
    private List<JLabel> exits_panels = new LinkedList<>();
    private JLabel total_cars_in_scene_panel;
    private JLabel cars_in_carpark_and_capacity;
    private JLabel total_cars_at_entrances_panel;
    private JLabel total_cars_at_exits_panel;
    private JLabel max_cars_in_simulation_panel;

    private int max_cars_in_simulation = 0;
    private int carpark_capacity = 0;

    private List<Integer> entrances_nums = new LinkedList<>();
    private List<Integer> exits_nums = new LinkedList<>();

    public GUIupdater updater;

    public SimulationGUI(int num_entrances, int num_exits, int carpark_capacity, int max_cars){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Carpark Simulation");

        // Panel
        mainBox = Box.createVerticalBox();
        this.add(mainBox);

        // Title
        JPanel center_panel = new JPanel(new GridLayout(0, 1));

        title = new JLabel("Carpark Simulation", SwingConstants.CENTER);
        title.setForeground(Color.BLUE);

        center_panel.setLayout(new BorderLayout(5, 5));
        center_panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        center_panel.add(title, SwingConstants.CENTER);
        mainBox.add(center_panel);
        mainBox.add(Box.createVerticalStrut(10));

        // Cars in scene
        totalCarsInSceneLabel();

        // Carpark Occupancy Capacity
        this.carpark_capacity = carpark_capacity;
        carparkOccupancyLabel();

        // Entrances
        totalCarsAtEntrancesLabel();
        individualEntranceLabels(num_entrances);

        // Exits
        totalCarsAtExitsLabel();
        individualExitLabels(num_exits);

        // Max cars in simulation
        this.max_cars_in_simulation = max_cars;
        maxCarsInSimulation();

        this.setLocationRelativeTo(null);
        this.setSize(600,400);

        // Centers Frame
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        this.setVisible(true);

        this.updater = new GUIupdater(
                entrances_panels, exits_panels, total_cars_in_scene_panel, cars_in_carpark_and_capacity,
                total_cars_at_entrances_panel, total_cars_at_exits_panel, max_cars_in_simulation_panel,
                entrances_nums, exits_nums, num_entrances, num_exits, max_cars_in_simulation, carpark_capacity, max_cars);
    }

    private void totalCarsInSceneLabel() {

        total_cars_in_scene_panel = new JLabel("Cars in scene: " + 0);
        addToMainbox(total_cars_in_scene_panel);
    }

    private void carparkOccupancyLabel() {

        cars_in_carpark_and_capacity = new JLabel("Carpark Occupancy: " + 0 + "/" + carpark_capacity);
        addToMainbox(cars_in_carpark_and_capacity);
    }

    private void totalCarsAtEntrancesLabel() {
        total_cars_at_entrances_panel =  new JLabel("Amount of cars at all entrances: "+ 0);
        addToMainbox(total_cars_at_entrances_panel);
    }

    private void individualEntranceLabels(int num_entrances) {

        for (int i = 0; i < num_entrances; i++){

            int num = i+1;
            entrances_nums.add(0);
            JLabel cars_at_entrance = new JLabel("Entrance " + num + ": 0");
            entrances_panels.add(cars_at_entrance);
            addToMainbox(cars_at_entrance);
        }
    }

    private void totalCarsAtExitsLabel() {

        total_cars_at_exits_panel =  new JLabel("Amount of cars at all entrances: "+ 0);
        addToMainbox(total_cars_at_exits_panel);
    }

    private void individualExitLabels(int num_exits) {

        for (int i = 0; i < num_exits; i++){

            int num = i+1;
            exits_nums.add(0);
            JLabel cars_at_exit = new JLabel("Exit " + num + ": 0");
            exits_panels.add(cars_at_exit);
            addToMainbox(cars_at_exit);
        }
    }

    private void maxCarsInSimulation() {

        max_cars_in_simulation_panel = new JLabel("Total cars in simulation: " +
                                                    0 + "/" + max_cars_in_simulation);
        addToMainbox(max_cars_in_simulation_panel);
    }

    private void addToMainbox(JLabel label) {

        mainBox.add(label);
        mainBox.add(Box.createVerticalStrut(10));
    }
}


