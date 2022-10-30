package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVDay;

import java.util.ArrayList;


public class PVDayResultLineChart extends ExtendedAreaChart {

    public static final String DEFAULT_X_AXIS_TITLE = "Tages-Verlauf";
    public static final String DEFAULT_Y_AXIS_TITLE = "[Wh]";
    private PVDay pvDay;

    public PVDayResultLineChart(String chartTitle) {
        super(chartTitle, DEFAULT_Y_AXIS_TITLE, DEFAULT_X_AXIS_TITLE);
        setAnimated(true);
        getYAxis().setAutoRanging(true);
        setLegendVisible(true);
    }

    public PVDay getPVDay() {
        return pvDay;
    }

    public void setPVDay(PVDay pvDay) {
        this.pvDay = pvDay;
        PVChartSeriesConverter converter = new PVChartSeriesConverter();
        ArrayList<Series<String, Number>> hourlyResultChartSeriesForDay = converter.getHourlyResultSeriesForDay(pvDay);
        setData(hourlyResultChartSeriesForDay);
    }
}
