package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.controllers.EconomicSettingsController;
import ch.shanehofstetter.pvdimension.gui.components.controllers.GeneratorCostSettingController;
import ch.shanehofstetter.pvdimension.gui.components.controllers.PowerPriceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class EconomicsView extends BorderPane {
    protected PowerPriceController powerPriceController;
    protected EconomicSettingsController economicSettingsController;
    protected GeneratorCostSettingController generatorCostSettingController;
    protected Button okButton;

    public EconomicsView() {
        TabPane tabPane = new TabPane();

        Tab powerPriceControllerTab = new Tab("Strom-Preise");
        powerPriceController = new PowerPriceController();
        powerPriceController.setPadding(new Insets(10, 5, 10, 5));
        powerPriceController.setSpacing(5);
        powerPriceControllerTab.setContent(powerPriceController);
        powerPriceControllerTab.setClosable(false);
        tabPane.getTabs().add(powerPriceControllerTab);

        Tab generatorCostControllerTab = new Tab("Anlage-Kosten");
        generatorCostSettingController = new GeneratorCostSettingController();
        generatorCostSettingController.setPadding(new Insets(10, 10, 10, 10));
        generatorCostControllerTab.setClosable(false);
        generatorCostControllerTab.setContent(generatorCostSettingController);
        tabPane.getTabs().add(generatorCostControllerTab);

        Tab economySettingsControllerTab = new Tab("Einstellungen");
        economicSettingsController = new EconomicSettingsController();
        economicSettingsController.setPadding(new Insets(10, 10, 10, 10));
        economySettingsControllerTab.setClosable(false);
        economySettingsControllerTab.setContent(economicSettingsController);
        tabPane.getTabs().add(economySettingsControllerTab);
        setMinWidth(100);
        setMinHeight(150);
        setCenter(tabPane);

        okButton = new Button("Ok");
        okButton.setMinWidth(75);
        okButton.setMaxWidth(75);
        okButton.setDefaultButton(true);
        HBox hBox = new HBox(okButton);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.CENTER_RIGHT);
        setBottom(hBox);
    }
}
