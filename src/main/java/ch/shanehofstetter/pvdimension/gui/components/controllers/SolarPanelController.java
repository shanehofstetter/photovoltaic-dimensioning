
package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.SolarPanelView;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Simon Müller
 */
public class SolarPanelController extends SolarPanelView {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(MainPaneController.class);
    private SolarPanelField solarPanelField;
    private ArrayList<SolarPanelControlListener> listeners = new ArrayList<>();

    public SolarPanelController() {
        this(new SolarPanelField());
    }

    public SolarPanelController(SolarPanelField solarPanelField) {
        this.solarPanelField = solarPanelField;
        resetSolarpanelField();
        getEfficiency().addListener(this::efficiencyChanged);
        getAcimut().addListener(this::acimutChanged);
        getAmount().addListener(this::amountChanged);
        getAngle().addListener(this::angleChanged);
//        getPower().addListener(this::powerChanged);
        getSize().addListener(this::sizeChanged);
    }

    public SolarPanelField getSolarPanelField() {
        return solarPanelField;
    }

    public void setSolarPanelField(SolarPanelField solarPanelField) {
        this.solarPanelField = solarPanelField;
        setValues(this.solarPanelField);
        solarPanelFieldChanged();
    }

    public void setSolarPanel(SolarPanel solarPanel) {
        // we do not set the given object because user can still change the data
        // and then the original solarpanel would get changed. we don't want this behaviour if its from a database
        this.solarPanelField.getSolarPanel().setManufacturer(solarPanel.getManufacturer());
        this.solarPanelField.getSolarPanel().setSize(solarPanel.getSize());
        this.solarPanelField.getSolarPanel().setEfficiency(solarPanel.getEfficiency());
        this.solarPanelField.getSolarPanel().setPower(solarPanel.getPower());
        this.solarPanelField.getSolarPanel().setTypeName(solarPanel.getTypeName());
        this.solarPanelField.getSolarPanel().setDetails(solarPanel.getDetails());
        this.solarPanelField.getSolarPanel().setCost(solarPanel.getCost());
        this.solarPanelField.getSolarPanel().setWidthMeters(solarPanel.getWidthMeters());
        this.solarPanelField.getSolarPanel().setHeightMeters(solarPanel.getHeightMeters());
        setValues(this.solarPanelField);
    }

    public void resetSolarpanelField() {
        this.solarPanelField.setAzimut(DEFAULT_ACIMUT);
        this.solarPanelField.setAmount(DEFAULT_AMOUNT);
        this.solarPanelField.setVerticalAngle(DEFAULT_ANGLE);
        this.solarPanelField.getSolarPanel().setEfficiency(DEFAULT_EFFICIENCY);
        this.solarPanelField.getSolarPanel().setPower(DEFAULT_POWER);
        this.solarPanelField.getSolarPanel().setSize(DEFAULT_SIZE);
        setValues(this.solarPanelField);
        solarPanelFieldChanged();
    }

    public void addListener(SolarPanelControlListener listener) {
        this.listeners.add(listener);
    }

    private void setValues(SolarPanelField solarPanelField) {
        getEfficiency().setValue(solarPanelField.getSolarPanel().getEfficiency());
        getAcimut().setValue(solarPanelField.getAzimut());
        getAmount().setValue(solarPanelField.getAmount());
        getAngle().setValue(solarPanelField.getVerticalAngle());
        getPower().setValue(solarPanelField.getSolarPanel().getPower() + "W");
        getSize().setValue(solarPanelField.getSolarPanel().getSize());
        getTotalPower().setValue(solarPanelField.getInstalledPower() + "W");
    }

    private void acimutChanged(double acimut) {
        logger.debug("acimut: " + acimut);
        solarPanelField.setAzimut(acimut);
        solarPanelFieldChanged();
    }

    private void sizeChanged(double value) {
        logger.debug("size: " + value);
        solarPanelField.getSolarPanel().setSize(value);
        powerChanged();
        solarPanelFieldChanged();
    }

    private void powerChanged() {
        solarPanelField.getSolarPanel().setPower(solarPanelField.getSolarPanel().calculatePower());
        getPower().setValue(solarPanelField.getSolarPanel().getPower() + "W");
        getTotalPower().setValue(solarPanelField.getInstalledPower() + "W");
    }

    private void angleChanged(double value) {
        logger.debug("angle: " + value);
        solarPanelField.setVerticalAngle(value);
        solarPanelFieldChanged();
    }

    private void amountChanged(double value) {
        logger.debug("amount: " + value);
        solarPanelField.setAmount((int) value);
        getTotalPower().setValue(solarPanelField.getInstalledPower() + "W");
        solarPanelFieldChanged();
    }

    private void efficiencyChanged(double efficiency) {
        logger.debug("efficiency: " + efficiency);
        solarPanelField.getSolarPanel().setEfficiency(efficiency);
        powerChanged();
        solarPanelFieldChanged();
    }

    private void solarPanelFieldChanged() {
        for (SolarPanelControlListener listener : listeners) {
            listener.changed(solarPanelField);
        }
    }

    public interface SolarPanelControlListener {
        void changed(SolarPanelField solarPanelField);
    }

}
