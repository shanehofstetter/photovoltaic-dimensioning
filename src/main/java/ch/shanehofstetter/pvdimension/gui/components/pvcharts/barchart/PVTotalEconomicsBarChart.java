package ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationElement;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.Currency;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts
 */
public class PVTotalEconomicsBarChart extends ExtendedBarChart {
    private PVChartSeriesConverter pvChartSeriesConverter = new PVChartSeriesConverter();

    private PVSimulationElement pvSimulationElement;

    public PVTotalEconomicsBarChart() {
        initBarChart();
        Economy.addListener(this::currencyChanged);
    }

    private void currencyChanged(Currency currency) {
        initBarChart();
        setPVData();
    }

    private void initBarChart() {
        // Don't know if its the cleanest way to make a new chart-object every time data updates
        // but I couldn't get it to work correctly with the data labels.. getting rid of them again is quite difficult..
        // this way everything works fine..
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Kostenübersicht");
        barChart.setLegendVisible(false);
        yAxis.setLabel("[" + Economy.getCurrencyCode() + "]");
        setCenter(barChart);
    }

    private void setPVData() {
        if (pvSimulationElement != null) {
            setPVData(pvSimulationElement);
        }
    }

    public void setPVData(PVSimulationElement pvSimulationElement) {
        getChildren().remove(getCenter()); //necessary?
        initBarChart();
        this.pvSimulationElement = pvSimulationElement;
        XYChart.Series<String, Number> series = pvChartSeriesConverter.getTotalEconomicalSeries(pvSimulationElement);
        addValueLabels(series, Economy.getCurrencyCode());
        barChart.getData().add(series);
        setBarColors();
    }

}
