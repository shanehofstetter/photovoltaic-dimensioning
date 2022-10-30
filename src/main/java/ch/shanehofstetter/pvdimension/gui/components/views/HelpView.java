package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.ApplicationInfo;
import ch.shanehofstetter.pvdimension.io.FileReader;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebView;


public class HelpView extends Alert {
    public HelpView() {
        super(Alert.AlertType.INFORMATION);
        setTitle(ApplicationInfo.APPLICATION_TITLE_SHORT + " Hilfe");
        setHeaderText(ApplicationInfo.APPLICATION_TITLE_SHORT + " Hilfe");

        String fileContent = FileReader.readStreamIntoString(getClass().getResourceAsStream("/ch/shanehofstetter/pvdimension/gui/help.html"));
        WebView helpView = new WebView();
        helpView.getEngine().loadContent(fileContent);


        helpView.setMaxWidth(Double.MAX_VALUE);
        helpView.setMaxHeight(Double.MAX_VALUE);


        ScrollPane expContent = new ScrollPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.setContent(helpView);


        getDialogPane().setContent(expContent);


    }


}
