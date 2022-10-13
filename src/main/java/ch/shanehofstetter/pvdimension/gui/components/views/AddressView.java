package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;


/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class AddressView extends TitledPane {

    protected TextField streetField;
    protected TextField streetnumberField;
    protected TextField cityField;
    protected TextField zipField;
    protected TextField countryField;
    protected Button submitButton;

    public AddressView() {

        GridPane gridPane = new GridPane();
        gridPane.setVgap(8);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(10, 10, 10, 20));

        Label streetLabel = new Label("Strasse");
        int labelWidth = 105;
        streetLabel.setMinWidth(labelWidth);
        gridPane.add(streetLabel, 0, 0);
        streetField = new TextField();
        gridPane.add(streetField, 1, 0);
        streetnumberField = new TextField();
        streetnumberField.setMaxWidth(45);
        gridPane.add(streetnumberField, 2, 0);

        Label zipLabel = new Label("PLZ");
        gridPane.add(zipLabel, 0, 1);
        zipField = new TextField();
        gridPane.add(zipField, 1, 1);
        GridPane.setColumnSpan(zipField, 2);

        Label cityLabel = new Label("Ort");
        cityLabel.setMinWidth(labelWidth);
        gridPane.add(cityLabel, 0, 2);
        cityField = new TextField();
        gridPane.add(cityField, 1, 2);
        GridPane.setColumnSpan(cityField, 2);

        Label countryLabel = new Label("Land");
        countryLabel.setMinWidth(labelWidth);
        gridPane.add(countryLabel, 0, 3);
        countryField = new TextField();
        gridPane.add(countryField, 1, 3);
        GridPane.setColumnSpan(countryField, 2);

        submitButton = new Button("Ok");
        submitButton.setMinWidth(45);
        gridPane.add(submitButton, 2, 4);

        setCollapsible(false);
        setContent(gridPane);
        setText("Adresse");

    }
}
