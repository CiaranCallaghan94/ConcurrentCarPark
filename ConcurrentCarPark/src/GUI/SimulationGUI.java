package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ciarancallaghan on 26/03/2017.
 */
public class SimulationGUI extends JFrame {

    public JLabel title;
    public JLabel carParkCapacity;
    public JLabel carsAtEntrance;
    public JLabel carsAtExit;


    public SimulationGUI(){

        this.setSize(200,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Carpark Simulation");

        // Panel
        Box mainBox = Box.createVerticalBox();
        this.add(mainBox);

        // Components
        title = new JLabel("Carpark Simulation");
        title.setForeground(Color.BLUE);

        carParkCapacity = new JLabel("CarPark: 0");

        carsAtEntrance = new JLabel("Cars at Entrances: 0");

        carsAtExit = new JLabel("Cars at Exits: 0");



        // Adding Components to panel
        mainBox.add(title);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(carParkCapacity);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(carsAtEntrance);
        mainBox.add(Box.createVerticalStrut(5));
        mainBox.add(carsAtExit);

        // Centers Frame
        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }




}


