package config;

/**
 * ArrivalRushHour is responsible for simply holding
 * the information for a particular rush hour
 */

public class ArrivalRushHour {

    public int TIME;
    public int PROPORTION;
    public int STD_DEVIATION;

    public ArrivalRushHour(int time, int proportion, int std_deviation) {

        TIME = time;
        PROPORTION = proportion;
        STD_DEVIATION = std_deviation;
    }
}
