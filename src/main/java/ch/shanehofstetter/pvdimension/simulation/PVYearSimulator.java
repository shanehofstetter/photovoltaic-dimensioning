package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.irradiationdata.IrradiationDataFetcher;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVMonth;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationElement;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVYear;
import ch.shanehofstetter.pvdimension.sungeodata.SunGeoData;
import ch.shanehofstetter.pvdimension.util.DateTimeUtil;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;


public class PVYearSimulator extends PVSimulator {

    private PVWeekSimulator pvWeekSimulator;
    private PVYear pvYear;

    /**
     * @param pvGenerator PV-Generator
     */
    public PVYearSimulator(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        this.pvWeekSimulator = new PVWeekSimulator(pvGenerator);
        this.pvWeekSimulator.addListener(new PVSimulatorListener() {
            @Override
            public void noProducedPower() {
                producingNoPower();
            }

            @Override
            public void finishedSimulation(PVSimulationElement pvSimulationElement) {
                reportProgress((pvYear.getPvMonths().size() + 1) * 7, 84); //need +1 because month will not be added yet
            }

            @Override
            public void progress(int progress, int total) {
                reportProgress((pvYear.getPvMonths().size()) * 7 + progress, 84);
            }
        });
        this.pvYear = new PVYear();
        this.pvYear.setPvMonths(new ArrayList<>());
    }

    /**
     * simulates a whole year
     * when simulation finished, finishedSimulation gets called in all listeners
     *
     * @throws MissingDataException if coordinates are missing
     * @throws IrradiationDataFetcher.IrradiationDataFetchException if no internet connection or service not reachable
     * @throws SunGeoData.LocalSunDataLoadException if local files cannot be read or parsed
     */
    public void simulateYear() throws MissingDataException, IrradiationDataFetcher.IrradiationDataFetchException, SunGeoData.LocalSunDataLoadException {
        if (pvGenerator.getSimulationParameters().getSunDataMode() == SunDataMode.WEBSERVICE && pvGenerator.getCoordinates() == null) {
            throw new MissingDataException("Coordinates are required");
        }
        simulationActive = true;
        logger.debug("simulating year...");

        pvYear.getPvMonths().clear();
        for (Month month : Month.values()) {
            logger.debug("simulating month in year: " + month);
            simulateMonth(month);
        }
        makeYearTotal();
        finishedSimulation(pvYear);
        logger.debug("finished year simulation");
        simulationActive = false;
    }

    @Override
    public void setPvGenerator(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        pvWeekSimulator.setPvGenerator(pvGenerator);
        this.pvYear = new PVYear();
        this.pvYear.setPvMonths(new ArrayList<>());
    }

    private void makeYearTotal() {
        pvYear.adoptValues(PVSimulationElement.cumulateSimulationResults(pvYear.getPvMonths()));
    }

    private void simulateMonth(Month month) throws SunGeoData.LocalSunDataLoadException, MissingDataException, IrradiationDataFetcher.IrradiationDataFetchException {
        int amountOfDays = DateTimeUtil.getNumberOfDaysInMonth(month);

        pvWeekSimulator.simulateWeek(month, pvGenerator.getSimulationParameters().getSunDataMode(), false);

        PVSimulationElement averageDay = PVSimulationElement.cumulateSimulationResults(pvGenerator.getSimulationData().getPvWeek().getPVDays());
        averageDay = PVSimulationElement.buildAverage(averageDay, 7);

        pvYear.getPvMonths().add(createMonthFromDay(month, averageDay, amountOfDays));
    }

    private PVMonth createMonthFromDay(Month month, PVSimulationElement averageDay, int amountOfDays) {
        PVMonth pvMonth = new PVMonth(month);
        BigDecimal factor = new BigDecimal(amountOfDays);
        pvMonth.setTotalSavings(averageDay.getTotalSavings().multiply(factor));
        pvMonth.setTotalPowerSellings(averageDay.getTotalPowerSellings().multiply(factor));
        pvMonth.setTotalEarnings(averageDay.getTotalEarnings().multiply(factor));
        pvMonth.setTotalPowerCosts(averageDay.getTotalPowerCosts().multiply(factor));

        pvMonth.setBatteryChargeLevel(averageDay.getBatteryChargeLevel() * amountOfDays);
        pvMonth.setConsumedPower(averageDay.getConsumedPower() * amountOfDays);
        pvMonth.setHorizontalRadianceEnergy(averageDay.getHorizontalRadianceEnergy() * amountOfDays);
        pvMonth.setIdealProduction(averageDay.getIdealProduction() * amountOfDays);
        pvMonth.setPowerPurchased(averageDay.getPowerPurchased() * amountOfDays);
        pvMonth.setRealProduction(averageDay.getRealProduction() * amountOfDays);
        pvMonth.setRealRadianceEnergy(averageDay.getRealRadianceEnergy() * amountOfDays);
        pvMonth.setProductionSurplus(averageDay.getProductionSurplus() * amountOfDays);
        pvMonth.setPowerToGrid(averageDay.getPowerToGrid() * amountOfDays);

        return pvMonth;
    }

}
