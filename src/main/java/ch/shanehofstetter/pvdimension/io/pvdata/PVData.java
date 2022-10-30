package ch.shanehofstetter.pvdimension.io.pvdata;

import ch.shanehofstetter.pvdimension.gui.components.controllers.SunpowerInputModeController;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;

import java.io.Serializable;

/**
 * Storage for complete project-specific data to serialize and write to a single file
 */
public class PVData implements Serializable {
    private PVGenerator pvGenerator;
    private SunpowerInputModeController.SunPowerInputMode sunPowerInputMode = SunpowerInputModeController.SunPowerInputMode.MANUAL;

    public PVData(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
    }

    public PVData(PVGenerator pvGenerator, SunpowerInputModeController.SunPowerInputMode sunPowerInputMode) {
        this.pvGenerator = pvGenerator;
        this.sunPowerInputMode = sunPowerInputMode;
    }

    public PVGenerator getPvGenerator() {
        return pvGenerator;
    }

    public SunpowerInputModeController.SunPowerInputMode getSunPowerInputMode() {
        return sunPowerInputMode;
    }

}
