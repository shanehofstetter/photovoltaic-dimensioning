package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.ApplicationInfo;
import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.gui.components.views.HelpView;
import ch.shanehofstetter.pvdimension.gui.components.views.MainPaneView;
import ch.shanehofstetter.pvdimension.gui.components.views.ProgressView;
import ch.shanehofstetter.pvdimension.io.csv.CSVSaver;
import ch.shanehofstetter.pvdimension.io.pdfexport.PVReportSaver;
import ch.shanehofstetter.pvdimension.io.pvdata.PVData;
import ch.shanehofstetter.pvdimension.io.pvdata.PVLoadDialog;
import ch.shanehofstetter.pvdimension.io.pvdata.PVSaveDialog;
import ch.shanehofstetter.pvdimension.irradiationdata.IrradiationDataFetcher;
import ch.shanehofstetter.pvdimension.location.Address;
import ch.shanehofstetter.pvdimension.location.Coordinates;
import ch.shanehofstetter.pvdimension.location.GeoCoordinateRequest;
import ch.shanehofstetter.pvdimension.pvgenerator.Battery;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import ch.shanehofstetter.pvdimension.simulation.*;
import ch.shanehofstetter.pvdimension.simulation.dataholder.*;
import ch.shanehofstetter.pvdimension.sungeodata.SunGeoData;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Month;
import java.util.Optional;


public class MainPaneController extends MainPaneView implements MenubarController.PVMenuBarListener {

    static final Logger logger = LogManager.getLogger();

    protected PVGenerator pvGenerator;
    private SunpowerInputModeController.SunPowerInputMode sunPowerInputMode;
    private PVWeekSimulator pvWeekSimulator;
    private PVYearSimulator pvYearSimulator;
    private SimulationStatus simulationStatus = SimulationStatus.NOT_READY;
    private UserDataStatus userDataStatus = UserDataStatus.UNCHANGED;

