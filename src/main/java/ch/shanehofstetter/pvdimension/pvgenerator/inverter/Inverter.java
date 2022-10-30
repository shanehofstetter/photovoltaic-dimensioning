package ch.shanehofstetter.pvdimension.pvgenerator.inverter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Inverter (unused class, support could be added in future)
 */
public class Inverter implements Serializable {

    private String manufacturer;
    private String typeName;
    private String details;
    private BigDecimal cost;
    private double coefficient;
    private String circuitType;
    private String usageType;
    private double maxGeneratorPower;
    private double maxACPower;

    public Inverter() {
        this(1.0);
    }

    public Inverter(double coefficient) {
        this.coefficient = coefficient;
        cost = new BigDecimal(2000.0);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getCircuitType() {
        return circuitType;
    }

    public void setCircuitType(String circuitType) {
        this.circuitType = circuitType;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public double getMaxGeneratorPower() {
        return maxGeneratorPower;
    }

    public void setMaxGeneratorPower(double maxGeneratorPower) {
        this.maxGeneratorPower = maxGeneratorPower;
    }

    public double getMaxACPower() {
        return maxACPower;
    }

    public void setMaxACPower(double maxACPower) {
        this.maxACPower = maxACPower;
    }
}
