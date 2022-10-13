package ch.shanehofstetter.pvdimension.pvgenerator.solarpanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.LoggerFactory;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.pvgenerator.solarpanel
 */
public class SolarPanelDatabase {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SolarPanelDatabase.class);
    private static ObservableList<SolarPanel> solarPanels;

    /**
     * load the database from file, if theres an error reading and loading the panels
     * an empty list gets created
     */
    public static void load() {
        SolarPanelReader reader = new SolarPanelReader();
        try {
            solarPanels = FXCollections.observableArrayList(reader.readModules());
            logger.info("number of modules: "+solarPanels.size());
        } catch (Exception e) {
            logger.error("error",e);
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