    private PVSimulatorListener weekSimulatorListener = new PVSimulatorListener() {
        @Override
        public void noProducedPower() {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warnung");
                alert.setHeaderText("Mit den gegebenen Parametern wird kein Strom produziert!");
                alert.setContentText(
                        "Mindestens ein eingestellter Wirkungsgrad beträgt 0 oder die Anzahl Panels beträgt 0.\n" +
                                "Bitte setzen Sie einen höheren Wert ein."
                );
                alert.show();
            });
        }

        @Override
        public void finishedSimulation(PVSimulationElement pvSimulationElement) {
            Platform.runLater(() -> {
                pvGenerator.getSimulationData().setPvWeek((PVWeek) pvSimulationElement);
                simulationResultView.getPvWeekOverviewChart().setPvWeek((PVWeek) pvSimulationElement);
                simulationResultView.getPvWeeklyResultChart().setPvWeek((PVWeek) pvSimulationElement);
                simulationResultView.getPvWeekTotalBarChart().setPVSimulationResult(pvSimulationElement);
                simulationResultView.getPvWeekTotalEconomicsBarChart().setPVData(pvSimulationElement);
            });
        }

        @Override
        public void progress(int progress, int total) {
            // covered in year simulator listener
        }
    };

    private PVSimulatorListener yearSimulatorListener = new PVSimulatorListener() {
        @Override
        public void noProducedPower() {
            // covered in week simulator listener
        }

        @Override
        public void finishedSimulation(PVSimulationElement pvSimulationElement) {
            Platform.runLater(() -> {
                pvGenerator.getSimulationData().setPvYear((PVYear) pvSimulationElement);
                progressView.closeProgressWindow();
                simulationResultView.showYearOverviewTab(true);
                simulationResultView.getPvYearOverviewLineChart().setPVMonths(((PVYear) pvSimulationElement).getPvMonths());
                simulationResultView.getPvYearTotalBarChart().setPVSimulationResult(pvSimulationElement);
                simulationResultView.getPvYearTotalEconomicsBarChart().setPVData(pvSimulationElement);
                PVAmortizationCalculator amortizationCalculator = new PVAmortizationCalculator((PVYear) pvSimulationElement, pvGenerator);
                PVAmortizationTime pvAmortizationTime = amortizationCalculator.calculate();
                pvGenerator.getSimulationData().setPvAmortizationTime(pvAmortizationTime);
                simulationResultView.getPvAmortisationBarChart().setPVData(pvAmortizationTime);
            });
        }

        @Override
        public void progress(int progress, int total) {
            Platform.runLater(() -> {
                if (progressView == null || !progressView.isShowing()) {
                    progressView = new ProgressView(getScene().getWindow());
                }
                progressView.updateProgress(progress, total);
            });
        }
    };

    public MainPaneController(Stage stage) {
        super();
        stage.setOnCloseRequest(this::onCloseEvent);
        pvGenerator = new PVGenerator(solarPanelController.getSolarPanelField(), batteryController.getBattery());
        consumptionInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        sunpowerInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        pvWeekSimulator = new PVWeekSimulator(pvGenerator);
        pvWeekSimulator.addListener(weekSimulatorListener);
        pvYearSimulator = new PVYearSimulator(pvGenerator);
        pvYearSimulator.addListener(yearSimulatorListener);
        registerEventHandlers();
        initEconomicsStage();
        initGeneratorParametersStage();
        simulationStatus = SimulationStatus.READY;
        simulate();
    }

    @Override
    public void changedInputMode(SunpowerInputModeController.SunPowerInputMode sunPowerInputMode) {
        super.changedInputMode(sunPowerInputMode);
        this.sunPowerInputMode = sunPowerInputMode;
        if (pvGenerator == null) {
            return;
        }
        switch (sunPowerInputMode) {
            case MANUAL:
                pvGenerator.getSimulationParameters().setSunDataMode(SunDataMode.LOCAL);
                simulate();
                break;
            case AUTOMATIC:
                pvGenerator.getSimulationParameters().setSunDataMode(SunDataMode.WEBSERVICE);
                break;
        }
        setDirty();
    }

    @Override
    public void aboutPressed() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(ApplicationInfo.APPLICATION_TITLE_SHORT);
        alert.setHeaderText(ApplicationInfo.APPLICATION_TITLE_LONG + "\n" + ApplicationInfo.APPLICATION_DETAIL);
        alert.setContentText(ApplicationInfo.APPLICATION_ABOUT_TEXT);
        alert.setGraphic(new ImageView(ApplicationInfo.APPLICATION_IMAGE));
        alert.showAndWait();
    }

    @Override
    public void saveFilePressed() {
        PVSaveDialog pvSaveDialog = new PVSaveDialog();
        pvSaveDialog.addListener(() -> userDataStatus = UserDataStatus.UNCHANGED);
        pvSaveDialog.save(new PVData(pvGenerator, sunPowerInputMode), getScene().getWindow());
    }

    @Override
    public void exportPDFPressed() {
        PVReportSaver pvReportSaver = new PVReportSaver();
        pvReportSaver.save(new PVData(pvGenerator), getScene().getWindow());
    }

    @Override
    public void exportCSVPressed() {
        CSVSaver csvSaver = new CSVSaver();
        csvSaver.save(pvGenerator, this.getScene().getWindow());
    }

    @Override
    public void loadFilePressed() {
        PVLoadDialog pvLoadDialog = new PVLoadDialog();
        pvLoadDialog.addListener(pvData -> {
            setPVData(pvData);
            userDataStatus = UserDataStatus.UNCHANGED;
        });
        pvLoadDialog.load(getScene().getWindow());
    }

    @Override
    public void closeApplicationPressed() {
        boolean close = true;
        if (userDataStatus == UserDataStatus.DIRTY) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Projekt wurde geändert");
            alert.setHeaderText("Wenn Sie das Programm beenden gehen alle nicht gespeicherten Daten verloren.");
            alert.setContentText("Wirklich beenden?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) {
                close = false;
            }
        }
        if (close) {
            ((Stage) this.getScene().getWindow()).close();
            Platform.exit();
        }
    }

    @Override
    public void resetDataPressed() {
        resetUserData();
    }

    @Override
    public void economicsPressed() {
        economicsStage.showAndWait();
    }

    @Override
    public void advancedSettingsPressed() {
        generatorParametersStage.showAndWait();
    }

    @Override
    public void solarPanelDatabasePressed() {
        showSolarPanelTableStage();
    }

    @Override
    public void helpPressed() {
        HelpView help = new HelpView();
        help.showAndWait();
    }

    /**
     * register the eventhandlers on gui elements
     */
    private void registerEventHandlers() {
        solarPanelController.addListener(this::solarPanelFieldChanged);
        addressCoordinateController.getAddressController().addListener(this::addressChanged);
        batteryController.addListener(this::batteryChanged);
        sunpowerInputChart.addListener(this::sunPowerInputChanged);
        consumptionInputChart.addListener(this::consumptionInputChanged);
        simulationDataController.addMonthListener(this::monthChanged);
        menuBar.addListener(this);
        solarPanelTableController.addListener(this::solarPanelChosen);
    }

    /**
     * eventhandler for solarpanel table controller
     *
     * @param solarPanel chosen solarpanel
     */
    private void solarPanelChosen(SolarPanel solarPanel) {
        logger.info("solarPanelChosen: " + solarPanel);
        solarPanelController.setSolarPanel(solarPanel);
        simulate();
    }

    /**
     * eventhandler for simulationdata controller
     * @param month selected month
     */
    private void monthChanged(Month month) {
        pvGenerator.getSimulationParameters().setSimulatingMonth(month);
        simulate();
    }

    /**
     * run simulation for given user-data<br>
     * Starts the simulator in a new thread so that the ui doesn't freeze if simulation takes longer<br>
     * will cancel if not ready
     */
    private void simulate() {
        if (simulationStatus == SimulationStatus.NOT_READY) {
            logger.warn("abort simulation, not ready");
            return;
        }

        pvGenerator.setSolarPanelField(solarPanelController.getSolarPanelField());
        new Thread(() -> {
            try {
                if (pvYearSimulator.isSimulationActive() || pvWeekSimulator.isSimulationActive()) {
                    logger.warn("aborting simulation, simulation is active");
                    return;
                }
                logger.debug("simulating with mode: " + pvGenerator.getSimulationParameters());
                pvYearSimulator.simulateYear();
                // Simulation of single week needs to be done afterwards because yearly simulator alters the PVWeek,
                // results of last simulated week (in december) will be present in the pvWeek object.
                // This shows that the pvWeek data structure is not perfect (user-given data is mixed with simulation results)
                pvWeekSimulator.simulateWeek(pvGenerator.getSimulationParameters());
            } catch (IrradiationDataFetcher.IrradiationDataFetchException e) {
                logger.error("Error during simulation, IrradiationDataFetchException", e);
                showErrorAlert("Fehler beim laden der Sonnendaten", "Beim laden der Wetterdaten ist ein Fehler aufgetreten.\n" +
                        "Bitte tragen Sie im Reiter «Anlagen-Standort» eine korrekte, europäische Adresse ein und bestätigen Sie mit «Ok».", "Fehler:" + e.getMessage());
            } catch (MissingDataException e) {
                logger.error("Error during simulation, MissingDataException", e);
                showErrorAlert("Fehler", "Während der Berechnungen ist ein Fehler aufgetreten:", e.getMessage());
            } catch (SunGeoData.LocalSunDataLoadException e) {
                logger.error("Error during simulation, LocalSunDataLoadException", e);
                showErrorAlert("Fehler beim laden der Sonnenbahnen", "Während der Berechnungen ist ein Fehler aufgetreten:", "Fehler:" + e.getMessage());
            } catch (Exception e) {
                logger.error("Unknown exception during simulation", e);
                showErrorAlert("Fehler", "Während der Berechnungen ist ein Fehler aufgetreten:", "Fehler:" + e.getMessage());
            } finally {
                pvYearSimulator.setSimulationActive(false);
                pvWeekSimulator.setSimulationActive(false);
            }
        }).start();
    }

    /**
     * shows a messagebox with the given content
     * @param title title of the box
     * @param header header of the box
     * @param content content of the box
     */
    private void showErrorAlert(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    /**
     * eventhandler for consumption input chart
     * @param pvWeek the changed pvweek (reference may be the same)
     */
    private void consumptionInputChanged(PVWeek pvWeek) {
        simulate();
        setDirty();
    }

    /**
     * eventhandler for manual sunpower input
     * @param pvWeek the changed pvweek (reference may be the same)
     */
    private void sunPowerInputChanged(PVWeek pvWeek) {
        if (sunPowerInputMode == SunpowerInputModeController.SunPowerInputMode.AUTOMATIC) {
            return;
        }
        simulate();
        setDirty();
    }

    /**
     * eventhandler for battery controller
     * @param battery changed battery
     */
    private void batteryChanged(Battery battery) {
        simulate();
        pvGenerator.setBattery(battery);
        setDirty();
        logger.debug(battery);
    }

    /**
     * eventhandler for address controller
     * @param address changed address
     */
    private void addressChanged(Address address) {
        try {
            Coordinates coordinates = GeoCoordinateRequest.getCoordinatesForAddress(address);
            addressCoordinateController.getCoordinateController().setCoordinates(coordinates);
            pvGenerator.setCoordinates(coordinates);
            pvGenerator.setAddress(address);
        } catch (Exception e) {
            logger.error("Retrieving coordinates for: " + address, e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Abfragen der Koordinaten:");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        simulate();
        setDirty();
    }

    /**
     * eventhandler for solarpanelfield controller
     * @param solarPanelField changed solarpanelfield
     */
    private void solarPanelFieldChanged(SolarPanelField solarPanelField) {
        simulate();
        pvGenerator.setSolarPanelField(solarPanelField);
        setDirty();
        logger.debug(solarPanelField);
    }

    /**
     * resets all given userdata, resets the application to default state
     */
    private void resetUserData() {
        simulationStatus = SimulationStatus.NOT_READY;
        this.solarPanelController.resetSolarpanelField();
        this.addressCoordinateController.reset();
        this.batteryController.reset();
        pvGenerator.setSimulationData(new PVSimulationData());
        consumptionInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        sunpowerInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        pvWeekSimulator.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        pvYearSimulator.setPvGenerator(pvGenerator);
        simulationDataController.setMonth(PVWeekSimulator.getDefaultSimulatingMonth());
        pvGenerator.getSimulationParameters().setSimulatingMonth(PVWeekSimulator.getDefaultSimulatingMonth());
        sunpowerInputModeController.setMode(SunpowerInputModeController.SunPowerInputMode.MANUAL);
        simulationStatus = SimulationStatus.READY;
        simulate();
        userDataStatus = UserDataStatus.UNCHANGED;
    }

    /**
     * set the pvdata containing the pvgenerator<br>
     * this object must contain the entire user-given data to display, it sets all texts and values in the ui<br>
     * call this from a file-loader class<br>
     * @param pvData pvData
     */
    private void setPVData(PVData pvData) {
        simulationStatus = SimulationStatus.NOT_READY;
        this.pvGenerator = pvData.getPvGenerator();
        this.addressCoordinateController.getAddressController().setAddress(pvGenerator.getAddress());
        this.addressCoordinateController.getCoordinateController().setCoordinates(pvGenerator.getCoordinates());
        this.solarPanelController.setSolarPanelField(pvGenerator.getSolarPanelField());
        this.batteryController.setBattery(pvGenerator.getBattery());
        sunpowerInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        consumptionInputChart.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        pvWeekSimulator.setPvWeek(pvGenerator.getSimulationData().getPvWeek());
        pvWeekSimulator.setPvGenerator(pvGenerator);
        pvYearSimulator.setPvGenerator(pvGenerator);
        Economy.setCurrencyByCode(pvGenerator.getCurrencyCode());
        if (sunPowerInputMode != pvData.getSunPowerInputMode()) {
            changedInputMode(pvData.getSunPowerInputMode());
            sunpowerInputModeController.setMode(pvData.getSunPowerInputMode());
        }
        simulationStatus = SimulationStatus.READY;
        simulate();
    }

    /**
     * set the dirty bit if user changed a setting<br>
     */
    private void setDirty() {
        userDataStatus = UserDataStatus.DIRTY;
    }

    private void onCloseEvent(WindowEvent event) {
        event.consume();
        closeApplicationPressed();
    }

    @Override
    protected void initEconomicsStage() {
        super.initEconomicsStage();
        EconomicsController economicsController = new EconomicsController(pvGenerator);
        economicsController.addListener(() -> {
            setDirty();
            simulate();
        });
        economicsStage.setScene(new Scene(economicsController, 500, 300));
        Platform.runLater(() -> economicsStage.initOwner(getScene().getWindow()));
    }

    @Override
    protected void initGeneratorParametersStage() {
        super.initGeneratorParametersStage();
        GeneratorParametersController controller = new GeneratorParametersController(pvGenerator);
        controller.addListener(() -> {
            setDirty();
            simulate();
        });
        generatorParametersStage.setScene(new Scene(controller, 500, 300));
        Platform.runLater(() -> generatorParametersStage.initOwner(getScene().getWindow()));
    }

}
