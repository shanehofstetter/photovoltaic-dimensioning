
package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.HelpView;
import ch.shanehofstetter.pvdimension.gui.components.views.MenubarView;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MenubarController extends MenubarView {

    private ArrayList<PVMenuBarListener> listeners = new ArrayList<>();

    public MenubarController() {
        super();
        registerEventListeners();
    }

    public void addListener(PVMenuBarListener listener) {
        this.listeners.add(listener);
    }

    private void registerEventListeners() {
        this.about.setOnAction(this::aboutPressed);
        this.closeApplication.setOnAction(this::closeAppPressed);
        this.exportPDF.setOnAction(this::exportPDFPressed);
        this.exportCSV.setOnAction(this::exportCSVPressed);
        this.openFile.setOnAction(this::openFilePressed);
        this.saveFile.setOnAction(this::saveFilePressed);
        this.resetData.setOnAction(this::resetDataPressed);
        this.economics.setOnAction(this::economicsPressed);
        this.advancedSettings.setOnAction(this::advancedSettingsPressed);
        this.solarPanelDatabase.setOnAction(this::solarPanelDatabasePressed);
        this.help.setOnAction(this::openHelpWindowPressed);

        this.closeApplication.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        this.exportPDF.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        this.exportCSV.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        this.economics.setAccelerator(new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN));
        this.solarPanelDatabase.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
        this.openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        this.saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN));
        this.help.setAccelerator(new KeyCodeCombination(KeyCode.F1));
    }

    private void openHelpWindowPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::helpPressed);
    }

    private void solarPanelDatabasePressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::solarPanelDatabasePressed);
    }

    private void exportCSVPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::exportCSVPressed);
    }

    private void advancedSettingsPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::advancedSettingsPressed);
    }

    private void economicsPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::economicsPressed);
    }

    private void resetDataPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::resetDataPressed);
    }

    private void saveFilePressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::saveFilePressed);
    }

    private void openFilePressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::loadFilePressed);
    }

    private void exportPDFPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::exportPDFPressed);
    }

    private void closeAppPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::closeApplicationPressed);
    }

    private void aboutPressed(ActionEvent actionEvent) {
        listeners.forEach(PVMenuBarListener::aboutPressed);
    }

    public interface PVMenuBarListener {
        /**
         * gets triggered when user presses about menu-item
         */
        void aboutPressed();

        /**
         * gets triggered when user wants to save the data
         */
        void saveFilePressed();

        /**
         * gets triggered when user wants to save a pdf report
         */
        void exportPDFPressed();

        /**
         * gets triggered when user wants to save csv data
         */
        void exportCSVPressed();

        /**
         * gets triggered when user wants to load a project file
         */
        void loadFilePressed();

        /**
         * gets triggered when user closes the application from the menu
         */
        void closeApplicationPressed();

        /**
         * gets triggered when user wants to reset the app-data
         */
        void resetDataPressed();

        /**
         * gets triggered when user presses economics menu-item
         */
        void economicsPressed();

        /**
         * gets triggered when user presses advanced settings menu-item
         */
        void advancedSettingsPressed();

        /**
         * gets triggered when user wants to see the solarpanel database
         */
        void solarPanelDatabasePressed();

        /**
         * gets triggered when user wants to see the help window
         */
        void helpPressed();
    }

}
