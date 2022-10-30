package ch.shanehofstetter.pvdimension.simulation.dataholder;

import ch.shanehofstetter.pvdimension.util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder
 */
public class PVPowerTime implements Serializable {

    static final Logger logger = LogManager.getLogger();
    private double power;
    private Date time;

    public PVPowerTime() {
    }

    public PVPowerTime(double power, Date time) {
        this.power = power;
        this.time = time;
    }

    public PVPowerTime(double power, String time) {
        this.power = power;
        setTimeString(time);
    }

    /**
     * make a new powerTime object with values of an existing one
     * in other words: copy an existing powerTime
     *
     * @param powerTime powerTime to copy
     */
    public PVPowerTime(PVPowerTime powerTime) {
        this.power = powerTime.getPower();
        this.time = powerTime.getTime();
    }

    public static ArrayList<PVPowerTime> cloneList(ArrayList<PVPowerTime> listToCopy) {
        ArrayList<PVPowerTime> clonedList = new ArrayList<>(listToCopy.size());
        listToCopy.stream().forEach(p -> clonedList.add(new PVPowerTime(p)));
        return clonedList;
    }

    /**
     * @return Power [W]
     */
    public double getPower() {
        return power;
    }

    /**
     * @param power Power[W]
     */
    public void setPower(double power) {
        this.power = power;
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

    @Override
    public String toString() {
        return "PVPowerTime{" +
                "power=" + power +
                ", time=" + DateTimeUtil.formatTime(time.getTime()) +
                '}';
    }
}
