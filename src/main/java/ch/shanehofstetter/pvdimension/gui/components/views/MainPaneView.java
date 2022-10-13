package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.ApplicationInfo;
import ch.shanehofstetter.pvdimension.gui.components.controllers.*;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVInputDataType;
import ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart.PVWeeklyInputChart;
import ch.shanehofstetter.pvdimension.gui.components.widgets.VTitledPane;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainPaneView extends BorderPane implements SunpowerInputModeController.SunPowerInputModeListener {

    protected SolarPanelController solarPanelController;
    protected BatteryController batteryController;
    protected AddressCoordinateController addressCoordinateController;
    protected SunpowerInputModeController sunpowerInputModeController;
    protected PVWeeklyInputChart consumptionInputChart;
    protected PVWeeklyInputChart sunpowerInputChart;
    protected SimulationResultView simulationResultView;
    protected SimulationDataController simulationDataController;
    protected MenubarController menuBar;
    protected Stage economicsStage;
    protected Stage generatorParametersStage;
    protected Stage solarPanelTableStage;
    protected SolarPanelTableController solarPanelTableController;
    protected ProgressView progressView;

    private Insets overallInsets = new Insets(5, 5, 5, 5);
    private TabPane inputControlsTabPane;
    private Tab addressCoordinateControllerTab;
    private Tab consumptionInputChartTab;
    private Tab sunpowerInputChartTab;
    private LayoutType layoutType = LayoutType.LARGE;
    private VBox leftLayout;
    private VBox rightLayout;
    private StackPane mainStackpane;
    private VTitledPane inputWidgetsPane;

    public MainPaneView() {
        super();
        mainStackpane = new StackPane();
        HBox mainLayout = new HBox();
        leftLayout = new VBox();
        rightLayout = new VBox();
        rightLayout.setMinHeight(650);
        TitledPane pvGeneratorDataPane = new TitledPane();
        pvGeneratorDataPane.setText("Photovoltaik-Anlage");
        pvGeneratorDataPane.setPadding(overallInsets);
        TabPane pvGeneratorDataTabPane = new TabPane();
        pvGeneratorDataTabPane.setMinHeight(290);
        Tab solarPanelControllerTab = new Tab("Photovoltaik-Panel");

        solarPanelController = new SolarPanelController();
        solarPanelController.setPadding(new Insets(10, 5, 10, 5));
        solarPanelController.setMinHeight(20);
        ScrollPane solarPanelControllerScrollPane = new ScrollPane(solarPanelController);
        solarPanelControllerScrollPane.setFitToHeight(true);
        solarPanelControllerScrollPane.setFitToWidth(true);
        solarPanelControllerTab.setContent(solarPanelControllerScrollPane);
        solarPanelControllerTab.setClosable(false);

        Tab batteryControllerTab = new Tab("Batterie");
        batteryController = new BatteryController();
        batteryController.setPadding(new Insets(10, 5, 10, 5));
        batteryController.setSpacing(5);
        batteryControllerTab.setContent(batteryController);
        batteryControllerTab.setClosable(false);

        pvGeneratorDataTabPane.getTabs().addAll(solarPanelControllerTab, batteryControllerTab);
        pvGeneratorDataPane.setContent(pvGeneratorDataTabPane);
        leftLayout.getChildren().add(pvGeneratorDataPane);

        inputWidgetsPane = new VTitledPane("Simulations-Daten", 5, new Insets(0, 0, 0, 0));
        inputWidgetsPane.setCollapsible(false);
        inputWidgetsPane.setPadding(overallInsets);
        inputWidgetsPane.setMinHeight(400);
        inputWidgetsPane.setMaxHeight(10000);
        VBox.setVgrow(inputWidgetsPane, Priority.ALWAYS);

        sunpowerInputModeController = new SunpowerInputModeController();
        sunpowerInputModeController.setPadding(overallInsets);
        sunpowerInputModeController.addListener(this);

        simulationDataController = new SimulationDataController();
        simulationDataController.setPadding(overallInsets);

        inputControlsTabPane = new TabPane();
        inputControlsTabPane.setPadding(new Insets(0, 0, 0, 0));
        inputControlsTabPane.setMinHeight(10);
        inputControlsTabPane.setMaxHeight(10000);

        Tab simulationSettingsTab = new Tab("Einstellungen");
        simulationSettingsTab.setClosable(false);
        VBox simulationSettingsContent = new VBox(5);
        simulationSettingsContent.setPadding(overallInsets);
        simulationSettingsContent.getChildren().addAll(simulationDataController, sunpowerInputModeController);
        simulationSettingsTab.setContent(simulationSettingsContent);
        inputControlsTabPane.getTabs().addAll(simulationSettingsTab);

        makeAddressControlTab();

        makeConsumptionInputTab();
        inputControlsTabPane.getTabs().add(consumptionInputChartTab);
        makeSunpowerInputTab();
        inputControlsTabPane.getSelectionModel().select(1);
        VBox.setVgrow(inputWidgetsPane, Priority.ALWAYS);
        VBox.setVgrow(inputControlsTabPane, Priority.ALWAYS);
        inputWidgetsPane.add(inputControlsTabPane);
        leftLayout.getChildren().add(inputWidgetsPane);
        sunpowerInputModeController.setMode(SunpowerInputModeController.SunPowerInputMode.MANUAL);

        //RIGHT
        simulationResultView = new SimulationResultView("Simulation", 5, new Insets(0, 0, 0, 0));
        simulationResultView.setCollapsible(false);
        simulationResultView.setMaxHeight(10000);
        simulationResultView.setPadding(overallInsets);
        VBox.setVgrow(simulationResultView, Priority.ALWAYS);
        rightLayout.getChildren().add(simulationResultView);
        rightLayout.setFillWidth(true);
        HBox.setHgrow(rightLayout, Priority.ALWAYS);

        //ADD TO MAIN
        // mainLayout.getChildren().addAll(leftLayout, rightLayout);
        mainStackpane.getChildren().add(mainLayout);
        setCenter(mainStackpane);
        changeToLargeLayout();

        menuBar = new MenubarController();
        setTop(menuBar);

        solarPanelTableController = new SolarPanelTableController();

        this.widthProperty().addListener((observable, oldValue, newValue) -> {
            widthChanged(newValue.doubleValue());
        });

    }

    @Override
    public void changedInputMode(SunpowerInputModeController.SunPowerInputMode sunPowerInputMode) {
        switch (sunPowerInputMode) {
            case MANUAL:
                inputControlsTabPane.getTabs().remove(addressCoordinateControllerTab);
                if (!inputControlsTabPane.getTabs().contains(sunpowerInputChartTab))
                    inputControlsTabPane.getTabs().add(sunpowerInputChartTab);
                break;
            case AUTOMATIC:
                if (!inputControlsTabPane.getTabs().contains(addressCoordinateControllerTab))
                    inputControlsTabPane.getTabs().add(addressCoordinateControllerTab);
                inputControlsTabPane.getTabs().remove(sunpowerInputChartTab);
                break;
        }
    }

    private void widthChanged(double newWidth) {
        if (newWidth < 800) {
            if (layoutType != LayoutType.SMALL) {
                changeToSmallLayout();
                layoutType = LayoutType.SMALL;
            }
        } else {
            if (layoutType != LayoutType.LARGE) {
                changeToLargeLayout();
                layoutType = LayoutType.LARGE;
            }
        }
    }

    private void changeToLargeLayout() {
        ScrollPane scrollPane = new ScrollPane(new HBox(leftLayout, rightLayout));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        mainStackpane.getChildren().clear();
        mainStackpane.getChildren().add(scrollPane);
    }

    private void changeToSmallLayout() {
        ScrollPane scrollPane = new ScrollPane(new VBox(leftLayout, rightLayout));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        mainStackpane.getChildren().clear();
        mainStackpane.getChildren().add(scrollPane);
    }

    private void makeSunpowerInputTab() {
        sunpowerInputChartTab = new Tab("Sonneneinstrahlung");
        sunpowerInputChart = new PVWeeklyInputChart(PVInputDataType.SUN_POWER);
        sunpowerInputChart.setMaxHeight(9999);
        sunpowerInputChart.setMinHeight(0);
        sunpowerInputChartTab.setContent(sunpowerInputChart);
        sunpowerInputChartTab.setClosable(false);
    }

    private void makeConsumptionInputTab() {
        consumptionInputChartTab = new Tab("Stromverbrauch");
        consumptionInputChart = new PVWeeklyInputChart(PVInputDataType.POWER_CONSUMPTION);
        consumptionInputChart.setMaxHeight(9999);
        consumptionInputChart.setMinHeight(0);
        consumptionInputChartTab.setContent(consumptionInputChart);
        consumptionInputChartTab.setClosable(false);
    }

    private void makeAddressControlTab() {
        addressCoordinateControllerTab = new Tab("Anlagen-Standort");
        addressCoordinateController = new AddressCoordinateController();
        addressCoordinateController.setPadding(overallInsets);
        addressCoordinateController.setMaxHeight(9999);
        addressCoordinateController.setMinHeight(0);
        addressCoordinateControllerTab.setContent(addressCoordinateController);
        addressCoordinateControllerTab.setClosable(false);
    }

    protected void initEconomicsStage() {
        economicsStage = new Stage();
        economicsStage.setAlwaysOnTop(true);
        economicsStage.setTitle("Wirtschaftlichkeit");
        economicsStage.getIcons().add(ApplicationInfo.APPLICATION_IMAGE);
        economicsStage.setMinHeight(250);
        economicsStage.setMinWidth(300);
        economicsStage.initModality(Modality.WINDOW_MODAL);
    }

    protected void initGeneratorParametersStage() {
        generatorParametersStage = new Stage();
        generatorParametersStage.setAlwaysOnTop(true);
        generatorParametersStage.setTitle("Erweiterte Anlagen-Parameter");
        generatorParametersStage.getIcons().add(ApplicationInfo.APPLICATION_IMAGE);
        generatorParametersStage.setMinHeight(250);
        generatorParametersStage.setMinWidth(300);
        generatorParametersStage.initModality(Modality.WINDOW_MODAL);
    }

    protected void showSolarPanelTableStage() {
        if (solarPanelTableStage == null) {
            solarPanelTableStage = new Stage();
            solarPanelTableStage.setTitle("Photovoltaik-Panel Datenbank");
            solarPanelTableStage.getIcons().add(ApplicationInfo.APPLICATION_IMAGE);
            solarPanelTableStage.setMinHeight(250);
            solarPanelTableStage.setMinWidth(300);
            solarPanelTableStage.initModality(Modality.WINDOW_MODAL);
            solarPanelTableStage.setScene(new Scene(solarPanelTableController, 900, 600));
        }
        solarPanelTableStage.show();
        solarPanelTableStage.toFront();
    }

    private enum LayoutType {
        SMALL,
        LARGE
    }
}
