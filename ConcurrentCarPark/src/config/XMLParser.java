package config;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XMLParser is responsible for reading in the various user inputs
 * from the XML input file. It also converts inputs into a
 * usable format (e.g. hh:mm:ss string -> milliseconds).
 */

public class XMLParser {

    public static String input_path = "./ConcurrentCarPark/src/config/input.xml";
    public static String input_path_2 = "./src/config/input.xml";

    // TIMES
    public static int SIMULATION_SPEED;

    // ARRIVAL RUSH HOURS
    public static List<ArrivalRushHour> ARRIVAL_RUSH_HOURS = new ArrayList<>(5);

    // CARPARK
    public static int CARPARK_CAPACITY;
    public static int NUM_ENTRANCES;
    public static int NUM_EXITS;

    // BARRIER
    public static int AVG_BARRIER_TIME;
    public static int AVG_BARRIER_PROBLEM_TIME;
    public static int PROPORTION_BARRIER_PROBLEM;

    // CARS
    public static int NUM_CARS;
    public static float PROPORTION_STUDENTS;

    // LECTURER CARS
    public static int AVG_LECTURER_DEXTERITY;
    public static int AVG_LECTURER_STAY_TIME;
    public static int LECTURER_STAY_TIME_DEVIATION;

    // STUDENT CARS
    public static int AVG_STUDENT_DEXTERITY;
    public static int AVG_STUDENT_STAY_TIME;
    public static int STUDENT_STAY_TIME_DEVIATION;

    public static void readInput() {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(input_path));

            // normalize text representation
            document.getDocumentElement().normalize();

            // One single node
            Node sim_speed = document.getElementsByTagName("simulation_speed").item(0);

            // NodeList contains a list of all nodes under the TagName
            NodeList arrival_rush_hours = document.getElementsByTagName("arrival_rush_hour");

            // Element contains all child attributes under the TagName
            Element carpark = (Element) document.getElementsByTagName("carpark").item(0);
            Element barrier = (Element) document.getElementsByTagName("barrier").item(0);
            Element cars = (Element) document.getElementsByTagName("cars").item(0);
            Element lecturer_cars = (Element) document.getElementsByTagName("lecturer_cars").item(0);
            Element student_cars = (Element) document.getElementsByTagName("student_cars").item(0);

            // SPEED
            SIMULATION_SPEED = Integer.parseInt(sim_speed.getTextContent());

            // ARRIVAL RUSH HOURS
            parseArrivalRushHours(arrival_rush_hours);

            // CARPARK
            CARPARK_CAPACITY = parseIntegerAttribute(carpark, "carpark_capacity");
            NUM_ENTRANCES = parseIntegerAttribute(carpark, "num_entrances");
            NUM_EXITS = parseIntegerAttribute(carpark, "num_exits");

            // BARRIER
            AVG_BARRIER_TIME = parseTimeAttribute(barrier, "avg_time");
            AVG_BARRIER_PROBLEM_TIME = parseTimeAttribute(barrier, "avg_problem_time");
            PROPORTION_BARRIER_PROBLEM = parseIntegerAttribute(barrier, "proportion_problem");

            // CARS
            NUM_CARS = parseIntegerAttribute(cars, "num_cars");
            PROPORTION_STUDENTS = parseIntegerAttribute(cars, "proportion_student");

            // LECTURER CARS
            AVG_LECTURER_DEXTERITY = parseIntegerAttribute(lecturer_cars, "avg_lecturer_dexterity");
            AVG_LECTURER_STAY_TIME = parseTimeAttribute(lecturer_cars, "avg_lecturer_stay_time");
            LECTURER_STAY_TIME_DEVIATION = parseTimeAttribute(lecturer_cars, "lecturer_stay_time_deviation");

            // STUDENT CARS
            AVG_STUDENT_DEXTERITY = parseIntegerAttribute(student_cars, "avg_student_dexterity");
            AVG_STUDENT_STAY_TIME = parseTimeAttribute(student_cars, "avg_student_stay_time");
            STUDENT_STAY_TIME_DEVIATION = parseTimeAttribute(student_cars, "student_stay_time_deviation");

        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
    }

    // Loop through and parse all the arrival rush hour nodes
    public static void parseArrivalRushHours(NodeList rush_hours) {

        ArrivalRushHour arrivalRushHour;
        for (int i = 0; i < rush_hours.getLength(); i++) {

            Node node = rush_hours.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element arrival_rush_hour = (Element) rush_hours.item(i);
                int time = parseTimeAttribute(arrival_rush_hour, "time");
                int proportion = parseIntegerAttribute(arrival_rush_hour, "proportion");
                int std_deviation = parseTimeAttribute(arrival_rush_hour, "std_deviation");

                arrivalRushHour = new ArrivalRushHour(time, proportion, std_deviation);
                ARRIVAL_RUSH_HOURS.add(arrivalRushHour);
            }
        }
    }

    // Parse String time (hh:mm:ss) XML attribute and convert to milliseconds
    public static int parseTimeAttribute(Element node, String attrib_name) {

        String value = node.getElementsByTagName(attrib_name).item(0).getTextContent();
        return timeToSimulationTime(value);
    }

    // Parse integer XML attribute and convert from String to Integer
    public static int parseIntegerAttribute(Element node, String attrib_name) {

        String value = node.getElementsByTagName(attrib_name).item(0).getTextContent();
        return Integer.parseInt(value);
    }

    //Convert String formatted time (hh:mm:ss) into milliseconds
    public static int timeToSimulationTime(String time) {

        String[] time_arr = time.split(":");

        int hours = Integer.parseInt(time_arr[0]);
        int mins = Integer.parseInt(time_arr[1]);
        int secs = Integer.parseInt(time_arr[2]);

        int time_in_seconds = hours * 60 * 60 + mins * 60 + secs;
        int time_in_milliseconds = time_in_seconds * 1000;

        return time_in_milliseconds / SIMULATION_SPEED;
    }
}
