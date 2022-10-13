package ch.shanehofstetter.pvdimension.gui.components.pvcharts;

import ch.shanehofstetter.pvdimension.simulation.dataholder.*;
import ch.shanehofstetter.pvdimension.util.DateTimeUtil;
import javafx.scene.chart.XYChart;
import org.slf4j.LoggerFactory;


import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts
 */
public class PVChartSeriesConverter {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PVChartSeriesConverter.class);

    private String idealProductionSeriesTitle = "Ideale Produktion";
    private String realProductionSeriesTitle = "Reale Produktion";
    private String productionSurplusSeriesTitle = "\u00DCberschuss";
    private String batteryChargeLevelSeriesTitle = "Batterie-Ladestatus";
    private String powerPurchasedSeriesTitle = "Strom-Einkauf";
    private String powerToGridSeriesTitle = "Strom-Verkauf";
    private String consumedEnergySeriesTitle = "Stromverbrauch";
    private String powerConsumptionSeriesTitle = "Stromverbrauch [W]";
    private String sunpowerSeriesTitle = "Sonneneinstrahlung [W/m2]";
    private String powerEarningsSeriesTitle = "Verkaufsgewinn";
    private String totalHardwareCostTitle = "Anlagekosten";
    private String powerSavingsSeriesTitle = "Ersparnis";

    public ArrayList<XYChart.Series<String, Number>> getHourlyChartSeriesForWeek(PVWeek pvWeek, PVInputDataType pvInputDataType) {

        ArrayList<XYChart.Series<String, Number>> seriesArrayList = new ArrayList<>();

        for (int i = 0; i < pvWeek.getPVDays().size(); i++) {
            PVDay pvDay = pvWeek.getPVDays().get(i);
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            if (pvInputDataType == PVInputDataType.POWER_CONSUMPTION) {
                series.setName(powerConsumptionSeriesTitle);
                for (PVPowerTime powerTime : pvDay.getPowerConsumptions()) {
                    series.getData().add(new XYChart.Data<>(DateTimeUtil.formatTime(powerTime.getTime().getTime()), powerTime.getPower()));
                    logger.trace(DateTimeUtil.formatTime(powerTime.getTime().getTime()) + " -> " + powerTime.getPower());
                }
                seriesArrayList.add(series);
            } else if (pvInputDataType == PVInputDataType.SUN_POWER) {
                series.setName(sunpowerSeriesTitle);
                for (PVPowerTime powerTime : pvDay.getSunPowers()) {
                    series.getData().add(new XYChart.Data<>(DateTimeUtil.formatTime(powerTime.getTime().getTime()), powerTime.getPower()));
                }
                seriesArrayList.add(series);
            }
        }

        return seriesArrayList;

    }

    public XYChart.Series<String, Number> getHourlyChartSeriesForDay(PVDay pvDay, PVInputDataType pvInputDataType) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        if (pvInputDataType == PVInputDataType.POWER_CONSUMPTION) {
            series.setName(powerConsumptionSeriesTitle);
            for (PVPowerTime powerTime : pvDay.getPowerConsumptions()) {
                series.getData().add(new XYChart.Data<>(DateTimeUtil.formatTime(powerTime.getTime().getTime()), powerTime.getPower()));
                logger.trace(DateTimeUtil.formatTime(powerTime.getTime().getTime()) + " -> " + powerTime.getPower());
            }
        } else if (pvInputDataType == PVInputDataType.SUN_POWER) {
            series.setName(sunpowerSeriesTitle);
            for (PVPowerTime powerTime : pvDay.getSunPowers()) {
                series.getData().add(new XYChart.Data<>(DateTimeUtil.formatTime(powerTime.getTime().getTime()), powerTime.getPower()));
                logger.trace(DateTimeUtil.formatTime(powerTime.getTime().getTime()) + " -> " + powerTime.getPower());
            }
        }
        return series;
    }

    public ArrayList<XYChart.Series<String, Number>> getHourlyResultSeriesForDay(PVDay pvDay) {

        ArrayList<XYChart.Series<String, Number>> resultSeries = new ArrayList<>();

        XYChart.Series<String, Number> realProductionSeries = new XYChart.Series<>();
        realProductionSeries.setName(realProductionSeriesTitle);
        XYChart.Series<String, Number> productionSurplusSeries = new XYChart.Series<>();
        productionSurplusSeries.setName(productionSurplusSeriesTitle);
        XYChart.Series<String, Number> batteryChargeLevelSeries = new XYChart.Series<>();
        batteryChargeLevelSeries.setName(batteryChargeLevelSeriesTitle);
        XYChart.Series<String, Number> powerPurchasedSeries = new XYChart.Series<>();
        powerPurchasedSeries.setName(powerPurchasedSeriesTitle);
        XYChart.Series<String, Number> powerToGridSeries = new XYChart.Series<>();
        powerToGridSeries.setName(powerToGridSeriesTitle);
        XYChart.Series<String, Number> energyConsumedSeries = new XYChart.Series<>();
        energyConsumedSeries.setName(consumedEnergySeriesTitle);

        for (PVTimeUnit pvTimeUnit : pvDay.getSimulatedTimeUnits()) {
            String time = DateTimeUtil.formatTime(pvTimeUnit.getTime().getTime());
            realProductionSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getRealProduction()));
            productionSurplusSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getProductionSurplus()));
            batteryChargeLevelSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getBatteryChargeLevel()));
            powerPurchasedSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getPowerPurchased()));
            powerToGridSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getPowerToGrid()));
            energyConsumedSeries.getData().add(new XYChart.Data<>(time, pvTimeUnit.getConsumedPower()));
        }

        resultSeries.add(realProductionSeries);
        resultSeries.add(productionSurplusSeries);
        resultSeries.add(batteryChargeLevelSeries);
        resultSeries.add(powerPurchasedSeries);
        resultSeries.add(powerToGridSeries);
        resultSeries.add(energyConsumedSeries);

        return resultSeries;
    }

    public ArrayList<XYChart.Series<String, Number>> getDailyResultSeriesForWeek(PVWeek pvWeek) {

        ArrayList<XYChart.Series<String, Number>> resultSeries = new ArrayList<>();

        XYChart.Series<String, Number> realProductionSeries = new XYChart.Series<>();
        realProductionSeries.setName(realProductionSeriesTitle);
        XYChart.Series<String, Number> productionSurplusSeries = new XYChart.Series<>();
        productionSurplusSeries.setName(productionSurplusSeriesTitle);
        XYChart.Series<String, Number> batteryChargeLevelSeries = new XYChart.Series<>();
        batteryChargeLevelSeries.setName(batteryChargeLevelSeriesTitle);
        XYChart.Series<String, Number> powerPurchasedSeries = new XYChart.Series<>();
        powerPurchasedSeries.setName(powerPurchasedSeriesTitle);
        XYChart.Series<String, Number> powerToGridSeries = new XYChart.Series<>();
        powerToGridSeries.setName(powerToGridSeriesTitle);
        XYChart.Series<String, Number> energyConsumedSeries = new XYChart.Series<>();
        energyConsumedSeries.setName(consumedEnergySeriesTitle);

        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {

            PVDay pvDay = pvWeek.getPVDays().get(dayOfWeek.getValue() - 1);
            String time = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.GERMAN);

            realProductionSeries.getData().add(new XYChart.Data<>(time, pvDay.getRealProduction()));
            productionSurplusSeries.getData().add(new XYChart.Data<>(time, pvDay.getProductionSurplus()));
            batteryChargeLevelSeries.getData().add(new XYChart.Data<>(time, pvDay.getBatteryChargeLevel()));
            powerPurchasedSeries.getData().add(new XYChart.Data<>(time, pvDay.getPowerPurchased()));
            powerToGridSeries.getData().add(new XYChart.Data<>(time, pvDay.getPowerToGrid()));
            energyConsumedSeries.getData().add(new XYChart.Data<>(time, pvDay.getConsumedPower()));
        }

        resultSeries.add(realProductionSeries);
        resultSeries.add(productionSurplusSeries);
        resultSeries.add(batteryChargeLevelSeries);
        resultSeries.add(powerPurchasedSeries);
        resultSeries.add(powerToGridSeries);
        resultSeries.add(energyConsumedSeries);

        return resultSeries;
    }

    public ArrayList<XYChart.Series<String, Number>> getMonthlyResultSeriesForYear(ArrayList<PVMonth> pvMonths) {
        ArrayList<XYChart.Series<String, Number>> resultSeries = new ArrayList<>();

        XYChart.Series<String, Number> realProductionSeries = new XYChart.Series<>();
        realProductionSeries.setName(realProductionSeriesTitle);
        XYChart.Series<String, Number> productionSurplusSeries = new XYChart.Series<>();
        productionSurplusSeries.setName(productionSurplusSeriesTitle);
        XYChart.Series<String, Number> batteryChargeLevelSeries = new XYChart.Series<>();
        batteryChargeLevelSeries.setName(batteryChargeLevelSeriesTitle);
        XYChart.Series<String, Number> powerPurchasedSeries = new XYChart.Series<>();
        powerPurchasedSeries.setName(powerPurchasedSeriesTitle);
        XYChart.Series<String, Number> powerToGridSeries = new XYChart.Series<>();
        powerToGridSeries.setName(powerToGridSeriesTitle);
        XYChart.Series<String, Number> energyConsumedSeries = new XYChart.Series<>();
        energyConsumedSeries.setName(consumedEnergySeriesTitle);

        for (PVMonth pvMonth : pvMonths) {
            String name = pvMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN);
            realProductionSeries.getData().add(new XYChart.Data<>(name, pvMonth.getRealProduction() / 1000.0));
            productionSurplusSeries.getData().add(new XYChart.Data<>(name, pvMonth.getProductionSurplus() / 1000.0));
            batteryChargeLevelSeries.getData().add(new XYChart.Data<>(name, pvMonth.getBatteryChargeLevel() / 1000.0));
            powerPurchasedSeries.getData().add(new XYChart.Data<>(name, pvMonth.getPowerPurchased() / 1000.0));
            powerToGridSeries.getData().add(new XYChart.Data<>(name, pvMonth.getPowerToGrid() / 1000.0));
            energyConsumedSeries.getData().add(new XYChart.Data<>(name, pvMonth.getConsumedPower() / 1000.0));
        }

        resultSeries.add(realProductionSeries);
        resultSeries.add(productionSurplusSeries);
        resultSeries.add(batteryChargeLevelSeries);
        resultSeries.add(powerPurchasedSeries);
        resultSeries.add(powerToGridSeries);
        resultSeries.add(energyConsumedSeries);

        return resultSeries;
    }

    public XYChart.Series<String, Number> getTotalSeries(PVSimulationElement simulationResult) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Data<String, Number> idealProductionData = new XYChart.Data<>(idealProductionSeriesTitle, simulationResult.getIdealProduction() / 1000.0);
        series.getData().add(idealProductionData);
        XYChart.Data<String, Number> realProductionData = new XYChart.Data<>(realProductionSeriesTitle, simulationResult.getRealProduction() / 1000.0);
        series.getData().add(realProductionData);
        XYChart.Data<String, Number> purchasedData = new XYChart.Data<>(powerPurchasedSeriesTitle, simulationResult.getPowerPurchased() / 1000.0);
        series.getData().add(purchasedData);
        XYChart.Data<String, Number> soldData = new XYChart.Data<>(powerToGridSeriesTitle, simulationResult.getPowerToGrid() / 1000.0);
        series.getData().add(soldData);
        XYChart.Data<String, Number> consumedData = new XYChart.Data<>(consumedEnergySeriesTitle, simulationResult.getConsumedPower() / 1000.0);
        series.getData().add(consumedData);
        return series;
    }

    public XYChart.Series<String, Number> getTotalEconomicalSeries(PVSimulationElement simulationResult) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        XYChart.Data<String, Number> powerToGridData = new XYChart.Data<>(powerToGridSeriesTitle, simulationResult.getTotalPowerSellings());
        series.getData().add(powerToGridData);
        XYChart.Data<String, Number> powerPurchasedData = new XYChart.Data<>(powerPurchasedSeriesTitle, simulationResult.getTotalPowerCosts());
        series.getData().add(powerPurchasedData);
        XYChart.Data<String, Number> earningsData = new XYChart.Data<>(powerEarningsSeriesTitle, simulationResult.getTotalEarnings());
        series.getData().add(earningsData);
        XYChart.Data<String, Number> savingsData = new XYChart.Data<>(powerSavingsSeriesTitle, simulationResult.getTotalSavings());
        series.getData().add(savingsData);
        return series;
    }

    public ArrayList<XYChart.Series<String, Number>> getAmortisationSeries(PVAmortizationTime amortizationTime) {
        ArrayList<XYChart.Series<String, Number>> seriesArrayList = new ArrayList<>();
        final String revenue = "Ertrag";
        final String costsRemaining = "Restkosten";

        XYChart.Series revenueSeries = new XYChart.Series();
        revenueSeries.setName(revenue);
        XYChart.Series remainingCostSeries = new XYChart.Series();
        remainingCostSeries.setName(costsRemaining);

        for (PVAmortizationTimeElement timeElement : amortizationTime.getAmortizationTimeElements()) {
            //noinspection unchecked,unchecked
            revenueSeries.getData().add(new XYChart.Data("Jahr " + timeElement.getYear(), timeElement.getRevenue().doubleValue()));
            //noinspection unchecked,unchecked
            remainingCostSeries.getData().add(new XYChart.Data("Jahr " + timeElement.getYear(), timeElement.getRemainingCosts().doubleValue()));
        }
        //noinspection unchecked
        seriesArrayList.add(revenueSeries);
        //noinspection unchecked
        seriesArrayList.add(remainingCostSeries);
        return seriesArrayList;
    }


}
