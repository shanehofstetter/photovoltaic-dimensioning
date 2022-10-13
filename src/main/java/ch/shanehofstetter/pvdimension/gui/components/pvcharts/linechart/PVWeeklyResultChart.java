package ch.shanehofstetter.pvdimension.gui.components.pvcharts.linechart;

import ch.shanehofstetter.pvdimension.simulation.dataholder.PVWeek;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.pvcharts
 */
public class PVWeeklyResultChart extends HBox {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PVWeeklyResultChart.class);
    private ListView<String> daysListView;
    private ObservableList<String> listItems;
    private PVWeek pvWeek;
    private PVDayResultLineChart pvDayResultChart;

    public PVWeeklyResultChart(PVWeek pvWeek) {
        this();
        setPvWeek(pvWeek);
    }

    public PVWeeklyResultChart() {
        setSpacing(1);
        daysListView = new ListView<>();
        daysListView.setMaxWidth(100);
        daysListView.setMinWidth(75);
        listItems = FXCollections.observableArrayList();
        generateListItems();
        daysListView.setItems(listItems);
        daysListView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 1) {
                int selectedIndex = daysListView.getSelectionModel().getSelectedIndex();
                itemSelected(selectedIndex);
            }
        });

        getChildren().add(daysListView);

        pvDayResultChart = new PVDayResultLineChart("");
        getChildren().add(pvDayResultChart);
        HBox.setHgrow(pvDayResultChart, Priority.ALWAYS);
        setFillHeight(true);
    }

    private void itemSelected(int selectedIndex) {
        if (listItems.size() <= selectedIndex) {
            return;
        }
        logger.debug("showing " + listItems.get(selectedIndex));
        pvDayResultChart.setTitle("Simulation " + listItems.get(selectedIndex));
        if (pvWeek != null) {
            pvDayResultChart.setPVDay(pvWeek.getPVDays().get(selectedIndex));
        }
    }

    private void generateListItems() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            listItems.add(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.GERMAN));
        }
    }

    public PVWeek getPvWeek() {
        return pvWeek;
    }

    public void setPvWeek(PVWeek pvWeek) {
        this.pvWeek = pvWeek;
        itemSelected(0);
    }
}
