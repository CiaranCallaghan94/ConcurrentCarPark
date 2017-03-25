package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLParser {

    private static final Logger LOGGER = LogManager.getLogger("XMLParser");

    public static String input_path = "./ConcurrentCarPark/src/config/input.xml";
    public static String input_path_2 = "./src/config/input.xml";

    // TIMES
    public static int OPEN_TIME;
    public static int CLOSE_TIME;
    public static int ARRIVAL_RUSH_HOUR;
    public static int STD_DEVIATION;
    public static int SIMULATION_SPEED;

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
    public static int AVG_LECTURER_CAR_WIDTH;
    public static int AVG_LECTURER_DEXTERITY;
    public static int AVG_LECTURER_STAY_TIME;

    // STUDENT CARS
    public static int AVG_STUDENT_CAR_WIDTH;
    public static int AVG_STUDENT_DEXTERITY;
    public static int AVG_STUDENT_STAY_TIME;


    public static int timeToSimulationTime(String time) {

        String[] time_arr = time.split(":");

        int hours = Integer.parseInt(time_arr[0]);
        int mins = Integer.parseInt(time_arr[1]);
        int secs = Integer.parseInt(time_arr[2]);

        int time_in_seconds = hours * 60 * 60 + mins * 60 + secs;
        int time_in_milliseconds = time_in_seconds * 1000;

        LOGGER.info("Time in millis: " + time_in_milliseconds);
        LOGGER.info("Time divided by speed: " + time_in_milliseconds / SIMULATION_SPEED);

        return time_in_milliseconds / SIMULATION_SPEED;
    }

    public static void readInput() {

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(input_path_2));

            // normalize text representation
            document.getDocumentElement().normalize();

            // NODES
            Element times = (Element) document.getElementsByTagName("times").item(0);
            Element carpark = (Element) document.getElementsByTagName("carpark").item(0);
            Element barrier = (Element) document.getElementsByTagName("barrier").item(0);
            Element cars = (Element) document.getElementsByTagName("cars").item(0);
            Element lecturer_cars = (Element) document.getElementsByTagName("lecturer_cars").item(0);
            Element student_cars = (Element) document.getElementsByTagName("student_cars").item(0);

            // TIMES
            SIMULATION_SPEED = parseIntegerAttribute(times, "simulation_speed");
            OPEN_TIME = parseTimeAttribute(times, "open_time");
            CLOSE_TIME = parseTimeAttribute(times, "close_time");
            ARRIVAL_RUSH_HOUR = parseTimeAttribute(times, "arrival_rush_hour");
            STD_DEVIATION = parseTimeAttribute(times, "std_deviation");

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
            AVG_LECTURER_CAR_WIDTH = parseIntegerAttribute(lecturer_cars, "avg_lecturer_car_width");
            AVG_LECTURER_DEXTERITY = parseIntegerAttribute(lecturer_cars, "avg_lecturer_dexterity");
            AVG_LECTURER_STAY_TIME = parseTimeAttribute(lecturer_cars, "avg_lecturer_stay_time");

            // STUDENT CARS
            AVG_STUDENT_CAR_WIDTH = parseIntegerAttribute(student_cars, "avg_student_car_width");
            AVG_STUDENT_DEXTERITY = parseIntegerAttribute(student_cars, "avg_student_dexterity");
            AVG_STUDENT_STAY_TIME = parseTimeAttribute(student_cars, "avg_student_stay_time");

        } catch (ParserConfigurationException e) {
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        }
    }

    public static int parseTimeAttribute(Element node, String attrib_name) {

        String value = node.getElementsByTagName(attrib_name).item(0).getTextContent();

        return timeToSimulationTime(value);
    }

    public static int parseIntegerAttribute(Element node, String attrib_name) {

        String value = node.getElementsByTagName(attrib_name).item(0).getTextContent();

        return Integer.parseInt(value);
    }
}
