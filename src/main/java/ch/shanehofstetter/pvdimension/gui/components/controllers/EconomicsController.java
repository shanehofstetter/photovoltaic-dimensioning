package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.economy.PowerPrice;
import ch.shanehofstetter.pvdimension.gui.components.views.EconomicsView;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class EconomicsController extends EconomicsView {

    private PVGenerator pvGenerator;
    private List<EconomicsChangeListener> listeners = new ArrayList<>();

    public EconomicsController(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        powerPriceController.addListener(this::powerPriceChanged);
        economicSettingsController.addListener(this::economicSettingsChanged);
        generatorCostSettingController.addListener(this::generatorCostsChanged);
        generatorCostSettingController.setPVGenerator(pvGenerator);
        okButton.setOnAction(this::okButtonPressed);
    }

    public void addListener(EconomicsChangeListener listener) {
        this.listeners.add(listener);
    }

    private void okButtonPressed(ActionEvent actionEvent) {
        generatorCostSettingController.submitChanges();
        powerPriceController.submitChanges();
        ((Stage) this.getScene().getWindow()).close();
    }

    private void generatorCostsChanged() {
        changed();
    }

    private void economicSettingsChanged() {
        pvGenerator.setCurrencyCode(Economy.getCurrency().getCurrencyCode());
    }

    private void powerPriceChanged(PowerPrice powerPrice) {
        this.pvGenerator.setPowerPrice(powerPrice);
        changed();
    }

    private void changed() {
        listeners.forEach(EconomicsChangeListener::changed);
    }

    public interface EconomicsChangeListener {
        void changed();
    }
}
