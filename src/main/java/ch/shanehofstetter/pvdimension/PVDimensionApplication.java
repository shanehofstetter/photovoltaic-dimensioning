package ch.shanehofstetter.pvdimension;

import ch.shanehofstetter.pvdimension.pvgenerator.Battery;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
public class PVDimensionApplication {
    private static final PVGenerator pvGenerator = new PVGenerator(new SolarPanelField(), new Battery(5000));

    public static PVGenerator getPvGenerator() {
        return pvGenerator;
    }
}
