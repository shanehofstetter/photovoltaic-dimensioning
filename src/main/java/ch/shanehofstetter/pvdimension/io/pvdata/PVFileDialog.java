package ch.shanehofstetter.pvdimension.io.pvdata;

import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVFileDialog {
    protected static final Logger logger = LogManager.getLogger();
    private static File initialDir = new File(System.getProperty("user.home"));
    protected String defaultFilename = "PVProjekt.pv";
    private FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PV-Simulator Datei (*.pv)", "*.pv");

    /**
     * @return initial directory path
     */
    public static File getInitialDir() {
        return initialDir;
    }

    /**
     * @param initialDir initial directory path
     */
    public static void setInitialDir(File initialDir) {
        PVFileDialog.initialDir = initialDir;
    }

    /**
     * @return default filename
     */
    public String getDefaultFilename() {
        return defaultFilename;
    }

    /**
     * @param defaultFilename default filename
     */
    public void setDefaultFilename(String defaultFilename) {
        this.defaultFilename = defaultFilename;
    }

    /**
     * @return extension filter
     */
    public FileChooser.ExtensionFilter getExtensionFilter() {
        return extensionFilter;
    }

    /**
     * @param extensionFilter extension filter
     */
    public void setExtensionFilter(FileChooser.ExtensionFilter extensionFilter) {
        this.extensionFilter = extensionFilter;
    }
}
