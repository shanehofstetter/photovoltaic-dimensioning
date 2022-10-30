package ch.shanehofstetter.pvdimension.simulation;


public enum SimulationStatus {
    READY,
    NOT_READY;

    @Override
    public String toString() {
        switch (this) {
            case READY:
                return "Ready";
            case NOT_READY:
                return "Not Ready";
            default:
                throw new IllegalArgumentException();
        }
    }
}
