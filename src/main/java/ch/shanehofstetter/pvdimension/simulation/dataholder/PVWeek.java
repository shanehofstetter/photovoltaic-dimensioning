package ch.shanehofstetter.pvdimension.simulation.dataholder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class PVWeek extends PVSimulationElement implements Serializable {
    static final Logger logger = LogManager.getLogger();
    protected List<PVDay> pvDays;

    public PVWeek() {
        pvDays = Arrays.asList(new PVDay[7]);
    }

    public PVWeek(List<PVDay> pvDays) {
        setPvDays(pvDays);
    }

    /**
     * @return 7 PVDays
     */
    public List<PVDay> getPVDays() {
        return pvDays;
    }

    /**
     * Set the PVDays array
     *
     * @param pvDays must be of length 7
     */
    public void setPvDays(List<PVDay> pvDays) {
        if (pvDays.size() != 7){
            throw new WrongSizeException("PVDays needs to be of size 7");
        }
        this.pvDays = pvDays;
    }

    @Override
    public ArrayList<String> getCSVHeaders() {
        ArrayList<String> headers = super.getCSVHeaders();
        headers.add(0, "Wochentag");
        return headers;
    }

    @Override
    public ArrayList<ArrayList<String>> getCSVData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < pvDays.size(); i++) {
            ArrayList<String> content = new ArrayList<>();
            content.add(DayOfWeek.of(i + 1).getDisplayName(TextStyle.FULL, Locale.GERMAN));
            content.addAll(pvDays.get(i).getCSVData().get(0));
            data.add(content);
        }
        return data;
    }

    public class WrongSizeException extends RuntimeException{
        public WrongSizeException(String message) {
            super(message);
        }
    }
}
