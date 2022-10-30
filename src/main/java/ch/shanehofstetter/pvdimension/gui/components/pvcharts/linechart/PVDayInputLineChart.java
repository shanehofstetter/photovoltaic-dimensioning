package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVInputDataType;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVDay;
import javafx.scene.chart.NumberAxis;

import java.util.ArrayList;
import java.util.List;


public class PVDayInputLineChart extends ExtendedLineChart {

    private PVDay pvDay;
    private PVInputDataType pvInputDataType;
    private List<PVDayInputChartListener> listeners = new ArrayList<>();

    // Maybe it would have been better if we made a subclass of PVDayInputChart for each Enum in PVInputDataType
    // instead of doing if else statements everywhere...

    public PVDayInputLineChart(String chartTitle, String xAxisTitle, String yAxisTitle, PVInputDataType pvInputDataType) {
        super(chartTitle, true, yAxisTitle, xAxisTitle);
        this.pvInputDataType = pvInputDataType;
        if (pvInputDataType == PVInputDataType.SUN_POWER) {
            setupAxis(false, 1000, 100, 1000);
        } else if (pvInputDataType == PVInputDataType.POWER_CONSUMPTION) {
            setupAxis(false, 5000, 250, 5000);
        }
        setLegendVisible(false);
    }

    public PVDayInputLineChart(String chartTitle, boolean isInteractive, String xAxisTitle, String yAxisTitle, double lowerLimit, double upperLimit) {
        super(chartTitle, isInteractive, yAxisTitle, xAxisTitle, lowerLimit, upperLimit);
        setLegendVisible(false);
    }

    private void setupAxis(boolean autoRanging, int upperBound, int tickUnit, int upperLimit) {
        getYAxis().setAutoRanging(autoRanging);
        ((NumberAxis) getYAxis()).setUpperBound(upperBound);
        ((NumberAxis) getYAxis()).setTickUnit(tickUnit);
        setUpperLimit(upperLimit);
    }

    public void setPVDay(PVDay pvDay, PVInputDataType pvInputDataType) {
        this.pvDay = pvDay;
        this.pvInputDataType = pvInputDataType;
        PVChartSeriesConverter converter = new PVChartSeriesConverter();
        Series<String, Number> hourlyChartSeriesForDay = converter.getHourlyChartSeriesForDay(pvDay, pvInputDataType);
        this.setSingleLineData(hourlyChartSeriesForDay);
    }

    public PVDay getPVDay() {
        return pvDay;
    }

    @Override
    public void valueChanged(double value, int atIndex) {
        switch (pvInputDataType) {
            case POWER_CONSUMPTION:
                pvDay.getPowerConsumptions().get(atIndex).setPower(value);
                break;
            case SUN_POWER:
                pvDay.getSunPowers().get(atIndex).setPower(value);
                break;
        }
        super.valueChanged(value, atIndex);
        dayChanged(pvDay);
    }

    public void addListener(PVDayInputChartListener toAdd) {
        listeners.add(toAdd);
    }

    public void dayChanged(PVDay pvDay) {
        for (PVDayInputChartListener listener : listeners) {
            listener.dayChanged(pvDay);
        }
    }


    public interface PVDayInputChartListener {
        void dayChanged(PVDay day);
    }
}
