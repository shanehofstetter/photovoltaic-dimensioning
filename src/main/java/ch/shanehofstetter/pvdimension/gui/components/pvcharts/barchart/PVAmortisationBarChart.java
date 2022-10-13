package ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVChartSeriesConverter;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVAmortizationTime;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Currency;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts
 */
public class PVAmortisationBarChart extends ExtendedBarChart {
    private PVChartSeriesConverter pvChartSeriesConverter = new PVChartSeriesConverter();

    private PVAmortizationTime pvAmortizationTime;

    public PVAmortisationBarChart() {
        initBarChart();
        Economy.addListener(this::currencyChanged);
    }

    private void currencyChanged(Currency currency) {
        initBarChart();
        setPVData();
    }

    private void initBarChart() {
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Amortisation");
        barChart.setLegendVisible(true);
        yAxis.setLabel("[" + Economy.getCurrencyCode() + "]");
        setCenter(barChart);
    }

    private void setPVData() {
        if (pvAmortizationTime != null) {
            setPVData(pvAmortizationTime);
        }
    }

    public void setPVData(PVAmortizationTime amortizationTime) {
        getChildren().remove(getCenter());
        initBarChart();
        this.pvAmortizationTime = amortizationTime;
        ArrayList<XYChart.Series<String, Number>> amortisationSeries = pvChartSeriesConverter.getAmortisationSeries(pvAmortizationTime);
        for (XYChart.Series<String, Number> series : amortisationSeries) {
            addValueLabels(series, Economy.getCurrencyCode());
        }
        barChart.getData().addAll(amortisationSeries);
    }

}
