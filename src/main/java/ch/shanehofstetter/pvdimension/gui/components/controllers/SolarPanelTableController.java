package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.gui.components.views.SolarPanelTableView;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelDatabase;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;


public class SolarPanelTableController extends SolarPanelTableView {

    private ArrayList<SolarPanelTableListener> listeners = new ArrayList<>();

    public SolarPanelTableController() {
        table.setRowFactory(tableView -> {
            final TableRow<SolarPanel> row = new TableRow<>();
            final ContextMenu contextMenu = new ContextMenu();
            final MenuItem submitMenuItem = new MenuItem("Solar-Panel übernehmen");
            submitMenuItem.setOnAction(event -> submitSolarPanel(row.getItem()));
            contextMenu.getItems().add(submitMenuItem);
            // Set context menu on row, but use a binding to make it only show for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );
            final EventHandler<MouseEvent> handler = event -> {
                if (event.getClickCount() > 1)
                    submitSolarPanel(row.getItem());
            };
            row.onMousePressedProperty().bind(Bindings.when(row.emptyProperty()).then((EventHandler<MouseEvent>) null).otherwise(handler));
            return row;
        });

        SolarPanelDatabase.load();
        table.setItems(SolarPanelDatabase.getSolarPanels());
    }

    public void addListener(SolarPanelTableListener listener) {
        listeners.add(listener);
    }

    private void submitSolarPanel(SolarPanel solarPanel) {
        for (SolarPanelTableListener listener : listeners) {
            listener.solarPanelChosen(solarPanel);
        }
    }

    public interface SolarPanelTableListener {
        void solarPanelChosen(SolarPanel solarPanel);
    }
}
