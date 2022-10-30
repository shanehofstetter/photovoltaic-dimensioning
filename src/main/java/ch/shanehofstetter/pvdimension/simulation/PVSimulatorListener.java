package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationElement;

/**
 * listener interface to get notifications about simulator activities
 *
 */
public interface PVSimulatorListener {
    void noProducedPower();

    void finishedSimulation(PVSimulationElement pvSimulationElement);

    void progress(int progress, int total);
}
