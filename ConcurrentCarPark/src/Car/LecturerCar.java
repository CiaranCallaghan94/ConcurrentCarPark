package Car;

import Gateway.Gateway;
import Carpark.Carpark;

/**
 * The LecturerCar class extends the car class.
 */
public class LecturerCar extends Car {

    public LecturerCar(Gateway gateway, Carpark carpark) {

        super(gateway, carpark);
    }

    public boolean isStudent() {
        return false;
    }
}
