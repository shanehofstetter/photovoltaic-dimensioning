package ch.shanehofstetter.pvdimension.simulation.dataholder;

import java.time.Month;


public class PVMonth extends PVSimulationElement {

    private Month month;

    public PVMonth(Month month) {
        this.month = month;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
