package Car;

public abstract class Car {

    protected int arrive_time_secs;
    protected int leave_time_secs;

    protected int width;
    protected int dexterity;

    protected boolean is_ready_to_park;

    Car(int arrive_time_secs0, int leave_time_secs0, int width0, int dexterity0) {

        arrive_time_secs    = arrive_time_secs0;
        leave_time_secs     = leave_time_secs0;
        width               = width0;
        dexterity           = dexterity0;

        is_ready_to_park = false;
    }

    public int getArriveTime() {
        return arrive_time_secs;
    }

    public int getLeaveTime() {
        return leave_time_secs;
    }

    public int getWidth() {
        return width;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setReadyToPark() {
        is_ready_to_park = true;
    }

    public boolean isReadyToPark() {

        return is_ready_to_park;
    }

    public void park() {

        // Iterate through the Spaces in the Carpark
        // When a free space is found, the car won't necessarily park there:
        //      -   The chances of parking in a particular empty space
        //          is dependant on car_width, width of neighbour cars,
        //          dexterity, & probability

        // This method should prob be in Carpark class.
    }

    public void leaveCarpark() {

        // Go straight to exit queue
    }
}
