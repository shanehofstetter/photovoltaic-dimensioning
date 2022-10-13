package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.widgets.DoubleTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class GeneratorParametersView extends BorderPane {

    protected Button okButton;
    protected DoubleTextField performanceFactorField;
    protected DoubleTextField shadowFactorField;
    protected String performanceFactorTooltipText =
            "Quotient aus dem tatsächlichen Nutzertrag einer Anlage und ihrem Sollertrag (zwischen 0 und 1). \n" +
                    "Dieser Wert muss für die Simulation abgeschätzt werden, weil er sich erst mit realen Messdaten ermitteln lässt.\n" +
                    "Ein Wert zwischen 0.8 und 0.9 ist normal.";
    protected String shadowFactorTooltipText = "Der Verschattungsfaktor gibt an, " +
            "wie stark sich die Umgebung der PV-Anlage durch Verschattung auf dessen " +
            "Ertrag auswirkt.\n(1 bedeutet keine Verschattung)";

    public GeneratorParametersView() {

        GridPane gridPane = new GridPane();
        gridPane.setVgap(8);
        gridPane.setHgap(8);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        Label performanceFactorLabel = new Label("Performance Ratio:");
        performanceFactorLabel.setTooltip(new Tooltip(performanceFactorTooltipText));
        gridPane.add(performanceFactorLabel, 0, 0);

        performanceFactorField = new DoubleTextField();
        performanceFactorField.setValue(0);
        performanceFactorField.setTooltip(new Tooltip(performanceFactorTooltipText));
        gridPane.add(performanceFactorField, 1, 0);

        Label shadowFactorLabel = new Label("Verschattungsfaktor:");
        shadowFactorLabel.setTooltip(new Tooltip(shadowFactorTooltipText));
        gridPane.add(shadowFactorLabel, 0, 1);

        shadowFactorField = new DoubleTextField();
        shadowFactorField.setValue(0);
        shadowFactorField.setTooltip(new Tooltip(shadowFactorTooltipText));
        gridPane.add(shadowFactorField, 1, 1);

        setCenter(gridPane);

        okButton = new Button("Ok");
        okButton.setMinWidth(75);
        okButton.setMaxWidth(75);
        okButton.setDefaultButton(true);
        HBox hBox = new HBox(okButton);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        setBottom(hBox);
    }
}
