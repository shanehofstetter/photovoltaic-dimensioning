package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.widgets.DoubleTextField;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class GeneratorCostSettingView extends GridPane {

    protected DoubleTextField solarpanelCostField;
    protected DoubleTextField batteryCostField;
    protected DoubleTextField inverterCostField;
    protected DoubleTextField planningInstallCostField;

    protected String solarpanelCostTooltipText = "Kosten eines Solar-Panels.";
    protected String batteryCostTooltipText = "Kosten der Batterie.";
    protected String inverterCostFieldTooltipText = "Kosten des Wechselrichters.";
    protected String planningInstallCostFieldTooltipText = "Total Kosten der Planung sowie Installation der PV-Anlage.";

    protected Currency currency;
    protected List<Label> currencyLabels = new ArrayList<>();

    protected int titleLabelMinWidth = 150;
    protected int inputFieldMaxWidth = 100;

    public GeneratorCostSettingView() {
        addTitleLabels();

        setHgap(7);
        setVgap(8);

        solarpanelCostField = new DoubleTextField();
        solarpanelCostField.setValue(0);
        solarpanelCostField.setTooltip(new Tooltip(solarpanelCostTooltipText));
        solarpanelCostField.setMaxWidth(inputFieldMaxWidth);

        batteryCostField = new DoubleTextField();
        batteryCostField.setValue(0);
        batteryCostField.setTooltip(new Tooltip(batteryCostTooltipText));
        batteryCostField.setMaxWidth(inputFieldMaxWidth);

        inverterCostField = new DoubleTextField();
        inverterCostField.setValue(0);
        inverterCostField.setTooltip(new Tooltip(inverterCostFieldTooltipText));
        inverterCostField.setMaxWidth(inputFieldMaxWidth);

        planningInstallCostField = new DoubleTextField();
        planningInstallCostField.setValue(0);
        planningInstallCostField.setTooltip(new Tooltip(planningInstallCostFieldTooltipText));
        planningInstallCostField.setMaxWidth(inputFieldMaxWidth);

        add(solarpanelCostField, 1, 0);
        add(batteryCostField, 1, 1);
        add(inverterCostField, 1, 2);
        add(planningInstallCostField, 1, 3);

        for (int i = 0; i < 4; i++) {
            Label currencyLabel = new Label("");
            currencyLabels.add(currencyLabel);
            add(currencyLabel, 2, i);
        }
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        for (Label currencyLabel : currencyLabels) {
            currencyLabel.setText(currency.getCurrencyCode());
        }
    }

    private void addTitleLabels() {
        Label solarPanelCostLabel = new Label("Solar-Panel:");
        Label batteryCostLabel = new Label("Batterie:");
        Label inverterCostLabel = new Label("Wechselrichter:");
        Label planningInstallationCostLabel = new Label("Planung und Installation:");

        solarPanelCostLabel.setMinWidth(titleLabelMinWidth);
        batteryCostLabel.setMinWidth(titleLabelMinWidth);
        inverterCostLabel.setMinWidth(titleLabelMinWidth);
        planningInstallationCostLabel.setMinWidth(titleLabelMinWidth);

        solarPanelCostLabel.setTooltip(new Tooltip(solarpanelCostTooltipText));
        batteryCostLabel.setTooltip(new Tooltip(batteryCostTooltipText));
        inverterCostLabel.setTooltip(new Tooltip(inverterCostFieldTooltipText));
        planningInstallationCostLabel.setTooltip(new Tooltip(planningInstallCostFieldTooltipText));

        add(solarPanelCostLabel, 0, 0);
        add(batteryCostLabel, 0, 1);
        add(inverterCostLabel, 0, 2);
        add(planningInstallationCostLabel, 0, 3);
    }
}
