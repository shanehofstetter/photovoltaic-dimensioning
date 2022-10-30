package ch.shanehofstetter.pvdimension.gui.components.pvcharts.barchart;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;


public class ExtendedBarChart extends BorderPane {
    protected final CategoryAxis xAxis = new CategoryAxis();
    protected final NumberAxis yAxis = new NumberAxis();
    protected BarChart<String, Number> barChart;
    protected String[] barColors = {
            "skyblue",
            "dodgerblue",
            "turquoise",
            "palegreen",
            "orange"
    };

    public BarChart getBarChart() {
        return barChart;
    }

    private void removeLabelsForData(XYChart.Data<String, Number> data) {
        Group parentGroup = (Group) data.getNode().getParent();
        for (Node node : parentGroup.getChildren()) {
            if (node instanceof Text) {
                parentGroup.getChildren().remove(node);
                break;
            }
        }
    }

    protected void addValueLabels(XYChart.Series<String, Number> series, String unitText) {
        for (XYChart.Data<String, Number> data : series.getData()) {
            data.nodeProperty().addListener((ov, oldNode, node) -> {
                if (node != null) {
//                    displayLabelForData(data, unitText);
                    node.setOnMouseEntered(event -> displayLabelForDataOnHover(data, unitText));
                    node.setOnMouseExited(event -> removeLabelsForData(data));
                }
            });
        }
    }

    protected void setBarColors() {
        int index = 0;
        for (XYChart.Series<String, Number> numberSeries : barChart.getData()) {
            for (XYChart.Data<String, Number> data : numberSeries.getData()) {
                data.getNode().setStyle("-fx-bar-fill: " + barColors[index] + ";");
                index++;
            }
        }
    }

    protected void displayLabelForDataOnHover(XYChart.Data<String, Number> data, String dataUnit) {
        final Node node = data.getNode();
        final Text dataText = new Text(Math.round(data.getYValue().doubleValue() * 100.0) / 100.0 + " " + dataUnit);
        Group parentGroup = (Group) node.getParent();

        for (Node node1 : parentGroup.getChildren()) {
            if (node1 instanceof Text) {
                parentGroup.getChildren().remove(node1);
                break;
            }
        }

        parentGroup.getChildren().add(dataText);
        ReadOnlyObjectProperty<Bounds> boundsReadOnlyObjectProperty = node.boundsInParentProperty();
        dataText.setLayoutX(
                Math.round(boundsReadOnlyObjectProperty.get().getMinX() + boundsReadOnlyObjectProperty.get().getWidth() / 2 - dataText.prefWidth(-1) / 2)
        );
        dataText.setLayoutY(
                Math.round(boundsReadOnlyObjectProperty.get().getMinY() - dataText.prefHeight(-1) * 0.2)
        );
    }

    protected void displayLabelForData(XYChart.Data<String, Number> data, String dataUnit) {
        final Node node = data.getNode();
        final Text dataText = new Text(Math.round(data.getYValue().doubleValue() * 100.0) / 100.0 + " " + dataUnit);
        node.parentProperty().addListener((ov, oldParent, parent) -> {
            Group parentGroup = (Group) parent;
            parentGroup.getChildren().add(dataText);
        });
        node.boundsInParentProperty().addListener((ov, oldBounds, bounds) -> {
            dataText.setLayoutX(
                    Math.round(bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2)
            );
            dataText.setLayoutY(
                    Math.round(bounds.getMinY() - dataText.prefHeight(-1) * 0.2)
            );
        });
    }
}
