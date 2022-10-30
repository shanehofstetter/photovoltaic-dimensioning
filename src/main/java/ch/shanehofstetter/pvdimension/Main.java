package ch.shanehofstetter.pvdimension;

import ch.shanehofstetter.pvdimension.gui.components.controllers.MainPaneController;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;

/**
 * Main starts the GUI
 */
public class Main extends Application {
    static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.entry();

        InputStream iconStream = getClass().getResourceAsStream("/ch/shanehofstetter/pvdimension/" + ApplicationInfo.APPLICATION_ICON_NAME);
        if (iconStream == null) {
            System.err.println("error loading app icon");
            System.exit(1);
        }
        ApplicationInfo.APPLICATION_IMAGE = new Image(iconStream);
        MainPaneController mainPane = new MainPaneController(primaryStage);
        primaryStage.setTitle(ApplicationInfo.APPLICATION_TITLE_LONG);
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(mainPane, Math.min(visualBounds.getWidth() - 50, 1400), Math.min(visualBounds.getHeight() - 50, 900));
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(150);
        primaryStage.setMinWidth(150);
        primaryStage.getIcons().add(ApplicationInfo.APPLICATION_IMAGE);
        primaryStage.show();
    }
}
