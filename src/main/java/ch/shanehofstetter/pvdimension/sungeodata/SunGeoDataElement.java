package ch.shanehofstetter.pvdimension.sungeodata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.Date;

/**
 * Sun Geographic Data Element stores information about the sun at a time of the day
 * important information is: the time and the suns azimut, altitude in degrees at this time
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.sungeodata
 */
public class SunGeoDataElement {
    static final Logger logger = LogManager.getLogger();
    private Date time;
    private double azimut;
    private double altitude;

    public SunGeoDataElement() {
    }

    public SunGeoDataElement(double azimut, double altitude) {
        this.azimut = azimut;
        this.altitude = altitude;
    }

    public double getAzimut() {
        return azimut;
    }

    public void setAzimut(double azimut) {
        this.azimut = azimut;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setTimeString(String timeString) {
        java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm");
        try {
            time = df.parse(timeString);
        } catch (ParseException e) {
            logger.error("Error parsing timeString");
        }
    }
}
