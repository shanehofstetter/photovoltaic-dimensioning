package ch.shanehofstetter.pvdimension.irradiationdata;

import ch.shanehofstetter.pvdimension.location.Coordinates;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import ch.shanehofstetter.pvdimension.util.DateTimeUtil;
import org.slf4j.LoggerFactory;


import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.irradiationdata
 */
public class IrradiationDataFetcher {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("");
    private ArrayList<IrradiationDataElements> cachedIrradiationDataElementsList = new ArrayList<>();
    private Coordinates cachedCoordinates = new Coordinates();
    private SolarPanelField cachedSolarPanelField = new SolarPanelField();

    /**
     * Fetches the real Irradiance data on Solar-Panels for given panelAngle, panelAzimut and Month
     * depending on the geographical position
     * <p>
     * The month is very important because the radiance varies significantly through the year
     *
     * @return All IrradiationDataElements for an average day in the specified month at given geolocation
     **/
    public ArrayList<IrradiationDataElement> getIrradiationDataElements(Month month, SolarPanelField solarPanelField, Coordinates coordinates) throws IrradiationDataFetchException {
        boolean sameCoordinates = compareCoordinates(cachedCoordinates, coordinates);
        boolean sameSolarPanelField = compareSolarPanelFields(cachedSolarPanelField, solarPanelField);

        if (!sameCoordinates || !sameSolarPanelField) {
            cachedIrradiationDataElementsList.clear();
        }
        if (sameCoordinates && sameSolarPanelField && cachedDataContains(month)) {
            logger.debug("using cached data");
            return getCachedIrradiationDataElements(month);
        }
        try {
            logger.debug("making new request to webservice..");
            String csv = IrradiationDataRequest.makeRequest(month, solarPanelField, coordinates);
            ArrayList<IrradiationDataElement> irradiationDataElements = IrradiationDataParser.parseCSVData(csv);
            this.cachedCoordinates.setLatitude(coordinates.getLatitude());
            this.cachedCoordinates.setLongitude(coordinates.getLongitude());
            this.cachedSolarPanelField.setVerticalAngle(solarPanelField.getVerticalAngle());
            this.cachedSolarPanelField.setAzimut(solarPanelField.getAzimut());

            IrradiationDataElements elementsOfMonth = new IrradiationDataElements(month);
            elementsOfMonth.setIrradiationDataElements(irradiationDataElements);
            this.cachedIrradiationDataElementsList.add(elementsOfMonth);

            extendIrradiationDataElements(irradiationDataElements);
            if (irradiationDataElements.size() == 0) {
                throw new IrradiationDataFetchException("Error loading Irradiation-Data, no data retrieved");
            }
            return irradiationDataElements;
        } catch (Exception e) {
            throw new IrradiationDataFetchException(e.getMessage());
        }
    }

    private boolean cachedDataContains(Month month) {
        for (IrradiationDataElements irradiationDataElements : cachedIrradiationDataElementsList) {
            if (irradiationDataElements.getMonth() == month) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<IrradiationDataElement> getCachedIrradiationDataElements(Month month) {
        for (IrradiationDataElements irradiationDataElements : cachedIrradiationDataElementsList) {
            if (irradiationDataElements.getMonth() == month) {
                return irradiationDataElements.getIrradiationDataElements();
            }
        }
        return null;
    }

    /**
     * Add data before earliest (morning) element and after latest (evening) element.
     * the new elements have zero radiance
     * This needs to be done because the retrieved data does not contain the whole day, but only
     * the times where there is some sun-power. When we are looking for the sunpower at a certain time of day we can
     * not know if the sun's down and therefore calculate with wrong values if we take the closest element
     * <p>
     * We add 4 elements before and after (with 15 minutes difference, therefore an hour)
     *
     * @param elements The extended elements
     */
    private void extendIrradiationDataElements(ArrayList<IrradiationDataElement> elements) {
        long earliest = elements.get(0).getTime().getTime();
        logger.debug("earliest: " + DateTimeUtil.formatTime(earliest));
        for (int i = 0; i < 4; i++) {
            IrradiationDataElement element = new IrradiationDataElement();
            element.setTime(new Date(DateTimeUtil.subtractMinutesFromTime(earliest, 15 * (i + 1))));
            elements.add(0, element);
        }
        long latest = elements.get(elements.size() - 1).getTime().getTime();
        logger.debug("latest: " + DateTimeUtil.formatTime(latest));
        for (int i = 0; i < 4; i++) {
            IrradiationDataElement element = new IrradiationDataElement();
            element.setTime(new Date(DateTimeUtil.addMinutesToTime(latest, 15 * (i + 1))));
            elements.add(elements.size() - 1, element);
        }
    }

    private boolean compareSolarPanelFields(SolarPanelField solarPanelField, SolarPanelField otherSolarPanelField) {
        return solarPanelField.getAzimut() == otherSolarPanelField.getAzimut() && solarPanelField.getVerticalAngle() == otherSolarPanelField.getVerticalAngle();
    }

    private boolean compareCoordinates(Coordinates coordinates, Coordinates otherCoordinates) {
        return coordinates.getLatitude() == otherCoordinates.getLatitude() && coordinates.getLongitude() == otherCoordinates.getLongitude();
    }

    public class IrradiationDataFetchException extends Exception {
        public IrradiationDataFetchException(String message) {
            super(message);
        }
    }
}
