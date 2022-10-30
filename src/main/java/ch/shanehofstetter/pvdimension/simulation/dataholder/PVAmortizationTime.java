package ch.shanehofstetter.pvdimension.simulation.dataholder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PVAmortizationTime implements Serializable {

    private double amortisationTimeYears;
    private BigDecimal yearlyRevenue;
    private ArrayList<PVAmortizationTimeElement> amortizationTimeElements;

    public PVAmortizationTime() {
    }

    public PVAmortizationTime(double amortisationTimeYears, BigDecimal yearlyRevenue, ArrayList<PVAmortizationTimeElement> amortizationTimeElements) {
        this.amortisationTimeYears = amortisationTimeYears;
        this.yearlyRevenue = yearlyRevenue;
        this.amortizationTimeElements = amortizationTimeElements;
    }

    public double getAmortisationTimeYears() {
        return amortisationTimeYears;
    }

    public void setAmortisationTimeYears(double amortisationTimeYears) {
        this.amortisationTimeYears = amortisationTimeYears;
    }

    public BigDecimal getYearlyRevenue() {
        return yearlyRevenue;
    }

    public void setYearlyRevenue(BigDecimal yearlyRevenue) {
        this.yearlyRevenue = yearlyRevenue;
    }

    public ArrayList<PVAmortizationTimeElement> getAmortizationTimeElements() {
        return amortizationTimeElements;
    }

    public void setAmortizationTimeElements(ArrayList<PVAmortizationTimeElement> amortizationTimeElements) {
        this.amortizationTimeElements = amortizationTimeElements;
    }
}
