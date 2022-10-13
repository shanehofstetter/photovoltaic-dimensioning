package ch.shanehofstetter.pvdimension.gui.components.controllers;

import ch.shanehofstetter.pvdimension.economy.Economy;
import ch.shanehofstetter.pvdimension.economy.PowerPrice;
import ch.shanehofstetter.pvdimension.gui.components.views.PowerPriceView;
import javafx.event.ActionEvent;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;

/**
 * @author Shane Hofstetter : shane.hofstetter@gmail.com<br>
 * ch.shanehofstetter.pvdimension.gui.components.controllers
 */
public class PowerPriceController extends PowerPriceView {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(PowerPriceController.class);
    private PowerPrice powerPrice;
    private ArrayList<PowerPriceControlListener> listeners = new ArrayList<>();

    public PowerPriceController() {
        this(new PowerPrice());
    }

    public PowerPriceController(PowerPrice powerPrice) {
        this(powerPrice, Economy.getCurrency());
    }

    public PowerPriceController(PowerPrice powerPrice, Currency currency) {
        this.powerPrice = powerPrice;
        setCurrency(currency);
        setValues();
        buyingPriceField.setOnAction(this::valueChanged);
        sellingPriceField.setOnAction(this::valueChanged);
        Economy.addListener(this::currencyChanged);
    }

    private void currencyChanged(Currency currency) {
        setCurrency(currency);
    }

    private void valueChanged(ActionEvent actionEvent) {
        this.powerPrice.setPurchaseKwhPrice(BigDecimal.valueOf(buyingPriceField.getValue()));
        this.powerPrice.setSellingKwhPrice(BigDecimal.valueOf(sellingPriceField.getValue()));
        logger.debug("Buying Price changed to: " + powerPrice.getPurchaseKwhPrice().doubleValue()
                + " Selling Price changed to: " + powerPrice.getSellingKwhPrice().doubleValue());
        powerPriceChanged();
    }

    public void resetPowerPrice() {
        powerPrice.init();
    }

    private void setValues() {
        buyingPriceField.setValue(powerPrice.getPurchaseKwhPrice().doubleValue());
        sellingPriceField.setValue(powerPrice.getSellingKwhPrice().doubleValue());
    }

    public void submitChanges() {
        if (this.powerPrice.getPurchaseKwhPrice().doubleValue() != buyingPriceField.getValue()) {
            valueChanged(null);
        }
        if (this.powerPrice.getSellingKwhPrice().doubleValue() != sellingPriceField.getValue()) {
            valueChanged(null);
        }
    }

    public void addListener(PowerPriceControlListener listener) {
        this.listeners.add(listener);
    }

    private void powerPriceChanged() {
        for (PowerPriceControlListener listener : listeners) {
            listener.changed(powerPrice);
        }
    }

    public interface PowerPriceControlListener {
        void changed(PowerPrice powerPrice);
    }
}
