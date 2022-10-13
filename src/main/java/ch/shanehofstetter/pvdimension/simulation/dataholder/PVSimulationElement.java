package ch.shanehofstetter.pvdimension.simulation.dataholder;

import ch.shanehofstetter.pvdimension.io.csv.CSVWriteable;
import ch.shanehofstetter.pvdimension.util.Utilities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder.result
 */
public class PVSimulationElement implements Serializable, CSVWriteable {

    protected double idealProduction; //Wh
    protected double realProduction; //Wh
    protected double productionSurplus; //Wh
    protected double batteryChargeLevel; //Wh
    protected double powerPurchased; //Wh
    protected double powerToGrid; //Wh
    protected double energyConsumed; //Wh
    protected double horizontalRadianceEnergy; //   Wh/m2
    protected double realRadianceEnergy; //   Wh/m2

    private BigDecimal totalPowerSellings = new BigDecimal(0);
    private BigDecimal totalPowerCosts = new BigDecimal(0);
    private BigDecimal totalEarnings = new BigDecimal(0);
    private BigDecimal totalSavings = new BigDecimal(0);

    /**
     * cumulate (add up) values of all elements in given PVSimulationElement List
     *
     * @param simulationResults elements to add up
     * @return new Element which contains all summed-up members
     */
    public static PVSimulationElement cumulateSimulationResults(List<? extends PVSimulationElement> simulationResults) {
        PVSimulationElement cumulatedResults = new PVSimulationElement();
        for (PVSimulationElement simulationResult : simulationResults) {
            // Money
            cumulatedResults.setTotalSavings(cumulatedResults.getTotalSavings().add(simulationResult.getTotalSavings()));
            cumulatedResults.setTotalPowerSellings(cumulatedResults.getTotalPowerSellings().add(simulationResult.getTotalPowerSellings()));
            cumulatedResults.setTotalEarnings(cumulatedResults.getTotalEarnings().add(simulationResult.getTotalEarnings()));
            cumulatedResults.setTotalPowerCosts(cumulatedResults.getTotalPowerCosts().add(simulationResult.getTotalPowerCosts()));
            // Power
            cumulatedResults.setConsumedPower(cumulatedResults.getConsumedPower() + simulationResult.getConsumedPower());
            cumulatedResults.setHorizontalRadianceEnergy(cumulatedResults.getHorizontalRadianceEnergy() + simulationResult.getHorizontalRadianceEnergy());
            cumulatedResults.setIdealProduction(cumulatedResults.getIdealProduction() + simulationResult.getIdealProduction());
            cumulatedResults.setPowerPurchased(cumulatedResults.getPowerPurchased() + simulationResult.getPowerPurchased());
            cumulatedResults.setRealProduction(cumulatedResults.getRealProduction() + simulationResult.getRealProduction());
            cumulatedResults.setRealRadianceEnergy(cumulatedResults.getRealRadianceEnergy() + simulationResult.getRealRadianceEnergy());
            cumulatedResults.setProductionSurplus(cumulatedResults.getProductionSurplus() + simulationResult.getProductionSurplus());
            cumulatedResults.setPowerToGrid(cumulatedResults.getPowerToGrid() + simulationResult.getPowerToGrid());
        }
        cumulatedResults.setBatteryChargeLevel(simulationResults.get(simulationResults.size() - 1).getBatteryChargeLevel());
        return cumulatedResults;
    }

    /**
     * build the average of a summed-up element with given number of elements n it was summed-up from<br>
     * @param simulationResult element containing cumulated results
     * @param n number of elements
     * @return averaged element
     */
    public static PVSimulationElement buildAverage(PVSimulationElement simulationResult, int n) {
        // Build Average
        BigDecimal divisor = new BigDecimal(n);
        simulationResult.setTotalSavings(simulationResult.getTotalSavings().divide(divisor, 2, RoundingMode.HALF_UP));
        simulationResult.setTotalPowerSellings(simulationResult.getTotalPowerSellings().divide(divisor, 2, RoundingMode.HALF_UP));
        simulationResult.setTotalEarnings(simulationResult.getTotalEarnings().divide(divisor, 2, RoundingMode.HALF_UP));
        simulationResult.setTotalPowerCosts(simulationResult.getTotalPowerCosts().divide(divisor, 2, RoundingMode.HALF_UP));

        simulationResult.setBatteryChargeLevel(simulationResult.getBatteryChargeLevel() / n);
        simulationResult.setConsumedPower(simulationResult.getConsumedPower() / n);
        simulationResult.setHorizontalRadianceEnergy(simulationResult.getHorizontalRadianceEnergy() / n);
        simulationResult.setIdealProduction(simulationResult.getIdealProduction() / n);
        simulationResult.setPowerPurchased(simulationResult.getPowerPurchased() / n);
        simulationResult.setRealProduction(simulationResult.getRealProduction() / n);
        simulationResult.setRealRadianceEnergy(simulationResult.getRealRadianceEnergy() / n);
        simulationResult.setProductionSurplus(simulationResult.getProductionSurplus() / n);
        simulationResult.setPowerToGrid(simulationResult.getPowerToGrid() / n);
        return simulationResult;
    }

