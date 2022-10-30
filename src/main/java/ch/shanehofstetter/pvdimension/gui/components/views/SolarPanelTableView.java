package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class SolarPanelTableView extends BorderPane {

    protected TableView<SolarPanel> table = new TableView<>();

    public SolarPanelTableView() {
        table.setEditable(false);

        TableColumn manufacturerColumn = new TableColumn("Hersteller");
        manufacturerColumn.setMinWidth(200);
        //noinspection unchecked
        manufacturerColumn.setCellValueFactory(
                new PropertyValueFactory<SolarPanel, String>("manufacturer"));

        TableColumn typeColumn = new TableColumn("Typ");
        typeColumn.setMinWidth(150);
        //noinspection unchecked
        typeColumn.setCellValueFactory(
                new PropertyValueFactory<SolarPanel, String>("typeName"));

        TableColumn powerColumn = new TableColumn("Nennleistung [W]");
        powerColumn.setMinWidth(150);
        //noinspection unchecked
        powerColumn.setCellValueFactory(
                new PropertyValueFactory<SolarPanel, String>("power"));

        TableColumn efficiencyColumn = new TableColumn("Wirkungsgrad [%]");
        efficiencyColumn.setMinWidth(150);
        //noinspection unchecked
        efficiencyColumn.setCellValueFactory(
                new PropertyValueFactory<SolarPanel, String>("efficiencyPercentage"));

        TableColumn sizeColumn = new TableColumn("Grösse [m²]");
        sizeColumn.setMinWidth(150);
        //noinspection unchecked
        sizeColumn.setCellValueFactory(
                new PropertyValueFactory<SolarPanel, String>("size"));

        table.getColumns().addAll(manufacturerColumn, typeColumn, powerColumn, efficiencyColumn, sizeColumn);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.getChildren().addAll(table);
        VBox.setVgrow(table, Priority.ALWAYS);
        setCenter(vbox);
    }
}
