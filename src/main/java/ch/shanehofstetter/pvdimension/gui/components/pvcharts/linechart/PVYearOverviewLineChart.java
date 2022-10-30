package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVMonth;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart
 */
public class PVYearOverviewLineChart extends ExtendedLineChart {

    public static final String DEFAULT_X_AXIS_TITLE = "Jahres-Verlauf";
    public static final String DEFAULT_Y_AXIS_TITLE = "[kWh]";

    public PVYearOverviewLineChart(String chartTitle) {
        super(chartTitle, false, DEFAULT_Y_AXIS_TITLE, DEFAULT_X_AXIS_TITLE);
        setAnimated(true);
        setLegendVisible(true);
        getXAxis().setLabel(null);
        getYAxis().setAutoRanging(true);
    }

    public void setPVMonths(ArrayList<PVMonth> pvMonths) {
        PVChartSeriesConverter converter = new PVChartSeriesConverter();
        ArrayList<XYChart.Series<String, Number>> overviewResultChartSeries = converter.getMonthlyResultSeriesForYear(pvMonths);
        setData(overviewResultChartSeries);
    }
}
