package ch.shanehofstetter.pvdimension.irradiationdata;

import ch.shanehofstetter.pvdimension.location.Coordinates;
import ch.shanehofstetter.pvdimension.net.HttpPostRequest;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public class IrradiationDataRequest {

    private static final String URL = "http://re.jrc.ec.europa.eu/pvgis/apps4/DRcalc.php";

    /**
     * @param month           Month for which the data should be requested, makes a big difference
     * @param solarPanelField Angle and Azimut are needed to get the data
     * @param coordinates     Coordinates of the PV-Generator
     * @return The Request result
     * @throws Exception When there's no internet connection or some other problems
     */
    public static String makeRequest(Month month, SolarPanelField solarPanelField, Coordinates coordinates) throws Exception {
        return makeRequest(month, solarPanelField.getVerticalAngle(), solarPanelField.getAzimut(), coordinates.getLatitude(), coordinates.getLongitude());
    }

    /**
     * @param month       Month for which the data should be requested, makes a big difference
     * @param panelAngle  The panelAngle in degrees, which the solarpanels are mounted relative to the ground (horizontal)
     *                    must be between 0 and 90
     * @param panelAzimut east, west, north, south orientation of solarpanels, value between -180 and 180 (0 is south)
     * @param coordinates Coordinates of the PV-Generator
     * @return The Request result
     * @throws Exception
     */
    public static String makeRequest(Month month, double panelAngle, double panelAzimut, Coordinates coordinates) throws Exception {
        return makeRequest(month, panelAngle, panelAzimut, coordinates.getLatitude(), coordinates.getLongitude());
    }

    /**
     * @param month       Month for which the data should be requested, makes a big difference
     * @param panelAngle  The panelAngle in degrees, which the solarpanels are mounted relative to the ground (horizontal)
     *                    must be between 0 and 90
     * @param panelAzimut east, west, north, south orientation of solarpanels, value between -180 and 180 (0 is south)
     * @param latitude    Coordinates of the PV-Generator
     * @param longitude   Coordinates of the PV-Generator
     * @return The Request result
     * @throws Exception
     */
    public static String makeRequest(Month month, double panelAngle, double panelAzimut, double latitude, double longitude) throws Exception {
        String urlParameters = "MAX_FILE_SIZE=10000&" +
                "dr_database=PVGIS-CMSAF" +
                "&DRangle=" + panelAngle +
                "&DRaspectangle=" + panelAzimut +
                "&global=true" +
                "&clearsky=true" +
                "&dr_dni=false" +
                "&glob_twoaxis=false" +
                "&clearsky_twoaxis=false" +
                "&showtemperatures=false" +
                "&outputformatchoice=csv" +
                "&DRlatitude=" + latitude +
                "&DRlongitude=" + longitude +
                "&DRmonth=" + month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) +
                "&regionname=europe" +
                "&language=en_en";
        return HttpPostRequest.sendPOSTRequest(URL, urlParameters);
    }

}
