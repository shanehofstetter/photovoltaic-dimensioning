
package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class ExtendedLineChart extends LineChart<String, Number> {

    static final Logger logger = LogManager.getLogger();

    public static double defaultLowerLimit = 0;
    public static double defaultUpperLimit = 100;
    public static double defaultTickUnit = 100;

    private List<ChartInputListener> listeners = new ArrayList<>();
    private boolean isInteractive;
    private double lowerLimit = 0;
    private double upperLimit = 0;

    public ExtendedLineChart(String chartTitle, boolean isInteractive) {
        this(chartTitle, isInteractive, null);
    }

    public ExtendedLineChart(String chartTitle, boolean isInteractive, String yAxisTitle) {
        this(chartTitle, isInteractive, yAxisTitle, null);
    }

    public ExtendedLineChart(String chartTitle, boolean isInteractive, String yAxisTitle, String xAxisTitle) {
        this(chartTitle, isInteractive, yAxisTitle, xAxisTitle, defaultLowerLimit, defaultUpperLimit);
    }

    public ExtendedLineChart(String chartTitle, boolean isInteractive, String yAxisTitle, String xAxisTitle, double lowerLimit, double upperLimit) {
        super(new CategoryAxis(), new NumberAxis(lowerLimit, upperLimit, defaultTickUnit));
        this.setTitle(chartTitle);
        this.setAnimated(false);
        this.isInteractive = isInteractive;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        if (xAxisTitle != null) {
            getXAxis().setLabel(xAxisTitle);
        }
        if (yAxisTitle != null) {
            getYAxis().setLabel(yAxisTitle);
        }
//        ((NumberAxis)getYAxis()).setForceZeroInRange(false);
    }

    public void setData(ArrayList<XYChart.Series<String, Number>> seriesArrayList) {
        this.getData().clear();
        for (XYChart.Series<String, Number> series : seriesArrayList) {
            this.getData().add(series);
        }
        if (isInteractive) {
            makeInteractive(getYAxis());
        }
    }

    public void setSingleLineData(XYChart.Series<String, Number> series) {
        this.getData().clear();
        this.getData().add(series);
        if (isInteractive) {
            makeInteractive(getYAxis());
        }
    }

    /**
     * Make the Chart Interactive by enabling dragging of value-points
     * Changes the values of the original chartDataSet Object directly and limits dragging by
     * lower- and upperLimit
     *
     * @param yAxis The axis on which the values are based on
     */
    public void makeInteractive(Axis<Number> yAxis) {
        for (Series<String, Number> dataLine : this.getData()) {
            for (Data<String, Number> data : dataLine.getData()) {
                Node node = data.getNode();
                node.setCursor(Cursor.HAND);
                node.setOnMouseReleased(e -> {
                    logger.debug("setOnMouseReleased");
                    Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                    double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                    Number y = yAxis.getValueForDisplay(yAxisLoc);
                    limitYaxisDrag(data, dataLine, y, true);
                });
                node.setOnMouseDragged(e -> {
                    logger.debug("setOnMouseDragged");
                    Point2D pointInScene = new Point2D(e.getSceneX(), e.getSceneY());
                    double yAxisLoc = yAxis.sceneToLocal(pointInScene).getY();
                    Number y = yAxis.getValueForDisplay(yAxisLoc);
                    limitYaxisDrag(data, dataLine, y, false);
                });
            }
        }

    }

    public void addListener(ChartInputListener toAdd) {
        listeners.add(toAdd);
    }

    public void valueChanged(double value, int atIndex) {
        for (ChartInputListener listener : listeners) {
            listener.valueChanged(value, atIndex);
        }
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    private void limitYaxisDrag(Data<String, Number> data, Series<String, Number> dataLine, Number y, boolean triggerEvent) {
        double value = y.doubleValue();
        if (y.doubleValue() > upperLimit) {
            data.setYValue(upperLimit);
            value = upperLimit;
        } else if (y.doubleValue() < lowerLimit) {
            data.setYValue(lowerLimit);
            value = lowerLimit;
        } else {
            data.setYValue(y);
        }
        if (triggerEvent) {
            valueChanged(value, dataLine.getData().indexOf(data));
        }
    }

    public interface ChartInputListener {

        void valueChanged(double value, int atIndex);

    }
}
