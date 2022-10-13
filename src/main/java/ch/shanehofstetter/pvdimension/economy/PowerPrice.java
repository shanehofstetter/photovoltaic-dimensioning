package ch.shanehofstetter.pvdimension.economy;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * PowerPrice specifies the costs of an amount of power when selling and buying<br>
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.economy
 */
public class PowerPrice implements Serializable {
    private String powerUnit;
    private BigDecimal purchaseKwhPrice;
    private BigDecimal sellingKwhPrice;

    public PowerPrice() {
        init();
    }

    /**
     * Initializes all Values to default
     */
    public void init() {
        powerUnit = "kWh";
        purchaseKwhPrice = BigDecimal.valueOf(0.15);
        sellingKwhPrice = BigDecimal.valueOf(0.2);
    }

    /**
     * @return power unit for which the prices are (defaults to kWh)
     */
    public String getPowerUnit() {
        return powerUnit;
    }

    /**
     * @return cost for one kWh when buying
     */
    public BigDecimal getPurchaseKwhPrice() {
        return purchaseKwhPrice;
    }

    /**
     * set the cost of one kWh
     * @param purchaseKwhPrice price of one kWh when purchasing
     */
    public void setPurchaseKwhPrice(BigDecimal purchaseKwhPrice) {
        this.purchaseKwhPrice = purchaseKwhPrice;
    }

    /**
     * @return cost of one kWh when selling it
     */
    public BigDecimal getSellingKwhPrice() {
        return sellingKwhPrice;
    }

    /**
     * set the cost of a kWh when selling it
     * @param sellingKwhPrice price of one kWh
     */
    public void setSellingKwhPrice(BigDecimal sellingKwhPrice) {
        this.sellingKwhPrice = sellingKwhPrice;
    }

    /**
     * @param kwhAmountPurchased Amount of Power purchased [kwh]
     * @return Total Costs of purchased Power
     */
    public BigDecimal getPurchaseCosts(double kwhAmountPurchased) {
        return purchaseKwhPrice.multiply(BigDecimal.valueOf(kwhAmountPurchased));
    }

    /**
     * @param kwhAmountSold Amount of Power sold [kwh]
     * @return Total Costs of sold power
     */
    public BigDecimal getSalesProfit(double kwhAmountSold) {
        return sellingKwhPrice.multiply(BigDecimal.valueOf(kwhAmountSold));
    }

}
