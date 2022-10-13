package ch.shanehofstetter.pvdimension.simulation.dataholder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder
 */
public class PVAmortizationTimeElement implements Serializable {
    private int year;
    private BigDecimal revenue;
    private BigDecimal remainingCosts;

    public PVAmortizationTimeElement() {
    }

    public PVAmortizationTimeElement(int year) {
        this.year = year;
    }

    public PVAmortizationTimeElement(int year, BigDecimal revenue, BigDecimal remainingCosts) {
        this.year = year;
        this.revenue = revenue;
        this.remainingCosts = remainingCosts;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getRemainingCosts() {
        return remainingCosts;
    }

    public void setRemainingCosts(BigDecimal remainingCosts) {
        this.remainingCosts = remainingCosts;
    }
}
