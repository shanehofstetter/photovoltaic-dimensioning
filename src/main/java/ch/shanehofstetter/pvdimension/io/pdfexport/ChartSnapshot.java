
package ch.shanehofstetter.pvdimension.io.pdfexport;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;

/**
 * With this tiny class it's possible to add take a ChartSnapshot
 * to get the Chartimage Back use the getter function getImage()
 *
 * @author Simon Müller : smueller@xiag.ch
 */
public class ChartSnapshot {

    private static int chartWidth = 1000;
    private static int chartHeight = 500;
    private WritableImage image;

    /**
     * Snapshot with default height and width
     * @param chart JavaFX chart from which the snapshot gets taken
     */
    public ChartSnapshot(Chart chart) {
        this(chart, chartHeight, chartWidth);
    }

    /**
     * Snapshot with custom height and width
     * @param chart JavaFX chart from which the snapshot gets taken
     * @param chartHeight height of the image
     * @param chartWidth width of the image
     */
    public ChartSnapshot(Chart chart, int chartHeight, int chartWidth) {

        // ! DO NOT REFACTOR WITHOUT TESTING IT AFTERWARDS !
        chart.setAnimated(false);
        // somehow it does not work when taking a snapshot of the chart directly, without adding it to a vbox and a scene
        // could be because of a time-delta that is needed to render or something.. would need further testing.
        // for the moment this approach works, may not be the most memory-efficient
        VBox chartContainer = new VBox();
        chartContainer.getChildren().add(chart);
        chartContainer.setMinSize(chartWidth, chartHeight);
        chart.setMinSize(chartWidth, chartHeight);
        @SuppressWarnings("UnusedAssignment") Scene snapshotScene = new Scene(chartContainer);
        this.image = chartContainer.snapshot(new SnapshotParameters(), null);
    }

    /**
     * @return a WritableImage JavaFX image
     */
    public WritableImage getImage() {
        return image;
    }

}
