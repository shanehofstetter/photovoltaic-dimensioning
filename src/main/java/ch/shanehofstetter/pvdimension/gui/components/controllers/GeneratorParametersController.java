package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.GeneratorParametersView;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GeneratorParametersController extends GeneratorParametersView {
    private PVGenerator pvGenerator;
    private List<GeneratorParametersControllerListener> listeners = new ArrayList<>();

    public GeneratorParametersController(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
        okButton.setOnAction(this::okButtonPressed);
        performanceFactorField.setOnAction(this::performanceFactorChanged);
        shadowFactorField.setOnAction(this::shadowFactorChanged);
        performanceFactorField.setMaxValue(1);
        performanceFactorField.setMinValue(0);
        shadowFactorField.setMaxValue(1);
        shadowFactorField.setMinValue(0);
        shadowFactorField.setValue(pvGenerator.getShadowFactor());
        performanceFactorField.setValue(pvGenerator.getPerformanceRatio());
    }

    private void shadowFactorChanged(ActionEvent actionEvent) {
        this.pvGenerator.setShadowFactor(shadowFactorField.getValue());
        changed();
    }

    private void performanceFactorChanged(ActionEvent actionEvent) {
        pvGenerator.setPerformanceRatio(performanceFactorField.getValue());
        changed();
    }

    private void okButtonPressed(ActionEvent actionEvent) {
        if (shadowFactorField.getValue() != pvGenerator.getShadowFactor()) {
            shadowFactorChanged(null);
        }
        if (performanceFactorField.getValue() != pvGenerator.getPerformanceRatio()) {
            performanceFactorChanged(null);
        }
        ((Stage) this.getScene().getWindow()).close();
    }

    private void changed() {
        listeners.forEach(GeneratorParametersControllerListener::changed);
    }

    public void addListener(GeneratorParametersControllerListener listener) {
        this.listeners.add(listener);
    }

    public interface GeneratorParametersControllerListener {
        void changed();
    }
}
