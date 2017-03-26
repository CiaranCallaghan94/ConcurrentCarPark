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
    public JLabel carParkCapacity;
    public List<JLabel> entrances = new LinkedList<>();
    public List<JLabel> exits = new LinkedList<>();


    public SimulationGUI(int num_entrances, int num_exits){

        this.setSize(400,400);
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

        // Carpark Capacity
        carParkCapacity = new JLabel("CarPark: 0");
        mainBox.add(carParkCapacity);
        mainBox.add(Box.createVerticalStrut(5));

        // Entrances
        for (int i = 0; i < num_entrances; i++){
            entrances.add( new JLabel("Cars at Entrance: 0"));
            mainBox.add(entrances.get(i));
            mainBox.add(Box.createVerticalStrut(5));
        }

        // Exits
        for (int i = 0; i < num_exits; i++){
            exits.add( new JLabel("Cars at Exit: 0"));
            mainBox.add(exits.get(i));
            mainBox.add(Box.createVerticalStrut(5));
        }

        // Centers Frame
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }




}


