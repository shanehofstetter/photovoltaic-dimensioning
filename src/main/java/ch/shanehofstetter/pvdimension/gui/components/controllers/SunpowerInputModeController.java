package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.SunpowerInputModeView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class SunpowerInputModeController extends SunpowerInputModeView {

    static final Logger logger = LogManager.getLogger();
    private ArrayList<SunPowerInputModeListener> listeners = new ArrayList<>();

    public SunpowerInputModeController() {
        super();
        manualButton.setOnAction(ev -> manualChanged());
        automaticButton.setOnAction(ev -> autoChanged());
    }

    public void setMode(SunPowerInputMode sunPowerInputMode) {
        switch (sunPowerInputMode) {
            case MANUAL:
                manualButton.setSelected(true);
                break;
            case AUTOMATIC:
                automaticButton.setSelected(true);
                break;
        }
        changedInputMode(sunPowerInputMode);
    }

    private void autoChanged() {
        logger.debug("autoChanged: " + automaticButton.isSelected());
        changedInputMode(SunPowerInputMode.AUTOMATIC);
    }

    private void manualChanged() {
        logger.debug("manualChanged: " + manualButton.isSelected());
        changedInputMode(SunPowerInputMode.MANUAL);
    }

    public void addListener(SunPowerInputModeListener listener) {
        this.listeners.add(listener);
    }

    private void changedInputMode(SunPowerInputMode mode) {
        for (SunPowerInputModeListener listener : listeners) {
            listener.changedInputMode(mode);
        }
    }

    public enum SunPowerInputMode {
        MANUAL,
        AUTOMATIC
    }

    public interface SunPowerInputModeListener {
        void changedInputMode(SunPowerInputMode sunPowerInputMode);
    }
}
