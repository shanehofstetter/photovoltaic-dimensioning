package ch.shanehofstetter.pvdimension;

import ch.shanehofstetter.pvdimension.pvgenerator.Battery;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;

public class PVDimensionApplication {
    private static final PVGenerator pvGenerator = new PVGenerator(new SolarPanelField(), new Battery(5000));

    public static PVGenerator getPvGenerator() {
        return pvGenerator;
    }
}
