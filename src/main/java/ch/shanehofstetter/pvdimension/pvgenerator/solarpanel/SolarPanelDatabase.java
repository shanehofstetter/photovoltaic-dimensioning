package ch.shanehofstetter.pvdimension.pvgenerator.solarpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.pvgenerator.solarpanel
 */
public class SolarPanelDatabase {

    private static ObservableList<SolarPanel> solarPanels;

    /**
     * load the database from file, if there's an error reading and loading the panels
     * an empty list gets created
     */
    public static void load() {
        SolarPanelReader reader = new SolarPanelReader();
        try {
            solarPanels = FXCollections.observableArrayList(reader.readModules());
        } catch (Exception e) {
            solarPanels = FXCollections.observableArrayList();
        }
    }

    /**
     * @return all available solarPanels
     */
    public static ObservableList<SolarPanel> getSolarPanels() {
        return solarPanels;
    }
}
