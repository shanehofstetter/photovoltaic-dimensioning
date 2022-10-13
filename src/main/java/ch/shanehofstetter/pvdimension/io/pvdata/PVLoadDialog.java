package ch.shanehofstetter.pvdimension.io.pvdata;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVLoadDialog extends PVFileDialog {
    private ArrayList<PVLoadDialogListener> listeners = new ArrayList<>();

    /**
     * show a filedialog in which user can select a project file and read it in
     * triggers "loaded" event when successfully read in file<br>
     * handles exception with an alertbox
     *
     * @param window related caller window
     */
    public void load(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Projekt Öffnen");
        fileChooser.setInitialDirectory(getInitialDir());
        fileChooser.setInitialFileName(defaultFilename);
        fileChooser.getExtensionFilters().add(getExtensionFilter());
        File file = fileChooser.showOpenDialog(window);

        if (file != null) {
            PVLoader pvLoader = new PVLoader();
            try {
                PVData pvData = pvLoader.load(file.getAbsolutePath());
                loaded(pvData);
            } catch (Exception e) {
                logger.error("loading " + file.getAbsolutePath(), e.getMessage(), e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehler beim Laden des Projekts:");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    public void addListener(PVLoadDialogListener listener) {
        listeners.add(listener);
    }

    private void loaded(PVData pvData) {
        for (PVLoadDialogListener listener : listeners) listener.loaded(pvData);
    }

    public interface PVLoadDialogListener {
        /**
         * loaded and deserialized pvData object
         * @param pvData deserialized object
         */
        void loaded(PVData pvData);
    }
}
