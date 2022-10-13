package ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationElement;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts
 */
public class PVTotalBarChart extends ExtendedBarChart {
    private PVChartSeriesConverter pvChartSeriesConverter = new PVChartSeriesConverter();

    public PVTotalBarChart() {
        initBarChart();
    }

    private void initBarChart() {
        // Don't know if its the cleanest way to make a new chart-object every time data updates
        // but I could'nt get it to work correctly with the data labels.. getting rid of them again is quite difficult..
        // this way everything works fine..
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Kumulierte Werte");
        barChart.setLegendVisible(false);
        yAxis.setLabel("[kWh]");
        setCenter(barChart);
    }

    public void setPVSimulationResult(PVSimulationElement simulationResult) {
        getChildren().remove(getCenter()); //necessary?
        initBarChart();

        XYChart.Series<String, Number> series = pvChartSeriesConverter.getTotalSeries(simulationResult);
        addValueLabels(series, "kWh");
        barChart.getData().add(series);
        setBarColors();
    }

}
