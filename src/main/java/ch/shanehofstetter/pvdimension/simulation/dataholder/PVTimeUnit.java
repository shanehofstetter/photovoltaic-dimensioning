package ch.shanehofstetter.pvdimension.simulation.dataholder;




import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * All members in Wh
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder
 */
public class PVTimeUnit extends PVSimulationElement {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("");

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
