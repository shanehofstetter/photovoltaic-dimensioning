package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;

/**
 * Extended Class for easier use of the AreaChart
 * <p>
 * Author: Shane Hofstetter - shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart
 */
public class ExtendedAreaChart extends AreaChart<String, Number> {

    public static double defaultLowerLimit = 0;
    public static double defaultUpperLimit = 1000;
    public static double defaultTickUnit = 100;

    public ExtendedAreaChart(String chartTitle, String yAxisTitle, String xAxisTitle) {
        this(chartTitle, yAxisTitle, xAxisTitle, defaultLowerLimit, defaultUpperLimit);
    }

    public ExtendedAreaChart(String chartTitle, String yAxisTitle, String xAxisTitle, double lowerLimit, double upperLimit) {
        super(new CategoryAxis(), new NumberAxis(lowerLimit, upperLimit, defaultTickUnit));
        this.setTitle(chartTitle);
        this.setAnimated(false);
        if (xAxisTitle != null) {
            getXAxis().setLabel(xAxisTitle);
        }
        if (yAxisTitle != null) {
            getYAxis().setLabel(yAxisTitle);
        }
    }

    public void setData(ArrayList<XYChart.Series<String, Number>> seriesArrayList) {
        this.getData().clear();
        for (XYChart.Series<String, Number> series : seriesArrayList) {
            this.getData().add(series);
            Node line = series.getNode().lookup(".chart-series-area-line");
            line.setStyle("-fx-stroke-width: 2px;");
        }
    }

}
