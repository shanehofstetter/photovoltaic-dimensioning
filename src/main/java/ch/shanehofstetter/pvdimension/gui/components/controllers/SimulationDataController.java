package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.SimulationDataView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.controllers
 */
public class SimulationDataController extends SimulationDataView {

    static final Logger logger = LogManager.getLogger();
    private ArrayList<SimulationDataControlMonthListener> monthListeners = new ArrayList<>();

    public SimulationDataController() {
        super();
        monthCombo.setOnAction(event -> monthChanged());
    }

    private void monthChanged() {
        Month month = Month.of(monthCombo.getSelectionModel().getSelectedIndex() + 1);
        logger.debug(month.getDisplayName(TextStyle.FULL, Locale.GERMAN));
        for (SimulationDataControlMonthListener monthListener : monthListeners) {
            monthListener.monthChanged(month);
        }
    }

    public void setMonth(Month month) {
        int index = month.getValue() - 1;
        monthCombo.getSelectionModel().select(index);
    }

    public void addMonthListener(SimulationDataControlMonthListener monthListener) {
        this.monthListeners.add(monthListener);
    }

    public interface SimulationDataControlMonthListener {
        void monthChanged(Month month);
    }
}
