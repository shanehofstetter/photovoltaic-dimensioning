package ch.shanehofstetter.pvdimension.pvgenerator;

import ch.shanehofstetter.pvdimension.economy.PowerPrice;
import ch.shanehofstetter.pvdimension.location.Address;
import ch.shanehofstetter.pvdimension.location.Coordinates;
import ch.shanehofstetter.pvdimension.pvgenerator.inverter.Inverter;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import ch.shanehofstetter.pvdimension.simulation.PVSimulationParameters;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationData;
import ch.shanehofstetter.pvdimension.util.Utilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * PVGenerator containing the complete PV-Installation components
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class PVGenerator implements Serializable {

    static final Logger logger = LogManager.getLogger();

    private static double defaultPerformanceRatio = 0.85;
    private static double defaultShadowFactor = 0.97;

    private SolarPanelField solarPanelField;
    private Battery battery;
    private double performanceRatio;
    private double shadowFactor;
    private Coordinates coordinates;
    private Address address;
    private PowerPrice powerPrice;
    private Inverter inverter;
    private PVSimulationParameters simulationParameters;
    private PVSimulationData simulationData;
    private String currencyCode;

    private BigDecimal planningAndInstallationCosts = new BigDecimal(4000.0);

    public PVGenerator() {
    }

    public PVGenerator(SolarPanelField solarPanelField, Battery battery) {
        this(solarPanelField, battery, new Coordinates());
    }

    public PVGenerator(SolarPanelField solarPanelField, Battery battery, Coordinates coordinates) {
        this.performanceRatio = defaultPerformanceRatio;
        this.shadowFactor = defaultShadowFactor;
        this.solarPanelField = solarPanelField;
        this.battery = battery;
        this.coordinates = new Coordinates();
        this.powerPrice = new PowerPrice();
        this.inverter = new Inverter();
        this.simulationParameters = new PVSimulationParameters();
        this.simulationData = new PVSimulationData();
    }

    /**
     * Overall Coefficient of the PV-Generator
     * Defaults to 0.85
     */
    public double getPerformanceRatio() {
        return performanceRatio;
    }

    /**
     * Default value = 0.85
     *
     * @param performanceRatio between 0 and 1
     */
    public void setPerformanceRatio(double performanceRatio) {
        this.performanceRatio = Utilities.limitValue(performanceRatio, 0, 1);
        logger.debug("Performance Ratio set to: " + this.performanceRatio);
    }

    /**
     * @return SolarPanelField object containing detailed information about pv-installation
     */
    public SolarPanelField getSolarPanelField() {
        return solarPanelField;
    }

    /**
     * @param solarPanelField solar-panel-field with parameters describing the pv-modules
     */
    public void setSolarPanelField(SolarPanelField solarPanelField) {
        this.solarPanelField = solarPanelField;
    }

    /**
     * @return The Battery which stores unused produced energy
     */
    public Battery getBattery() {
        return battery;
    }

    /**
     * Add a Power-Storage to the PV-Generator which stores unused produced energy
     *
     * @param battery Battery
     */
    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    /**
     * Default value = 0.97<br>
     * This means shadow lowers power-production by 3%
     *
     * @return Shadow Factor
     */
    public double getShadowFactor() {
        return shadowFactor;
    }

    /**
     * Default value = 0.97<br>
     * This means shadow lowers power-production by 3%
     *
     * @param shadowFactor between 0 and 1, 1.0 means 0% shadow
     */
    public void setShadowFactor(double shadowFactor) {
        this.shadowFactor = Utilities.limitValue(shadowFactor, 0, 1);
        logger.debug("shadowFactor set to: " + this.shadowFactor);
    }

    /**
     * @return coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates coordinates
     */
    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return powerprice
     */
    public PowerPrice getPowerPrice() {
        return powerPrice;
    }

    /**
     * @param powerPrice powerprice
     */
    public void setPowerPrice(PowerPrice powerPrice) {
        this.powerPrice = powerPrice;
    }

    /**
     * @return inverter
     */
    public Inverter getInverter() {
        return inverter;
    }

    /**
     * @param inverter inverter
     */
    public void setInverter(Inverter inverter) {
        this.inverter = inverter;
    }

    /**
     * calculate the total generator costs
     *
     * @return total costs
     */
    public BigDecimal getTotalCosts() {
        BigDecimal totalCosts = getBattery().getCost();
        totalCosts = totalCosts.add(getSolarPanelField().getTotalCost());
        totalCosts = totalCosts.add(getInverter().getCost());
        totalCosts = totalCosts.add(getPlanningAndInstallationCosts());
        return totalCosts;
    }

    /**
     * @return Planning And Installation Costs
     */
    public BigDecimal getPlanningAndInstallationCosts() {
        return planningAndInstallationCosts;
    }

    /**
     * @param planningAndInstallationCosts Planning And Installation Costs
     */
    public void setPlanningAndInstallationCosts(BigDecimal planningAndInstallationCosts) {
        this.planningAndInstallationCosts = planningAndInstallationCosts;
    }

    /**
     * @return simulation parameters
     */
    public PVSimulationParameters getSimulationParameters() {
        return simulationParameters;
    }

    /**
     * @param simulationParameters simulation parameters
     */
    public void setSimulationParameters(PVSimulationParameters simulationParameters) {
        this.simulationParameters = simulationParameters;
    }

    /**
     * @return simulation data
     */
    public PVSimulationData getSimulationData() {
        return simulationData;
    }

    /**
     * @param simulationData simulation data
     */
    public void setSimulationData(PVSimulationData simulationData) {
        this.simulationData = simulationData;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * checks if one of the factors relevant for producing power is zero,
     * in this case the generator cannot produce any energy
     *
     * @return true if can produce power, false if not
     */
    public boolean canProducePower() {
        boolean zeroEfficiency = getPerformanceRatio() == 0;
        zeroEfficiency |= getShadowFactor() == 0;
        zeroEfficiency |= getSolarPanelField().getSolarPanel().getEfficiency() == 0;
        zeroEfficiency |= getSolarPanelField().getAmount() == 0;
        return !zeroEfficiency;
    }
}
