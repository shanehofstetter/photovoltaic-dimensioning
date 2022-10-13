
package ch.shanehofstetter.pvdimension.gui.components.views;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * @author Simon Müller : smueller@xiag.ch
 */
public class MenubarView extends MenuBar {

    protected MenuItem openFile;
    protected MenuItem closeApplication;
    protected MenuItem saveFile;
    protected MenuItem exportPDF;
    protected MenuItem exportCSV;
    protected MenuItem about;
    protected MenuItem resetData;
    protected MenuItem economics;
    protected MenuItem solarPanelDatabase;
    protected MenuItem advancedSettings;
    protected MenuItem help;
    public MenubarView() {

        // File Menu
        Menu fileMenu = new Menu("Datei");

        openFile = new MenuItem("Öffnen..");
        closeApplication = new MenuItem("Beenden");
        saveFile = new MenuItem("Speichern unter..");
        exportPDF = new MenuItem("als PDF..");
        Menu exportMenu = new Menu("Exportieren");
        exportMenu.getItems().add(exportPDF);
        exportCSV = new MenuItem("Simulationsergebnisse als CSV..");
        exportMenu.getItems().add(exportCSV);

        fileMenu.getItems().addAll(openFile, saveFile, new SeparatorMenuItem(), exportMenu, new SeparatorMenuItem(), closeApplication);

        // Edit Menu
        Menu editMenu = new Menu("Bearbeiten");
        resetData = new MenuItem("Daten zurücksetzen");
        editMenu.getItems().add(resetData);

        //Project Menu
        Menu projectMenu = new Menu("Projekt");
        economics = new MenuItem("Wirtschaftlichkeit..");
        projectMenu.getItems().add(economics);
        advancedSettings = new MenuItem("Erweiterte Anlagen-Parameter..");
        projectMenu.getItems().add(advancedSettings);

        //Components Menu
        Menu componentsMenu = new Menu("Komponenten");
        solarPanelDatabase = new MenuItem("Solar-Panels..");
        componentsMenu.getItems().add(solarPanelDatabase);

        // Help Menu
        Menu aboutMenu = new Menu("Hilfe");

        help = new MenuItem("Hilfe anzeigen");
        about = new MenuItem("Über PVDimension..");

        aboutMenu.getItems().addAll(help,about);

        // ADD ALL THE MENUS!
        this.getMenus().addAll(fileMenu, editMenu, projectMenu, componentsMenu, aboutMenu);
    }

}
