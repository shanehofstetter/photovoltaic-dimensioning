package ch.shanehofstetter.pvdimension.sungeodata;

import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import ch.shanehofstetter.pvdimension.util.DateTimeUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Month;
import java.util.ArrayList;

/**
 * SunGeoData calculates a radiance factor based on a set of parameters
 * (time of day, month, location, panel-Angle, panel-Azimut)
 *
 * The Radiance factor specifies how the horizontal radiance from the sun (radiance on horizontal plane)
 * gets in- or decreased. the radiance which then hits the generator is calculated with this formula:
 * horizontal radiance * radiance factor = direct generator radiance
 *
 * In order to calculate this factor, some location-specific data is needed.
 * To calculate the factor for a specific time of a day, this class needs the suns angle and azimut at this time
 */
public class SunGeoData {
    static final Logger logger = LogManager.getLogger();
    private ArrayList<SunGeoDataElement> cachedSunGeoData;
    private Month cachedMonth;

    /**
     * Sun-Azimut and Angle are very different each month, so the month is very important
     *
     * @param month           The month for which the factor is calculated
     * @param solarPanelField The Solarpanel-Field for which the factor gets calculated f(angle and azimut)
     * @return Global Radiance Factor
     * @throws Exception
     */
    public double getGlobalRadianceFactorForAverageDay(Month month, SolarPanelField solarPanelField) throws Exception {
        return getGlobalRadianceFactorForAverageDay(month, solarPanelField.getVerticalAngle(), solarPanelField.getAzimut());
    }

    /**
     * Calculates the radiance factor for an average day in specified month
     * The factor gets calculated for every available value (e.g. hourly),
     * then the average of these gets returned
     * <p>
     * Sun-Azimut and Angle are very different each month, so the month is very important
     *
     * @param month       The month for which the factor is calculated
     * @param panelAngle  Mounting Angle of Solar-Panel relative to horizontal ground [0-90°]
     * @param panelAzimut Azimuth angle from -180 to 180. East=-90, South=0
     * @return Average Global Radiance Factor for specified month
     * @throws Exception if local data cannot be loaded
     */
    public double getGlobalRadianceFactorForAverageDay(Month month, double panelAngle, double panelAzimut) throws Exception {
        ArrayList<SunGeoDataElement> sunGeoData = loadSunGeoDataElements(month);
        double factorSum = 0.0;
        sunGeoData.stream().mapToDouble(e -> calculateRadianceFactor(e, panelAngle, panelAzimut)).sum();
        return factorSum / sunGeoData.size();
    }

    /**
     * find the closest match for the time of day and calculate the radiance factor
     *
     * @param month       The month for which the factor is calculated
     * @param time        The time of day (e.g. 8:00) for which the factor is needed
     * @param panelAngle  Mounting Angle of Solar-Panel relative to horizontal ground [0-90°]
     * @param panelAzimut Azimuth angle from -180 to 180. East=-90, South=0
     * @return Radiance Factor for specified month
     * @throws LocalSunDataLoadException in case of FileReadError (e.g. if a data-file is missing or corrupted)
     */
    public double getRadianceFactorForTimeOfDay(Month month, long time, double panelAngle, double panelAzimut) throws LocalSunDataLoadException {
        ArrayList<SunGeoDataElement> sunGeoData;
        try {
            sunGeoData = loadSunGeoDataElements(month);
        } catch (Exception e) {
            throw new LocalSunDataLoadException(e.getMessage());
        }
        long timeDifference = 0;
        int closestIndex = 0;
        for (int i = 0; i < sunGeoData.size(); i++) {
            if (i == 0) {
                timeDifference = time - sunGeoData.get(i).getTime().getTime();
                timeDifference = Math.abs(timeDifference);
            } else {
                long diff = time - sunGeoData.get(i).getTime().getTime();
                diff = Math.abs(diff);
                if (diff < timeDifference) {
                    timeDifference = diff;
                    closestIndex = i;
                }
            }
        }
        double radianceFactor = calculateRadianceFactor(sunGeoData.get(closestIndex), panelAngle, panelAzimut);
        logger.trace("radiance factor for " + DateTimeUtil.formatTime(time) + " is: " + radianceFactor);
        return calculateRadianceFactor(sunGeoData.get(closestIndex), panelAngle, panelAzimut);
    }

    private ArrayList<SunGeoDataElement> loadSunGeoDataElements(Month month) throws Exception {
        ArrayList<SunGeoDataElement> sunGeoData;
        if (cachedMonth == month && cachedSunGeoData != null) {
            logger.debug("returning cached sungeodata");
            sunGeoData = cachedSunGeoData;
        } else {
            SunGeoDataReader dataReader = new SunGeoDataReader();
            sunGeoData = dataReader.readSunGeoData(month);
            cachedSunGeoData = sunGeoData;
            cachedMonth = month;
        }
        return sunGeoData;
    }

    /**
     * Der Einfallswinkel zum Generator ist der Winkel zwischen einem Vektor s in Sonnenrichtung und dem Normalenvektor
     * n der Ebene
     * <p>
     * siehe Dokumentation/abschattungsverluste.pdf ab Seite 98
     *
     * @param sunGeoDataElement SunGeoDataElement
     * @param panelAngle        panelAngle
     * @param panelAzimut       panelAzimut
     * @return generator angle
     */
    private double calculateGeneratorAngle(SunGeoDataElement sunGeoDataElement, double panelAngle, double panelAzimut) {
        return Math.acos(-cosDeg(sunGeoDataElement.getAltitude())
                * sinDeg(panelAngle) * cosDeg(sunGeoDataElement.getAzimut() - panelAzimut)
                + sinDeg(sunGeoDataElement.getAltitude()) * cosDeg(panelAngle)) * 180.0 / Math.PI;
    }

    /**
     * Der Einstrahlungsfaktor ist notwendig, um die Sonneneinstrahlungs-Messwerte, welche auf
     * der horizontalen gemessen wurden, auf die Solar-Panel Fläche umzurechnen.
     *
     * @return Radiance Factor
     */
    private double calculateRadianceFactor(SunGeoDataElement sunGeoDataElement, double panelAngle, double panelAzimut) {
        double generatorAngle = calculateGeneratorAngle(sunGeoDataElement, panelAngle, panelAzimut);
        return calculateRadianceFactor(generatorAngle, sunGeoDataElement.getAltitude());
    }

    /**
     * Der Einstrahlungsfaktor ist notwendig, um die Sonneneinstrahlungs-Messwerte, welche auf
     * der horizontalen gemessen wurden, auf die Solar-Panel Fläche umzurechnen.
     *
     * @param generatorAngle Calculated Generator Angle
     * @param sunAltitude    Sun Altitude 0-90°
     * @return Radiance Factor
     */
    private double calculateRadianceFactor(double generatorAngle, double sunAltitude) {
        if (sunAltitude <= 0) {
            return 0;
        }
        double factor = cosDeg(generatorAngle) / sinDeg(sunAltitude);
        if (factor < 0) {
            return 0;
        }
        return factor;
    }

    /**
     * calculate the sine of a value in degree mode (Math.sin calculates in radians)
     *
     * @param value value
     * @return sine
     */
    private double sinDeg(double value) {
        return Math.sin(value / 180 * Math.PI);
    }

    /**
     * calculate the cosine of a value in degree mode (Math.sin calculates in radians)
     * @param value value
     * @return cosine
     */
    private double cosDeg(double value) {
        return Math.cos(value / 180 * Math.PI);
    }


    public class LocalSunDataLoadException extends Exception {
        public LocalSunDataLoadException(String message) {
            super(message);
        }
    }
}
