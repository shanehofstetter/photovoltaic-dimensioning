package ch.shanehofstetter.pvdimension.simulation;

import java.io.Serializable;
import java.time.Month;


public class PVSimulationParameters implements Serializable {
    private Month simulatingMonth;
    private SunDataMode sunDataMode;

    public PVSimulationParameters() {
        this(Month.JANUARY, SunDataMode.LOCAL);
    }

    public PVSimulationParameters(Month simulatingMonth, SunDataMode sunDataMode) {
        this.simulatingMonth = simulatingMonth;
        this.sunDataMode = sunDataMode;
    }

    /**
     * @return month to simulate
     */
    public Month getSimulatingMonth() {
        return simulatingMonth;
    }

    /**
     * @param simulatingMonth month to simulate
     */
    public void setSimulatingMonth(Month simulatingMonth) {
        this.simulatingMonth = simulatingMonth;
    }

    /**
     * @return sundata mode (manually or webservice)
     */
    public SunDataMode getSunDataMode() {
        return sunDataMode;
    }

    /**
     * @param sunDataMode sundata mode (manually or webservice)
     */
    public void setSunDataMode(SunDataMode sunDataMode) {
        this.sunDataMode = sunDataMode;
    }

    @Override
    public String toString() {
        return "PVSimulationParameters{" +
                "simulatingMonth=" + simulatingMonth +
                ", sunDataMode=" + sunDataMode +
                '}';
    }
}
