package ch.shanehofstetter.pvdimension.simulation.dataholder;

import ch.shanehofstetter.pvdimension.util.DateTimeUtil;

import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension
 */
public class PVDay extends PVSimulationElement {

    private static int defaultStartHour = 0;
    private static int defaultHourAmount = 24;
    protected ArrayList<PVPowerTime> powerConsumptions;
    protected ArrayList<PVPowerTime> sunPowers;
    protected ArrayList<PVTimeUnit> simulatedTimeUnits;

    public PVDay() {
        simulatedTimeUnits = new ArrayList<>();
    }

    public static int getDefaultStartHour() {
        return defaultStartHour;
    }

    public static void setDefaultStartHour(int defaultStartHour) {
        PVDay.defaultStartHour = defaultStartHour;
    }

    public static int getDefaultHourAmount() {
        return defaultHourAmount;
    }

    public static void setDefaultHourAmount(int defaultHourAmount) {
        PVDay.defaultHourAmount = defaultHourAmount;
    }

    /**
     *
     * @return power consumptions
     */
    public ArrayList<PVPowerTime> getPowerConsumptions() {
        return powerConsumptions;
    }

    public void setPowerConsumptions(ArrayList<PVPowerTime> powerConsumptions) {
        this.powerConsumptions = powerConsumptions;
    }

    /**
     * Power coming from the sun to horizontal surface [W/m2]
     * @return sun powers
     */
    public ArrayList<PVPowerTime> getSunPowers() {
        return sunPowers;
    }

    public void setSunPowers(ArrayList<PVPowerTime> sunPowers) {
        this.sunPowers = sunPowers;
    }

    /**
     * @return simulated time units
     */
    public ArrayList<PVTimeUnit> getSimulatedTimeUnits() {
        return simulatedTimeUnits;
    }

    public void setSimulatedTimeUnits(ArrayList<PVTimeUnit> simulatedTimeUnits) {
        this.simulatedTimeUnits = simulatedTimeUnits;
    }

    /**
     * Given Powers during the day are in Watts or Watts/m2
     * we need to calculate the energies from these values [Wh]
     * <p>
     * Resulting list will be of size n-1
     * @return consumed energies
     */
    public ArrayList<PVPowerTime> calculateConsumedEnergies() {
        if (powerConsumptions != null) {
            if (powerConsumptions.size() >= 2) {
                powerConsumptions.sort(new PVPowerTimeComparator());
                ArrayList<PVPowerTime> powerTimes = calculateEnergiesForPowers(powerConsumptions);
                double totalConsumedEnergy = powerTimes.stream().mapToDouble(PVPowerTime::getPower).sum();
                this.setConsumedPower(totalConsumedEnergy);
                return powerTimes;
            }
        }
        return null;
    }

    /**
     * Given Powers during the day are in Watts or Watts/m2
     * we need to calculate the energies from these values [Wh]
     * <p>
     * Resulting list will be of size n-1
     * @return radiance energies
     */
    public ArrayList<PVPowerTime> calculateRadianceEnergies() {
        if (sunPowers != null) {
            if (sunPowers.size() >= 2) {
                sunPowers.sort(new PVPowerTimeComparator());
                ArrayList<PVPowerTime> powerTimes = calculateEnergiesForPowers(sunPowers);
                double totalHorizontalRadianceEnergies = powerTimes.stream().mapToDouble(PVPowerTime::getPower).sum();
                this.setHorizontalRadianceEnergy(totalHorizontalRadianceEnergies);
                return powerTimes;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "PVDay{\n" +
                "powerConsumptions=" + powerConsumptions +
                ", \nsunPowers=" + sunPowers +
                ", \nsimulatedTimeUnits=" + simulatedTimeUnits +
                '}';
    }

    /**
     * @param powers powers
     * @return energies
     */
    private ArrayList<PVPowerTime> calculateEnergiesForPowers(ArrayList<PVPowerTime> powers) {
        ArrayList<PVPowerTime> energyResults = new ArrayList<>();
        for (int i = 0; i < powers.size(); i++) {
            if (i + 1 < powers.size()) {
                PVPowerTime now = powers.get(i);
                PVPowerTime later = powers.get(i + 1);
                double hoursFromTime = DateTimeUtil.getHoursFromTime(later.getTime().getTime() - now.getTime().getTime());
                double averagePower = (now.getPower() + later.getPower()) / 2;
                double energy = averagePower * hoursFromTime;
                energyResults.add(new PVPowerTime(energy, later.getTime()));
            }
        }
        return energyResults;
    }
}
