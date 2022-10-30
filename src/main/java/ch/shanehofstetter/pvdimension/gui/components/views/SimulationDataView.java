package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;


public class SimulationDataView extends BorderPane {

    protected ComboBox monthCombo;

    protected String tooltipText = "Die Simulation wird für eine Woche dieses Monats durchgeführt.";

    public SimulationDataView() {
//        setText("Simulations-Daten");
        VBox layout = new VBox(4);
        HBox monthBox = new HBox(4);
        Label titleLabel = new Label("Monat:");
        titleLabel.setTooltip(new Tooltip(tooltipText));
        monthBox.getChildren().add(titleLabel);
        ObservableList<String> options = FXCollections.observableArrayList();
        monthCombo = new ComboBox();
        //noinspection unchecked
        monthCombo.setItems(options);
        for (Month month : Month.values()) {
            options.add(month.getDisplayName(TextStyle.FULL, Locale.GERMAN));
        }
        monthBox.getChildren().add(monthCombo);

        monthCombo.getSelectionModel().select(0);
        layout.getChildren().add(monthBox);
        setCenter(layout);
//        setContent(layout);
    }


}
