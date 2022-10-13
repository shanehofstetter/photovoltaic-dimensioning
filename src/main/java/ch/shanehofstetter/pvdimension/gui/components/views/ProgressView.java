package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.ApplicationInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class ProgressView extends Stage {

    protected ProgressBar progressBar;
    protected Label label;
    protected Label progressLabel;
    private boolean closeable = false;

    public ProgressView(Window window) {
        initOwner(window);
        initModality(Modality.WINDOW_MODAL);
        setResizable(false);
        setAlwaysOnTop(true);
        setTitle("Berechnen..");
        getIcons().add(ApplicationInfo.APPLICATION_IMAGE);
        setHeight(150);
        setWidth(250);
        setOnCloseRequest(this::onClose);

        VBox contentBox = new VBox(5);
        contentBox.setPadding(new Insets(10, 10, 10, 10));
        label = new Label("Fortschritt:");

        HBox progressBox = new HBox(5);

        progressLabel = new Label("0%");
        progressLabel.setMinHeight(30);

        progressBar = new ProgressBar(0F);
        progressBar.prefWidthProperty().bind(progressBox.widthProperty().subtract(35));
        progressBar.setPrefHeight(30);
        progressBox.getChildren().addAll(progressBar, progressLabel);

        contentBox.getChildren().addAll(label, progressBox);
        contentBox.setFillWidth(true);
        contentBox.setAlignment(Pos.CENTER_LEFT);

        setScene(new Scene(contentBox));
        show();
    }

    public void closeProgressWindow() {
        closeable = true;
        close();
    }

    public void updateProgress(int progress, int total) {
        double val = (double) progress / (double) total;
        progressBar.setProgress(val);
        progressLabel.setText((int) (val * 100) + "%");
    }

    public void updateProgressMessage(String message) {
        label.setText(message);
    }

    private void onClose(WindowEvent windowEvent) {
        if (!closeable)
            windowEvent.consume();
    }

}
