package ch.shanehofstetter.pvdimension.simulation.dataholder;

import java.util.Comparator;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.simulation.dataholder
 */
public class PVPowerTimeComparator implements Comparator<PVPowerTime> {
    @Override
    public int compare(PVPowerTime powerTime1, PVPowerTime powerTime2) {
        return (int) (powerTime1.getTime().getTime() - powerTime2.getTime().getTime());
    }
}