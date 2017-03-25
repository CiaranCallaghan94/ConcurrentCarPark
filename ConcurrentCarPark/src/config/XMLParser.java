package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XMLParser {

    private static final Logger LOGGER = LogManager.getLogger( "XMLParser" );

    public static String input_path	 = "./ConcurrentCarPark/src/config/input.xml";

    // TIMES
	public static String OPEN_TIME              = "08:00";
    public static String CLOSE_TIME             = "19:00";
    public static String ARRIVAL_RUSH_HOUR      = "09:00";
	public static String STD_DEVIATION          = "00:30";
	public static int    SIMULATION_SPEED       = 10000;

	// CARPARK
    public static int    CARPARK_CAPACITY       = 1000;
    public static int    NUM_ENTRANCES          = 3;
    public static int    NUM_EXITS              = 3;

    // CARS
	public static int	 NUM_CARS              	= 2500;
	public static float  PROPORTION_STUDENTS	= 0.9f;

	// LECTURER CARS
    public static int 	 AVG_LECTURER_CAR_WIDTH  = 200;
    public static int 	 AVG_LECTURER_DEXTERITY  = 60;
	public static String AVG_LECTURER_STAY_TIME	 = "07:00";

    // STUDENT CARS
	public static int 	 AVG_STUDENT_CAR_WIDTH   = 170;
	public static int 	 AVG_STUDENT_DEXTERITY   = 40;
	public static String AVG_STUDENT_STAY_TIME	 = "03:00";

	public static void readInput() {

		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(input_path));

			// normalize text representation
			document.getDocumentElement().normalize ();

			Element times 			= (Element) document.getElementsByTagName("times").item(0);
			Element carpark 		= (Element) document.getElementsByTagName("carpark").item(0);
			Element cars 			= (Element) document.getElementsByTagName("cars").item(0);
			Element lecturer_cars 	= (Element) document.getElementsByTagName("lecturer_cars").item(0);
			Element student_cars 	= (Element) document.getElementsByTagName("student_cars").item(0);

			// TIMES
			OPEN_TIME 			= times.getElementsByTagName("open_time").item(0).getTextContent();
			CLOSE_TIME 			= times.getElementsByTagName("close_time").item(0).getTextContent();
			ARRIVAL_RUSH_HOUR 	= times.getElementsByTagName("arrival_rush_hour").item(0).getTextContent();
			STD_DEVIATION 		= times.getElementsByTagName("std_deviation").item(0).getTextContent();
			SIMULATION_SPEED 	= Integer.parseInt(times.getElementsByTagName("simulation_speed").item(0).getTextContent());

			// CARPARK
			CARPARK_CAPACITY 	= Integer.parseInt(carpark.getElementsByTagName("carpark_capacity").item(0).getTextContent());
			NUM_ENTRANCES 		= Integer.parseInt(carpark.getElementsByTagName("num_entrances").item(0).getTextContent());
			NUM_EXITS 			= Integer.parseInt(carpark.getElementsByTagName("num_exits").item(0).getTextContent());

			// CARS
			NUM_CARS 			= Integer.parseInt(cars.getElementsByTagName("num_cars").item(0).getTextContent());
			PROPORTION_STUDENTS = Integer.parseInt(cars.getElementsByTagName("proportion_student").item(0).getTextContent());

			// LECTURER CARS
			AVG_LECTURER_CAR_WIDTH 	= Integer.parseInt(lecturer_cars.getElementsByTagName("avg_lecturer_car_width").item(0).getTextContent());
			AVG_LECTURER_DEXTERITY 	= Integer.parseInt(lecturer_cars.getElementsByTagName("avg_lecturer_dexterity").item(0).getTextContent());
			AVG_LECTURER_STAY_TIME 	= lecturer_cars.getElementsByTagName("avg_lecturer_stay_time").item(0).getTextContent();

			// STUDENT CARS
			AVG_STUDENT_CAR_WIDTH 	= Integer.parseInt(student_cars.getElementsByTagName("avg_student_car_width").item(0).getTextContent());
			AVG_STUDENT_DEXTERITY 	= Integer.parseInt(student_cars.getElementsByTagName("avg_student_dexterity").item(0).getTextContent());
			AVG_STUDENT_STAY_TIME 	= student_cars.getElementsByTagName("avg_student_stay_time").item(0).getTextContent();

		}
		catch(ParserConfigurationException e) {}
		catch(SAXException e) {}
		catch(IOException e) {}

	}
}
