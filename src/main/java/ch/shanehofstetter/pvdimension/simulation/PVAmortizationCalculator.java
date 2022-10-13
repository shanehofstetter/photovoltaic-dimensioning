package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVAmortizationTime;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVAmortizationTimeElement;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVYear;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation
 */
public class PVAmortizationCalculator {

    private PVYear pvYear;
    private PVGenerator pvGenerator;

    public PVAmortizationCalculator(PVYear pvYear, PVGenerator pvGenerator) {
        this.pvYear = pvYear;
        this.pvGenerator = pvGenerator;
    }

    /**
     * simulate the amortisation during a time-range of 30 years
     *
     * @return pvAmortizationTime containing simulationresults
     */
    public PVAmortizationTime calculate() {
        // jahresgewinn = gewinn + kostenersparnis
        // amortisationszeit = Total-Kosten/Jahresgewinn
        // Restkosten für einen zeitraum von 30 Jahren rechnen, in 3 Jahren abstand

        PVAmortizationTime pvAmortizationTime = new PVAmortizationTime();
        pvAmortizationTime.setYearlyRevenue(pvYear.getTotalEarnings().add(pvYear.getTotalSavings()));
        if (pvAmortizationTime.getYearlyRevenue().compareTo(BigDecimal.ZERO) > 0){
            pvAmortizationTime.setAmortisationTimeYears(pvGenerator.getTotalCosts().divide(pvAmortizationTime.getYearlyRevenue(), BigDecimal.ROUND_HALF_UP).doubleValue());
        }else{
            pvAmortizationTime.setAmortisationTimeYears(0);
        }

        ArrayList<PVAmortizationTimeElement> timeElements = new ArrayList<>();
        // we only want to know the value for every 3 years, starting at year 3
        for (int i = 3; i <= 30; i += 3) {
            PVAmortizationTimeElement timeElement = new PVAmortizationTimeElement(i);
            timeElement.setRevenue(pvAmortizationTime.getYearlyRevenue().multiply(new BigDecimal(i)));
            timeElement.setRemainingCosts(pvGenerator.getTotalCosts().subtract(timeElement.getRevenue()));
            timeElements.add(timeElement);
        }
        pvAmortizationTime.setAmortizationTimeElements(timeElements);

        return pvAmortizationTime;
    }
}
