package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.widgets.InputWidget;
import javafx.scene.layout.VBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class BatteryView extends VBox {

    protected static double DEFAULT_BATTERY_CAPACITY_KWH = 5;
    protected static double DEFAULT_BATTERY_INITIAL_LEVEL_KWH = 0;
    protected InputWidget capacityWidget;
    protected InputWidget initialChargeLevelWidget;

    public BatteryView() {
        capacityWidget = new InputWidget("Kapazit\u00E4t [kWh]", true, InputWidget.INPUT_TYPE.DOUBLE, 0, 100, DEFAULT_BATTERY_CAPACITY_KWH);
        capacityWidget.getSlider().setMajorTickUnit(10);
        capacityWidget.getSlider().setMinorTickCount(9);
        capacityWidget.getSlider().setShowTickLabels(true);
        capacityWidget.getSlider().setShowTickMarks(true);
        capacityWidget.getSlider().setSnapToTicks(true);
        capacityWidget.setLabelMinWidth(160);
        getChildren().add(capacityWidget);

        initialChargeLevelWidget = new InputWidget("Anfangs-Ladezustand [kWh]", true, InputWidget.INPUT_TYPE.DOUBLE, 0, 100, DEFAULT_BATTERY_INITIAL_LEVEL_KWH);
        initialChargeLevelWidget.getSlider().setMajorTickUnit(10);
        initialChargeLevelWidget.getSlider().setMinorTickCount(9);
        initialChargeLevelWidget.getSlider().setShowTickLabels(true);
        initialChargeLevelWidget.getSlider().setShowTickMarks(true);
        initialChargeLevelWidget.getSlider().setSnapToTicks(true);
        initialChargeLevelWidget.setLabelMinWidth(160);
        getChildren().add(initialChargeLevelWidget);
    }
}
