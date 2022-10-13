package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.irradiationdata.IrradiationData;
import ch.shanehofstetter.pvdimension.irradiationdata.IrradiationDataElement;
import ch.shanehofstetter.pvdimension.irradiationdata.IrradiationDataFetcher;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.simulation.dataholder.*;
import ch.shanehofstetter.pvdimension.sungeodata.SunGeoData;
import ch.shanehofstetter.pvdimension.util.DateTimeUtil;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class PVWeekSimulator extends PVSimulator {

    private static Month defaultSimulatingMonth = Month.JANUARY;
    SunGeoData sunGeoData = new SunGeoData();
    private PVWeek pvWeek;
    private IrradiationDataFetcher irradiationDataFetcher;

    public PVWeekSimulator(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        this.pvWeek = pvGenerator.getSimulationData().getPvWeek();
    }

    public static Month getDefaultSimulatingMonth() {
        return defaultSimulatingMonth;
    }

    public static void setDefaultSimulatingMonth(Month defaultSimulatingMonth) {
        PVWeekSimulator.defaultSimulatingMonth = defaultSimulatingMonth;
    }

    /**
     * Make a PVWeek object containing 7 PVDays, each with same power-consumptions and sun-radiations
     *
     * @param startHour beginning of the day, e.g. 6 = 6:00
     * @param hours     amount of hours, starting at startHour (max. 24-startHour)
     * @return PVWeek
     */
    public static PVWeek makeDefaultWeek(int startHour, int hours) {
        startHour = Math.abs(startHour);
        hours = Math.abs(hours);
        if (hours + startHour > 24) {
            hours = 24 - startHour;
        }
        PVWeek pvWeek = new PVWeek();
        for (int i = 0; i < 7; i++) {
            PVDay day = new PVDay();
            day.setPowerConsumptions(makeDefaultPowerTimes(startHour, hours, 850.0, 1));
            day.setSunPowers(makeDefaultPowerTimes(startHour, hours, 250.0, 0));
            pvWeek.getPVDays().set(i, day);
        }
        return pvWeek;
    }

    /**
     * @param startHour starting hour
     * @param hours     amount of hours to generate (startHour + hours = max. 24)
     * @param factor    amplitude of curve
     * @param type      0 = sine-function, 1 = cosine-function
     * @return list of generated powerTimes
     */
    private static ArrayList<PVPowerTime> makeDefaultPowerTimes(int startHour, int hours, double factor, int type) {
        ArrayList<PVPowerTime> powers = new ArrayList<>();
        for (int j = 0; j < hours; j++) {
            PVPowerTime pt = new PVPowerTime();
            pt.setTimeString((j + startHour) + ":00");
            // use a nice sine or cosine function to generate a curvy power curve
            if (type == 0) {
                double x = 3.0 / (double) hours * j;
                pt.setPower(Math.pow(Math.sin(x), 7) * factor);
            } else if (type == 1) {
                double x = 9.5 / (double) hours * j + 1.5;
                pt.setPower(Math.pow(Math.cos(x), 2) * factor);
            }
            powers.add(pt);
        }
        return powers;
    }

    /**
     * Make a PVWeek object containing 7 PVDays, each with same power-consumptions and sun-radiations
     *
     * @return PVWeek
     */
    public static PVWeek makeDefaultWeek() {
        return makeDefaultWeek(PVDay.getDefaultStartHour(), PVDay.getDefaultHourAmount());
    }

    @Override
    public void setPvGenerator(PVGenerator pvGenerator) {
        super.setPvGenerator(pvGenerator);
        setPvWeek(pvGenerator.getSimulationData().getPvWeek());
    }

    /**
     * @return unsimulated PVWeek
     */
    public PVWeek getPvWeek() {
        return pvWeek;
    }

    /**
     * @param pvWeek PVWeek containing PVDays[7] with which the simulation calculates
     */
    public void setPvWeek(PVWeek pvWeek) {
        this.pvWeek = pvWeek;
    }

    /**
     * simulate a week with a day as smallest unit of time
     *
     * @return simulated week
     * @throws MissingDataException when generator or week is null
     */
    public PVWeek simulateSimpleWeek() throws MissingDataException {
        if (pvGenerator == null || pvWeek == null) {
            throw new MissingDataException("PVGenerator and PVWeek are required");
        }
        pvGenerator.getBattery().initialize();
        for (PVDay pvDay : pvWeek.getPVDays()) {

            simulateTimeUnit(pvDay);
            logger.debug(pvDay + "\n");
        }
        return pvWeek;
    }

    /**
     * Run a simulation for the given Data
     * WARNING: Initializes the Battery
     *
     * @param simulationParameters simulation parameters
     * @throws MissingDataException if some data is corrupt, or missing, or no internet connection or whatsoever
     */
    public void simulateWeek(PVSimulationParameters simulationParameters) throws MissingDataException,
            SunGeoData.LocalSunDataLoadException, IrradiationDataFetcher.IrradiationDataFetchException {
        simulateWeek(simulationParameters.getSimulatingMonth(), simulationParameters.getSunDataMode(), true);
    }

    /**
     * Run a simulation for the given Data
     * WARNING: Initializes the Battery
     * After the successful simulation, finishedSimulation in all listeners gets called
     * @param month       month which to simulate
     * @param sunDataMode sun data mode, where to get radiations from
     * @throws MissingDataException if some data is corrupt, or missing, or no internet connection or whatsoever
     */
    public void simulateWeek(Month month, SunDataMode sunDataMode) throws MissingDataException,
            SunGeoData.LocalSunDataLoadException, IrradiationDataFetcher.IrradiationDataFetchException {
        simulateWeek(month, sunDataMode, true);
    }

    /**
     * Run a simulation for the given Data
     * After the successful simulation, finishedSimulation in all listeners gets called
     *
     * @param month             Month in which the simulation runs
     * @param sunDataMode       the sun data mode, where to get the radiations
     * @param initializeBattery initialize the battery when true (charge-level is set to 0)
     * @throws MissingDataException                                 if some data is corrupt, or missing, or no internet connection or whatsoever
     * @throws SunGeoData.LocalSunDataLoadException                 if local data files cannot be read
     * @throws IrradiationDataFetcher.IrradiationDataFetchException if no internet connection or no data returned
     */
    public void simulateWeek(Month month, SunDataMode sunDataMode, boolean initializeBattery) throws MissingDataException,
            SunGeoData.LocalSunDataLoadException, IrradiationDataFetcher.IrradiationDataFetchException {
        if (pvGenerator == null || pvWeek == null) {
            throw new MissingDataException("PVGenerator and PVWeek is required");
        }
        simulationActive = true;
        logger.debug("simulation mode: " + sunDataMode);
        if (initializeBattery)
            pvGenerator.getBattery().initialize();

        if (!pvGenerator.canProducePower())
            producingNoPower();

        List<PVDay> pvDays = pvWeek.getPVDays();
        for (int index = 0; index < pvDays.size(); index++) {
            PVDay pvDay = pvDays.get(index);
            if (pvDay.getPowerConsumptions() == null && pvDay.getSunPowers() == null) {
                simulateSimpleWeek();
                return;
            }
            pvDay.getSimulatedTimeUnits().clear();
            ArrayList<PVPowerTime> consumedEnergies = pvDay.calculateConsumedEnergies();
            ArrayList<PVPowerTime> radianceEnergies = pvDay.calculateRadianceEnergies();

            if (consumedEnergies.size() != radianceEnergies.size() && sunDataMode == SunDataMode.LOCAL) {
                throw new MissingDataException("Different amounts of data points");
            }
            for (int i = 0; i < consumedEnergies.size(); i++) {
                PVTimeUnit simulatedTimeUnit = new PVTimeUnit();
                simulatedTimeUnit.setTime(consumedEnergies.get(i).getTime());
                simulatedTimeUnit.setConsumedPower(consumedEnergies.get(i).getPower());
                if (sunDataMode == SunDataMode.LOCAL) {
                    calculateRealRadianceFromLocalData(month, radianceEnergies.get(i), sunGeoData, simulatedTimeUnit);
                } else if (sunDataMode == SunDataMode.WEBSERVICE) {
                    calculateRealRadianceFromWebservice(month, simulatedTimeUnit);
                }
                simulateTimeUnit(simulatedTimeUnit);
                pvDay.getSimulatedTimeUnits().add(simulatedTimeUnit);
            }
            pvDay.adoptValues(PVSimulationElement.cumulateSimulationResults(pvDay.getSimulatedTimeUnits()));
            calculateEconomicsForSimulationResult(pvDay);
            reportProgress(index+1, 7);
        }
        pvWeek.adoptValues(PVSimulationElement.cumulateSimulationResults(pvWeek.getPVDays()));
        calculateEconomicsForSimulationResult(pvWeek);
        finishedSimulation(pvWeek);
        simulationActive = false;
    }

    private void calculateEconomicsForSimulationResult(PVSimulationElement simulationResult) {
        simulationResult.setTotalPowerSellings(pvGenerator.getPowerPrice().getSellingKwhPrice().multiply(
                BigDecimal.valueOf(simulationResult.getPowerToGrid() / 1000.0)));
        simulationResult.setTotalPowerCosts(pvGenerator.getPowerPrice().getPurchaseKwhPrice().multiply(
                BigDecimal.valueOf(simulationResult.getPowerPurchased() / 1000.0)));
        simulationResult.setTotalEarnings(simulationResult.getTotalPowerSellings().subtract(
                simulationResult.getTotalPowerCosts()));
        simulationResult.setTotalSavings(pvGenerator.getPowerPrice().getPurchaseKwhPrice().multiply(
                BigDecimal.valueOf((simulationResult.getConsumedPower() - simulationResult.getPowerPurchased()) / 1000.0)));
    }

    private void calculateRealRadianceFromWebservice(Month month, PVTimeUnit simulatedTimeUnit) throws IrradiationDataFetcher.IrradiationDataFetchException {
        if (irradiationDataFetcher == null) {
            irradiationDataFetcher = new IrradiationDataFetcher();
        }
        ArrayList<IrradiationDataElement> irradiationDataElements =
                irradiationDataFetcher.getIrradiationDataElements(month, pvGenerator.getSolarPanelField(), pvGenerator.getCoordinates());
        IrradiationData irradiationData = new IrradiationData(irradiationDataElements);
        double realRadiationWhForTimeOfDay = irradiationData.getRealRadiationWhForTimeOfDay(simulatedTimeUnit.getTime().getTime());
        logger.debug("Real Radiance for " + DateTimeUtil.formatTime(simulatedTimeUnit.getTime().getTime()) + " is: " + realRadiationWhForTimeOfDay);
        simulatedTimeUnit.setRealRadianceEnergy(realRadiationWhForTimeOfDay);
    }

    private void calculateRealRadianceFromLocalData(Month month, PVPowerTime radianceEnergy,
                                                    SunGeoData sunGeoData, PVTimeUnit simulatedTimeUnit) throws SunGeoData.LocalSunDataLoadException {
        simulatedTimeUnit.setHorizontalRadianceEnergy(radianceEnergy.getPower());
        double radianceFactor = sunGeoData.getRadianceFactorForTimeOfDay(month, simulatedTimeUnit.getTime().getTime(),
                pvGenerator.getSolarPanelField().getVerticalAngle(), pvGenerator.getSolarPanelField().getAzimut());
        simulatedTimeUnit.setRealRadianceEnergy(simulatedTimeUnit.getHorizontalRadianceEnergy() * radianceFactor);
    }

    private void simulateTimeUnit(PVSimulationElement simulatedTimeUnit) {
        simulatedTimeUnit.setIdealProduction(PVMath.calculateIdealProduction(
                simulatedTimeUnit.getRealRadianceEnergy(),
                pvGenerator.getSolarPanelField().getSolarPanel().getEfficiency(),
                pvGenerator.getSolarPanelField().getTotalSize()));
        simulatedTimeUnit.setRealProduction(PVMath.calculateRealProduction(simulatedTimeUnit.getIdealProduction(),
                pvGenerator.getPerformanceRatio(), pvGenerator.getShadowFactor()));
        simulatedTimeUnit.setProductionSurplus(PVMath.calculateProductionSurplus(simulatedTimeUnit.getRealProduction(),
                simulatedTimeUnit.getConsumedPower()));
        if (simulatedTimeUnit.getProductionSurplus() >= 0) {
            double tooMuch = pvGenerator.getBattery().charge(simulatedTimeUnit.getProductionSurplus());
            if (tooMuch > 0) {
                simulatedTimeUnit.setPowerToGrid(tooMuch);
            }
        } else {
            double tooLittle = pvGenerator.getBattery().discharge(Math.abs(simulatedTimeUnit.getProductionSurplus()));
            if (tooLittle > 0) {
                simulatedTimeUnit.setPowerPurchased(tooLittle);
            }
        }
        simulatedTimeUnit.setBatteryChargeLevel(pvGenerator.getBattery().getChargeLevel());
    }

}
