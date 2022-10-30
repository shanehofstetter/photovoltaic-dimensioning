package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVWeek;

import java.util.ArrayList;


public class PVWeekOverviewLineChart extends ExtendedLineChart {

    public static final String DEFAULT_X_AXIS_TITLE = "Wochen-Verlauf";
    public static final String DEFAULT_Y_AXIS_TITLE = "[Wh]";
    private PVWeek pvWeek;

    public PVWeekOverviewLineChart(String chartTitle) {
        super(chartTitle, false, DEFAULT_Y_AXIS_TITLE, DEFAULT_X_AXIS_TITLE);
        setAnimated(true);
        setLegendVisible(true);
        getXAxis().setLabel(null);
        getYAxis().setAutoRanging(true);
    }

    public PVWeek getPvWeek() {
        return pvWeek;
    }

    public void setPvWeek(PVWeek pvWeek) {
        this.pvWeek = pvWeek;
        PVChartSeriesConverter converter = new PVChartSeriesConverter();
        ArrayList<Series<String, Number>> overviewResultChartSeriesForWeek = converter.getDailyResultSeriesForWeek(pvWeek);
        setData(overviewResultChartSeriesForWeek);
    }
}
