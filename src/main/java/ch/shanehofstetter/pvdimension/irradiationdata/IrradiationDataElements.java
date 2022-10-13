package ch.shanehofstetter.pvdimension.irradiationdata;

import java.time.Month;
import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.irradiationdata
 */
public class IrradiationDataElements {
    private Month month;
    private ArrayList<IrradiationDataElement> irradiationDataElements;

    public IrradiationDataElements() {
    }

    public IrradiationDataElements(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public ArrayList<IrradiationDataElement> getIrradiationDataElements() {
        return irradiationDataElements;
    }

    public void setIrradiationDataElements(ArrayList<IrradiationDataElement> irradiationDataElements) {
        this.irradiationDataElements = irradiationDataElements;
    }
}
