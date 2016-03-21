package example.com.weatherapp;

/**
 * Created by Dnyanshree on 3/19/2016.
 */
public class City {
    private long citykey;
    private String cityname, state;

    public City(String cityname, String state) {
        this.cityname = cityname;
        this.state = state;
    }

    public City() {
    }

    public long getCitykey() {
        return citykey;
    }

    public void setCitykey(long citykey) {
        this.citykey = citykey;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "City{" +
                "citykey=" + citykey +
                ", cityname='" + cityname + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
