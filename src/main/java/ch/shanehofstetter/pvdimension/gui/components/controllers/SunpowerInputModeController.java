package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.SunpowerInputModeView;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.controllers
 */
public class SunpowerInputModeController extends SunpowerInputModeView {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MainPaneController.class);
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
