package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class EconomicSettingsView extends GridPane {

    protected ComboBox currencyComboBox;

    public EconomicSettingsView() {
        currencyComboBox = new ComboBox();
        Label currencyTitleLabel = new Label("Währung:");
        currencyTitleLabel.setTooltip(new Tooltip("Die angezeigte Währung."));
        currencyTitleLabel.setMinWidth(125);
        add(currencyTitleLabel, 0, 0);
        add(currencyComboBox, 1, 0);
    }
}
