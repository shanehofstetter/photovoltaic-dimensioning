package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.views.GeneratorCostSettingView;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import javafx.event.ActionEvent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public class GeneratorCostSettingController extends GeneratorCostSettingView {
    private PVGenerator pvGenerator;
    private List<GeneratorCostSettingChangeListener> listeners = new ArrayList<>();

    public GeneratorCostSettingController() {
        Economy.addListener(this::currencyChanged);
        setCurrency(Economy.getCurrency());
    }

    public void setPVGenerator(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        setValues();
        solarpanelCostField.setOnAction(this::solarpanelCostChanged);
        batteryCostField.setOnAction(this::batteryCostChanged);
        inverterCostField.setOnAction(this::inverterCostChanged);
        planningInstallCostField.setOnAction(this::planningInstallationCostChanged);
    }

    private void currencyChanged(Currency currency) {
        setCurrency(currency);
    }

    public void submitChanges() {
        if (planningInstallCostField.getValue() != pvGenerator.getPlanningAndInstallationCosts().doubleValue()) {
            planningInstallationCostChanged(null);
        }
        if (batteryCostField.getValue() != pvGenerator.getBattery().getCost().doubleValue()) {
            batteryCostChanged(null);
        }
        if (inverterCostField.getValue() != pvGenerator.getInverter().getCost().doubleValue()) {
            inverterCostChanged(null);
        }
        if (solarpanelCostField.getValue() != pvGenerator.getSolarPanelField().getSolarPanel().getCost().doubleValue()) {
            solarpanelCostChanged(null);
        }
    }

    private void setValues() {
        solarpanelCostField.setValue(pvGenerator.getSolarPanelField().getSolarPanel().getCost().doubleValue());
        batteryCostField.setValue(pvGenerator.getBattery().getCost().doubleValue());
        inverterCostField.setValue(pvGenerator.getInverter().getCost().doubleValue());
        planningInstallCostField.setValue(pvGenerator.getPlanningAndInstallationCosts().doubleValue());
    }

    private void planningInstallationCostChanged(ActionEvent actionEvent) {
        pvGenerator.setPlanningAndInstallationCosts(BigDecimal.valueOf(planningInstallCostField.getValue()));
        changedValues();
    }

    private void inverterCostChanged(ActionEvent actionEvent) {
        pvGenerator.getInverter().setCost(BigDecimal.valueOf(inverterCostField.getValue()));
        changedValues();
    }

    private void batteryCostChanged(ActionEvent actionEvent) {
        pvGenerator.getBattery().setCost(BigDecimal.valueOf(batteryCostField.getValue()));
        changedValues();
    }

    private void solarpanelCostChanged(ActionEvent actionEvent) {
        pvGenerator.getSolarPanelField().getSolarPanel().setCost(BigDecimal.valueOf(solarpanelCostField.getValue()));
        changedValues();
    }

    public void addListener(GeneratorCostSettingChangeListener listener) {
        this.listeners.add(listener);
    }

    private void changedValues() {
        listeners.forEach(GeneratorCostSettingChangeListener::generatorCostSettingChanged);
    }

    public interface GeneratorCostSettingChangeListener {
        void generatorCostSettingChanged();
    }
}