    @Override
    public ArrayList<String> getCSVHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        headers.add("Ideale Produktion [Wh]");
        headers.add("Reale Produktion [Wh]");
        headers.add("Produktionsüberschuss [Wh]");
        headers.add("Batterie-Ladestand [Wh]");
        headers.add("Eingekaufter Strom [Wh]");
        headers.add("Verkaufter Strom [Wh]");
        headers.add("Verbrauchter Strom [Wh]");
        headers.add("Einstrahlung Horizontal [Wh/m2]");
        headers.add("Einstrahlung Direkt [Wh/m2]");
        headers.add("Einnahmen aus Verkauf");
        headers.add("Stromeinkaufs-Kosten");
        headers.add("Gewinn aus Verkauf");
        headers.add("Eingesparte Strom-Kosten");
        return headers;
    }

    @Override
    public ArrayList<ArrayList<String>> getCSVData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        ArrayList<String> content = new ArrayList<>();
        content.add(getIdealProduction()+"");
        content.add(getRealProduction()+"");
        content.add(getProductionSurplus()+"");
        content.add(getBatteryChargeLevel()+"");
        content.add(getPowerPurchased()+"");
        content.add(getPowerToGrid()+"");
        content.add(getConsumedPower()+"");
        content.add(getHorizontalRadianceEnergy()+"");
        content.add(getRealRadianceEnergy()+"");
        content.add(getTotalPowerSellings().toString());
        content.add(getTotalPowerCosts().toString());
        content.add(getTotalEarnings().toString());
        content.add(getTotalSavings().toString());
        data.add(content);
        return data;
    }

    public double getIdealProduction() {
        return Utilities.round(idealProduction, 2);
    }

    public void setIdealProduction(double idealProduction) {
        this.idealProduction = idealProduction;
    }

    /**
     * @return Real Production [Wh]
     */
    public double getRealProduction() {
        return Utilities.round(realProduction, 2);
    }

    public void setRealProduction(double realProduction) {
        this.realProduction = realProduction;
    }

    /**
     * @return [Wh]
     */
    public double getProductionSurplus() {
        return Utilities.round(productionSurplus, 2);
    }

    public void setProductionSurplus(double productionSurplus) {
        this.productionSurplus = productionSurplus;
    }

    /**
     * @return [Wh]
     */
    public double getBatteryChargeLevel() {
        return Utilities.round(batteryChargeLevel, 2);
    }

    public void setBatteryChargeLevel(double batteryChargeLevel) {
        this.batteryChargeLevel = batteryChargeLevel;
    }

    /**
     * @return [Wh]
     */
    public double getPowerPurchased() {
        return Utilities.round(powerPurchased, 2);
    }

    public void setPowerPurchased(double powerPurchased) {
        this.powerPurchased = powerPurchased;
    }

    /**
     * @return [Wh]
     */
    public double getPowerToGrid() {
        return Utilities.round(powerToGrid, 2);
    }

    public void setPowerToGrid(double powerToGrid) {
        this.powerToGrid = powerToGrid;
    }

    /**
     * @return [Wh]
     */
    public double getConsumedPower() {
        return Utilities.round(energyConsumed, 2);
    }

    public void setConsumedPower(double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public double getHorizontalRadianceEnergy() {
        return Utilities.round(horizontalRadianceEnergy, 2);
    }

    public void setHorizontalRadianceEnergy(double horizontalRadianceEnergy) {
        this.horizontalRadianceEnergy = horizontalRadianceEnergy;
    }

    public double getRealRadianceEnergy() {
        return Utilities.round(realRadianceEnergy, 2);
    }

    public void setRealRadianceEnergy(double realRadianceEnergy) {
        this.realRadianceEnergy = realRadianceEnergy;
    }

    public BigDecimal getTotalPowerSellings() {
        return totalPowerSellings;
    }

    public void setTotalPowerSellings(BigDecimal totalPowerSellings) {
        this.totalPowerSellings = totalPowerSellings;
    }

    public BigDecimal getTotalPowerCosts() {
        return totalPowerCosts;
    }

    public void setTotalPowerCosts(BigDecimal totalPowerCosts) {
        this.totalPowerCosts = totalPowerCosts;
    }

    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(BigDecimal totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public BigDecimal getTotalSavings() {
        return totalSavings;
    }

    public void setTotalSavings(BigDecimal totalSavings) {
        this.totalSavings = totalSavings;
    }

    /**
     * adopts all values from given simulation element (kind of copy it)
     *
     * @param simulationElement simulationElement to adopt
     */
    public void adoptValues(PVSimulationElement simulationElement) {
        setBatteryChargeLevel(simulationElement.getBatteryChargeLevel());
        setConsumedPower(simulationElement.getConsumedPower());
        setHorizontalRadianceEnergy(simulationElement.getHorizontalRadianceEnergy());
        setIdealProduction(simulationElement.getIdealProduction());
        setPowerPurchased(simulationElement.getPowerPurchased());
        setPowerToGrid(simulationElement.getPowerToGrid());
        setProductionSurplus(simulationElement.getProductionSurplus());
        setRealProduction(simulationElement.getRealProduction());
        setRealRadianceEnergy(simulationElement.getRealRadianceEnergy());
        setTotalEarnings(simulationElement.getTotalEarnings());
        setTotalPowerCosts(simulationElement.getTotalPowerCosts());
        setTotalPowerSellings(simulationElement.getTotalPowerSellings());
        setTotalSavings(simulationElement.getTotalSavings());
    }

    @Override
    public String toString() {
        return "PVSimulationElement{" +
                "\nidealProduction=" + idealProduction +
                ", \nrealProduction=" + realProduction +
                ", \nproductionSurplus=" + productionSurplus +
                ", \nbatteryChargeLevel=" + batteryChargeLevel +
                ", \npowerPurchased=" + powerPurchased +
                ", \npowerToGrid=" + powerToGrid +
                '}';
    }


}
