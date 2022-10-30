package ch.shanehofstetter.pvdimension.simulation.dataholder;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.util.Date;

/**
 * All members in Wh
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder
 */
public class PVTimeUnit extends PVSimulationElement {
    static final Logger logger = LogManager.getLogger();

    private Date time;

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
