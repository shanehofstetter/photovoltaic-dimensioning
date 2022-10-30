package ch.shanehofstetter.pvdimension.pvgenerator.solarpanel;

import ch.shanehofstetter.pvdimension.util.Utilities;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.math.BigDecimal;

/**
 * Solarpanel represents a physical Solarpanel<br>
 * It holds all important information about a Panel
 */
public class SolarPanel implements Serializable {

    private SimpleDoubleProperty heightMeters = new SimpleDoubleProperty();
    private SimpleDoubleProperty widthMeters = new SimpleDoubleProperty();
    private SimpleDoubleProperty size = new SimpleDoubleProperty();
    private SimpleDoubleProperty efficiency = new SimpleDoubleProperty();
    private SimpleDoubleProperty efficiencyPercentage = new SimpleDoubleProperty();
    private SimpleDoubleProperty power = new SimpleDoubleProperty();
    private SimpleStringProperty manufacturer = new SimpleStringProperty("");
    private SimpleStringProperty typeName = new SimpleStringProperty("");
    private SimpleStringProperty details = new SimpleStringProperty("");
    private BigDecimal cost = BigDecimal.valueOf(250);

    /**
     * The solar-panels height in meters
     */
    public double getHeightMeters() {
        return heightMeters.get();
    }

    /**
     * set the panel height in meters
     *
     * @param heightMeters height in meters
     */
    public void setHeightMeters(double heightMeters) {
        if (heightMeters < 0) {
            return;
        }
        this.heightMeters.set(heightMeters);
        calculateSize();
    }

    /**
     * The solar-panels width in meters
     */
    public double getWidthMeters() {
        return widthMeters.get();
    }

    /**
     * Set the panel width in meters
     * @param widthMeters width in meters
     */
    public void setWidthMeters(double widthMeters) {
        if (widthMeters < 0) {
            return;
        }
        this.widthMeters.set(widthMeters);
        calculateSize();
    }

    /**
     * @return Efficiency - between 0 and 1
     * determines how much of the received sun radiation can be transformed into electricity.
     */
    public double getEfficiency() {
        return efficiency.get();
    }

    /**
     * @param efficiency efficiency must be between 0.0 and 1.0
     */
    public void setEfficiency(double efficiency) {
        if (efficiency >= 0 && efficiency <= 1.0) {
            this.efficiency.set(efficiency);
        }
    }

    /**
     * @return Power in Watts of the module
     */
    public double getPower() {
        return power.get();
    }

    /**
     * @param power Power [Watts]
     */
    public void setPower(double power) {
        if (power < 0) {
            return;
        }
        this.power.set(power);
    }

    /**
     * @return The manufacturer
     */
    public String getManufacturer() {
        return manufacturer.get();
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer.set(manufacturer);
    }

    /**
     * @return Type of Solar-Panel
     * e.g. Poly-Crystalline oder Mono-Crystalline
     */
    public String getTypeName() {
        return typeName.get();
    }

    /**
     * Set the panel type name
     * article specification by manufacturer
     * @param typeName type name
     */
    public void setTypeName(String typeName) {
        this.typeName.set(typeName);
    }

    /**
     * @return Some details, description of the panel
     */
    public String getDetails() {
        return details.get();
    }

    /**
     * Set some details about the solarpanel
     * @param details details
     */
    public void setDetails(String details) {
        this.details.set(details);
    }

    /**
     * Cost of one Solarpanel
     * @return cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * set the cost of one solarpanel
     * @param cost cost
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * @return size in square-meters
     */
    public double getSize() {
        return size.get();
    }

    /**
     * @param size Size in square-meters [m2]
     */
    public void setSize(double size) {
        this.size.set(size);
    }

    /**
     * calculates the size of the panel based on its power and efficiency
     */
    public void setSizeByPowerAndEfficiency() {
        setSize(Utilities.round(getPower() / (getEfficiency() * 1000.0), 2));
    }

    /**
     * Calculates the efficiency of the module based on the size and power of it
     *
     * @return efficiency
     */
    public double calculateEfficiency() {
        return Utilities.round(getPower() / 1000.0 / getSize(), 2);
    }

    /**
     * calculates the power of the module based on the efficiency and size of it
     *
     * @return power W
     */
    public double calculatePower() {
        return Utilities.round(getEfficiency() * getSize() * 1000.0, 2);
    }

    /**
     * @return Efficiency as percentage, between 0.0 and 100.0 %
     * determines how much of the received sun radiation can be transformed into electricity.
     * rounded to 1 decimal place
     */
    public double getEfficiencyPercentage() {
        if (efficiencyPercentage == null) {
            efficiencyPercentage = new SimpleDoubleProperty(0);
        }
        efficiencyPercentage.set(Utilities.round(efficiency.get() * 100.0, 1));
        return efficiencyPercentage.get();
    }

    @Override
    public String toString() {
        return "SolarPanel{" +
                "heightMeters=" + heightMeters +
                ", widthMeters=" + widthMeters +
                ", size=" + size +
                ", efficiency=" + efficiency +
                ", efficiencyPercentage=" + efficiencyPercentage +
                ", power=" + power +
                ", manufacturer=" + manufacturer +
                ", typeName=" + typeName +
                ", details=" + details +
                ", cost=" + cost +
                '}';
    }

    /**
     * calculate the size of the panel in square meters
     * from height and width
     */
    private void calculateSize() {
        if (heightMeters.get() > 0 && widthMeters.get() > 0) {
            size.set(heightMeters.get() * widthMeters.get());
        }
    }

    /**
     * Serialize this instance.
     *
     * @param out Target to which this instance is written.
     * @throws IOException Thrown if exception occurs during serialization.
     */
    private void writeObject(final ObjectOutputStream out) throws IOException {
        out.writeObject(getHeightMeters());
        out.writeObject(getWidthMeters());
        out.writeObject(getSize());
        out.writeObject(getEfficiency());
        out.writeObject(getPower());
        out.writeObject(getManufacturer());
        out.writeObject(getTypeName());
        out.writeObject(getDetails());
        out.writeObject(getCost().toString());
    }

    /**
     * Deserialize this instance from input stream.
     *
     * @param in Input Stream from which this instance is to be deserialized.
     * @throws IOException            Thrown if error occurs in deserialization.
     * @throws ClassNotFoundException Thrown if expected class is not found.
     */
    private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
        heightMeters = new SimpleDoubleProperty((double) in.readObject());
        widthMeters = new SimpleDoubleProperty((double) in.readObject());
        size = new SimpleDoubleProperty((double) in.readObject());
        efficiency = new SimpleDoubleProperty((double) in.readObject());
        power = new SimpleDoubleProperty((double) in.readObject());
        manufacturer = new SimpleStringProperty((String) in.readObject());
        typeName = new SimpleStringProperty((String) in.readObject());
        details = new SimpleStringProperty((String) in.readObject());
        setCost(new BigDecimal((String) in.readObject()));
    }

    private void readObjectNoData() throws ObjectStreamException {
        throw new InvalidObjectException("Stream data required");
    }
}
