package ch.shanehofstetter.pvdimension.io.pvdata;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVSaveDialog extends PVFileDialog {

    private ArrayList<PVSaveDialogListener> listeners = new ArrayList<>();

    /**
     * show a filechooser dialog where user can specify a filename to store the data to
     *
     * @param pvData pvData to store
     * @param window related caller window
     */
    public void save(PVData pvData, Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Projekt Speichern");
        fileChooser.setInitialDirectory(getInitialDir());
        fileChooser.setInitialFileName(defaultFilename);
        fileChooser.getExtensionFilters().add(getExtensionFilter());
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            PVSaver pvSaver = new PVSaver();
            try {
                pvSaver.save(pvData, file.getAbsolutePath());
                saved();
            } catch (IOException e) {
                logger.error("Saving to " + file.getAbsolutePath(), e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehler beim Speichern des Projekts:");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }


    public void addListener(PVSaveDialogListener listener) {
        listeners.add(listener);
    }

    private void saved() {
        listeners.forEach(PVSaveDialogListener::saved);
    }

    public interface PVSaveDialogListener {
        /**
         * called when data successfully saved
         */
        void saved();
    }
}
