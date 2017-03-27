package Car;

import Carpark.Carpark;
import Gateway.Gateway;

/**
 * The StudentCar class extends the car class.
 */

public class StudentCar extends Car {

    public StudentCar(Gateway gateway, Carpark carpark) {

        super(gateway, carpark);
    }


    public boolean isStudent() {
        return true;
    }
}
