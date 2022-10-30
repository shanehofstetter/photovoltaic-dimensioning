package ch.shanehofstetter.pvdimension.irradiationdata;

import ch.shanehofstetter.pvdimension.util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * this class computes the retrieved radiation data from the webservice which is
 * in 15min frequency, we adapt it for other time units
 * <p>
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.irradiationdata
 */
public class IrradiationData {

    static final Logger logger = LogManager.getLogger();
    private ArrayList<IrradiationDataElement> irradiationDataElements;

    public IrradiationData() {
    }

    public IrradiationData(ArrayList<IrradiationDataElement> irradiationDataElements) {
        this.irradiationDataElements = irradiationDataElements;
    }

    public ArrayList<IrradiationDataElement> getIrradiationDataElements() {
        return irradiationDataElements;
    }

    public void setIrradiationDataElements(ArrayList<IrradiationDataElement> irradiationDataElements) {
        this.irradiationDataElements = irradiationDataElements;
    }

    public ArrayList<Double> getWeeklyIrradiation() {
        if (irradiationDataElements == null) {
            return null;
        }
        if (irradiationDataElements.size() == 0) {
            return null;
        }
        return calculateWeeklyData();
    }

    /**
     * Get the real radiation (the radiation directly on the solar-panel, with which the produced power gets calculated)
     *
     * @param irradiationDataElements irradiation data elements
     * @param time                    time
     * @return Real Radiation [Wh] , returns 0 if irradiationDataElements == null
     */
    public double getRealRadiationWhForTimeOfDay(ArrayList<IrradiationDataElement> irradiationDataElements, long time) {
        this.irradiationDataElements = irradiationDataElements;
        return getRealRadiationWhForTimeOfDay(time);
    }

    /**
     * Get the real radiation (the radiation directly on the solar-panel, with which the produced power gets calculated)
     *
     * @return Real Radiation [Wh], returns 0 if irradiationDataElements == null
     */
    public double getRealRadiationWhForTimeOfDay(long time) {
        if (irradiationDataElements == null) {
            return 0;
        }

        // Elements have a time difference of 15 minutes and are in Watts/m2, we want to return a value [Wh/m2] so we
        // need to get the value which is closest to the requested time and the 4 values below this closest-index
        // and build the average of them all.
        // then we have covered an hour. -> Wh/m2
        logger.debug("searching for: " + time + " : " + DateTimeUtil.formatTime(time));
        long timeDifference = 0;
        int closestIndex = 0;
        for (int i = 0; i < irradiationDataElements.size(); i++) {
            if (i == 0) {
                timeDifference = time - irradiationDataElements.get(i).getTime().getTime();
                timeDifference = Math.abs(timeDifference);
            } else {
                long diff = time - irradiationDataElements.get(i).getTime().getTime();
                diff = Math.abs(diff);
                if (diff < timeDifference) {
                    timeDifference = diff;
                    closestIndex = i;
                }
            }
        }

        logger.debug("found: " + irradiationDataElements.get(closestIndex).getTime().getTime() + " : " + DateTimeUtil.formatTime(irradiationDataElements.get(closestIndex).getTime().getTime()));

        double gSum = irradiationDataElements.get(closestIndex).getGlobalIrradiance();
        double gcSum = irradiationDataElements.get(closestIndex).getClearSkyIrradiance();
        for (int i = 1; i < 5; i++) {
            if (closestIndex >= i) {
                gSum += irradiationDataElements.get(closestIndex - i).getGlobalIrradiance();
                gcSum += irradiationDataElements.get(closestIndex - i).getClearSkyIrradiance();
            } else break;
        }
        //always divide by 5, so the missing values are assumed as 0
        return ((gSum / 5) + (gcSum / 5)) / 2;
    }

    /**
     * Calculates the total radiance for each day in a week
     * <p>
     * Details: Calculate arithmetic average of global irradiance and clear sky irradiance
     * given values are for every 15min and in Watts
     *
     * @return ArrayList of radiance-values covering a week (7-days)
     * [Wh/m2]
     */
    private ArrayList<Double> calculateWeeklyData() {
        double hours = getTimeSpan();
        double globalIrradianceTotal = irradiationDataElements.stream()
                .mapToDouble(IrradiationDataElement::getGlobalIrradiance)
                .sum();
        //now build the daily average [W/m2]
        double radianceAverage = globalIrradianceTotal / irradiationDataElements.size();
        //now get the daily power [Wh/m2]
        double radiancePower = radianceAverage * hours;

        ArrayList<Double> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weekDays.add(radiancePower);
        }
        return weekDays;
    }

    /**
     * @return time span [hrs] between first and last value
     */
    private double getTimeSpan() {
        long first = irradiationDataElements.get(0).getTime().getTime();
        long last = irradiationDataElements.get(irradiationDataElements.size() - 1).getTime().getTime();
        return DateTimeUtil.getHoursFromTime(last - first);
    }

}
