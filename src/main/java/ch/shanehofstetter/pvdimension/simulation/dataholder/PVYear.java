package ch.shanehofstetter.pvdimension.simulation.dataholder;

import ch.shanehofstetter.pvdimension.io.csv.CSVWriteable;

import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;


public class PVYear extends PVSimulationElement implements Serializable, CSVWriteable {
    private ArrayList<PVMonth> pvMonths;

    public PVYear() {
    }

    public PVYear(ArrayList<PVMonth> pvMonths) {
        this.pvMonths = pvMonths;
    }

    public ArrayList<PVMonth> getPvMonths() {
        return pvMonths;
    }

    public void setPvMonths(ArrayList<PVMonth> pvMonths) {
        this.pvMonths = pvMonths;
    }

    @Override
    public ArrayList<String> getCSVHeaders() {
        ArrayList<String> headers = super.getCSVHeaders();
        headers.add(0, "Monat");
        return headers;
    }

    @Override
    public ArrayList<ArrayList<String>> getCSVData() {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (int i = 0; i < pvMonths.size(); i++) {
            ArrayList<String> content = new ArrayList<>();
            content.add(Month.of(i + 1).getDisplayName(TextStyle.FULL, Locale.GERMAN));
            content.addAll(pvMonths.get(i).getCSVData().get(0));
            data.add(content);
        }
        return data;
    }
}
