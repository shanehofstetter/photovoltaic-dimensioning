
package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.widgets.InputWidget;
import ch.shanehofstetter.pvdimension.gui.components.widgets.ValueLabelPair;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class SolarPanelView extends BorderPane {

    public static final int DEFAULT_AMOUNT = 10;
    public static final double DEFAULT_POWER = 344.4;
    public static final double DEFAULT_SIZE = 1.64;
    public static final double DEFAULT_EFFICIENCY = 0.21;
    public static final double DEFAULT_ANGLE = 10;
    public static final double DEFAULT_ACIMUT = 0;

    private InputWidget amount;
    private ValueLabelPair power;
    private ValueLabelPair totalPower;
    private InputWidget size;
    private InputWidget efficiency;
    private InputWidget angle;
    private InputWidget acimut;

    private String amountTooltipText = "Anzahl der Photovoltaik-Panels.";
    private String powerTooltipText = "Leistung (in Watt) eines PV-Panels (abhängig vom Modulwirkungsgrad und der Modulgrösse).";
    private String totalPowerTooltipText = "Komplette Anlagen-Leistung (Watt) ergibt sich aus Panel-Anzahl * Panel-Leistung.";
    private String sizeTooltipText = "Fläche eines Photovoltaik-Panels, in Quadratmeter.";
    private String efficiencyTooltipText = "Wirkungsgrad eines Photovoltaik-Panels zwischen 0.0 und 1.0";
    private String angleTooltipText = "Neigungswinkel der Panels, zwischen 0° und 90° (vertikal aufgestellt).";
    private String acimutTooltipText = "Horizontalwinkel der Panels, zwischen -180° und 180° wobei 0° Süden ist.";

    public SolarPanelView() {
        createInputFields();
    }

    public void createInputFields() {
        VBox fieldBox = new VBox();
        fieldBox.setSpacing(5);
        fieldBox.setPadding(new Insets(0, 0, 0, 0));
        amount = new InputWidget("Anzahl:", false, InputWidget.INPUT_TYPE.INTEGER, 0, 1000, DEFAULT_AMOUNT);
        amount.setPrecision(1);
        amount.setTooltipText(amountTooltipText);

        power = new ValueLabelPair("Leistung:", "0W", true);
        power.setTitleLabelWidth(125);
        power.setTooltipText(powerTooltipText);
        power.setPadding(new Insets(5, 10, 5, 10));

        totalPower = new ValueLabelPair("Generator-Leistung:", "0W", true);
        totalPower.setTitleLabelWidth(125);
        totalPower.setTooltipText(totalPowerTooltipText);
        totalPower.setPadding(new Insets(5, 10, 5, 10));

        size = new InputWidget("Grösse [m\u00B2]:", false, InputWidget.INPUT_TYPE.DOUBLE, 0, 10, DEFAULT_SIZE);
        size.setTooltipText(sizeTooltipText);

        efficiency = new InputWidget("Wirkungsgrad:", true, InputWidget.INPUT_TYPE.DOUBLE, 0, 1, DEFAULT_EFFICIENCY);
        efficiency.setPrecision(100.0);
        efficiency.getSlider().setMajorTickUnit(0.25);
        efficiency.getSlider().setMinorTickCount(9);
        efficiency.getSlider().setShowTickLabels(true);
        efficiency.getSlider().setShowTickMarks(true);
        efficiency.getSlider().setSnapToTicks(true);
        efficiency.setTooltipText(efficiencyTooltipText);

        angle = new InputWidget("Aufstellwinkel:", true, InputWidget.INPUT_TYPE.DOUBLE, 0, 90, DEFAULT_ANGLE);
        angle.setPrecision(100.0);
        angle.getSlider().setMajorTickUnit(10);
        angle.getSlider().setMinorTickCount(9);
        angle.getSlider().setShowTickLabels(true);
        angle.getSlider().setShowTickMarks(true);
        angle.getSlider().setSnapToTicks(true);
        angle.setTooltipText(angleTooltipText);

        acimut = new InputWidget("Azimut:", true, InputWidget.INPUT_TYPE.DOUBLE, -180, 180, DEFAULT_ACIMUT);
        acimut.setPrecision(100.0);
        acimut.getSlider().setMajorTickUnit(30);
        acimut.getSlider().setMinorTickCount(9);
        acimut.getSlider().setShowTickLabels(true);
        acimut.getSlider().setShowTickMarks(true);
        acimut.getSlider().setSnapToTicks(true);
        acimut.setTooltipText(acimutTooltipText);

        fieldBox.getChildren().addAll(amount, size, efficiency, angle, acimut, new Separator(Orientation.HORIZONTAL), power, totalPower);
        setCenter(fieldBox);
    }

    /**
     * @return the amount
     */
    public InputWidget getAmount() {
        return amount;
    }

    /**
     * @return the power
     */
    public ValueLabelPair getPower() {
        return power;
    }

    /**
     * @return the size
     */
    public InputWidget getSize() {
        return size;
    }

    /**
     * @return the efficiency
     */
    public InputWidget getEfficiency() {
        return efficiency;
    }

    /**
     * @return the angle
     */
    public InputWidget getAngle() {
        return angle;
    }

    /**
     * @return the acimut
     */
    public InputWidget getAcimut() {
        return acimut;
    }

    public ValueLabelPair getTotalPower() {
        return totalPower;
    }
}
