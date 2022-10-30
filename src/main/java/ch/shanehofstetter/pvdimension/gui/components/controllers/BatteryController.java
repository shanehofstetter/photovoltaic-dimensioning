package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.BatteryView;
import ch.shanehofstetter.pvdimension.pvgenerator.Battery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public class BatteryController extends BatteryView {

    private static final Logger logger = LogManager.getLogger();
    private Battery battery;
    private ArrayList<BatteryControlListener> listeners = new ArrayList<>();

    public BatteryController(Battery battery) {
        super();
        capacityWidget.addListener(this::capacityChanged);
        initialChargeLevelWidget.addListener(this::initialChargeLevelChanged);
        setBattery(battery);
    }

    public BatteryController() {
        this(new Battery(DEFAULT_BATTERY_CAPACITY_KWH * 1000));
    }

    public void reset() {
        this.battery.setCapacityWh(DEFAULT_BATTERY_CAPACITY_KWH * 1000);
        this.battery.setInitialChargeLevelkWh(DEFAULT_BATTERY_INITIAL_LEVEL_KWH);
        this.capacityWidget.setValue(battery.getCapacitykWh());
        capacityChanged(battery.getCapacitykWh());
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
        this.capacityWidget.setValue(battery.getCapacitykWh());
        capacityChanged(battery.getCapacitykWh());
    }

    public void addListener(BatteryControlListener batteryControlListener) {
        this.listeners.add(batteryControlListener);
    }

    private void initialChargeLevelChanged(double newInitialChargeLevel) {
        logger.trace("initialChargeLevel: " + newInitialChargeLevel);
        battery.setInitialChargeLevelkWh(newInitialChargeLevel);
        batteryChanged();
    }

    private void capacityChanged(double capacitykWh) {
        logger.trace("capacitykWh: " + capacitykWh);
        battery.setCapacityWh(capacitykWh * 1000);
        initialChargeLevelWidget.setSliderMax(battery.getCapacitykWh());
        initialChargeLevelWidget.getSlider().setMajorTickUnit(battery.getCapacitykWh() > 0 ? battery.getCapacitykWh() / 10 : 1);
        initialChargeLevelWidget.getSlider().setMinorTickCount(4);
        batteryChanged();
    }

    private void batteryChanged() {
        for (BatteryControlListener listener : listeners) {
            listener.batteryChanged(battery);
        }
    }

    public interface BatteryControlListener {
        void batteryChanged(Battery battery);
    }
}
