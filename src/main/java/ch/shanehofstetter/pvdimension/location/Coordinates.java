package ch.shanehofstetter.pvdimension.location;

import java.io.Serializable;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class Coordinates implements Serializable {
    private double longitude;
    private double latitude;

    public Coordinates() {
    }

    /**
     * @param latitude  Latitude [°]
     * @param longitude Longitude [°]
     */
    public Coordinates(double latitude, double longitude) {
        setLatitude(latitude);
        setLongitude(longitude);
    }

    /**
     * @return Longitude [°]
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude Longitude [°]
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return Latitude [°]
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude Latitude [°]
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return "lat: " + latitude + " lon: " + longitude;
    }


}
