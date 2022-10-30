package ch.shanehofstetter.pvdimension.simulation.dataholder;

import ch.shanehofstetter.pvdimension.simulation.PVWeekSimulator;

import java.io.Serializable;
import java.util.ArrayList;


public class PVSimulationData implements Serializable {
    private PVWeek pvWeek;
    private PVYear pvYear;
    private PVAmortizationTime pvAmortizationTime;

    public PVSimulationData() {
        this.pvWeek = PVWeekSimulator.makeDefaultWeek();
        this.pvYear = new PVYear();
    }

    public PVWeek getPvWeek() {
        return pvWeek;
    }

    public void setPvWeek(PVWeek pvWeek) {
        this.pvWeek = pvWeek;
    }

    public ArrayList<PVMonth> getPvMonths() {
        return pvYear.getPvMonths();
    }

    public void setPvMonths(ArrayList<PVMonth> pvMonths) {
        this.pvYear.setPvMonths(pvMonths);
    }

    public PVSimulationElement getYearTotal() {
        return pvYear;
    }

    public PVYear getPvYear() {
        return pvYear;
    }

    public void setPvYear(PVYear pvYear) {
        this.pvYear = pvYear;
    }

    public PVAmortizationTime getPvAmortizationTime() {
        return pvAmortizationTime;
    }

    public void setPvAmortizationTime(PVAmortizationTime pvAmortizationTime) {
        this.pvAmortizationTime = pvAmortizationTime;
    }
}
