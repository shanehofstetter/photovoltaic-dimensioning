package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.gui.components.pvcharts.PVInputDataType;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVDay;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVPowerTime;
import ch.shanehofstetter.pvdimension.simulation.dataholder.PVWeek;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PVWeeklyInputChart extends VBox implements PVDayInputLineChart.PVDayInputChartListener {
    static final Logger logger = LogManager.getLogger();

    private final String dailyCheckboxTitle = "Täglich";
    private final String chartTitle = "";
    private final String xAxisTitle = "Tages-Verlauf";
    private final String yAxisTitle = "[Watt]";

    private PVInputDataType pvInputDataType;
    private ListView<String> daysListView;
    private ObservableList<String> daysList;
    private PVWeek pvWeek;
    private PVDayInputLineChart pvDayInputChart;
    private List<PVWeeklyInputChartListener> listeners = new ArrayList<>();
    private CheckBox dailyCheckBox;
    private int selectedDayIndex = 0;
    private boolean dailyMode = true;

    public PVWeeklyInputChart(PVWeek pvWeek, PVInputDataType pvInputDataType) {
        this(pvInputDataType);
        this.pvWeek = pvWeek;
        daySelected(selectedDayIndex);
    }

    public PVWeeklyInputChart(PVInputDataType pvInputDataType) {
        this.pvInputDataType = pvInputDataType;
        HBox hBox = new HBox(1);
        VBox leftBox = new VBox(3);

        daysListView = new ListView<>();
        daysListView.setMaxWidth(100);
        daysListView.setMinWidth(80);
        daysList = FXCollections.observableArrayList();
        generateDaysList();
        daysListView.setItems(daysList);
        daysListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            daySelected(newValue.intValue());
        });

        dailyCheckBox = new CheckBox(dailyCheckboxTitle);
        dailyCheckBox.setSelected(true);
        dailyCheckBox.setOnAction(ev -> dailyCheckBoxChanged());

        leftBox.getChildren().add(dailyCheckBox);
        leftBox.getChildren().add(daysListView);
        hBox.getChildren().add(leftBox);

        pvDayInputChart = new PVDayInputLineChart(chartTitle, xAxisTitle, yAxisTitle, pvInputDataType);
        pvDayInputChart.addListener(this);
        pvDayInputChart.setMinHeight(20);
        pvDayInputChart.setMaxHeight(9999);
        hBox.getChildren().add(pvDayInputChart);
        HBox.setHgrow(pvDayInputChart, Priority.ALWAYS);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setMinHeight(20);
        setMinHeight(20);
        getChildren().add(hBox);
        VBox.setVgrow(hBox, Priority.ALWAYS);
    }

    @Override
    public void dayChanged(PVDay changedDay) {
        logger.trace("Day Changed");
        logger.trace(changedDay);
        if (!dailyMode) {
            applyChangesToWholeWeek(changedDay);
        }
        pvWeekChanged(pvWeek);
    }

    public void addListener(PVWeeklyInputChartListener toAdd) {
        listeners.add(toAdd);
    }

    public void pvWeekChanged(PVWeek pvWeek) {
        for (PVWeeklyInputChartListener listener : listeners) {
            listener.pvWeekChanged(pvWeek);
        }
    }

    public PVWeek getPvWeek() {
        return pvWeek;
    }

    public void setPvWeek(PVWeek pvWeek) {
        this.pvWeek = pvWeek;
        daySelected(selectedDayIndex);
        dailyCheckBox.setSelected(true);
        // needs to be triggered manually
        dailyCheckBoxChanged();
    }

    /**
     * @return the pvDayInputChart
     */
    public PVDayInputLineChart getPvDayInputChart() {
        return pvDayInputChart;
    }

    private void dailyCheckBoxChanged() {
        logger.trace("Checkbox: " + dailyCheckBox.isSelected());

        dailyMode = dailyCheckBox.isSelected();
        if (!dailyMode) {
            //take selected day and apply the values to whole week
            pvDayInputChart.setTitle(pvInputDataType.toString());
            daysListView.setDisable(true);
            dayChanged(pvWeek.getPVDays().get(selectedDayIndex));
        } else {
            updateChartTitle();
            daysListView.setDisable(false);
        }
    }

    private void updateChartTitle() {
        pvDayInputChart.setTitle(pvInputDataType.toString() + " " + daysList.get(selectedDayIndex));
    }

    private void daySelected(int selectedIndex) {
        selectedDayIndex = selectedIndex;
        logger.trace("showing " + daysList.get(selectedIndex));
        updateChartTitle();
        if (pvWeek != null) {
            pvDayInputChart.setPVDay(pvWeek.getPVDays().get(selectedIndex), pvInputDataType);
        }
    }

    private void generateDaysList() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            daysList.add(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.GERMAN));
        }
    }

    private void applyChangesToWholeWeek(PVDay changedDay) {
        for (PVDay pvDay : pvWeek.getPVDays()) {
            if (pvDay != changedDay) {
                switch (pvInputDataType) {
                    case POWER_CONSUMPTION:
                        pvDay.setPowerConsumptions(PVPowerTime.cloneList(changedDay.getPowerConsumptions()));
                        break;
                    case SUN_POWER:
                        pvDay.setSunPowers(PVPowerTime.cloneList(changedDay.getSunPowers()));
                        break;
                }
            }
        }
    }

    public interface PVWeeklyInputChartListener {
        void pvWeekChanged(PVWeek pvWeek);
    }
}
