package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components
 */
public class SunpowerInputModeView extends HBox {

    protected RadioButton manualButton;
    protected RadioButton automaticButton;

    protected String manualTooltipText =
            "Im manuellen Modus können Sie die Sonneneinstrahlung für den Zeitraum selber festlegen.";
    protected String autoTooltipText =
            "Im automatischen Modus werden die Sonnendaten anhand der " +
                    "eingegebenen Adresse aus dem Internet geladen. Es ist eine Internetverbindung notwendig!";

    public SunpowerInputModeView() {
        setSpacing(5);
        setPadding(new Insets(5, 5, 5, 5));
        final ToggleGroup group = new ToggleGroup();
        manualButton = new RadioButton("Manuelle Eingabe der Sonneneinstrahlung");
        manualButton.setToggleGroup(group);
        manualButton.setSelected(true);
        manualButton.setTooltip(new Tooltip(manualTooltipText));
        automaticButton = new RadioButton("Autom. Ermittlung der Sonneneinstrahlung");
        automaticButton.setToggleGroup(group);
        automaticButton.setTooltip(new Tooltip(autoTooltipText));
        getChildren().addAll(manualButton, automaticButton);
    }
}
