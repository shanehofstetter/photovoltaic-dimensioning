package ch.shanehofstetter.pvdimension.pvgenerator.solarpanel;


import java.io.Serializable;
import java.math.BigDecimal;

public class SolarPanelField implements Serializable {

    private int amount;
    private double verticalAngle;
    private double azimut;
    private SolarPanel solarPanel;

    public SolarPanelField() {
        this(0, 0);
    }

    public SolarPanelField(double verticalAngle, double azimut) {
        this(0, verticalAngle, azimut, new SolarPanel());
    }

    public SolarPanelField(int amount, double verticalAngle, double azimut, SolarPanel solarPanel) {
        this.amount = amount;
        this.verticalAngle = verticalAngle;
        this.azimut = azimut;
        this.solarPanel = solarPanel;
    }

    /**
     * amount of panels
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount amount of solarpanels [larger than 0]
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * the vertical angle which the solarpanels stand, relative to horizontal ground
     * between 0 and 90°
     */
    public double getVerticalAngle() {
        return verticalAngle;
    }

    /**
     * @param verticalAngle between 0 and 90°
     */
    public void setVerticalAngle(double verticalAngle) {
        if (verticalAngle >= 0 && verticalAngle <= 90) {
            this.verticalAngle = verticalAngle;
        }
    }

    /**
     * @return Azimuth angle from -180 to 180. East=-90, South=0
     */
    public double getAzimut() {
        return azimut;
    }

    /**
     * @param azimut angle from -180 to 180
     */
    public void setAzimut(double azimut) {
        if (azimut >= -180 && azimut <= 180) {
            this.azimut = azimut;
        }
    }

    /**
     * The solar-panel details
     * A PV-field consists of equal solar-panels
     */
    public SolarPanel getSolarPanel() {
        return solarPanel;
    }

    public void setSolarPanel(SolarPanel solarPanel) {
        this.solarPanel = solarPanel;
    }

    /**
     * Amount of SolarPanels * Solarpanel-Power
     *
     * @return installed Power [Watts]
     */
    public double getInstalledPower() {
        if (amount > 0 && solarPanel != null) {
            return amount * solarPanel.getPower();
        }
        return 0;
    }

    /**
     * Amount of SolarPanels * Solarpanel-Size
     *
     * @return total Field-Size [m2]
     */
    public double getTotalSize() {
        if (amount > 0 && solarPanel != null) {
            return amount * solarPanel.getSize();
        }
        return 0;
    }

    public BigDecimal getTotalCost() {
        return getSolarPanel().getCost().multiply(BigDecimal.valueOf(getAmount()));
    }

    @Override
    public String toString() {
        return "SolarPanelField{" +
                "amount=" + amount +
                ", verticalAngle=" + verticalAngle +
                ", azimut=" + azimut +
                ", solarPanel=" + solarPanel +
                '}';
    }
}
