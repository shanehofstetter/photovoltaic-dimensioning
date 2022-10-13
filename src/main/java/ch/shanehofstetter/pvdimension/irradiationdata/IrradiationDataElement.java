package ch.shanehofstetter.pvdimension.irradiationdata;



import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

public class IrradiationDataElement {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("");
    private Date time;
    private double globalIrradiance;
    private double diffuseIrradiance;
    private double clearSkyIrradiance;

    public void setTimeString(String timeString) {
        java.text.DateFormat df = new java.text.SimpleDateFormat("HH:mm");
        try {
            time = df.parse(timeString);
        } catch (ParseException e) {
            logger.error("Error parsing timeString");
        }
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * @return G: Global irradiance on a fixed plane (W/m2)
     */
    public double getGlobalIrradiance() {
        return globalIrradiance;
    }

    /**
     * @param globalIrradiance G: Global irradiance on a fixed plane (W/m2)
     */
    public void setGlobalIrradiance(double globalIrradiance) {
        this.globalIrradiance = globalIrradiance;
    }

    /**
     * @return Gd: Diffuse irradiance on a fixed plane (W/m2)
     */
    public double getDiffuseIrradiance() {
        return diffuseIrradiance;
    }

    /**
     * @param diffuseIrradiance Gd: Diffuse irradiance on a fixed plane (W/m2)
     */
    public void setDiffuseIrradiance(double diffuseIrradiance) {
        this.diffuseIrradiance = diffuseIrradiance;
    }

    /**
     * @return Gc: Global clear-sky irradiance on a fixed plane (W/m2)
     */
    public double getClearSkyIrradiance() {
        return clearSkyIrradiance;
    }

    /**
     * @param clearSkyIrradiance Gc: Global clear-sky irradiance on a fixed plane (W/m2)
     */
    public void setClearSkyIrradiance(double clearSkyIrradiance) {
        this.clearSkyIrradiance = clearSkyIrradiance;
    }
}
