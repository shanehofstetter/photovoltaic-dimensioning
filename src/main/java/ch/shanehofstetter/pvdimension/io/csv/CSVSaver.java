package ch.shanehofstetter.pvdimension.io.csv;

import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

public class CSVSaver {

    /**
     * shows a gui-window where user needs to choose a directory where the exported data should be stored
     * <p>
     * multiple .csv files get created
     * </p>
     * shows error-messageboxes when exporting fails
     *
     * @param pvGenerator pvGenerator
     * @param window      related caller window
     */
    public void save(PVGenerator pvGenerator, Window window) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Daten exportieren..");
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File directory = directoryChooser.showDialog(window);
        if (directory != null) {
            CSVWriterController csvWriterController = new CSVWriterController();
            try {
                csvWriterController.saveCSVData(directory, pvGenerator);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export erfolgreich");
                alert.setHeaderText("Simulationsergebnisser wurden exportiert nach:");
                alert.setContentText(directory.toString());
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export fehlgeschlagen");
                alert.setHeaderText("Es gab ein Problem beim Speichern der Daten:" + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Export abgebrochen");
            alert.setHeaderText("Es wurde kein Ordner ausgewählt.");
            alert.showAndWait();
        }
    }
}
