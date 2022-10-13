package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class CoordinateView extends TitledPane {

    protected Label latitudeValue;
    protected Label longitudeValue;

    protected double labelWidth = 80;

    public CoordinateView() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label latitudeLabel = new Label("Breitengrad:");
        latitudeLabel.setMinWidth(labelWidth);
        Label longitudeLabel = new Label("L\u00E4ngengrad:");
        longitudeLabel.setMinWidth(labelWidth);
        gridPane.add(latitudeLabel, 0, 0);
        gridPane.add(longitudeLabel, 0, 1);

        latitudeValue = new Label("\uc2b0");
        longitudeValue = new Label("\uc2b0");
        gridPane.add(latitudeValue, 1, 0);
        gridPane.add(longitudeValue, 1, 1);

        setContent(gridPane);

        setCollapsible(false);
        setText("Koordinaten");
    }
}
