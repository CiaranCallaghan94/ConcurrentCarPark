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

    private static final Logger LOGGER = LogManager.getLogger( "XMLParser" );

    public static String input_path	 = "./ConcurrentCarPark/src/config/input.xml";
	public static String input_path_2	 = "./src/config/input.xml";

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
	public static int	NUM_CARS;
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

        String [] time_arr = time.split(":");

        int hours = Integer.parseInt(time_arr[0]);
        int mins = Integer.parseInt(time_arr[1]);

        int time_in_seconds = hours*60*60 + mins*60;
        int time_in_milliseconds = time_in_seconds * 1000;

        LOGGER.info("Time in millis: " + time_in_milliseconds);
        LOGGER.info("Time divided by speed: " + time_in_milliseconds / SIMULATION_SPEED);

        return time_in_milliseconds / SIMULATION_SPEED;
    }

	public static void readInput() {

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(input_path));

			// normalize text representation
			document.getDocumentElement().normalize ();

			Element times 			= (Element) document.getElementsByTagName("times").item(0);
			Element carpark 		= (Element) document.getElementsByTagName("carpark").item(0);
			Element barrier 		= (Element) document.getElementsByTagName("barrier").item(0);
			Element cars 			= (Element) document.getElementsByTagName("cars").item(0);
			Element lecturer_cars 	= (Element) document.getElementsByTagName("lecturer_cars").item(0);
			Element student_cars 	= (Element) document.getElementsByTagName("student_cars").item(0);

			// TIMES
            SIMULATION_SPEED 	= Integer.parseInt( times.getElementsByTagName("simulation_speed").item(0).getTextContent() );
			OPEN_TIME 			= timeToSimulationTime( times.getElementsByTagName("open_time").item(0).getTextContent() );
			CLOSE_TIME 			= timeToSimulationTime( times.getElementsByTagName("close_time").item(0).getTextContent() );
			ARRIVAL_RUSH_HOUR 	= timeToSimulationTime( times.getElementsByTagName("arrival_rush_hour").item(0).getTextContent() );;
			STD_DEVIATION 		= timeToSimulationTime( times.getElementsByTagName("std_deviation").item(0).getTextContent() );

			// CARPARK
			CARPARK_CAPACITY 	= Integer.parseInt( carpark.getElementsByTagName("carpark_capacity").item(0).getTextContent() );
			NUM_ENTRANCES 		= Integer.parseInt( carpark.getElementsByTagName("num_entrances").item(0).getTextContent() );
			NUM_EXITS 			= Integer.parseInt( carpark.getElementsByTagName("num_exits").item(0).getTextContent() );

            // BARRIER
            AVG_BARRIER_TIME 	        = timeToSimulationTime( barrier.getElementsByTagName("avg_time").item(0).getTextContent() );
            AVG_BARRIER_PROBLEM_TIME    = timeToSimulationTime( barrier.getElementsByTagName("avg_problem_time").item(0).getTextContent() );
            PROPORTION_BARRIER_PROBLEM  = Integer.parseInt( barrier.getElementsByTagName("proportion_problem").item(0).getTextContent() );

            // CARS
			NUM_CARS 			= Integer.parseInt( cars.getElementsByTagName("num_cars").item(0).getTextContent() );
			PROPORTION_STUDENTS = Integer.parseInt( cars.getElementsByTagName("proportion_student").item(0).getTextContent() );

			// LECTURER CARS
			AVG_LECTURER_CAR_WIDTH 	= Integer.parseInt( lecturer_cars.getElementsByTagName("avg_lecturer_car_width").item(0).getTextContent() );
			AVG_LECTURER_DEXTERITY 	= Integer.parseInt( lecturer_cars.getElementsByTagName("avg_lecturer_dexterity").item(0).getTextContent() );
			AVG_LECTURER_STAY_TIME 	= timeToSimulationTime( lecturer_cars.getElementsByTagName("avg_lecturer_stay_time").item(0).getTextContent() );

			// STUDENT CARS
			AVG_STUDENT_CAR_WIDTH 	= Integer.parseInt( student_cars.getElementsByTagName("avg_student_car_width").item(0).getTextContent() );
			AVG_STUDENT_DEXTERITY 	= Integer.parseInt( student_cars.getElementsByTagName("avg_student_dexterity").item(0).getTextContent() );
			AVG_STUDENT_STAY_TIME 	= timeToSimulationTime( student_cars.getElementsByTagName("avg_student_stay_time").item(0).getTextContent() );

		}
		catch(ParserConfigurationException e) {}
		catch(SAXException e) {}
		catch(IOException e) {}
	}
}
