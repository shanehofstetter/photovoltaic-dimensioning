package ch.shanehofstetter.pvdimension.io.pdfexport;

import ch.shanehofstetter.pvdimension.io.pvdata.PVData;
import ch.shanehofstetter.pvdimension.io.pvdata.PVFileDialog;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Report Saver shows a file chooser dialog, generates the pdf using
 * PVReportCreator and writes the file to the selected location
 *
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.io.pvdata
 */
public class PVReportSaver extends PVFileDialog {

    private ArrayList<PVSaveDialogListener> listeners = new ArrayList<>();

    public PVReportSaver() {
        setDefaultFilename("Projektbericht.pdf");
        setExtensionFilter(new FileChooser.ExtensionFilter("PDF-Datei (*.pdf)", "*.pdf"));
    }

    /**
     * Generate a pdf report from the given PVData object
     * <p>
     * if the saving fails, an alert gets shown which gives more info about the error
     *
     * @param pvData pvData
     * @param window window
     */
    public void save(PVData pvData, Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Projekt-Bericht Speichern");
        fileChooser.setInitialDirectory(getInitialDir());
        fileChooser.setInitialFileName(getDefaultFilename());
        fileChooser.getExtensionFilters().add(getExtensionFilter());
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            PVReportCreator pvReportCreator = new PVReportCreator(file);
            try {
                pvReportCreator.makePDF(pvData.getPvGenerator());
                saved();
                openPDF(file);
            } catch (Exception e) {
                logger.error("Saving to " + file.getAbsolutePath(), e);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fehler");
                alert.setHeaderText("Fehler beim Speichern des Projekt-Berichts:");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    /**
     * add a listener which gets notified on save success
     *
     * @param listener listener
     */
    public void addListener(PVSaveDialogListener listener) {
        listeners.add(listener);
    }

    private void openPDF(File file) {
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(file);
        } catch (IOException e) {
            logger.error("Error while opening PDF after saving it:", e.getMessage(), e);
        }
    }

    /**
     * notify listeners
     */
    private void saved() {
        listeners.forEach(PVSaveDialogListener::saved);
    }

    /**
     * PVSaveDialogListener interface
     */
    public interface PVSaveDialogListener {
        void saved();
    }
}
