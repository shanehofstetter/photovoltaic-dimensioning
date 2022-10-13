package ch.shanehofstetter.pvdimension.pvgenerator;

import ch.shanehofstetter.pvdimension.util.Utilities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Battery
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class Battery implements Serializable {
    private double capacityWh;
    private double chargeLevel;
    private double chargeCoefficient = 1.0;
    private double initialChargeLevel = 0.0;
    private String manufacturer;
    private String typeName;
    private String details;
    private BigDecimal cost = BigDecimal.valueOf(2000);

    public Battery() {
    }

    public Battery(double capacityWh) {
        this.capacityWh = capacityWh;
    }

    /**
     * Determines how much energy the battery can store/give
     *
     * @return Batterys capacity in kWh
     */
    public double getCapacityWh() {
        return capacityWh;
    }

    /**
     * @param capacityWh Capacity in kWh (larger than 0)
     */
    public void setCapacityWh(double capacityWh) {
        if (capacityWh < 0) {
            return;
        }
        this.capacityWh = capacityWh;
        setInitialChargeLevelWh(this.initialChargeLevel);
    }

    /**
     * Determines how much energy the battery can store/give
     *
     * @return Batterys capacity in Wh
     */
    public double getCapacitykWh() {
        return capacityWh / 1000;
    }

    /**
     * The Battery's manufacturer, e.g. Tesla
     *
     * @return The Manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return Type or product-name of the battery
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Type or product-name of the battery
     *
     * @param typeName Type
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * @return Some detail-information about the battery
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details detail-information
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return The charge-level (maximum is the capacity of the battery, minimum is 0)
     */
    public double getChargeLevel() {
        return chargeLevel;
    }

    /**
     * Charge the battery with an amount of Wh
     * if the amount is larger than the battery's capacity,
     * the difference gets returned
     * <p>
     * if amount is negative, battery will discharge
     * <p>
     * e.g. battery's capacity is 20'000 Wh, we charge it with 25'000 Wh
     * 5'000 Wh returned because it doesn't fit into the battery
     *
     * @param amount Amount of Wh
     * @return returns the amount which cannot be stored
     */
    public double charge(double amount) {
        if (amount < 0) {
            return discharge(amount);
        }
        chargeLevel += (amount * chargeCoefficient);
        double difference = chargeLevel - capacityWh;
        if (difference > 0) {
            chargeLevel = capacityWh;
            return difference;
        }
        return 0;
    }

    /**
     * discharge the battery with an amount of Wh
     *
     * @param amount Wh the battery needs to give
     * @return the amount which could NOT be uncharged, because there isn't enough energy stored in the battery
     */
    public double discharge(double amount) {
        amount = Math.abs(amount);
        chargeLevel -= amount;
        if (chargeLevel < 0) {
            double difference = Math.abs(chargeLevel);
            chargeLevel = 0;
            return difference;
        }
        return 0;
    }

    public double getChargeCoefficient() {
        return chargeCoefficient;
    }

    /**
     * The Battery's coefficient
     *
     * @param chargeCoefficient Coefficient [0..1]
     */
    public void setChargeCoefficient(double chargeCoefficient) {
        this.chargeCoefficient = chargeCoefficient;
    }

    /**
     * @return Initial Charge Level at beginning of simulation [Wh]
     */
    public double getInitialChargeLevel() {
        return initialChargeLevel;
    }

    /**
     * Must be lower than or equal to capacity
     *
     * @param initialChargeLevel Initial Charge Level at beginning of simulation [Wh]
     */
    public void setInitialChargeLevelWh(double initialChargeLevel) {
        this.initialChargeLevel = Utilities.limitValue(initialChargeLevel, 0, capacityWh);
        initialize();
    }

    /**
     * Must be lower than or equal to capacity
     *
     * @param initialChargeLevelkWh Initial Charge Level at beginning of simulation [kWh]
     */
    public void setInitialChargeLevelkWh(double initialChargeLevelkWh) {
        initialChargeLevel = Utilities.limitValue(initialChargeLevelkWh * 1000, 0, capacityWh);
        initialize();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Initialize the Battery, sets the charge-level to the initial charge level
     * default: 0 Wh
     */
    public void initialize() {
        chargeLevel = this.initialChargeLevel;
    }

    @Override
    public String toString() {
        return "Battery{" +
                "capacityWh=" + capacityWh +
                ", chargeLevel=" + chargeLevel +
                '}';
    }
}
