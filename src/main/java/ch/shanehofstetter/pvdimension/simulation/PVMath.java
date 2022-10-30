package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.util.Utilities;

public class PVMath {

    /**
     * Ideal Production = sun-radiance [kWh/m2] * Panel-Efficiency * Total Solar-Panel Size
     *
     * @param radiance        sun-radiance [kWh/m2]
     * @param panelEfficiency Panel-Efficiency
     * @param panelFieldSize  Total Solar-Panel Size
     * @return Ideal Production
     */
    public static double calculateIdealProduction(double radiance, double panelEfficiency, double panelFieldSize) {
        if (panelEfficiency < 0 || panelEfficiency > 1) {
            panelEfficiency = 1;
        }
        return radiance * panelEfficiency * panelFieldSize;
    }

    /**
     * Real Production = ideal production * Performance Ratio * Shadow Factor
     *
     * @param idealProduction  ideal production
     * @param performanceRatio Performance Ratio
     * @param shadowFactor     Shadow Factor
     * @return Real Production
     */
    public static double calculateRealProduction(double idealProduction, double performanceRatio, double shadowFactor) {
        shadowFactor = Utilities.limitValue(shadowFactor, 0, 1);
        performanceRatio = Utilities.limitValue(performanceRatio, 0, 1);
        return idealProduction * performanceRatio * shadowFactor;
    }

    /**
     * Production Surplus = Real Production - Power Consumption (signed)
     *
     * @param realProduction   Real Production
     * @param powerConsumption Power Consumption (signed)
     * @return Production Surplus
     */
    public static double calculateProductionSurplus(double realProduction, double powerConsumption) {
        return realProduction - powerConsumption;
    }
}
