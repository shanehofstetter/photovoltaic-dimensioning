package ch.shanehofstetter.pvdimension.gui.components.pvcharts;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 */
public enum PVInputDataType {
    /**
     * Power Consumption
     */
    POWER_CONSUMPTION,
    /**
     * Sun Power
     */
    SUN_POWER;

    @Override
    public String toString() {
        switch (this) {
            case SUN_POWER:
                return "Sonneneinstrahlung";
            case POWER_CONSUMPTION:
                return "Stromverbrauch";
            default:
                return "PVInputDataType unknown";
        }
    }
}
