package ch.shanehofstetter.pvdimension.simulation;

import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVSimulationElement;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;

/**
 * abstract simulator class providing basic members and an implementation of some listener-callers
 *
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 *         ch.shanehofstetter.pvdimension.simulation
 */
public abstract class PVSimulator {
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger("");
    protected PVGenerator pvGenerator;
    protected ArrayList<PVSimulatorListener> listeners = new ArrayList<>();
    protected boolean simulationActive = false;

    public void addListener(PVSimulatorListener listener) {
        listeners.add(listener);
    }

    /**
     * checks if a simulation is active
     * only relevant when simulation is run in a different thread
     *
     * @return true if simulation is active
     */
    public boolean isSimulationActive() {
        return simulationActive;
    }

    public void setSimulationActive(boolean simulationActive) {
        this.simulationActive = simulationActive;
    }

    /**
     * @return PVGenerator which represents the PV-Installation
     */
    public PVGenerator getPvGenerator() {
        return pvGenerator;
    }

    /**
     * @param pvGenerator represents the PV-Installation
     */
    public void setPvGenerator(PVGenerator pvGenerator) {
        this.pvGenerator = pvGenerator;
    }

    protected void producingNoPower() {
        for (PVSimulatorListener listener : listeners) listener.noProducedPower();
    }

    protected void finishedSimulation(PVSimulationElement pvSimulationElement) {
        for (PVSimulatorListener listener : listeners) listener.finishedSimulation(pvSimulationElement);
    }

    protected void reportProgress(int progress, int total) {
        for (PVSimulatorListener listener : listeners) listener.progress(progress, total);
    }
}
