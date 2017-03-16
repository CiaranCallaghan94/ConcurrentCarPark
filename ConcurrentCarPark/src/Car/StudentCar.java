package Car;

public class StudentCar extends Car {

    public StudentCar(int arrive_time_secs0, int leave_time_secs0, int width0, int dexterity0) {

        super(arrive_time_secs0, leave_time_secs0, width0, dexterity0);
    }

    // Methods specific to Student here

    public void showArriveTime() {
        System.out.print(getArriveTime() + " " + arrive_time_secs);
    }

}
