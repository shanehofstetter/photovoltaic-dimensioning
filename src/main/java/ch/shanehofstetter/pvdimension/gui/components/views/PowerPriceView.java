package ch.shanehofstetter.pvdimension.gui.components.views;

import ch.shanehofstetter.pvdimension.gui.components.widgets.DoubleTextField;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Currency;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.views
 */
public class PowerPriceView extends VBox {

    protected DoubleTextField buyingPriceField;
    protected DoubleTextField sellingPriceField;
    protected String buyingPriceTooltipText = "Strom-Kosten im Einkauf [kWh]";
    protected String sellingPriceTooltipText = "Strom-Preis im Verkauf [kWh]";
    protected Currency currency;
    protected Label currencyLabelBuy;
    protected Label currencyLabelSell;

    public PowerPriceView() {
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(5, 5, 5, 5));
        formGrid.setHgap(7);
        formGrid.setVgap(8);

        Label buyingPriceLabel = new Label("Einkauf (kWh):");
        buyingPriceLabel.setTooltip(new Tooltip(buyingPriceTooltipText));
        buyingPriceLabel.setMinWidth(125);

        Label sellingPriceLabel = new Label("Verkauf (kWh):");
        sellingPriceLabel.setTooltip(new Tooltip(sellingPriceTooltipText));
        sellingPriceLabel.setMinWidth(125);

        formGrid.add(buyingPriceLabel, 0, 0);
        formGrid.add(sellingPriceLabel, 0, 1);

        buyingPriceField = new DoubleTextField();
        buyingPriceField.setValue(0);
        buyingPriceField.setTooltip(new Tooltip(buyingPriceTooltipText));
        buyingPriceField.setMaxWidth(75);
        sellingPriceField = new DoubleTextField();
        sellingPriceField.setValue(0);
        sellingPriceField.setTooltip(new Tooltip(sellingPriceTooltipText));
        sellingPriceField.setMaxWidth(75);

        formGrid.add(buyingPriceField, 1, 0);
        formGrid.add(sellingPriceField, 1, 1);

        currencyLabelBuy = new Label("");
        currencyLabelSell = new Label("");
        formGrid.add(currencyLabelBuy, 2, 0);
        formGrid.add(currencyLabelSell, 2, 1);

        getChildren().add(formGrid);
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        currencyLabelBuy.setText(currency.getCurrencyCode());
        currencyLabelSell.setText(currency.getCurrencyCode());
    }
}
